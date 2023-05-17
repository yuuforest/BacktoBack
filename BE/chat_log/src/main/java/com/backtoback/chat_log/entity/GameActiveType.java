package com.backtoback.chat_log.entity;

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
