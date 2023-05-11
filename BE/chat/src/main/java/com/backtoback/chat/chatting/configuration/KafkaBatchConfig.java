package com.backtoback.chat.chatting.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;

import com.backtoback.chat.chatting.domain.ChatLog;
import com.backtoback.chat.chatting.dto.request.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class KafkaBatchConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final KafkaConsumerConfig kafkaConsumerConfig;
	private final KafkaTopicProperties kafkaTopicProperties;

	/**
	 * [Job 생성]
	 */
	@Bean
	public Job chatLogJob(List<Step> steps) {
		log.info("[Job Start]...........................................................");
		SimpleJobBuilder job = jobBuilderFactory.get("chatLogJob").start(steps.get(0));
		for(int i = 1, stepSize = steps.size(); i < stepSize; i++){
			log.info("job Number.....................................................: {}", i);
			job.next(steps.get(i));
		}
		return job.build();
	}

	/**
	 * [Step 구성]
	 */
	@Bean
	public List<Step> steps(List<KafkaItemReader<Long, ChatMessage>> itemReaders,
							ItemProcessor<ChatMessage, ChatLog> itemProcessor,
							JpaItemWriter<ChatLog> itemWriter) {
		List<Step> steps = new ArrayList<>();

		for(int i = 0, itemSize = itemReaders.size(); i < itemSize; i++){
			log.info("Item No................................................: {}", i);
			Step step = stepBuilderFactory.get("step" + (i + 1))
				.<ChatMessage, ChatLog>chunk(10)
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
	public List<KafkaItemReader<Long, ChatMessage>> itemReaders() {

		List<String> topics = kafkaTopicProperties.getTopics();

		log.info("[Item Reader Start]...........................................................");
		List<KafkaItemReader<Long, ChatMessage>> readers = new ArrayList<>();

		//Consumer config
		ConsumerFactory<Long, ChatMessage> consumerFactory = kafkaConsumerConfig.consumerFactory();
		Map<String, Object> consumerProps = consumerFactory.getConfigurationProperties();
		Properties props = new Properties();
		props.putAll(consumerProps);

		for(String topic : topics){
			log.info("Topic......................................................:{}", topic);
			KafkaItemReader<Long, ChatMessage> reader = new KafkaItemReaderBuilder<Long, ChatMessage>()
				.name("reader_" + topic)
				.saveState(true)
				.topic(topic)
				.partitions(0)
				.consumerProperties(props)
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
	public JpaItemWriter<ChatLog> itemWriter(EntityManagerFactory entityManagerFactory){
		log.info("[Item Writer Start]...........................................................");
		JpaItemWriter<ChatLog> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}

}
