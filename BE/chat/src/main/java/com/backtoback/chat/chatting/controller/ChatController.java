package com.backtoback.chat.chatting.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backtoback.chat.chatting.dto.response.GameTeamSeqRes;
import com.backtoback.chat.chatting.service.FeignService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("")
public class ChatController {

	private final FeignService feignService;

	@GetMapping(value = "/teams")		//클라이언트에서 api 호출하는 주소
	public GameTeamSeqRes getGameTeamSeq(@RequestParam Long gameSeq){
		return feignService.getGameTeamSeq(gameSeq);
		// GameTeamSeqRes dummy = new GameTeamSeqRes(1l, 2l);
		// return dummy;
	}

}
