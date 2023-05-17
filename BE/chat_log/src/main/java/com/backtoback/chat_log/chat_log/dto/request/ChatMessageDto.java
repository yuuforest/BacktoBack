package com.backtoback.chat_log.chat_log.dto.request;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
// @RequiredArgsConstructor
@NoArgsConstructor
public class ChatMessageDto implements Serializable {

	String message;

	@Builder
	public ChatMessageDto(String message) {
		this.message = message;
	}

	public static ChatMessageDto from(Message message) {
		ChatMessageDto dto = new ChatMessageDto();
		GenericMessage convertMessage = (GenericMessage)message;
		BeanUtils.copyProperties(convertMessage.getPayload(), dto);
		return dto;
	}

	public static ChatMessageDto from(byte[] messagePayload) {
		// 역직렬화
		ObjectMapper objectMapper = new ObjectMapper();
		ChatMessageDto chatMessageDto;
		try {
			chatMessageDto = objectMapper.readValue(messagePayload, ChatMessageDto.class);
			return chatMessageDto;
		} catch (Exception e) {
			e.printStackTrace();

			return ChatMessageDto.builder().message("No message").build(); //null safety
		}
	}

	@Override
	public String toString() {
		return String.format("ChatMessage{message='%s'}", message);
	}

}
