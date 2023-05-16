package com.backtoback.business.game.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel(value = "게임")
public class GameResponseDto {

	@ApiModelProperty(value = "게임 시퀀스")
	private Long gameSeq;

}
