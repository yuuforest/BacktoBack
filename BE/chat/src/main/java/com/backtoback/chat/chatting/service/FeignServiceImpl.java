package com.backtoback.chat.chatting.service;

import org.springframework.stereotype.Service;

import com.backtoback.chat.chatting.controller.BusinessFeignClient;
import com.backtoback.chat.chatting.dto.response.GameTeamSeqRes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeignServiceImpl implements FeignService{

	private final BusinessFeignClient feignClient;

	@Override
	public GameTeamSeqRes getGameTeamSeq(Long gameSeq) {
		return feignClient.getGameTeamSeq(gameSeq);
	}
}
