package com.backtoback.business.game.dto.betting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
public class GameSimpleInfoRes {

	@ApiModelProperty(name = "게임 Sequence ID")
	private Long gameSeq;

	@ApiModelProperty(name = "홈팀 Sequence ID")
	private Long homeSeq;

	@ApiModelProperty(name = "게임 Sequence ID")
	private Long awaySeq;

}
