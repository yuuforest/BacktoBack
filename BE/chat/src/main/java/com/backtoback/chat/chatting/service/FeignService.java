package com.backtoback.chat.chatting.service;

import org.springframework.stereotype.Service;

import com.backtoback.chat.chatting.dto.response.GameTeamSeqRes;

@Service
public interface FeignService {

	GameTeamSeqRes getGameTeamSeq(Long gameSeq);
}
