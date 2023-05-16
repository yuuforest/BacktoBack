package com.backtoback.chat_log.chat_log.service;

import com.backtoback.chat_log.chat_log.dto.response.GameTeamSeqResponseDto;

public interface BusinessService {

	GameTeamSeqResponseDto getGameTeamSeqAndTopicNumber(Long gameSeq);

}
