package com.backtoback.chat.chatting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameTeamSeqRes {
	private Long homeSeq;
	private Long awaySeq;
}
