package com.backtoback.chat_log.chat_log.dto.request;

import java.io.Serializable;

import com.backtoback.chat_log.entity.GameActiveType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameConditionDto implements Serializable {

	private Long gameSeq;
	private GameActiveType gameActiveType;

	@Builder
	public GameConditionDto(Long gameSeq, GameActiveType gameActiveType) {
		this.gameSeq = gameSeq;
		this.gameActiveType = gameActiveType;
	}

	@Override
	public String toString() {
		return String.format("GameAction{gameSeq=%d, gameActiveType=%s}", gameSeq, gameActiveType);
	}

}