package com.backtoback.chat_log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableAsync
@EnableFeignClients
@EnableKafka
// @EnableDiscoveryClient
public class ChatLogApplication {

	// public static void main(String[] args) {
	// 	SpringApplication.run(ChatLogApplication.class, "--spring.cloud.stream.pollable-source=chat-room-1");
	// }

	public static void main(String[] args) {
		SpringApplication.run(ChatLogApplication.class, args);
	}

	// @Bean
	// public Supplier<ChatMessage> process2() {
	// 	return () -> {
	// 		log.info("##### 보낸다 ########");
	//
	// 		return new ChatMessage("hihi");
	// 	};
	// }

	/*
	게임 시작,끝 Event 받는 Consumer
	 */
	// @Bean
	// public Consumer<GameConditionDto> getGameCondition(MessagePollScheduler messagePollScheduler) {
	// 	return gameConditionDto -> {
	// 		log.info(gameConditionDto.toString());
	//
	// 		Long gameSeq = gameConditionDto.getGameSeq();
	// 		GameActiveType gameActiveType = gameConditionDto.getGameActiveType();
	//
	// 		// ScheduledFuture<?> scheduledFuture = null; //null 처리 필요
	//
	// 		if (gameActiveType == GameActiveType.IN_GAME) { //경기 시작
	// 			log.info("경기 시작");
	// 			messagePollScheduler.startTask();
	// 		} else if (gameActiveType == GameActiveType.AFTER_GAME) { //경기 끝
	// 			log.info("경기 끝");
	// 			messagePollScheduler.cancelTask();
	// 		}
	// 	};
	// }

	// @Bean
	// public Consumer<String> process() {
	// 	return msg -> {
	// 		log.info(msg);
	// 	};
	// }

	// @Bean
	// public Consumer<ChatMessageDto> pollableInput() {
	// 	return msg -> {
	// 		// log.info(msg.toString());
	// 	};
	// }

	// @Bean
	// public Consumer<List<ChatMessage>> process() {
	// 	return chatMessageList -> {
	// 		log.info("##### 시작한 시각 : {}", System.currentTimeMillis());
	//
	// 		log.info("##### message for process : {}", chatMessageList.size());
	// 		for (ChatMessage message : chatMessageList) {
	// 			log.info(message.getMessage());
	// 		}
	//
	// 		log.info("##### 끝나는 시각 : {}", System.currentTimeMillis());
	// 	};
	// }

	// @Bean
	// public ApplicationRunner pollableInput2(PollableMessageSource destIn) {
	// 	return args -> {
	// 		log.info("여기진입");
	// 		while (true) {
	// 			try {
	// 				if (!destIn.poll(m -> {
	// 					String newPayload = (new String((byte[])m.getPayload())).toUpperCase();
	// 					System.out.println("PAYLOAD: " + newPayload);
	// 					log.info("poll 중 - {}", newPayload);
	//
	// 				})) {
	// 					log.info("Thread Sleep...........");
	// 					boolean result = destIn.poll(m -> {
	// 						log.info(m.toString());
	// 						String newPayload = (new String((byte[])m.getPayload())).toUpperCase();
	// 						System.out.println("PAYLOAD: " + newPayload);
	// 						log.info("poll 중 - {}", newPayload);
	// 					});
	//
	// 					log.info(String.valueOf(result));
	// 					Thread.sleep(5000);
	// 				}
	// 			} catch (Exception e) {
	// 				e.printStackTrace();
	// 			}
	// 		}
	// 	};
	// }

	// @Bean
	// public Consumer<PollableMessageSource> process() {
	// 	return chatMessage -> {
	// 		log.info("##### message for process : {}", chatMessage.toString());
	// 	};
	// }

	// @Bean
	// public Function<Flux<ChatMessage>, Mono<Void>> direct() {
	// 	return myMessageFlux -> myMessageFlux.doOnNext(myMessage -> log.info("##### message for direct : {}", myMessage.getMessage()))
	// 		.then();
	// }



	/*
	주기적으로 Message polling
	 */
	// @Bean
	// public ApplicationRunner poller(PollableMessageSource destIn) {
	// 	return args -> {
	// 		while (true) {
	// 			try {
	// 				if (!destIn.poll(m -> {
	// 					String newPayload = ((String) m.getPayload()).toUpperCase();
	// 					// destOut.send(new GenericMessage<>(newPayload));
	// 				})) {
	// 					Thread.sleep(5000);
	// 				}
	// 			}
	// 			catch (Exception e) {
	// 				// handle failure
	// 			}
	// 		}
	// 	};
	// }
	//
	// @Scheduled(fixedDelay = 5_000)
	// public void poll() {
	// 	System.out.println("Polling...");
	// 	this.source.poll(m -> {
	// 		System.out.println(m.getPayload());
	//
	// 	}, new ParameterizedTypeReference<Foo>() { });
	// }
	//
	// boolean result = pollableSource.poll(received -> {
	// 	Map<String, Foo> payload = (Map<String, Foo>) received.getPayload();
	//         ...
	//
	// }, new ParameterizedTypeReference<Map<String, Foo>>() {});

}
