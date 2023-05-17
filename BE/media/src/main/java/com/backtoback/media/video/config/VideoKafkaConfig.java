package com.backtoback.media.video.config;


import com.backtoback.media.video.dto.HighLightMessageDto;
import com.backtoback.media.video.dto.HighLightPosition;
import com.backtoback.media.video.dto.MessageDto;
import com.backtoback.media.video.service.MpegService;
import com.backtoback.media.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class VideoKafkaConfig {

    private final VideoService videoService;

    private final MpegService mpegService;

    @Bean
    public Consumer<MessageDto> consumer() {
        return message -> System.out.println("received " + message);
    }

    @Bean
    public Consumer<HighLightMessageDto> highlight(){
        return message -> {
            try {
                videoService.makeHighLight(message);
                videoService.sendHighLight(message.getGameSeq());
                videoService.deleteHighLight(message.getGameSeq());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
