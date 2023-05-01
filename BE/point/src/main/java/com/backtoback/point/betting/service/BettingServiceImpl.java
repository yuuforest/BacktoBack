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

            // [Redis] 만료기한
            redisTemplate.expire(homeKey + ":count", 24, TimeUnit.HOURS);
            redisTemplate.expire(awayKey + ":count", 24, TimeUnit.HOURS);
            redisTemplate.expire(homeKey + ":point", 24, TimeUnit.HOURS);
            redisTemplate.expire(awayKey + ":point", 24, TimeUnit.HOURS);
        }
    }

    @Override
    public void startBetting(Long memberSeq, BettingInfoReq bettingInfoReq) {

        // *Question*
        Member member = getMemberByMembers(memberSeq);

        // 베팅 가능한 포인트인지 확인
        if(bettingInfoReq.getBettingPoint() > member.getPoint()) throw new PointLackException(POINT_LACK_ERROR);

        // [betting]
        createBettingLog(memberSeq, bettingInfoReq);

        // [Redis]
        updateRedisLog(bettingInfoReq);
    }

    @Override
    public void createBettingLog(Long memberSeq, BettingInfoReq bettingInfoReq) {

        // *Question*
        Member member = getMemberByMembers(memberSeq);
        Game game = getGameByGames(bettingInfoReq.getGameSeq());
        Team team = getTeamByTeams(bettingInfoReq.getTeamSeq());

        Betting betting = Betting.builder()
                .bettingPoint(bettingInfoReq.getBettingPoint())
                .game(game)
                .team(team)
                .member(member)
                .build();
        bettingRepository.save(betting);
    }

    @Override
    public void updateRedisLog(BettingInfoReq bettingInfoReq) {

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        // *Question*
        Game game = getGameByGames(bettingInfoReq.getGameSeq());
        Team team = getTeamByTeams(bettingInfoReq.getTeamSeq());

        String key = "game:" + game.getGameSeq() + ":team:" + team.getTeamSeq();

        if(valueOperations.get(key + ":count") == null || valueOperations.get(key + ":point") == null)
            throw new RedisNotFoundException(REDIS_NOT_FOUND);

        valueOperations.set(key + ":count", valueOperations.get(key + ":count") + 1);
        valueOperations.set(key + ":point", valueOperations.get(key + ":point") + bettingInfoReq.getBettingPoint());
    }

    // *Question* Rest API로 호출해서 다시 구성해야 함 //////////////////////////////////////////////////////////////////////

    public List<Game> getListByGames(String date) {
        return gameRepository.getAllGameByDate(date);
    }

    public Member getMemberByMembers(Long memberSeq) {
        return memberRepository.findById(memberSeq).orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
    }

    public Game getGameByGames(Long gameSeq) {
        return gameRepository.findById(gameSeq).orElseThrow(() -> new EntityNotFoundException(GAME_NOT_FOUND));
    }

    public Team getTeamByTeams(Long teamSeq) {
        return teamRepository.findById(teamSeq).orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }



}
