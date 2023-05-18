package com.backtoback.business.common.dto;

import java.io.Serializable;

import com.backtoback.business.game.domain.GameActiveType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameConditionDto implements Serializable {

	private Long gameSeq;
	private GameActiveType gameActiveType;
	private Long mediaStartTime;

	@Builder
	public GameConditionDto(Long gameSeq, GameActiveType gameActiveType, Long mediaStartTime) {
		this.gameSeq = gameSeq;
		this.gameActiveType = gameActiveType;
		this.mediaStartTime = mediaStartTime;
	}

	@Override
	public String toString() {
		return "GameConditionDto{" +
			"gameSeq=" + gameSeq +
			", gameActiveType=" + gameActiveType +
			", mediaStartTime=" + mediaStartTime +
			'}';
	}

}