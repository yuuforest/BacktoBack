package com.backtoback.chat.chatting.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.backtoback.chat.chatting.dto.request.ChatMessage;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

	@Bean
	public ProducerFactory<Long, ChatMessage> producerFactory(){
		return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
	}

	@Bean
	public Map<String, Object> kafkaProducerConfiguration(){
		Map<String, Object> configurations = new HashMap<>();
		//브로커 주소 설정 : 미정
		configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		//key Serializer
		configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
		//value Serializer
		configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return configurations;
	}

	@Bean
	public KafkaTemplate<Long, ChatMessage> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}

}
