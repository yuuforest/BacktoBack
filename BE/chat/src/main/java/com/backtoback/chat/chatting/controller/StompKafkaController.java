package com.backtoback.chat.chatting.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backtoback.chat.chatting.dto.request.ChatMessage;
import com.backtoback.chat.chatting.service.KafkaConsumer;
import com.backtoback.chat.chatting.service.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/kafka")
public class StompKafkaController {

	private final KafkaProducer kafkaProducer;

	//테스트 용 postmapping --- 실제로는 사용안함
	@PostMapping("/message")
	@MessageMapping("/message")
	public void message(@RequestBody ChatMessage chatMessage) throws JsonProcessingException {
		log.debug("request 요청 들어옴...........................");
		log.info("client message......................... {}", chatMessage);
		kafkaProducer.send("kafka-chat", chatMessage);
	}
}
