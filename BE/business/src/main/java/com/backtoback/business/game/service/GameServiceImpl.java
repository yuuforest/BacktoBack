package com.backtoback.business.game.service;


import com.backtoback.business.game.domain.Game;
import com.backtoback.business.game.domain.GameActiveType;
import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.dto.GameRoomResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqResponseDto;
import com.backtoback.business.game.dto.betting.GameInfoRes;
import com.backtoback.business.game.dto.betting.GameResultRes;
import com.backtoback.business.game.dto.betting.GameSimpleInfoRes;
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

    public GameRoomResponseDto getGameInformation(Long gameSeq) {
        GameRoomResponseDto gameInformation = gameRepository.getGameInformation(gameSeq);
        if(gameInformation.getGameActiveType().equals(GameActiveType.IN_GAME)){
            gameInformation.setIsActive(true);
        } else{
            gameInformation.setIsActive(false);
        }
        return gameInformation;
    }

    @Override
    public List<GameSimpleInfoRes> getGameSimpleInfo() {
        List<Game> gameList = gameRepository.getAllTodayGame();
        List<GameSimpleInfoRes> infoList = new ArrayList<>();
        for (Game game : gameList) {
            GameSimpleInfoRes res = GameSimpleInfoRes.builder()
                    .gameSeq(game.getGameSeq())
                    .homeSeq(game.getHomeTeam().getTeamSeq())
                    .awaySeq(game.getAwayTeam().getTeamSeq())
                    .build();
            infoList.add(res);
        }
        return infoList;
    }

    @Override
    public GameInfoRes getGameInfo(Long gameSeq) {
        Game game = gameRepository.findByGameSeq(gameSeq);
        return GameInfoRes.builder()
                .gameSeq(game.getGameSeq())
                .gameDatetime(game.getGameDatetime())
                .place(game.getPlace())
                .gameActiveType(game.getGameActiveType().toString())
                .homeTeamSeq(game.getHomeTeam().getTeamSeq())
                .awayTeamSeq(game.getAwayTeam().getTeamSeq())
                .build();
    }

    @Override
    public GameResultRes getGameResult(Long gameSeq) {
        Game game = gameRepository.findByGameSeq(gameSeq);
        return GameResultRes.builder()
                .homeTeamSeq(game.getHomeTeam().getTeamSeq())
                .awayTeamSeq(game.getAwayTeam().getTeamSeq())
                .winTeamSeq(game.getWinTeam().getTeamSeq()).build();
    }
}
