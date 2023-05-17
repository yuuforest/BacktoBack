package com.backtoback.chat_log.chat_log.service;

import org.springframework.stereotype.Service;

import com.backtoback.chat_log.chat_log.dto.response.GameTeamSeqResponseDto;
import com.backtoback.chat_log.chat_log.feign_client.BusinessClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

	private final BusinessClient businessClient;

	@Override
	public GameTeamSeqResponseDto getGameTeamSeqAndTopicNumber(Long gameSeq) {
		return businessClient.getGameTeamSeqAndTopicNumber(gameSeq);
	}

}
