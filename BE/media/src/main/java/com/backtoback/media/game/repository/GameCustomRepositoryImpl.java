package com.backtoback.media.game.repository;

import com.backtoback.media.game.domain.Game;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.backtoback.media.game.domain.QGame.game;

@Slf4j
@RequiredArgsConstructor
@Repository
public class GameCustomRepositoryImpl implements GameCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Game> getAllTodayGame() {
        ZoneId seoulZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime seoulTime = ZonedDateTime.now(seoulZone);
        LocalDateTime now = seoulTime.toLocalDateTime();

        LocalDateTime startOfDay = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN).atZone(seoulZone).toLocalDateTime();
        LocalDateTime endOfDay = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX).atZone(seoulZone).toLocalDateTime();

        log.info(now+":now");
        log.info(startOfDay + ":startOfDay");
        log.info(endOfDay + ":endOfDay");

        return jpaQueryFactory
                .selectFrom(game)
                .where(game.gameDatetime.between(startOfDay, endOfDay))
                .fetch();
    }



}
