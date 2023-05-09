package com.backtoback.business.common.config;


import com.backtoback.business.common.dto.MessageDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class KafkaConfig {

  @Bean
  public Consumer<MessageDto> consumer() {
    return message -> System.out.println("received " + message);
  }
}
