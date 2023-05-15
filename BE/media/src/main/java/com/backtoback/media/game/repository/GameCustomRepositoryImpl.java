package com.backtoback.media.game.repository;

import com.backtoback.media.game.domain.Game;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import static com.backtoback.media.game.domain.QGame.game;


@RequiredArgsConstructor
@Repository
public class GameCustomRepositoryImpl implements GameCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Game> getAllTodayGame() {
        LocalDateTime now = LocalDateTime.now();
        ZoneId seoulZone = ZoneId.of("Asia/Seoul");

        LocalDateTime startOfDay = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN).atZone(seoulZone).toLocalDateTime();
        LocalDateTime endOfDay = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX).atZone(seoulZone).toLocalDateTime();


        return jpaQueryFactory
                .selectFrom(game)
                .where(game.gameDatetime.between(startOfDay, endOfDay))
                .fetch();
    }



}
