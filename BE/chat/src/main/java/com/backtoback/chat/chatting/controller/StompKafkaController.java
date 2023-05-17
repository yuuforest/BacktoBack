package com.backtoback.chat.chatting.controller;

import java.time.LocalDateTime;

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

	@MessageMapping("/chat.message.all.{topicNumber}")
	public void messageOfAllChat(@RequestBody ChatMessage chatMessage,
						@DestinationVariable Long topicNumber) {
		log.info("client message from game all chatting......................... {}", chatMessage);

		chatMessage.setTime(LocalDateTime.now());

		//Kafka Producer send
		StringBuilder kafkaTopicName = new StringBuilder(100);
		kafkaTopicName.append("chat.all.game.").append(topicNumber);
		kafkaProducer.send(kafkaTopicName.toString(), chatMessage);
	}

	@MessageMapping("/chat.message.team.{teamSeq}")
	public void messageOfTeamChat(@RequestBody ChatMessage chatMessage,
							@DestinationVariable Long teamSeq) {
		log.info("client message from team chatting......................... {}", chatMessage);

		chatMessage.setTime(LocalDateTime.now());

		StringBuilder kafkaTopicName = new StringBuilder(100);
		kafkaTopicName.append("chat.team.").append(teamSeq);
		kafkaProducer.send(kafkaTopicName.toString(), chatMessage);
	}
}
