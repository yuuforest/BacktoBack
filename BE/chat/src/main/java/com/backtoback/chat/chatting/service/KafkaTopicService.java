package com.backtoback.chat.chatting.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface KafkaTopicService {

	void createTopic(List<String> topicNameList, int partitions, int replication);

	void deleteTopic(List<String> topicNameList);

}
