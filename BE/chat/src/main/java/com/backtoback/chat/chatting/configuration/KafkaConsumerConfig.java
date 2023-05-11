package com.backtoback.chat.chatting.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.backtoback.chat.chatting.dto.request.ChatMessage;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Long, ChatMessage> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<Long, ChatMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<Long, ChatMessage> consumerFactory(){
		//ChatMessage 객체를 Kafka에서 해독할 수 있도록 해줌
		JsonDeserializer<ChatMessage> deserializer = new JsonDeserializer<>(ChatMessage.class);
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);

		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);	//브로커 주소 설정 : 카프카 주소 + 포트
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);	//key Deserializer
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);	//value Deserializer
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);

		return new DefaultKafkaConsumerFactory<>(config, new LongDeserializer(), deserializer);
	}

	// @Bean
	// public KafkaItemReader<Long, ChatMessage> kafkaItemReader(){
	// 	ConsumerFactory<Long, ChatMessage> consumerFactory = consumerFactory();
	// 	Map<String, Object> consumerProps = consumerFactory.getConfigurationProperties();
	// 	Properties properties = new Properties();
	// 	properties.putAll(consumerProps);
	//
	// 	return new KafkaItemReaderBuilder<Long, ChatMessage>()
	// 		.name("kafkaItemReader")
	// 		.partitions(0)
	// 		.consumerProperties(properties)
	// 		.topic(Pattern.compile("chat.*"))
	// 		.build();
	// }

}
