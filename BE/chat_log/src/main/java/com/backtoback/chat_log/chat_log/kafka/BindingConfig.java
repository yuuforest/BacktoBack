package com.backtoback.chat_log.chat_log.kafka;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backtoback.chat_log.chat_log.dto.request.GameConditionDto;
import com.backtoback.chat_log.entity.GameActiveType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BindingConfig {

	private final CustomKafkaListener customKafkaListener;

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

	@Bean
	// @Async
	public Consumer<GameConditionDto> getGameCondition() {
		return gameConditionDto -> {
			log.info(gameConditionDto.toString());

			Long gameSeq = gameConditionDto.getGameSeq();
			GameActiveType gameActiveType = gameConditionDto.getGameActiveType();

			// this.setDestination(gameSeq);
			log.info("gameSeq: {}", gameSeq);
			log.info("gameActiveType: {}", gameActiveType);

			this.manageContainer(gameActiveType);
		};
	}


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

	private void manageContainer(GameActiveType gameActiveType) {
		if (gameActiveType == GameActiveType.IN_GAME) { //경기 시작
			log.info("경기 시작");
			customKafkaListener.start();
		} else if (gameActiveType == GameActiveType.AFTER_GAME) { //경기 끝
			log.info("경기 끝");
			customKafkaListener.stop();
		}
	}

}
