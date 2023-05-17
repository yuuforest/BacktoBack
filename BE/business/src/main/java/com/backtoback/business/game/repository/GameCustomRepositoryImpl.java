package com.backtoback.business.game.repository;

import static com.backtoback.business.game.domain.QGame.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.backtoback.business.game.domain.Game;
import com.backtoback.business.game.dto.GameRoomResponseDto;
import com.backtoback.business.team.domain.QTeam;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.*;
import java.util.List;

import static com.backtoback.business.game.domain.QGame.game;
import static com.backtoback.business.team.domain.QTeam.*;

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

	@Override
	public List<Game> getAllYesterdayGame() {

		LocalDate yesterday = LocalDate.now().minusDays(1);
		LocalDateTime startOfDay = LocalDateTime.of(yesterday, LocalTime.MIN);
		LocalDateTime endOfDay = LocalDateTime.of(yesterday, LocalTime.MAX);

		return jpaQueryFactory
			.selectFrom(game)
			.where(game.gameDatetime.between(startOfDay, endOfDay))
			.fetch();
	}

	@Override
	public GameRoomResponseDto getGameInformation(Long gameSeq) {

		QTeam homeTeam = new QTeam("homeTeam");
		QTeam awayTeam = new QTeam("awayTeam");

		return jpaQueryFactory
			.select(Projections.constructor(
				GameRoomResponseDto.class,
				game.gameSeq,
				homeTeam.teamSeq,
				awayTeam.teamSeq,
				homeTeam.teamName,
				awayTeam.teamName,
				game.gameActiveType,
				game.topicNumber
			))
			.from(game)
			.innerJoin(homeTeam).on(game.homeTeam.teamSeq.eq(homeTeam.teamSeq))
			.innerJoin(awayTeam).on(game.awayTeam.teamSeq.eq(awayTeam.teamSeq))
			.where(game.gameSeq.eq(gameSeq))
			.fetchOne();
	}

}
