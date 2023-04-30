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

@Service
@RequiredArgsConstructor
public class BettingServiceImpl implements BettingService{

    final RedisTemplate<String, Integer> redisTemplate;

    final GameRepository gameRepository;
    final TeamRepository teamRepository;

    @Override
    public void readyToStartBetting() {
        // 현재 날짜인 경기 목록 불러오기
        String nowDate = String.valueOf(LocalDate.now(ZoneId.of("Asia/Seoul")));     // "2023-04-30"
        List<Game> games = gameRepository.getAllGameByDate(nowDate);

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        // [Redis] 각 팀의 베팅수 0 저장
        for(Game game : games) {

            Long gameId = game.getGameSeq();
            Long homeId = game.getHomeTeam().getTeamSeq();
            Long awayId = game.getAwayTeam().getTeamSeq();

            // [Redis] 각 팀의 베팅수 0 저장
            valueOperations.set("game:" + gameId + ":team:" + homeId + ":count", 0);
            valueOperations.set("game:" + gameId + ":team:" + awayId + ":count", 0);

            // [Redis] 각 팀의 총 베팅 포인트 0 저장
            valueOperations.set("game:" + gameId + ":team:" + homeId + ":point", 0);
            valueOperations.set("game:" + gameId + ":team:" + awayId + ":point", 0);
        }
    }
}
