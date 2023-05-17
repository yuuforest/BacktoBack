package com.backtoback.point.game.repository;

import com.backtoback.point.game.domain.Game;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.backtoback.point.game.domain.QGame.game;


@RequiredArgsConstructor
@Repository
public class GameCustomRepositoryImpl implements GameCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Game> getAllGameByDate(String date) {
        return jpaQueryFactory
                .selectFrom(game)
                .where(game.gameDatetime.stringValue().contains(date))
                .fetch();
    }
}
