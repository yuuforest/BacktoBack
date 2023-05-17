package com.backtoback.chat.chatting.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.backtoback.chat.chatting.configuration.FeignConfig;
import com.backtoback.chat.chatting.dto.response.GameTeamSeqRes;

@FeignClient(name="BusinessFeign", url="http://localhost:8000/business", configuration = FeignConfig.class)		//url: 비즈니스 api 호출하는 url
public interface BusinessFeignClient {
	@RequestMapping(method = RequestMethod.GET, value = "/teams/game/{gameSeq}")
	GameTeamSeqRes getGameTeamSeq(@PathVariable("gameSeq") Long gameSeq);

	@RequestMapping(method = RequestMethod.GET, value = "/games/today")
	List<Long> getTodayGameSeq();

	@RequestMapping(method = RequestMethod.GET, value = "/games/yesterday")
	List<Long> getYesterdayGameSeq();
}
