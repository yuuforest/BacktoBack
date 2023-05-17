// package com.backtoback.chat_log.chat_log.kafka;
//
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.messaging.handler.annotation.Payload;
//
// public class KafkaConsumer2 {
//
// 	private int count1 = 0;
// 	private int count2 = 0;
// 	private int count3 = 0;
//
// 	@KafkaListener(topics = {"topic1", "topic2", "topic3"})
// 	public void listen(@Payload String message) {
// 		if ("topic1".equals(topic)) {
// 			count1++;
// 		} else if ("topic2".equals(topic)) {
// 			count2++;
// 		} else if ("topic3".equals(topic)) {
// 			count3++;
// 		}
// 	}
//
// }
