package com.backtoback.business.game.service;


import com.backtoback.business.game.domain.Game;
import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;

    public List<GameResponseDto> getAllTodayGame() {
       List<Game> gameList = gameRepository.getAllTodayGame();
       List<GameResponseDto> gameRequestDtoList = new ArrayList<>();

       for(Game game:gameList){
           gameRequestDtoList.add(GameResponseDto.builder().gameSeq(game.getGameSeq()).build());
       }

       return gameRequestDtoList;
    }
}
