package com.backtoback.chat_log.chat_log.feign_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.backtoback.chat_log.chat_log.dto.response.GameTeamSeqResponseDto;

@FeignClient(name = "business", url = "localhost:8086/business") //추후 url 수정 필요
public interface BusinessClient {

	@RequestMapping(method = RequestMethod.GET, value = "/teams/game/{gameSeq}")
	GameTeamSeqResponseDto getGameTeamSeq(@PathVariable("gameSeq") Long gameSeq);

}
