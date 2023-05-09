package com.backtoback.media.video.config;


import com.backtoback.media.video.dto.MessageDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class VideoKafkaConfig {

    @Bean
    public Consumer<MessageDto> consumer() {
        return message -> System.out.println("received " + message);
    }
}
