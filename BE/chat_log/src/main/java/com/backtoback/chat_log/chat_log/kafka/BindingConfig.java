package com.backtoback.chat_log.chat_log.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.MessageListenerContainer;

import com.backtoback.chat_log.chat_log.domain.GameActiveType;
import com.backtoback.chat_log.chat_log.dto.request.GameConditionDto;
import com.backtoback.chat_log.chat_log.dto.response.GameTeamSeqResponseDto;
import com.backtoback.chat_log.chat_log.dto.response.HighLightMessageDto;
import com.backtoback.chat_log.chat_log.service.BusinessService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BindingConfig {

	private final CustomKafkaListener customKafkaListener;
	private final BusinessService businessService;
	private final StreamBridge streamBridge;
	private final List<CustomListener> customListenerList = new ArrayList<>(5);
	// private final CustomListener[] customListenerList = new CustomListener[5];

	@PostConstruct
	private void init() {
		for (int i = 0; i < 5; i++) {
			customListenerList.add(null);
		}
	}

	@Bean
	// @Async
	public Consumer<GameConditionDto> getGameCondition() {
		return gameConditionDto -> {
			log.info(gameConditionDto.toString());

			Long gameSeq = gameConditionDto.getGameSeq();
			GameActiveType gameActiveType = gameConditionDto.getGameActiveType();
			// Long mediaStartTime = gameConditionDto.getMediaStartTime();
			Long mediaStartTime = System.currentTimeMillis();

			log.info("mediaStartTime: " + mediaStartTime);

			GameTeamSeqResponseDto gameTeamSeqResponseDto = businessService.getGameTeamSeqAndTopicNumber(gameSeq);

			int topicNumber = gameTeamSeqResponseDto.getTopicNumber();
			String containerId = customKafkaListener.getContainerId(topicNumber);

			if (gameActiveType == GameActiveType.IN_GAME) { //경기 시작
				this.startContainer(
					containerId,
					topicNumber,
					gameTeamSeqResponseDto.getHomeSeq(),
					gameTeamSeqResponseDto.getAwaySeq(),
					mediaStartTime
				);
			} else if (gameActiveType == GameActiveType.AFTER_GAME) { //경기 끝
				this.stopContainer(topicNumber, gameSeq, containerId);
			}
		};
	}

	private void startContainer(String containerId, int topicNumber, Long homeSeq, Long awaySeq, Long mediaStartTime) {
		log.info("경기 시작");

		String homeTopicName = customKafkaListener.getChatTeamTopicName(homeSeq);
		String awayTopicName = customKafkaListener.getChatTeamTopicName(awaySeq);

		CustomListener customListener = customKafkaListener.startContainer(
			customListenerList,
			containerId,
			topicNumber,
			mediaStartTime,
			homeTopicName,
			awayTopicName
		);
	}

	private void stopContainer(int topicNumber, Long gameSeq, String containerId) {
		log.info("경기 끝");
		MessageListenerContainer stoppedContainer = customKafkaListener.stopContainer(containerId);

		//////////////////////////Exception으로 변경 필요///////////////////////////
		if (stoppedContainer == null) {
			log.error("container가 없음");
			return;
		}

		streamBridge.send("highlight-out-0", getMessageDtoFromContainer(topicNumber, stoppedContainer, gameSeq));

		customListenerList.set(topicNumber - 1, null);
	}

	private HighLightMessageDto getMessageDtoFromContainer(
		int topicNumber,
		MessageListenerContainer messageListenerContainer,
		Long gameSeq
	) {
		HighLightMessageDto highLightMessageDto = HighLightMessageDto.builder().gameSeq(gameSeq).build();
		this.customListenerList.get(topicNumber - 1).getHighLightPositionList().forEach(
			highLightMessageDto::addHighLightPositionList
		);

		return highLightMessageDto;
	}

	// @Bean
	// public NewDestinationBindingCallback<KafkaConsumerProperties> dynamicConfigurer() {
	// 	return (name, channel, props, extended) -> {
	// 		props.setRequiredGroups("bindThisQueue");
	// 		extended.setQueueNameGroupOnly(true);
	// 		extended.setAutoBindDlq(true);
	// 		extended.setDeadLetterQueueName("myDLQ");
	// 	};
	// }

	/*
	게임 시작,끝 Event 받는 Consumer
	 */
	// @Bean
	// // @Async
	// public Consumer<GameConditionDto> getGameCondition(MessagePollScheduler messagePollScheduler) {
	// 	return gameConditionDto -> {
	// 		log.info(gameConditionDto.toString());
	//
	// 		Long gameSeq = gameConditionDto.getGameSeq();
	// 		GameActiveType gameActiveType = gameConditionDto.getGameActiveType();
	//
	// 		// this.setDestination(gameSeq);
	// 		log.info("gameSeq: {}", gameSeq);
	//
	// 		this.manageScheduler(messagePollScheduler, gameActiveType);
	// 		log.info("gameActiveType: {}", gameActiveType);
	// 	};
	// }

	/*
	내가 개발한,, 방법
	 */
	// private void setDestination(Long gameSeq) {
	//
	// 	Map<String, BindingProperties> bindings = bindingServiceProperties.getBindings();
	// 	BindingProperties pollableInputProperties = bindings.get("pollableInput-in-0");
	// 	log.info("현재 destination: {}", pollableInputProperties.getDestination());
	//
	// 	pollableInputProperties.setDestination("chat-room-" + gameSeq);
	// 	log.info("변경된 destination: {}", pollableInputProperties.getDestination());
	// 	// bindingService.doBindPollableConsumer(bindingName, MessageChannel.class, bindingProperties);
	// 	// bindingService.doBindPollableConsumer();
	//
	// }

	// private void manageScheduler(MessagePollScheduler messagePollScheduler, GameActiveType gameActiveType) {
	// 	if (gameActiveType == GameActiveType.IN_GAME) { //경기 시작
	// 		log.info("경기 시작");
	// 		messagePollScheduler.startTask();
	// 	} else if (gameActiveType == GameActiveType.AFTER_GAME) { //경기 끝
	// 		log.info("경기 끝");
	// 		messagePollScheduler.cancelTask();
	// 	}
	// }

}
