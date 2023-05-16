package com.backtoback.business.game.service;

import java.util.List;

import com.backtoback.business.game.dto.GameResponseDto;
import com.backtoback.business.game.dto.GameRoomResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqAndTopicNumberResponseDto;
import com.backtoback.business.game.dto.GameTeamSeqResponseDto;

public interface GameService {

	List<GameResponseDto> getAllTodayGame();
 
	GameTeamSeqResponseDto getGameTeamSeq(Long gameSeq);

	GameTeamSeqAndTopicNumberResponseDto getGameTeamSeqAndTopicNumber(Long gameSeq);

	List<Long> getTodayGameSeq();

	List<Long> getYesterdayGameSeq();

	GameRoomResponseDto getGameInformation(Long gameSeq);

}
