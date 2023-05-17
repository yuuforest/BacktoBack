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

	private final KafkaTemplate<String, ChatMessage> kafkaTemplate;

	public void send(String topic, ChatMessage chatMessage){
		log.info("Producer!!!!! topic.........................{}", topic);
		kafkaTemplate.send(topic, chatMessage.getGameSeq().toString(), chatMessage);	//topic, key, value : topic에 메시지 보내준다.
	}
}
