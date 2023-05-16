package com.backtoback.business.game.repository;

import java.util.List;

import com.backtoback.business.game.domain.Game;
import com.backtoback.business.game.dto.GameRoomResponseDto;

public interface GameCustomRepository {

    List<Game> getAllTodayGame();

    List<Game> getAllYesterdayGame();

    GameRoomResponseDto getGameInformation(Long gameSeq);

}
