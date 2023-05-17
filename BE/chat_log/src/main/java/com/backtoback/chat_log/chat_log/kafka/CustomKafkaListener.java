package com.backtoback.chat_log.chat_log.kafka;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

import com.backtoback.chat_log.chat_log.dto.request.ChatMessageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomKafkaListener {

	public static final String BEAN_NAME_PREFIX = "listener_";
	private static final String CHAT_ALL_GAME_TOPIC_PREFIX = "chat.all.game.";
	private static final String CHAT_TEAM_TOPIC_PREFIX = "chat.team.";
	private final KafkaListenerContainerFactory<?> kafkaListenerContainerFactory;
	private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	private final CustomListener customListener;

	// private ThreadLocal<Long> homeSeq;
	// private ThreadLocal<Long> awaySeq;

	// @KafkaListener(
	// 	id = CHAT_ALL_GAME_TOPIC_PREFIX + 1,
	// 	topics = {
	// 		CHAT_ALL_GAME_TOPIC_PREFIX + 1,
	// 		"",
	// 		""
	// 	},
	// 	batch = "true",
	// 	groupId = "group_sample",
	// 	autoStartup = "false",
	// 	properties = {"spring.json.value.default.type=com.backtoback.chat_log.chat_log.dto.request.ChatMessageDto"},
	// 	containerFactory = "customKafkaListenerContainerFactory"
	// )
	// private void listenChatDto(List<ChatMessageDto> message) {
	// 	log.info("================receive====================");
	// 	log.info("############ 총 size: {}", message.size());
	// }

	// public void setHomeSeq(Long homeSeq) {
	// 	if (this.homeSeq == null) {
	// 		this.homeSeq = homeSeq;
	// 	}
	// }
	//
	// public void setAwaySeq(Long awaySeq) {
	// 	if (this.awaySeq == null) {
	// 		this.awaySeq = awaySeq;
	// 	}
	// }

	public String getContainerId(Integer topicNumber) {
		return CHAT_ALL_GAME_TOPIC_PREFIX + topicNumber;
	}

	public String getChatTeamTopicName(Long teamSeq) {
		return CHAT_TEAM_TOPIC_PREFIX + teamSeq;
	}

	public void startContainer(String containerId, @NotNull String... topicName) {
		log.info("====================== Listener Container 시작 ===========================");
		if (this.kafkaListenerEndpointRegistry.getListenerContainer(containerId) != null) {
			///////////////Exception 발생으로 수정 필요/////////////////
			log.info("이미 listener 컨테이너 있어서 함수 종료");
			return;
		}

		//Listener Container 생성
		ConcurrentMessageListenerContainer<String, ChatMessageDto> container =
			(ConcurrentMessageListenerContainer<String, ChatMessageDto>)kafkaListenerContainerFactory.createContainer(
				containerId, topicName[0], topicName[1]
			);

		container.getContainerProperties()
			.setMessageListener(customListener);
		container.getContainerProperties().setGroupId(containerId);
		container.getContainerProperties().setClientId(containerId);
		container.setBeanName(containerId);
		container.setCommonErrorHandler(new DefaultErrorHandler());
		container.setupMessageListener(customListener);

		//Listener Endpoint 생성
		log.info("====================== Listener Endpoint 생성 ===========================");
		MethodKafkaListenerEndpoint myEndpoint = new MethodKafkaListenerEndpoint<>();
		myEndpoint.setId(containerId);
		myEndpoint.setGroupId(containerId);
		myEndpoint.setBean(customListener);
		try {
			myEndpoint.setMethod(customListener.getClass().getMethod("onMessage", List.class, Consumer.class));
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e); ///////////////////////수정 필요//////////////////////////
		}
		myEndpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
		myEndpoint.setupListenerContainer(container, null);
		myEndpoint.setTopics(containerId, topicName[0], topicName[1]);

		kafkaListenerEndpointRegistry.registerListenerContainer(myEndpoint, kafkaListenerContainerFactory, true);
	}

	public void stopContainer(String containerId) {
		log.info("====================== Listener Container 멈춤 ===========================");
		Objects.requireNonNull(this.kafkaListenerEndpointRegistry.getListenerContainer(containerId)).stop();
	}

}
