package com.backtoback.point.game.service;


import com.backtoback.point.game.domain.Game;

import java.util.List;

public interface GameService {

    Game getGame(Long gameSeq);
    List<Game> getListByGames(String date);
}
