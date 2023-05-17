package com.backtoback.cheer.game.repository;

import com.backtoback.cheer.game.domain.Game;

import java.util.List;

public interface GameCustomRepository {

    List<Game> getAllGameByDate(String date);
}
