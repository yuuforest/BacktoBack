package com.backtoback.chat.chatting.service;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import com.backtoback.chat.chatting.dto.request.ChatMessage;

public class ChatMessageJsonDeserializer extends JsonDeserializer<ChatMessage>{

	public ChatMessageJsonDeserializer(){
		super(ChatMessage.class);
		this.setRemoveTypeHeaders(false);
		this.addTrustedPackages("*");
		this.setUseTypeMapperForKey(true);
	}

}
