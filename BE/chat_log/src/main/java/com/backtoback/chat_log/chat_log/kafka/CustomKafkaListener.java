package com.backtoback.chat_log.chat_log.kafka;

import java.util.List;
import java.util.Objects;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import com.backtoback.chat_log.chat_log.dto.request.ChatMessageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomKafkaListener {

	private final KafkaListenerEndpointRegistry registry;

	@KafkaListener(
		id = "viva_listener_chatDto",
		topics = {"viva4", "viva5", "viva6"},
		batch = "true",
		groupId = "group_sample",
		autoStartup = "false",
		properties = {"spring.json.value.default.type=com.backtoback.chat_log.chat_log.dto.request.ChatMessageDto"},
		containerFactory = "customKafkaListenerContainerFactory"
	)
	private void listenChatDto(List<ChatMessageDto> message) {
		log.info("================receive====================");
		log.info("############ 총 size: {}", message.size());
	}

	public void start() {
		log.info("====================== 컨테이너 시작 ===========================");
		Objects.requireNonNull(this.registry.getListenerContainer("viva_listener_chatDto")).start();
	}

	public void stop() {
		log.info("====================== 컨테이너 멈춤 ===========================");
		Objects.requireNonNull(this.registry.getListenerContainer("viva_listener_chatDto")).stop();
	}

}
