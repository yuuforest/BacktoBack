package com.backtoback.chat.chatting.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public ProducerFactory<String, ChatMessage> producerFactory(){
		return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
	}

	@Bean
	public Map<String, Object> kafkaProducerConfiguration(){
		Map<String, Object> configurations = new HashMap<>();
		//브로커 주소 설정 : 카프카 주소 + 포트
		configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		//key Serializer
		configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		//value Serializer
		configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return configurations;
	}

	@Bean
	public KafkaTemplate<String, ChatMessage> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}

}
