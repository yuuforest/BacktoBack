package com.backtoback.business.game.repository;



import com.backtoback.business.game.domain.Game;
import com.backtoback.business.game.dto.GameRoomResponseDto;

import java.util.List;

public interface GameCustomRepository {

    List<Game> getAllTodayGame();

    List<Game> getAllYesterdayGame();

    GameRoomResponseDto getGameInformation(Long gameSeq);
}
