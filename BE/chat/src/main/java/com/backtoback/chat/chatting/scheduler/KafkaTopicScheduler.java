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

	//화~일 아침 7시 토픽 생성
	// @Scheduled(cron="0 0 7 * * 2-7")
	@Scheduled(cron = "0 16 10 * * *")
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

	//월, 수~일 새벽 2시 토픽 삭제
	// @Scheduled(cron="0 0 2 * * 1,3-7")
	@Scheduled(cron = "0 15 10 * * *")
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

