package com.backtoback.media.game.repository;



import com.backtoback.media.game.domain.Game;

import java.util.List;

public interface GameCustomRepository {

    List<Game> getAllTodayGame();
}
