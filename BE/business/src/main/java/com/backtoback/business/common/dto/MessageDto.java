package com.backtoback.business.common.dto;

import java.io.Serializable;

import com.backtoback.business.game.domain.GameActiveType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageDto implements Serializable {

	private Long gameSeq;

	private GameActiveType gameActiveType;

	public MessageDto(Long gameSeq, GameActiveType gameActiveType) {
		this.gameSeq = gameSeq;
		this.gameActiveType = gameActiveType;
	}

	public MessageDto(Long gameSeq) {
		this.gameSeq = gameSeq;
	}

	@Override
	public String toString() {
		return "MessageDto{" +
			"gameSeq=" + gameSeq +
			", gameActiveType=" + gameActiveType +
			'}';
	}

}
