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

	@KafkaListener(topicPattern = "chat.all.game.*")
	public void consumeAllChat(ChatMessage chatMessage) throws JsonProcessingException {
		log.info("Consumed ChatMessage.........................{}", chatMessage.getMessage());

		Map<String, String> msg = new HashMap<>();
		msg.put("chatRoomId", chatMessage.getChatRoomId().toString());
		msg.put("chatRoomType", chatMessage.getChatRoomType().toString());
		msg.put("memberId", chatMessage.getMemberId().toString());
		msg.put("message", chatMessage.getMessage());
		// msg.put("time", chatMessage.getTime().toString());

		ObjectMapper mapper = new ObjectMapper();
		StringBuilder destination = new StringBuilder(50);

		destination.append("/topic")
					.append("/chat.message.all.")
					.append(chatMessage.getChatRoomId().toString());	//실제로는 gameId가 들어가야한다.

		//STOMP Websocket으로 메세지 날려주기
		template.convertAndSend(String.valueOf(destination), mapper.writeValueAsString(msg));
	}

	/*
	* team chatting consumer 만들기
	* */

}
