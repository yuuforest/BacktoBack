package com.backtoback.chat_log.chat_log.kafka;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

import com.backtoback.chat_log.chat_log.dto.common.ChatMessageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomKafkaListener {

	// public static final String BEAN_NAME_PREFIX = "listener_";
	private static final String CHAT_ALL_GAME_TOPIC_PREFIX = "chat.all.game.";
	private static final String CHAT_TEAM_TOPIC_PREFIX = "chat.team.";

	private final KafkaListenerContainerFactory<?> kafkaListenerContainerFactory;
	private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	public String getContainerId(Integer topicNumber) {
		return CHAT_ALL_GAME_TOPIC_PREFIX + topicNumber;
	}

	public String getChatTeamTopicName(Long teamSeq) {
		return CHAT_TEAM_TOPIC_PREFIX + teamSeq;
	}

	/*
	리팩토링 - 로직 분리 필요
	 */
	public void startContainer(List<CustomListener> customListenerList, String containerId,
		int topicNumber, Long mediaStartTime, @NotNull String... topicName) {
		log.info("====================== Listener Container 시작 ===========================");
		log.info("####################containerId: {}##################", containerId);

		if (this.kafkaListenerEndpointRegistry.getListenerContainer(containerId) != null
			&& this.kafkaListenerEndpointRegistry.getListenerContainer(containerId).isRunning()) {

			///////////////Exception 발생으로 수정 필요/////////////////
			log.info("이미 listener 컨테이너 있어서 함수 종료");
			return;
		}

		//Listener Class 생성
		final CustomListener customListener = new CustomListener(mediaStartTime);

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
		customListenerList.add(topicNumber - 1, customListener);

		log.info("####################registry 속 containerIDs: {}",
			this.kafkaListenerEndpointRegistry.getListenerContainerIds());
	}

	public MessageListenerContainer stopContainer(String containerId) {
		log.info("====================== Listener Container 멈춤 ===========================");
		ConcurrentMessageListenerContainer<String, ChatMessageDto> container = (ConcurrentMessageListenerContainer<String, ChatMessageDto>)this.kafkaListenerEndpointRegistry.getListenerContainer(
			containerId);

		if (container == null) {
			///////////////Exception 발생으로 수정 필요/////////////////
			log.info("listener 컨테이너 없어서 함수 종료");
			return null;
		}

		container.stop();
		kafkaListenerEndpointRegistry.unregisterListenerContainer(containerId);

		return container;
	}

}
