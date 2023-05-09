package com.backtoback.business.game.repository;



import com.backtoback.business.game.domain.Game;

import java.util.List;

public interface GameCustomRepository {

    List<Game> getAllTodayGame();

    List<Game> getAllYesterdayGame();
}
