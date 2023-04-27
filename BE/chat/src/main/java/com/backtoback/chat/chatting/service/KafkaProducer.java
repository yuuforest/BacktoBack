package com.backtoback.chat.chatting.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.backtoback.chat.chatting.dto.request.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

	private final KafkaTemplate<Long, ChatMessage> kafkaTemplate;

	public void send(String topic, ChatMessage chatMessage){
		log.info("topic.........................{}", topic);
		log.info("room....................chatRoomId: {}", chatMessage.getChatRoomId());
		log.info("sender...................memberId: {}", chatMessage.getMemberId());
		log.info("message..................{}", chatMessage.getMessage());
		kafkaTemplate.send(topic, chatMessage.getChatRoomId(), chatMessage);	//topic, key, value
	}
}
