package com.backtoback.chat.chatting.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.backtoback.chat.chatting.dto.request.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

	private final SimpMessagingTemplate template;

	/**
	 * 경기 내 전체 채팅 Consumer
	 * @param chatMessage
	 * @throws JsonProcessingException
	 */
	@KafkaListener(topicPattern = "chat.all.game.*", groupId = "kafka-chat-group")
	public void consumeAllChat(ChatMessage chatMessage) throws JsonProcessingException {
		log.info("Consumed ChatMessage.........................{}", chatMessage.toString());

		Map<String, String> msg = new HashMap<>();
		msg.put("gameSeq", chatMessage.getGameSeq().toString());
		msg.put("memberId", chatMessage.getMemberSeq().toString());
		msg.put("memberTeamSeq", chatMessage.getMemberTeamSeq().toString());
		msg.put("nickname", chatMessage.getNickname());
		msg.put("message", chatMessage.getMessage());
		msg.put("time", chatMessage.getTime().toString());
		msg.put("topicNumber", chatMessage.getTopicNumber().toString());

		ObjectMapper mapper = new ObjectMapper();
		StringBuilder destination = new StringBuilder(50);

		destination.append("/topic")
					.append("/chat.message.all.")
					.append(chatMessage.getTopicNumber());

		//STOMP Websocket으로 메세지 날려주기
		template.convertAndSend(String.valueOf(destination), mapper.writeValueAsString(msg));
	}

	/**
	 * 경기 내 마이팀 채팅 Consumer
	 * @param chatMessage
	 * @throws JsonProcessingException
	 */
	@KafkaListener(topicPattern = "chat.team.*", groupId = "kafka-chat-group")
	public void consumeTeamChat(ChatMessage chatMessage) throws JsonProcessingException {
		log.info("Consumed ChatMessage.........................{}", chatMessage.toString());

		Map<String, String> msg = new HashMap<>();
		msg.put("gameSeq", chatMessage.getGameSeq().toString());
		msg.put("memberId", chatMessage.getMemberSeq().toString());
		msg.put("memberTeamSeq", chatMessage.getMemberTeamSeq().toString());
		msg.put("nickname", chatMessage.getNickname());
		msg.put("message", chatMessage.getMessage());
		msg.put("time", chatMessage.getTime().toString());
		msg.put("topicNumber", chatMessage.getTopicNumber().toString());

		ObjectMapper mapper = new ObjectMapper();
		StringBuilder destination = new StringBuilder(50);

		destination.append("/topic")
					.append("/chat.message.team.")
					.append(chatMessage.getMemberTeamSeq());

		template.convertAndSend(String.valueOf(destination), mapper.writeValueAsString(msg));
	}
}
