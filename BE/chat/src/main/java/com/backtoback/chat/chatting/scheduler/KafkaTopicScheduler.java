package com.backtoback.chat.chatting.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.backtoback.chat.chatting.service.FeignService;
import com.backtoback.chat.chatting.service.KafkaTopicService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaTopicScheduler {

	private KafkaTopicService kafkaTopicService;
	private FeignService feignService;

	private final String allChatPrefix = "chat.all.game.";
	private final String teamChatPrefix = "chat.team.";
	private final String teamChatPostfix = ".game.";

	//화~일 아침 7시 토픽 생성
	@Scheduled(cron="0 0 7 * * 2-7")
	public void createTopic(){

		List<String> topicList = new ArrayList<>();
		List<Long> gameSeqList = feignService.getTodayGameSeq();

		log.debug("[CREATE SCHEDULER START].........................................................");
		log.debug("[CREATE TOPIC] gameSeqList............................................: {}", gameSeqList);

		if(gameSeqList != null && gameSeqList.size() != 0){
			//전체 채팅 토픽 생성
			for(Long gameSeq : gameSeqList){
				String newTopicName = allChatPrefix + gameSeq;
				topicList.add(newTopicName);
			}

			//팀 채팅 토픽 생성
			for(Long gameSeq : gameSeqList){
				for(int i = 1; i <= 10; i++){
					String newTopicName = teamChatPrefix + i + teamChatPostfix + gameSeq;
					topicList.add(newTopicName);
				}
			}

			kafkaTopicService.createTopic(topicList, 0, 0);
		}
	}

	//월, 수~일 새벽 2시 토픽 삭제
	@Scheduled(cron="0 0 2 * * 1,3-7")
	public void deleteTopic(){
		List<String> topicList = new ArrayList<>();
		List<Long> gameSeqList = feignService.getYesterdayGameSeq();

		log.debug("[DELETE SCHEDULER START].........................................................");
		log.debug("[DELETE TOPIC] gameSeqList............................................: {}", gameSeqList);

		if(gameSeqList != null && gameSeqList.size() != 0){
			//전체 토픽 삭제
			for(Long gameSeq : gameSeqList){
				String deleteTopicName = allChatPrefix + gameSeq;
				topicList.add(deleteTopicName);
			}
			//팀 토픽 삭제
			for(Long gameSeq : gameSeqList){
				for(int i = 1; i <= 10; i++){
					String deleteTopicName = teamChatPrefix + i + teamChatPostfix + gameSeq;
					topicList.add(deleteTopicName);
				}
			}

			kafkaTopicService.deleteTopic(topicList);
		}
	}
}
