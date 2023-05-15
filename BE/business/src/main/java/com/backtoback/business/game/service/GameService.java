package com.backtoback.business.game.service;

import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.dto.GameRoomResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqResponseDto;
import com.backtoback.business.game.dto.betting.GameInfoRes;
import com.backtoback.business.game.dto.betting.GameResultRes;
import com.backtoback.business.game.dto.betting.GameSimpleInfoRes;

import java.util.List;

public interface GameService {

    public List<GameResponseDto> getAllTodayGame();

    GameTeamSeqResponseDto getGameTeamSeq(Long gameSeq);

    List<Long> getTodayGameSeq();

    List<Long> getYesterdayGameSeq();

    GameRoomResponseDto getGameInformation(Long gameSeq);

    List<GameSimpleInfoRes> getGameSimpleInfo();

    GameInfoRes getGameInfo(Long gameSeq);

    GameResultRes getGameResult(Long gameSeq);
}
