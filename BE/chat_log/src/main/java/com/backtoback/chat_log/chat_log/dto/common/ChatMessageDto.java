package com.backtoback.chat_log.chat_log.dto.common;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
// @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto implements Serializable {

	private Long gameSeq;
	private Long memberSeq;
	private Long memberTeamSeq;
	private String nickname;
	private String message;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime time;

	private Integer topicNumber;

	// public static ChatMessageDto from(Message message) {
	// 	ChatMessageDto dto = new ChatMessageDto();
	// 	GenericMessage convertMessage = (GenericMessage)message;
	// 	BeanUtils.copyProperties(convertMessage.getPayload(), dto);
	// 	return dto;
	// }
	//
	// public static ChatMessageDto from(byte[] messagePayload) {
	// 	// 역직렬화
	// 	ObjectMapper objectMapper = new ObjectMapper();
	// 	ChatMessageDto chatMessageDto;
	// 	try {
	// 		chatMessageDto = objectMapper.readValue(messagePayload, ChatMessageDto.class);
	// 		return chatMessageDto;
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	//
	// 		return ChatMessageDto.builder().message("No message").build(); //null safety
	// 	}
	// }

	@Override
	public String toString() {
		return "ChatMessageDto{" +
			"gameSeq=" + gameSeq +
			", memberSeq=" + memberSeq +
			", memberTeamSeq=" + memberTeamSeq +
			", nickname='" + nickname + '\'' +
			", message='" + message + '\'' +
			", time=" + time +
			", topicNumber=" + topicNumber +
			'}';
	}

}
