package com.backtoback.business.game.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backtoback.business.common.exception.ErrorCode;
import com.backtoback.business.common.exception.business.EntityNotFoundException;
import com.backtoback.business.game.domain.Game;
import com.backtoback.business.game.domain.GameActiveType;
import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.dto.GameRoomResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqAndTopicNumberResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqResponseDto;
import com.backtoback.business.game.repository.GameRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class GameServiceImpl implements GameService {

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
}
