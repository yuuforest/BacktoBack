package com.backtoback.business.gamemanager.scheduler;


import com.backtoback.business.gamemanager.feignclient.CheerServiceClient;
import com.backtoback.business.gamemanager.feignclient.PointServiceClient;
import com.backtoback.business.gamemanager.feignclient.VideoServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Slf4j
@RequiredArgsConstructor
@Component
@EnableAsync
@Configuration
public class GameScheduler {

	private final VideoServiceClient videoServiceClient;

    private final PointServiceClient pointServiceClient;

	private final CheerServiceClient cheerServiceClient;

	private final StreamBridge streamBridge;

//	@Scheduled(cron = "0 0 0 * * *")
//    public void midnightScheduler() {
//		videoServiceClient.makeRoom();
//		pointServiceClient.readyToStartBetting();
//    }

	@Scheduled(initialDelay = 20000, fixedDelay = 300000)
	public void dateChange() {
		log.info("dateChange!!!!!!");
		videoServiceClient.makeRoom();
		pointServiceClient.readyToStartBetting();
		cheerServiceClient.readyToStartCheer();
	}

}
