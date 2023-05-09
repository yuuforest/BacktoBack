package com.backtoback.business.game.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel(value="게임에 참여하는 홈/원정 팀 시퀀스")
public class GameTeamSeqResponseDto {

	@ApiModelProperty(value="홈팀 시퀀스")
	private Long homeSeq;

	@ApiModelProperty(value="원정팀 시퀀스")
	private Long awaySeq;
}
