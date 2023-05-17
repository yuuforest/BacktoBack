package com.backtoback.chat.chatting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backtoback.chat.chatting.dto.response.GameTeamSeqRes;

@Service
public interface FeignService {

	GameTeamSeqRes getGameTeamSeq(Long gameSeq);
	List<Long> getTodayGameSeq();
	List<Long> getYesterdayGameSeq();
}
