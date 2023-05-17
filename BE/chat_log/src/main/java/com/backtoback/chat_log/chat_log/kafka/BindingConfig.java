package com.backtoback.chat_log.chat_log.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.MessageListenerContainer;

import com.backtoback.chat_log.chat_log.domain.GameActiveType;
import com.backtoback.chat_log.chat_log.dto.common.GameConditionDto;
import com.backtoback.chat_log.chat_log.dto.response.GameTeamSeqResponseDto;
import com.backtoback.chat_log.chat_log.dto.common.HighLightMessageDto;
import com.backtoback.chat_log.chat_log.service.BusinessService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BindingConfig {

	private final CustomKafkaListener customKafkaListener;
	private final BusinessService businessService;
	private final StreamBridge streamBridge;
	private final List<CustomListener> customListenerList = new ArrayList<>(5);
	// private final CustomListener[] customListenerList = new CustomListener[5];

	@PostConstruct
	private void init() {
		for (int i = 0; i < 5; i++) {
			customListenerList.add(null);
		}
	}

	@Bean
	// @Async
	public Consumer<GameConditionDto> getGameCondition() {
		return gameConditionDto -> {
			log.info(gameConditionDto.toString());

			Long gameSeq = gameConditionDto.getGameSeq();
			GameActiveType gameActiveType = gameConditionDto.getGameActiveType();
			Long mediaStartTime = gameConditionDto.getMediaStartTime(); //배포용
			// Long mediaStartTime = System.currentTimeMillis(); //로컬테스트용

			log.info("mediaStartTime: " + mediaStartTime);

			GameTeamSeqResponseDto gameTeamSeqResponseDto = businessService.getGameTeamSeqAndTopicNumber(gameSeq);

			int topicNumber = gameTeamSeqResponseDto.getTopicNumber();
			String containerId = customKafkaListener.getContainerId(topicNumber);

			if (gameActiveType == GameActiveType.IN_GAME) { //경기 시작
				this.startContainer(
					containerId,
					topicNumber,
					gameTeamSeqResponseDto.getHomeSeq(),
					gameTeamSeqResponseDto.getAwaySeq(),
					mediaStartTime
				);
			} else if (gameActiveType == GameActiveType.AFTER_GAME) { //경기 끝
				this.stopContainer(topicNumber, gameSeq, containerId);
			}
		};
	}

	private void startContainer(String containerId, int topicNumber, Long homeSeq, Long awaySeq, Long mediaStartTime) {
		log.info("경기 시작");

		String homeTopicName = customKafkaListener.getChatTeamTopicName(homeSeq);
		String awayTopicName = customKafkaListener.getChatTeamTopicName(awaySeq);

		customKafkaListener.startContainer(
			customListenerList,
			containerId,
			topicNumber,
			mediaStartTime,
			homeTopicName,
			awayTopicName
		);
	}

	private void stopContainer(int topicNumber, Long gameSeq, String containerId) {
		log.info("경기 끝");
		MessageListenerContainer stoppedContainer = customKafkaListener.stopContainer(containerId);

		//////////////////////////Exception으로 변경 필요///////////////////////////
		if (stoppedContainer == null) {
			log.error("container가 없음");
			return;
		}

		streamBridge.send("highlight-out-0", getMessageDtoFromContainer(topicNumber, gameSeq));

		customListenerList.set(topicNumber - 1, null);
	}

	private HighLightMessageDto getMessageDtoFromContainer(
		int topicNumber,
		Long gameSeq
	) {
		HighLightMessageDto highLightMessageDto = HighLightMessageDto.builder().gameSeq(gameSeq).build();
		this.customListenerList.get(topicNumber - 1).getHighLightPositionList().forEach(
			highLightMessageDto::addHighLightPositionList
		);

		return highLightMessageDto;
	}

}
