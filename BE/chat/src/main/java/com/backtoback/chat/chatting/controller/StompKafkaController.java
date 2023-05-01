package com.backtoback.chat.chatting.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
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
public class StompKafkaController {

	private final KafkaProducer kafkaProducer;

	@MessageMapping("/chat.message.{chatRoomType}.{gameId}")
	public void message(@RequestBody ChatMessage chatMessage,
						@DestinationVariable String chatRoomType,
						@DestinationVariable Long gameId) throws JsonProcessingException {
		log.info("client message......................... {}", chatMessage);

		//Kafka Producer send
		StringBuilder kafkaTopicName = new StringBuilder(100);
		kafkaTopicName.append("chat.").append(chatRoomType).append(".game.").append(gameId);	//chat.all.game.1
		kafkaProducer.send(kafkaTopicName.toString(), chatMessage);
	}
}
