package com.backtoback.point.betting.service;

import com.backtoback.point.betting.domain.Betting;
import com.backtoback.point.betting.dto.request.BettingInfoReq;
import com.backtoback.point.betting.repository.BettingRepository;
import com.backtoback.point.common.exception.business.EntityNotFoundException;
import com.backtoback.point.common.exception.business.PointLackException;
import com.backtoback.point.common.exception.business.RedisNotFoundException;
import com.backtoback.point.game.domain.Game;
import com.backtoback.point.game.repository.GameRepository;
import com.backtoback.point.member.domain.Member;
import com.backtoback.point.member.repository.MemberRepository;
import com.backtoback.point.team.domain.Team;
import com.backtoback.point.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Override
    public void readyToStartBetting(){

        // 현재 날짜인 경기 목록 불러오기
        String nowDate = String.valueOf(LocalDate.now(ZoneId.of("Asia/Seoul")));     // "2023-04-30"
        // *Question*
        List<Game> games = getListByGames(nowDate);

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        Long gameId, homeId, awayId;
        String homeKey, awayKey;

        // [Redis] 각 팀의 베팅수 0 저장
        for(Game game : games) {

            gameId = game.getGameSeq();
            homeId = game.getHomeTeam().getTeamSeq();
            awayId = game.getAwayTeam().getTeamSeq();

            homeKey = "game:" + gameId + ":team:" + homeId;
            awayKey = "game:" + gameId + ":team:" + awayId;

            // [Redis] 각 팀의 베팅수 0 저장
            valueOperations.set(homeKey + ":count", 0);
            valueOperations.set(awayKey + ":count", 0);

            // [Redis] 각 팀의 총 베팅 포인트 0 저장
            valueOperations.set(homeKey + ":point", 0);
            valueOperations.set(awayKey + ":point", 0);

            // [Redis] 만료기한
            redisTemplate.expire(homeKey + ":count", 24, TimeUnit.HOURS);
            redisTemplate.expire(awayKey + ":count", 24, TimeUnit.HOURS);
            redisTemplate.expire(homeKey + ":point", 24, TimeUnit.HOURS);
            redisTemplate.expire(awayKey + ":point", 24, TimeUnit.HOURS);
        }
    }

    @Override
    @Transactional
    public void startBetting(Long memberSeq, BettingInfoReq bettingInfoReq) {

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        // *Question*
        Member member = getMemberByMembers(memberSeq);
        Game game = getGameByGames(bettingInfoReq.getGameSeq());
        Team team = getTeamByTeams(bettingInfoReq.getTeamSeq());

        Integer bettingPoint = bettingInfoReq.getBettingPoint();

        // 베팅 가능한 포인트인지 확인
        if(bettingPoint > member.getPoint()) throw new PointLackException(POINT_LACK_ERROR);

        // [betting]
        Betting betting = Betting.builder()
                .bettingPoint(bettingPoint)
                .game(game)
                .team(team)
                .member(member)
                .build();
        bettingRepository.save(betting);

        // [members]
        member.setPoint(member.getPoint() - bettingPoint);
        member.setBettingTotal(member.getBettingTotal() + 1);

        // [Redis]
        String key = "game:" + game.getGameSeq() + ":team:" + team.getTeamSeq();

        if(valueOperations.get(key + ":count") == null || valueOperations.get(key + ":point") == null)
            throw new RedisNotFoundException(REDIS_NOT_FOUND);

        valueOperations.set(key + ":count", valueOperations.get(key + ":count") + 1);
        valueOperations.set(key + ":point", valueOperations.get(key + ":point") + bettingPoint);


        System.out.println("count : " + valueOperations.get(key + ":count"));
        System.out.println("point : " + valueOperations.get(key + ":point"));



        // [pointLogs]

    }

    // *Question* Rest API로 호출해서 다시 구성해야 함 //////////////////////////////////////////////////////////////////////

    public List<Game> getListByGames(String date) {
        return gameRepository.getAllGameByDate(date);
    }

    public Member getMemberByMembers(Long memberSeq) {
        return memberRepository.findById(memberSeq).orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
    }

//    @Transactional
//    public void bettingByMembers(Long memberSeq, Integer point) {
//        Member member = memberRepository.findById(memberSeq)
//                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
//        System.out.println("#########################"+ member.getBettingTotal());
//        member.setBettingTotal(member.getBettingTotal() + 1);
//        member.setPoint(member.getPoint() - point);
//    }

    public Game getGameByGames(Long gameSeq) {
        return gameRepository.findById(gameSeq).orElseThrow(() -> new EntityNotFoundException(GAME_NOT_FOUND));
    }

    public Team getTeamByTeams(Long teamSeq) {
        return teamRepository.findById(teamSeq).orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }



}
