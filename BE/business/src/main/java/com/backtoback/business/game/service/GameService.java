package com.backtoback.business.game.service;

import com.backtoback.business.game.dto.GameResponseDto;

import java.util.List;

public interface GameService {

    public List<GameResponseDto> getAllTodayGame();

}
