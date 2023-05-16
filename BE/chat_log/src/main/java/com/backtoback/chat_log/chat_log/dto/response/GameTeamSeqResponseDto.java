package com.backtoback.chat_log.chat_log.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GameTeamSeqResponseDto {

	private Integer topicNumber;
	private Long homeSeq;
	private Long awaySeq;

	@Override
	public String toString() {
		return "GameTeamSeqResponseDto{" +
			"topicNumber=" + topicNumber +
			", homeSeq=" + homeSeq +
			", awaySeq=" + awaySeq +
			'}';
	}
	
}
