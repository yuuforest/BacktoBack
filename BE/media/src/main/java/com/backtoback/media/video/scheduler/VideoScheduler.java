package com.backtoback.media.video.scheduler;

import com.backtoback.media.game.domain.GameActiveType;
import com.backtoback.media.video.dto.MessageDto;
import com.backtoback.media.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class VideoScheduler {

    private final StreamBridge streamBridge;
    private final VideoService videoService;

    @Scheduled(cron = "0 1/2 * * * *")
    public void startGame() {
        videoService.startTodayGame();
    }


}
