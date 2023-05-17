package com.backtoback.chat_log.chat_log.kafka;

import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.BatchConsumerAwareMessageListener;
import org.springframework.stereotype.Component;

import com.backtoback.chat_log.chat_log.dto.request.ChatMessageDto;

import lombok.extern.slf4j.Slf4j;

@Component(value = "customListener")
@Slf4j
public class CustomListener implements BatchConsumerAwareMessageListener<String, ChatMessageDto> {

	@Override
	public void onMessage(List<ConsumerRecord<String, ChatMessageDto>> data) {
		BatchConsumerAwareMessageListener.super.onMessage(data);
		log.info("list size(): {}", data.size());
	}

	@Override
	public void onMessage(List<ConsumerRecord<String, ChatMessageDto>> data, Consumer<?, ?> consumer) {
		log.info("list size(): {}", data.size());
	}

}
