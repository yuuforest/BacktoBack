package com.backtoback.business.game.controller;

import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqResponseDto;
import com.backtoback.business.game.service.GameService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "게임 API")
@RequiredArgsConstructor
@RestController
@Slf4j
public class GameController {

  private final GameService gameService;

  @GetMapping("/game")
  public List<GameResponseDto> getGame() {
    return gameService.getAllTodayGame();
  }

  @GetMapping("/teams/game/{gameSeq}")
  public GameTeamSeqResponseDto getGameTeamSeq(@PathVariable("gameSeq") Long gameSeq){
    return gameService.getGameTeamSeq(gameSeq);
  }

  @GetMapping("/games/today")
  public List<Long> getTodayGameSeq(){
    log.info("chat server call...........................");
    return gameService.getTodayGameSeq();
  }

  @GetMapping("/games/yesterday")
  public List<Long> getYesterdayGameSeq(){
    return gameService.getYesterdayGameSeq();
  }

}
