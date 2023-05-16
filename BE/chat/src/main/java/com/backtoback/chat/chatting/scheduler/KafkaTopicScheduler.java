package com.backtoback.chat.chatting.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.backtoback.chat.chatting.dto.response.GameTeamSeqRes;
import com.backtoback.chat.chatting.service.FeignService;
import com.backtoback.chat.chatting.service.KafkaTopicService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaTopicScheduler {

	private final KafkaTopicService kafkaTopicService;
	private final String allChatPrefix = "chat.all.game.";
	private final String teamChatPrefix = "chat.team.";

	// @Scheduled(cron="0 0 6 * * *")		//MON-SUN 06:00 AM
	public void createTopic() {
		log.info("[CREATE SCHEDULER START].........................................................");

		List<String> topicList = new ArrayList<>();

		for(int i = 1; i <= 5; i++){
			String newTopicName = allChatPrefix + i;
			topicList.add(newTopicName);
		}

		for(int i = 1; i <= 10; i++){
			String newTopicName = teamChatPrefix + i;
			topicList.add(newTopicName);
		}

		kafkaTopicService.createTopic(topicList, 1, 1);
	}

	// @Scheduled(cron="0 0 5 * * *")		//MON-SUN 05:00 AM
	public void deleteTopic() {
		log.info("[DELETE SCHEDULER START].........................................................");

		List<String> topicList = new ArrayList<>();

		for(int i = 1; i <= 5; i++){
			String newTopicName = allChatPrefix + i;
			topicList.add(newTopicName);
		}

		for(int i = 1; i <= 10; i++){
			String newTopicName = teamChatPrefix + i;
			topicList.add(newTopicName);
		}

		kafkaTopicService.deleteTopic(topicList);
	}
}

