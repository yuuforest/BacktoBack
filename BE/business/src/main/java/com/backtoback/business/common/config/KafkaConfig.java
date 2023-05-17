package com.backtoback.business.common.config;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backtoback.business.common.dto.MessageDto;

@Configuration
public class KafkaConfig {

	@Bean
	public Consumer<MessageDto> consumer() {
		return message -> System.out.println("received " + message);
	}

}
