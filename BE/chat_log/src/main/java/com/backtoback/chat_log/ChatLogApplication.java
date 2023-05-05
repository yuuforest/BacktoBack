package com.backtoback.chat_log;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.cloud.stream.binder.PollableSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.backtoback.chat_log.chat_log.dto.request.ChatMessage;
import com.backtoback.chat_log.chat_log.dto.request.ChatRoom;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableAsync
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

	// @Bean
	// public Consumer<ChatMessage> process() {
	// 	return chatMessage -> {
	// 		log.info("##### message for process : {}", chatMessage.toString());
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
	// public ApplicationRunner poller(PollableMessageSource destIn) {
	// 	return args -> {
	// 		log.info("여기진입");
	// 		while (true) {
	// 			try {
	// 				if (!destIn.poll(m -> {
	// 					String newPayload = (new String((byte[]) m.getPayload())).toUpperCase();
	// 					System.out.println("PAYLOAD: " + newPayload);
	// 					log.info("poll 중 - {}", newPayload);
	//
	// 				})) {
	// 					log.info("Thread Sleep...........");
	// 					boolean result = destIn.poll(m -> {
	// 						log.info(m.toString());
	// 						String newPayload = (new String((byte[]) m.getPayload())).toUpperCase();
	// 						System.out.println("PAYLOAD: " + newPayload);
	// 						log.info("poll 중 - {}", newPayload);
	// 					});
	//
	// 					log.info(String.valueOf(result));
	// 					Thread.sleep(5000);
	// 				}
	// 			}
	// 			catch (Exception e) {
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
