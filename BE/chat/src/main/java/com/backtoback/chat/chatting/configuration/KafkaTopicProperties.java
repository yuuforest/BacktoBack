package com.backtoback.chat.chatting.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "chat.kafka")
@Getter
@Setter
public class KafkaTopicProperties {

	private final List<String> topics = new ArrayList<>();

}

