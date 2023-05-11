package com.backtoback.chat.chatting.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTopicServiceImpl implements KafkaTopicService{

	private final KafkaAdmin kafkaAdmin;

	@Override
	public void createTopic(List<String> topicNameList, int partitions, int replication) {
		AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
		List<NewTopic> newTopicList = new ArrayList<>();
		for(String topicName : topicNameList) {
			NewTopic newTopic = new NewTopic(topicName, partitions, (short)replication);
			newTopicList.add(newTopic);
		}
		CreateTopicsResult createTopicsResult = adminClient.createTopics(newTopicList);

		try{
			//토픽 생성이 완료되길 기다림 ... createTopics가 비동기여서 필요하다.
			createTopicsResult.all().get();
			log.info("토픽 생성 완료............................................");
		} catch(InterruptedException | ExecutionException e) {
			//토픽 생성 중 에러 처리
			e.printStackTrace();
		}
		adminClient.close();
	}

	@Override
	public void deleteTopic(List<String> topicNameList) {
		AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());

		try{
			//토픽 삭제가 완료되길 기다림 ... deleteTopics가 비동기여서 필요하다.
			DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(topicNameList);
			deleteTopicsResult.all().get();
			log.info("토픽 삭제 완료............................................");
		} catch (InterruptedException | ExecutionException e){
			//토픽 삭제 중 에러 처리
			e.printStackTrace();
		}
		adminClient.close();
	}
}
