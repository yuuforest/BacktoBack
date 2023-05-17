package com.backtoback.chat_log.chat_log.kafka;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backtoback.chat_log.chat_log.domain.GameActiveType;
import com.backtoback.chat_log.chat_log.dto.request.GameConditionDto;
import com.backtoback.chat_log.chat_log.dto.response.GameTeamSeqResponseDto;
import com.backtoback.chat_log.chat_log.service.BusinessService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BindingConfig {

	private final CustomKafkaListener customKafkaListener;
	private final BusinessService businessService;

	@Bean
	// @Async
	public Consumer<GameConditionDto> getGameCondition() {
		return gameConditionDto -> {
			log.info(gameConditionDto.toString());

			Long gameSeq = gameConditionDto.getGameSeq();
			GameActiveType gameActiveType = gameConditionDto.getGameActiveType();

			GameTeamSeqResponseDto gameTeamSeqResponseDto = businessService.getGameTeamSeqAndTopicNumber(gameSeq);

			String containerId = customKafkaListener.getContainerId(gameTeamSeqResponseDto.getTopicNumber());

			if (gameActiveType == GameActiveType.IN_GAME) { //경기 시작
				this.startContainer(
					containerId,
					gameTeamSeqResponseDto.getHomeSeq(),
					gameTeamSeqResponseDto.getAwaySeq()
				);
			} else if (gameActiveType == GameActiveType.AFTER_GAME) { //경기 끝
				this.stopContainer(containerId);
			}
		};
	}

	private void startContainer(String containerId, Long homeSeq, Long awaySeq) {
		log.info("경기 시작");

		String homeTopicName = customKafkaListener.getChatTeamTopicName(homeSeq);
		String awayTopicName = customKafkaListener.getChatTeamTopicName(awaySeq);

		customKafkaListener.startContainer(containerId, homeTopicName, awayTopicName);
	}

	private void stopContainer(String containerId) {
		log.info("경기 끝");
		customKafkaListener.stopContainer(containerId);
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
