package com.backtoback.business.game.service;

import java.util.List;

import com.backtoback.business.game.dto.GameInfoWithTeamCode;
import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.dto.GameRoomResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqAndTopicNumberResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqResponseDto;
import com.backtoback.business.game.dto.betting.GameInfoRes;
import com.backtoback.business.game.dto.betting.GameResultRes;
import com.backtoback.business.game.dto.betting.GameSimpleInfoRes;

public interface GameService {

	List<GameResponseDto> getAllTodayGame();

	GameTeamSeqResponseDto getGameTeamSeq(Long gameSeq);

	GameTeamSeqAndTopicNumberResponseDto getGameTeamSeqAndTopicNumber(Long gameSeq);

	List<Long> getTodayGameSeq();

	List<Long> getYesterdayGameSeq();

	GameRoomResponseDto getGameInformation(Long gameSeq);

	List<GameSimpleInfoRes> getGameSimpleInfo();

	GameInfoRes getGameInfo(Long gameSeq);

	GameInfoWithTeamCode getGameInfoWithTeamCode(Long gameSeq);

	GameResultRes getGameResult(Long gameSeq);

	void afterGameResult(Long gameSeq);

}
