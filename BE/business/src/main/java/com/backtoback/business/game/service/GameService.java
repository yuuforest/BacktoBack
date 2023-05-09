package com.backtoback.business.game.service;

import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqResponseDto;

import java.util.List;

public interface GameService {

    public List<GameResponseDto> getAllTodayGame();

    GameTeamSeqResponseDto getGameTeamSeq(Long gameSeq);

    List<Long> getTodayGameSeq();

    List<Long> getYesterdayGameSeq();

}
