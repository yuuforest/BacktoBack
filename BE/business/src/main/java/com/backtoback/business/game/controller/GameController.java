package com.backtoback.business.game.controller;

import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.dto.GameRoomResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqResponseDto;
import com.backtoback.business.game.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<GameTeamSeqResponseDto> getGameTeamSeq(@PathVariable("gameSeq") Long gameSeq){
    GameTeamSeqResponseDto response = gameService.getGameTeamSeq(gameSeq);
    return ResponseEntity.status(200).body(response);
  }

  @GetMapping("/games/today")
  public ResponseEntity<List<Long>> getTodayGameSeq(){
    List<Long> response = gameService.getTodayGameSeq();
    return ResponseEntity.status(200).body(response);
  }

  @GetMapping("/games/yesterday")
  public ResponseEntity<List<Long>> getYesterdayGameSeq(){
    List<Long> response = gameService.getYesterdayGameSeq();
    return ResponseEntity.status(200).body(response);
  }

  @GetMapping("/games/{gameSeq}/info")
  public ResponseEntity<GameRoomResponseDto> getGameInformation(@PathVariable("gameSeq") Long gameSeq){
    GameRoomResponseDto response = gameService.getGameInformation(gameSeq);
    return ResponseEntity.status(200).body(response);
  }

}
