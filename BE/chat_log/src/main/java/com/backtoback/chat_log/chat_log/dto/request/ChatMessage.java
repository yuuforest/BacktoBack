package com.backtoback.chat_log.chat_log.dto.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
// @RequiredArgsConstructor
@NoArgsConstructor
public class ChatMessage implements Serializable {

	String message;

	@Builder
	public ChatMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ChatMessage{" +
			"message='" + message + '\'' +
			'}';
	}

	public static ChatMessage from(Message message) {
		ChatMessage dto = new ChatMessage();
		GenericMessage convertMessage = (GenericMessage)message;
		BeanUtils.copyProperties(convertMessage.getPayload(), dto);
		return dto;
	}

	public static ChatMessage from(byte[] messagePayload) {
		// 역직렬화
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ChatMessage chatMessage = objectMapper.readValue(messagePayload, ChatMessage.class);
			// System.out.println(myClassDto);
			return chatMessage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
