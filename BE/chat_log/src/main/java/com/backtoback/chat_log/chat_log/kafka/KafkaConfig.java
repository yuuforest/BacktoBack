package com.backtoback.chat_log.chat_log.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.backtoback.chat_log.chat_log.dto.common.ChatMessageDto;

@Configuration
public class KafkaConfig {

	// @Value("${spring.kafka.consumer.bootstrap-servers}")
	// String BOOTSTRAP_SERVER;

	@Bean
	KafkaListenerContainerFactory<?> customKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ChatMessageDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(1);
		// factory.setMessageConverter(new JsonMessageConverter());
		factory.setAutoStartup(false);
		factory.setBatchListener(true);
		factory.getContainerProperties().setPollTimeout(1);
		factory.getContainerProperties().setIdleBetweenPolls(2500);
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);

		return factory;
	}

	@Bean
	public ConsumerFactory<String, ChatMessageDto> consumerFactory() {
		return new DefaultKafkaConsumerFactory<String, ChatMessageDto>(
			consumerConfigs(),
			new StringDeserializer(),
			new JsonDeserializer<>()
		);
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10000");
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(
			ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
			ErrorHandlingDeserializer.class);
		props.put(
			ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
			ErrorHandlingDeserializer.class);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

		props.put("spring.deserializer.key.delegate.class", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("spring.deserializer.value.delegate.class",
			"org.springframework.kafka.support.serializer.JsonDeserializer");
		props.put("spring.json.value.default.type", "com.backtoback.chat_log.chat_log.dto.common.ChatMessageDto");
		props.put("spring.json.trusted.packages", "com.backtoback.chat_log.chat_log.dto");

		// props.put(ProducerConfig.BOOTSTRAP_SERV ERS_CONFIG, embeddedKafka.getBrokersAsString());
		return props;
	}

}