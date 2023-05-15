package com.backtoback.cheer.game.service;


import com.backtoback.cheer.game.domain.Game;

import java.util.List;

public interface GameService {

    Game getGame(Long gameSeq);
    List<Game> getListByGames(String date);
}
