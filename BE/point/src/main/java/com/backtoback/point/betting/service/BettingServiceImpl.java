package com.backtoback.point.betting.service;

import com.backtoback.point.betting.domain.Betting;
import com.backtoback.point.betting.dto.request.BettingInfoReq;
import com.backtoback.point.betting.dto.response.BettingResultRes;
import com.backtoback.point.betting.repository.BettingRepository;
import com.backtoback.point.common.exception.business.*;
import com.backtoback.point.game.domain.Game;
import com.backtoback.point.game.domain.GameActiveType;
import com.backtoback.point.game.repository.GameRepository;
import com.backtoback.point.member.domain.Member;
import com.backtoback.point.member.repository.MemberRepository;
import com.backtoback.point.team.domain.Team;
import com.backtoback.point.team.repository.TeamRepository;
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

    final RedisTemplate<String, Integer> redisTemplate;

    final BettingRepository bettingRepository;

    final GameRepository gameRepository;
    final TeamRepository teamRepository;
    final MemberRepository memberRepository;

    // [베팅 시작 전] ////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void readyToStartBetting(){

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        // *Question* 현재 날짜인 경기 목록 불러오기
        List<Game> games = getListByGames(String.valueOf(LocalDate.now(ZoneId.of("Asia/Seoul"))));

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

        Member member = getMember(memberSeq);
        // 베팅 가능한 포인트인지 확인
        if(bettingInfoReq.getBettingPoint() > member.getPoint()) throw new PointLackException(POINT_LACK_ERROR);
        // [betting]
        createBettingLog(member, bettingInfoReq);
        // [Redis]
        updateRedisLog(bettingInfoReq);
    }

    @Override
    public void createBettingLog(Member member, BettingInfoReq bettingInfoReq) {

        Betting betting = Betting.builder()
                .bettingPoint(bettingInfoReq.getBettingPoint())
                .game(getGame(bettingInfoReq.getGameSeq()))
                .team(getTeam(bettingInfoReq.getTeamSeq()))
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

        if(count == null || point == null) throw new RedisNotFoundException(REDIS_NOT_FOUND);

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

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        Game game = getGame(gameSeq);

        System.out.println(game.getGameActiveType());

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

        if(homeCount == null || awayCount == null) throw new RedisNotFoundException(REDIS_NOT_FOUND);

        return Math.round(homeCount / (double)(homeCount + awayCount) * 100);
    }

    @Override
    public Long calculateDivdends(Betting betting, Long homeSeq, Long awaySeq, String key) {

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        Integer homePoint = valueOperations.get(key + homeSeq + ":point");
        Integer awayPoint  = valueOperations.get(key + awaySeq + ":point");

        if(homePoint == null || awayPoint == null) throw new RedisNotFoundException(REDIS_NOT_FOUND);

//        System.out.println("### calculateHomeRate ######################################################################");
//        System.out.println("HOMEPOINT : " + homePoint);
//        System.out.println("AWAYPOINT : " + awayPoint);

        Long bettingSeq = betting.getTeam().getTeamSeq();
        Integer bettingPoint = betting.getBettingPoint();

        if(bettingSeq.equals(homeSeq)) return Math.round(awayPoint * ((double)bettingPoint/homePoint) * (9/10.0)) + bettingPoint;
        else return Math.round(homePoint * ((double)bettingPoint/awayPoint) * (9/10.0)) + bettingPoint;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Game> getListByGames(String date) {
        return gameRepository.getAllGameByDate(date);
    }

    public Member getMember(Long memberSeq) {
        return memberRepository.findById(memberSeq).orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
    }

    public Game getGame(Long gameSeq) {
        return gameRepository.findById(gameSeq).orElseThrow(() -> new EntityNotFoundException(GAME_NOT_FOUND));
    }

    public Team getTeam(Long teamSeq) {
        return teamRepository.findById(teamSeq).orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }

    public Betting getBettingByMemberGame(Long memberSeq, Long gameSeq) {
        return bettingRepository.findByMemberAndGame(getMember(memberSeq), getGame(gameSeq))
                .orElseThrow(() -> new EntityNotFoundException(BETTING_NOT_FOUND));
    }

}
