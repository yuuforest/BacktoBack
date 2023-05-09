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
	private final FeignService feignService;

	private final String allChatPrefix = "chat.all.game.";
	private final String teamChatPrefix = "chat.team.";
	private final String teamChatPostfix = ".game.";

	//화~일 아침 7시 토픽 생성
	// @Scheduled(cron="0 0 7 * * 2-7")
	// @Scheduled(initialDelay = 10000, fixedDelay = 10000000)
	@Scheduled(cron = "0 29 17 * * *")
	public void createTopic(){
		log.info("[CREATE SCHEDULER START].........................................................");

		List<String> topicList = new ArrayList<>();

		log.info("list는 생성됨..................................");
		System.out.println(feignService+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		List<Long> gameSeqList = feignService.getTodayGameSeq();

		log.info("[CREATE TOPIC] gameSeqList............................................: {}", gameSeqList);

		if(gameSeqList != null && gameSeqList.size() != 0){
			//전체 채팅 토픽 생성
			for(Long gameSeq : gameSeqList){
				String newTopicName = allChatPrefix + gameSeq;
				topicList.add(newTopicName);
			}

			//팀 채팅 토픽 생성 : gameSeq에 따른 어떤 team이 참여하는 지 알아야 함.
			for(Long gameSeq : gameSeqList){
				GameTeamSeqRes teams = feignService.getGameTeamSeq(gameSeq);
				String topic1 = teamChatPrefix + teams.getHomeSeq() + teamChatPostfix + gameSeq;
				String topic2 = teamChatPrefix + teams.getAwaySeq() + teamChatPostfix + gameSeq;
				topicList.add(topic1);
				topicList.add(topic2);
			}

			kafkaTopicService.createTopic(topicList, 1, 1);
		}
	}

	//월, 수~일 새벽 2시 토픽 삭제
	// @Scheduled(cron="0 0 2 * * 1,3-7")
	@Scheduled(cron = "0 38 17 * * *")
	public void deleteTopic(){
		log.info("[DELETE SCHEDULER START].........................................................");

		List<String> topicList = new ArrayList<>();
		List<Long> gameSeqList = feignService.getYesterdayGameSeq();

		log.info("[DELETE TOPIC] gameSeqList............................................: {}", gameSeqList);

		if(gameSeqList != null && gameSeqList.size() != 0){
			//전체 토픽 삭제
			for(Long gameSeq : gameSeqList){
				String deleteTopicName = allChatPrefix + gameSeq;
				topicList.add(deleteTopicName);
			}
			//팀 토픽 삭제
			for(Long gameSeq : gameSeqList){
				GameTeamSeqRes teams = feignService.getGameTeamSeq(gameSeq);
				String topic1 = teamChatPrefix + teams.getHomeSeq() + teamChatPostfix + gameSeq;
				String topic2 = teamChatPrefix + teams.getAwaySeq() + teamChatPostfix + gameSeq;
				topicList.add(topic1);
				topicList.add(topic2);
			}

			kafkaTopicService.deleteTopic(topicList);
		}
	}
}
