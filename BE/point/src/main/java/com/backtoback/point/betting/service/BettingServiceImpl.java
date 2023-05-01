package com.backtoback.point.betting.service;

import com.backtoback.point.game.domain.Game;
import com.backtoback.point.game.repository.GameRepository;
import com.backtoback.point.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BettingServiceImpl implements BettingService{

    final RedisTemplate<String, Integer> redisTemplate;

    final GameRepository gameRepository;
    final TeamRepository teamRepository;

    @Override
    public void readyToStartBetting(){
        // 현재 날짜인 경기 목록 불러오기
        String nowDate = String.valueOf(LocalDate.now(ZoneId.of("Asia/Seoul")));     // "2023-04-30"
        List<Game> games = gameRepository.getAllGameByDate(nowDate);

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
}
