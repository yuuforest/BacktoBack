package com.backtoback.point.game.repository;

import com.backtoback.point.game.domain.Game;

import java.util.List;

public interface GameCustomRepository {

    List<Game> getAllGameByDate(String date);
}
