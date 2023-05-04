package com.backtoback.point.betting.service;

import com.backtoback.point.betting.domain.Betting;
import com.backtoback.point.betting.dto.request.BettingInfoReq;
import com.backtoback.point.betting.dto.response.BettingResultRes;
import com.backtoback.point.betting.dto.request.KafkaReq;
import com.backtoback.point.betting.repository.BettingRepository;
import com.backtoback.point.common.exception.business.*;
import com.backtoback.point.game.domain.Game;
import com.backtoback.point.game.domain.GameActiveType;
import com.backtoback.point.game.service.GameService;
import com.backtoback.point.member.domain.Member;
import com.backtoback.point.member.service.MemberService;
import com.backtoback.point.pointlog.service.PointLogService;
import com.backtoback.point.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.backtoback.point.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BettingServiceImpl implements BettingService{

    private final RedisTemplate<String, Integer> redisTemplate;

    private final MemberService memberService;
    private final GameService gameService;
    private final TeamService teamService;
    private final PointLogService pointLogService;

    private final BettingRepository bettingRepository;

    // [베팅 시작 전] ////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void readyToStartBetting(){

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        // *Question* 현재 날짜인 경기 목록 불러오기
        List<Game> games = gameService.getListByGames(String.valueOf(LocalDate.now(ZoneId.of("Asia/Seoul"))));

        String homeKey, awayKey;

        // [Redis] 각 팀의 베팅수 0 저장
        for(Game game : games) {

            homeKey = "game:" + game.getGameSeq() + ":team:" + game.getHomeTeam().getTeamSeq();
            awayKey = "game:" + game.getGameSeq() + ":team:" + game.getAwayTeam().getTeamSeq();

            // [Redis] 각 팀의 베팅수 0 저장
            valueOperations.set(homeKey + ":count", 0);
            valueOperations.set(awayKey + ":count", 0);

            // [Redis] 각 팀의 총 베팅 포인트 0 저장
            valueOperations.set(homeKey + ":point", 0);
            valueOperations.set(awayKey + ":point", 0);

//            System.out.println("### readyToStartBetting ##############################################################");
//            System.out.println(homeKey + ":count = " + valueOperations.get(homeKey + ":count"));
//            System.out.println(awayKey + ":count = " + valueOperations.get(awayKey + ":count"));
//            System.out.println(homeKey + ":point = " + valueOperations.get(homeKey + ":point"));
//            System.out.println(awayKey + ":point = " + valueOperations.get(awayKey + ":point"));

            // [Redis] 만료기한
            redisTemplate.expire(homeKey + ":count", 24, TimeUnit.HOURS);
            redisTemplate.expire(awayKey + ":count", 24, TimeUnit.HOURS);
            redisTemplate.expire(homeKey + ":point", 24, TimeUnit.HOURS);
            redisTemplate.expire(awayKey + ":point", 24, TimeUnit.HOURS);
        }
    }

    // [베팅 시작] //////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void startBetting(Long memberSeq, BettingInfoReq bettingInfoReq) {

        Member member = memberService.getMember(memberSeq);
        // 베팅 가능한 포인트인지 확인
        if(bettingInfoReq.getBettingPoint() > member.getPoint()) throw new PointLackException(POINT_LACK_ERROR);
        // [betting]
        createBettingLog(member, bettingInfoReq);
        // [member]
        memberService.updateByBetting(memberSeq, bettingInfoReq.getBettingPoint());
        // [Redis]
        updateRedisLog(bettingInfoReq);
        // [pointLog]
        pointLogService.createMinusPointLog(memberSeq, bettingInfoReq.getBettingPoint());
    }

    @Override
    public void createBettingLog(Member member, BettingInfoReq bettingInfoReq) {

        Betting betting = Betting.builder()
                .bettingPoint(bettingInfoReq.getBettingPoint())
                .game(gameService.getGame(bettingInfoReq.getGameSeq()))
                .team(teamService.getTeam(bettingInfoReq.getTeamSeq()))
                .member(member)
                .build();

        try {
            bettingRepository.save(betting);
        } catch (Exception e) {
            throw new BettingAlreadyExistException(BETTING_ALREADY_EXIST);
        }
    }

    @Override
    public void updateRedisLog(BettingInfoReq bettingInfoReq) {

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        String key = "game:" + bettingInfoReq.getGameSeq() + ":team:" + bettingInfoReq.getTeamSeq();

        Integer count = valueOperations.get(key + ":count");
        Integer point = valueOperations.get(key + ":point");

        if(count == null || point == null) throw new RedisNotFoundException(
                "해당하는 key 정보가 Redis에 존재하지 않습니다.", REDIS_NOT_FOUND);

        valueOperations.set(key + ":count", count + 1);
        valueOperations.set(key + ":point", point + bettingInfoReq.getBettingPoint());

//        System.out.println("### updateRedisLog #######################################################################");
//        System.out.println("betting " + bettingInfoReq.getTeamSeq() + " team");
//        System.out.println(valueOperations.get(key + ":count"));
//        System.out.println(valueOperations.get(key + ":point"));
    }

    // [베팅 종료] ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public BettingResultRes anticipateBettingResult(Long memberSeq, Long gameSeq) {

        Game game = gameService.getGame(gameSeq);

        if(game.getGameActiveType().equals(GameActiveType.BEFORE_GAME))
            throw new GameNotYetStartException(GAME_NOT_YET_START);

        Long homeSeq = game.getHomeTeam().getTeamSeq();
        Long awaySeq = game.getAwayTeam().getTeamSeq();

        // [Redis Key]
        String redisKey = "game:" + gameSeq + ":team:";

        // [베팅률]
        Long homePercent = calculateHomeRate(homeSeq, awaySeq, redisKey);
        long awayPercent = 100 - homePercent;

        // [예상 배당금]
        Long divdends = calculateDivdends(getBettingByMemberGame(memberSeq, gameSeq), homeSeq, awaySeq, redisKey);

        return BettingResultRes.builder()
                .homeSeq(homeSeq)
                .homePercent(homePercent)
                .awaySeq(awaySeq)
                .awayPercent(awayPercent)
                .divdends(divdends)
                .build();
    }

    @Override
    public Long calculateHomeRate(Long homeSeq, Long awaySeq, String key) {

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        Integer homeCount = valueOperations.get(key + homeSeq + ":count");
        Integer awayCount = valueOperations.get(key + awaySeq + ":count");

//        System.out.println("### calculateHomeRate ######################################################################");
//        System.out.println("HOMECOUNT : " + homeCount);
//        System.out.println("AWAYCOUNT : " + awayCount);

        if(homeCount == null || awayCount == null) throw new RedisNotFoundException(
                "해당하는 key 정보가 Redis에 존재하지 않습니다.", REDIS_NOT_FOUND);

        return Math.round(homeCount / (double)(homeCount + awayCount) * 100);
    }

    @Override
    public Long calculateDivdends(Betting betting, Long homeSeq, Long awaySeq, String key) {

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        Integer homePoint = valueOperations.get(key + homeSeq + ":point");
        Integer awayPoint  = valueOperations.get(key + awaySeq + ":point");

        if(homePoint == null || awayPoint == null) throw new RedisNotFoundException(
                "해당하는 key 정보가 Redis에 존재하지 않습니다.", REDIS_NOT_FOUND);

//        System.out.println("### calculateHomeRate ######################################################################");
//        System.out.println("HOMEPOINT : " + homePoint);
//        System.out.println("AWAYPOINT : " + awayPoint);

        Long bettingSeq = betting.getTeam().getTeamSeq();
        Integer bettingPoint = betting.getBettingPoint();

        if(bettingSeq.equals(homeSeq))
            return Math.round(awayPoint * ((double)bettingPoint/homePoint) * (9/10.0)) + bettingPoint;
        else
            return Math.round(homePoint * ((double)bettingPoint/awayPoint) * (9/10.0)) + bettingPoint;
    }

    // [베팅 결과] ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void getBettingResult(KafkaReq kafkaRes) {

        Game game = gameService.getGame(kafkaRes.getGameSeq());

        // 경기 결과 가져오기 - 승리팀 Sequence ID 조회
        if(game.getWinTeam() == null) throw new ResultNotFoundException(
                "경기 결과를 DB에서 찾을 수 없습니다. ", RESULT_NOT_FOUND);
        Long winSeq = game.getWinTeam().getTeamSeq();

        // 해당 경기에 베팅한 유저 목록 조회
        List<Betting> bettings = getBettingByGame(game);

        Long gameSeq, homeSeq, awaySeq;

        for (Betting betting : bettings) {

            if(!betting.getTeam().getTeamSeq().equals(winSeq)) continue;

            gameSeq = game.getGameSeq();
            homeSeq = game.getHomeTeam().getTeamSeq();
            awaySeq = game.getAwayTeam().getTeamSeq();

            Integer resultPoint = calculateDivdends(betting, homeSeq, awaySeq, "game:" + gameSeq + ":team:").intValue();

            memberService.updateByBettingResult(betting.getMember().getMemberSeq(), resultPoint);
            pointLogService.createPlusPointLog(betting.getMember().getMemberSeq(), resultPoint);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Betting getBettingByMemberGame(Long memberSeq, Long gameSeq) {
        return bettingRepository.findByMemberAndGame(memberService.getMember(memberSeq), gameService.getGame(gameSeq))
                .orElseThrow(() -> new EntityNotFoundException(
                        "해당하는 베팅 ID가 존재하지 않습니다.", ENTITY_NOT_FOUND));
    }

    public List<Betting> getBettingByGame(Game game) {
        return bettingRepository.findByGame(game);
    }

}
