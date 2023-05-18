package com.backtoback.chat_log.chat_log.feign_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.backtoback.chat_log.chat_log.dto.response.GameTeamSeqResponseDto;

@FeignClient(name = "business", url = "k8a708.p.ssafy.io:8086/api/business") //추후 url 수정 필요
public interface BusinessClient {

	@RequestMapping(method = RequestMethod.GET, value = "/v2/teams/game/{gameSeq}")
	GameTeamSeqResponseDto getGameTeamSeqAndTopicNumber(@PathVariable("gameSeq") Long gameSeq);

}
