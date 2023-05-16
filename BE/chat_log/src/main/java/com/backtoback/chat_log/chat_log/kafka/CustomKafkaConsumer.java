// package com.backtoback.chat_log.chat_log.kafka;
//
// import java.time.Duration;
// import java.util.Arrays;
// import java.util.Properties;
//
// import org.apache.kafka.clients.consumer.Consumer;
// import org.apache.kafka.clients.consumer.ConsumerConfig;
// import org.apache.kafka.clients.consumer.ConsumerRecords;
// import org.apache.kafka.clients.consumer.KafkaConsumer;
// import org.apache.kafka.common.serialization.StringDeserializer;
//
// public class CustomKafkaConsumer {
//
// 	// Kafka Consumer 설정
// 	Properties props = new Properties();
// 	// props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
// 	// props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
// 	// props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
// 	// props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//
// 	// Kafka Consumer 생성
// 	KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//
// 	// 구독할 토픽 지정
// 	consumer.(Arrays.asList("test-topic"));
//
// 	// 5초 동안 쌓인 모든 메시지를 가져옴
// 	ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
//
// 	// 메시지 처리
// 	records.forEach(record -> {
// 		System.out.printf("Received message: %s\n", record.value());
// 	});
//
// 	// Consumer 종료
// 	consumer.close();
//
// }
