package com.backtoback.point.game.service;


import com.backtoback.point.game.domain.Game;
import com.backtoback.point.game.dto.response.GameTeamListResultRes;

import java.util.List;

public interface GameService {

    Game getGame(Long gameSeq);
    List<Game> getListByGames(String date);

    List<GameTeamListResultRes> getTeamListResult(String date);
}
