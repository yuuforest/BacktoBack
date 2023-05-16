package com.backtoback.chat_log.chat_log.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public enum GameActiveType {

	@JsonProperty("BEFORE_GAME")
	BEFORE_GAME,

	@JsonProperty("IN_GAME")
	IN_GAME,

	@JsonProperty("AFTER_GAME")
	AFTER_GAME

}
