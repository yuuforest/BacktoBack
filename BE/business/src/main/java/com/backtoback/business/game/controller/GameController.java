package com.backtoback.business.game.controller;

import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.service.GameService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "게임 API")
@RequiredArgsConstructor
@RestController
public class GameController {

  private final GameService gameService;

  @GetMapping("/game")
  public List<GameResponseDto> getGame() {
    return gameService.getAllTodayGame();
  }

}
