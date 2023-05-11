package com.backtoback.chat.chatting.service;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import com.backtoback.chat.chatting.dto.request.ChatMessage;

public class ChatMessageJsonDeserializer extends JsonDeserializer<ChatMessage>{

	public ChatMessageJsonDeserializer(){
		super(ChatMessage.class);
	}

	// @Bean
	// public JsonDeserializer<ChatMessage> chatMessageDeserializer(){
	// 	JsonDeserializer<ChatMessage> deserializer = new JsonDeserializer<>(ChatMessage.class);
	// 	deserializer.setRemoveTypeHeaders(false);
	// 	deserializer.addTrustedPackages("*");
	// 	deserializer.setUseTypeMapperForKey(true);
	//
	// 	return deserializer;
	// }
}
