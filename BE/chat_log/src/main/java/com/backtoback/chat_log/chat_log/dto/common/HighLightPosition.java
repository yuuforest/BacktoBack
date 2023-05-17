package com.backtoback.chat_log.chat_log.dto.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
// @Builder
public class HighLightPosition implements Serializable {

	@JsonIgnore
	private static final double interval = 5.0;

	private Double start;
	private Double finish;

	@Builder
	public HighLightPosition(Double start) {
		this.start = start;
		this.finish = start + interval;
	}

	@Override
	public String toString() {
		return "HighLightPosition{" +
			"start=" + start +
			", finish=" + finish +
			'}';
	}
	
}
