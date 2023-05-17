// package com.backtoback.chat_log.chat_log.kafka;
//
// import org.springframework.cloud.stream.annotation.EnableBinding;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;
//
// import com.backtoback.chat_log.chat_log.dto.common.ChatMessageDto;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Component
// @EnableBinding({ChatMessageProcessor.class})//Here we bind our custom channel
// // @EnableAsync
// @RequiredArgsConstructor
// public class PollableMessageSourceRunner2 {
//
// 	private final ChatMessageProcessor chatMessageProcessor;
//
// 	/*
// 	ver.1 @Scheduled 방식 -> hasMessage가 계속 존재하는 한 함수 자체가 안 끝난다. 계속 가져옴
// 	 */
// 	//Every 5000ms this method will be executed
// 	// @Scheduled(fixedDelay = 5000)
// 	// // @Async
// 	// public void pollMessages() throws InterruptedException {
// 	// 	log.info("##########pollMessage() start###########");
// 	//
// 	// 	boolean hasMessage = true;
// 	//
// 	// 	// 	//.poll() method returns boolean,
// 	// 	// 	//e.g. if kafka topic contained message it will return 'true'
// 	// 	// 	//and it will execute method inside .poll()
// 	// 		while (hasMessage) {
// 	// 			//we are pulling message by message from the topic,
// 	// 			//if topic empty we stop
// 	// 			// hasMessage = chatMessageProcessor.pollableInput().poll(
// 	// 			hasMessage = chatMessageProcessor.pollableInput().poll(
// 	// 				message -> {
// 	// 					//send our message to external service
// 	// 					// externalService.enrichData(message.getPayload().toString());
// 	// 					log.info(message.getPayload().toString());
// 	// 				}
// 	// 			);
// 	// 		}
// 	//
// 	// 	log.info("##########pollMessage() finish###########");
// 	// }
//
// 	/*
// 	ver.2 쓰레드 sleep - 이것도 마찬가지로 while문이 끝나야 실행이 되는데 while문이 안 끝나면 소용 없음
// 	 */
// 	// //Every 5000ms this method will be executed
// 	// // @Scheduled(fixedDelayString = "5000")
// 	// @Scheduled(fixedDelay = 5000)
// 	// // @Async
// 	// public void pollMessages() throws InterruptedException {
// 	// 	log.info("##########pollMessage() start###########");
// 	//
// 	// 	boolean hasMessage = true;
// 	//
// 	// 	// 	//.poll() method returns boolean,
// 	// 	// 	//e.g. if kafka topic contained message it will return 'true'
// 	// 	// 	//and it will execute method inside .poll()
// 	// 	while (hasMessage) {
// 	// 		//we are pulling message by message from the topic,
// 	// 		//if topic empty we stop
// 	// 		// hasMessage = chatMessageProcessor.pollableInput().poll(
// 	// 		hasMessage = chatMessageProcessor.pollableInput().poll(
// 	// 			message -> {
// 	// 				//send our message to external service
// 	// 				// externalService.enrichData(message.getPayload().toString());
// 	// 				log.info(message.getPayload().toString());
// 	// 			}
// 	// 		);
// 	// 	}
// 	//
// 	// 	log.info("##########pollMessage() finish###########");
// 	// 	Thread.sleep(5000);
// 	// }
//
// 	/*
// 	ver.3 while문 조건을 poll의 결과 값이 아닌 남은 시간 타이머 개념으로 사용.
// 	 */
// 	// @Async
// 	@Scheduled(fixedDelay = 5000)
// 	public void pollMessages() throws InterruptedException {
// 		log.info("##########pollMessage() start###########");
//
// 		long startTime = System.currentTimeMillis(); // 현재 시간 측정
//
// 		while (System.currentTimeMillis() - startTime < 5000) { //5초동안 poll
// 			chatMessageProcessor.pollableInput().poll(
// 				message -> {
// 					byte[] payload = (byte[])message.getPayload();
// 					log.info(ChatMessageDto.from(payload).toString());
// 				}
// 			);
// 		}
//
// 		log.info("##########pollMessage() finish###########");
// 	}
//
// }
