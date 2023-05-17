package com.backtoback.chat.chatting.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

	private Long gameSeq;
	private Long memberSeq;
	private Long memberTeamSeq;
	private String nickname;
	private String message;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime time;

	private Integer topicNumber;

}
