package com.backtoback.chat.chatting.service;

import java.util.List;

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

	@Override
	public List<Long> getTodayGameSeq() {
		log.info("feign client 호출!!!!!!!");
		return feignClient.getTodayGameSeq();
	}

	@Override
	public List<Long> getYesterdayGameSeq() {
		return feignClient.getYesterdayGameSeq();
	}
}
