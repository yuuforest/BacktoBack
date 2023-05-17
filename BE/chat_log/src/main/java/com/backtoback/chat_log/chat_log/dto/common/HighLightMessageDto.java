package com.backtoback.chat_log.chat_log.dto.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.backtoback.chat_log.chat_log.dto.common.HighLightPosition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HighLightMessageDto implements Serializable {

	private final List<HighLightPosition> highLightPositionList = new ArrayList<>();
	private Long gameSeq;

	public void addHighLightPositionList(HighLightPosition highLightPosition) {
		highLightPositionList.add(highLightPosition);
	}

}
