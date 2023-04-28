package com.backtoback.chat.chatting.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage {

	private Long chatRoomId;
	private ChatRoomType chatRoomType;
	private Long memberId;
	private String message;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime time;

}
