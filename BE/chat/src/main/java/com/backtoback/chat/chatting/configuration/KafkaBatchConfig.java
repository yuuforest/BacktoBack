package com.backtoback.chat.chatting.configuration;

import static org.springframework.batch.item.kafka.KafkaItemReader.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backtoback.chat.chatting.domain.ChatLog;
import com.backtoback.chat.chatting.dto.request.ChatMessage;
import com.backtoback.chat.chatting.service.ChatMessageJsonDeserializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class KafkaBatchConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final KafkaTopicProperties kafkaTopicProperties;
	private final DataSource dataSource;

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;


	/**
	 * [Job 생성]
	 */
	@Bean
	public Job chatLogJob(List<Step> steps) {
		log.info("[Job Start]...........................................................");
		SimpleJobBuilder job = jobBuilderFactory.get("chatLogJob").start(steps.get(0));
		for(int i = 1, stepSize = steps.size(); i < stepSize; i++){
			job.next(steps.get(i));
		}
		return job.build();
	}

	/**
	 * [Step 구성]
	 */
	@Bean
	public List<Step> steps(List<KafkaItemReader<String, ChatMessage>> itemReaders,
							ItemProcessor<ChatMessage, ChatLog> itemProcessor,
							ItemWriter<ChatLog> itemWriter) {
		log.info("[Step Start]...........................................................");
		List<Step> steps = new ArrayList<>();

		for(int i = 0, itemSize = itemReaders.size(); i < itemSize; i++){
			Step step = stepBuilderFactory.get("step" + (i + 1))
				.<ChatMessage, ChatLog>chunk(100)
				.reader(itemReaders.get(i))
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();

			steps.add(step);
		}
		return steps;
	}

	/**
	 * [Item Reader] Read Kafka Topics' Data
	 */
	@Bean
	public List<KafkaItemReader<String, ChatMessage>> itemReaders() {

		List<String> topics = kafkaTopicProperties.getTopics();

		log.info("[Item Reader Start]...........................................................");
		List<KafkaItemReader<String, ChatMessage>> readers = new ArrayList<>();

		//Consumer config
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ChatMessageJsonDeserializer.class);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-chat-batch-group");

		Properties props = new Properties();
		props.putAll(config);

		System.out.println("###########################Consumer Config###############################");
		log.info("{}", props);

		for(String topic : topics){
			log.info("Topic......................................................:{}", topic);
			KafkaItemReader<String, ChatMessage> reader = new KafkaItemReaderBuilder<String, ChatMessage>()
				.name("reader_" + topic)
				.saveState(true)
				.topic(topic)
				.partitions(0)
				.consumerProperties(props)
				.partitionOffsets(new HashMap<>())
				.build();

			readers.add(reader);
		}
		return readers;
	}

	/**
	 * [Item Processor] Convert data type : ChatMessage -> ChatLog
	 */
	@Bean
	public ItemProcessor<ChatMessage, ChatLog> itemProcessor(){
		log.info("[Item Processor Start]...........................................................");
		return chatMessage -> {
			log.info("ChatMessage ------------------> ChatLog");
			ChatLog chatLog = ChatLog.builder()
				.gameSeq(chatMessage.getGameSeq())
				.memberSeq(chatMessage.getMemberSeq())
				.nickname(chatMessage.getNickname())
				.message(chatMessage.getMessage())
				.time(chatMessage.getTime())
				.build();
			return chatLog;
		};
	}

	/**
	 * [Item Writer] Save Data
	 */
	@Bean
	public ItemWriter<ChatLog> itemWriter(){
		return new JdbcBatchItemWriterBuilder<ChatLog>()
			.dataSource(dataSource)
			.sql("insert into chatlogs(chatlog_seq, game_seq, member_seq, nickname, message, time) "
				+ "values (:chatLogSeq, :gameSeq, :memberSeq, :nickname, :message, :time)")
			.beanMapped()
			.build();
	}

}
