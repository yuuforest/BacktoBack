package com.backtoback.business.common.config;

import java.util.function.Consumer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backtoback.business.common.dto.GameConditionDto;
import com.backtoback.business.common.dto.GameResultKafkaRes;
import com.backtoback.business.game.domain.GameActiveType;
import com.backtoback.business.game.service.GameService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaConfig {

	private final StreamBridge streamBridge;
	private final GameService gameService;

	@Bean
	// @Async
	public Consumer<GameConditionDto> getGameCondition() {
		return gameConditionDto -> {
			log.info(gameConditionDto.toString());

			Long gameSeq = gameConditionDto.getGameSeq();
			GameActiveType gameActiveType = gameConditionDto.getGameActiveType();

			if (gameActiveType == GameActiveType.AFTER_GAME) { //경기 끝
				//경기 결과 크롤링
				gameService.afterGameResult(gameSeq);
				streamBridge.send("game-result-out-0", new GameResultKafkaRes(gameSeq));
			}
		};
	}

}
