package com.backtoback.business.game.service;


import com.backtoback.business.game.domain.Game;
import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqResponseDto;
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

    public GameTeamSeqResponseDto getGameTeamSeq(Long gameSeq) {
        Game findGame = gameRepository.findByGameSeq(gameSeq);
        GameTeamSeqResponseDto gameTeamSeqResponseDto = GameTeamSeqResponseDto.builder()
                                                            .homeSeq(findGame.getHomeTeam().getTeamSeq())
                                                            .awaySeq(findGame.getAwayTeam().getTeamSeq())
                                                            .build();
        return gameTeamSeqResponseDto;
    }

    public List<Long> getTodayGameSeq() {
        List<Game> gameList = gameRepository.getAllTodayGame();
        List<Long> gameSeqList = new ArrayList<>();

        for(Game game : gameList){
            gameSeqList.add(game.getGameSeq());
        }

        return gameSeqList;
    }

    public List<Long> getYesterdayGameSeq() {
        List<Game> gameList = gameRepository.getAllYesterdayGame();
        List<Long> gameSeqList = new ArrayList<>();

        for(Game game : gameList){
            gameSeqList.add(game.getGameSeq());
        }

        return gameSeqList;

    }
}
