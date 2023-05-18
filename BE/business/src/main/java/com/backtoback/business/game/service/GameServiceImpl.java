package com.backtoback.business.game.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backtoback.business.common.config.WebDriverUtil;
import com.backtoback.business.common.dto.GameResultCrawlingDto;
import com.backtoback.business.common.exception.ErrorCode;
import com.backtoback.business.common.exception.business.EntityNotFoundException;
import com.backtoback.business.game.domain.Game;
import com.backtoback.business.game.domain.GameActiveType;
import com.backtoback.business.game.dto.GameInfoWithTeamCode;
import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.dto.GameResultWithTeamCode;
import com.backtoback.business.game.dto.GameRoomResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqAndTopicNumberResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqResponseDto;
import com.backtoback.business.game.dto.betting.GameInfoRes;
import com.backtoback.business.game.dto.betting.GameResultRes;
import com.backtoback.business.game.dto.betting.GameSimpleInfoRes;
import com.backtoback.business.game.repository.GameRepository;
import com.backtoback.business.team.domain.Team;
import com.backtoback.business.team.domain.TeamCode;
import com.backtoback.business.team.repository.TeamRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class GameServiceImpl implements GameService {

	private final TeamRepository teamRepository;

	private final GameRepository gameRepository;

	public List<GameResponseDto> getAllTodayGame() {
		List<Game> gameList = gameRepository.getAllTodayGame();
		List<GameResponseDto> gameRequestDtoList = new ArrayList<>();

		for (Game game : gameList) {
			gameRequestDtoList.add(GameResponseDto.builder().gameSeq(game.getGameSeq()).build());
		}

		return gameRequestDtoList;
	}

	public GameTeamSeqResponseDto getGameTeamSeq(Long gameSeq) {
		Game findGame = gameRepository.findByGameSeq(gameSeq);
		GameTeamSeqResponseDto gameTeamSeqResponseDto = GameTeamSeqResponseDto.builder()
			.homeSeq(findGame.getHomeTeam().getTeamSeq())
			.awaySeq(findGame.getAwayTeam().getTeamSeq())
			.build();
		return gameTeamSeqResponseDto;
	}

	@Override
	public GameTeamSeqAndTopicNumberResponseDto getGameTeamSeqAndTopicNumber(Long gameSeq) {
		Game findGame = gameRepository.findById(gameSeq)
			.orElseThrow(() -> new EntityNotFoundException(
				"해당 gameSeq({})에 해당하는 Entity가 없습니다.", ErrorCode.ENTITY_NOT_FOUND)
			);

		GameTeamSeqAndTopicNumberResponseDto gameTeamSeqAndTopicNumberResponseDto = GameTeamSeqAndTopicNumberResponseDto.builder()
			.topicNumber(findGame.getTopicNumber())
			.homeSeq(findGame.getHomeTeam().getTeamSeq())
			.awaySeq(findGame.getAwayTeam().getTeamSeq())
			.build();

		return gameTeamSeqAndTopicNumberResponseDto;
	}

	public List<Long> getTodayGameSeq() {
		List<Game> gameList = gameRepository.getAllTodayGame();
		List<Long> gameSeqList = new ArrayList<>();

		for (Game game : gameList) {
			gameSeqList.add(game.getGameSeq());
		}

		return gameSeqList;
	}

	public List<Long> getYesterdayGameSeq() {
		List<Game> gameList = gameRepository.getAllYesterdayGame();
		List<Long> gameSeqList = new ArrayList<>();

		for (Game game : gameList) {
			gameSeqList.add(game.getGameSeq());
		}

		return gameSeqList;

	}

	public GameRoomResponseDto getGameInformation(Long gameSeq) {
		GameRoomResponseDto gameInformation = gameRepository.getGameInformation(gameSeq);
		gameInformation.setIsActive(gameInformation.getGameActiveType().equals(GameActiveType.IN_GAME));
		return gameInformation;
	}

	@Override
	public List<GameSimpleInfoRes> getGameSimpleInfo() {
		List<Game> gameList = gameRepository.getAllTodayGame();
		List<GameSimpleInfoRes> infoList = new ArrayList<>();
		for (Game game : gameList) {
			GameSimpleInfoRes res = GameSimpleInfoRes.builder()
				.gameSeq(game.getGameSeq())
				.homeSeq(game.getHomeTeam().getTeamSeq())
				.awaySeq(game.getAwayTeam().getTeamSeq())
				.build();
			infoList.add(res);
		}
		return infoList;
	}

	@Override
	public GameInfoRes getGameInfo(Long gameSeq) {
		Game game = gameRepository.findByGameSeq(gameSeq);
		return GameInfoRes.builder()
			.gameSeq(game.getGameSeq())
			.gameDatetime(game.getGameDatetime())
			.place(game.getPlace())
			.gameActiveType(game.getGameActiveType().toString())
			.homeTeamSeq(game.getHomeTeam().getTeamSeq())
			.awayTeamSeq(game.getAwayTeam().getTeamSeq())
			.build();
	}

	@Override
	public GameInfoWithTeamCode getGameInfoWithTeamCode(Long gameSeq) {
		Game game = gameRepository.findById(gameSeq)
			.orElseThrow(() -> new EntityNotFoundException(
				"해당 gameSeq(" + gameSeq + ")에 해당하는 Game 엔티티가 없습니다.",
				ErrorCode.GAME_NOT_FOUND)
			);

		return GameInfoWithTeamCode.builder()
			.gameDatetime(game.getGameDatetime())
			.gameSeq(gameSeq)
			.homeSeq(game.getHomeTeam().getTeamSeq())
			.homeTeamCode(game.getHomeTeam().getTeamCode())
			.awaySeq(game.getAwayTeam().getTeamSeq())
			.awayTeamCode(game.getAwayTeam().getTeamCode())
			.build();
	}

	@Override
	public GameResultRes getGameResult(Long gameSeq) {
		Game game = gameRepository.findByGameSeq(gameSeq);
		return GameResultRes.builder()
			.homeTeamSeq(game.getHomeTeam().getTeamSeq())
			.awayTeamSeq(game.getAwayTeam().getTeamSeq())
			.winTeamSeq(game.getWinTeam().getTeamSeq()).build();
	}

	private GameResultWithTeamCode crawlGameResult(Long gameSeq, String date, TeamCode homeTeamCode,
		TeamCode awayTeamCode) {
		GameResultCrawlingDto gameResultCrawlingDto = GameResultCrawlingDto.builder()
			/* 실제 코드 */
			// .date(date)
			// .homeTeamCode(homeTeamCode.name())
			// .awayTeamCode(awayTeamCode.name())

			/* 시연용 코드 */
			.gameSeq(gameSeq)
			.date("230516")
			.homeTeamCode(TeamCode.valueOf("OB"))
			.awayTeamCode(TeamCode.valueOf("WO"))
			.build();

		WebDriverUtil util = new WebDriverUtil(gameResultCrawlingDto) {
			@Override
			public Object process(WebDriver driver, Object param) {
				GameResultCrawlingDto gameResultCrawlingDto = (GameResultCrawlingDto)param;

				WebElement tblScordboard = driver.findElement(By.id("tblScordboard1"));
				List<WebElement> teams = tblScordboard.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

				//index 0 : 홈팀
				//index 1 : 원정팀
				WebElement homeTeam = teams.get(0).findElement(By.tagName("td"));
				WebElement awayTeam = teams.get(1).findElement(By.tagName("td"));

				TeamCode winTeamCode;
				TeamCode loseTeamCode;

				if (homeTeam.getText().equals("승")) { //홈팀 승
					log.info("####################homeTeam인 {} 가 승리###################",
						gameResultCrawlingDto.getHomeTeamCode());
					winTeamCode = gameResultCrawlingDto.getHomeTeamCode();
					loseTeamCode = gameResultCrawlingDto.getAwayTeamCode();
				} else if (homeTeam.getText().equals("패")) { //홈팀 패
					log.info("####################homeTeam인 {} 가 패배###################",
						gameResultCrawlingDto.getHomeTeamCode());
					winTeamCode = gameResultCrawlingDto.getAwayTeamCode();
					loseTeamCode = gameResultCrawlingDto.getHomeTeamCode();
				} else { //무승부
					winTeamCode = gameResultCrawlingDto.getHomeTeamCode();
					loseTeamCode = gameResultCrawlingDto.getHomeTeamCode();
				}

				return GameResultWithTeamCode.builder()
					.gameSeq(gameResultCrawlingDto.getGameSeq())
					.winTeamCode(winTeamCode)
					.loseTeamCode(loseTeamCode)
					.build();
			}
		};

		return (GameResultWithTeamCode)util.useDriver(
			String.format(
				"https://www.koreabaseball.com/Schedule/GameCenter/Main.aspx?gameDate=%s&gameId=20%s%s%s0&section=REVIEW",
				gameResultCrawlingDto.getDate(),
				gameResultCrawlingDto.getDate(),
				gameResultCrawlingDto.getHomeTeamCode(),
				gameResultCrawlingDto.getAwayTeamCode()
			));
	}

	private void saveGameResult(GameResultWithTeamCode gameResultWithTeamCode) {
		Game game = gameRepository.findByGameSeq(gameResultWithTeamCode.getGameSeq());

		Team winTeam = teamRepository.findByTeamCode(gameResultWithTeamCode.getWinTeamCode());
		Team loseTeam = teamRepository.findByTeamCode(gameResultWithTeamCode.getLoseTeamCode());

		game.setWinTeam(winTeam);
		game.setLoseTeam(loseTeam);
	}

	@Override
	public void afterGameResult(Long gameSeq) {
		//1. 경기 정보 + 팀정보 get
		GameInfoWithTeamCode gameInfoWithTeamCode = getGameInfoWithTeamCode(gameSeq);
		//2. 크롤링 시작
		GameResultWithTeamCode gameResultWithTeamCode = crawlGameResult(
			gameInfoWithTeamCode.getGameSeq(),
			gameInfoWithTeamCode.getGameDatetime().format(DateTimeFormatter.ofPattern("yyMMdd")),
			gameInfoWithTeamCode.getHomeTeamCode(),
			gameInfoWithTeamCode.getAwayTeamCode()
		);
		//3. 경기 결과 저장
		saveGameResult(gameResultWithTeamCode);
	}

}
