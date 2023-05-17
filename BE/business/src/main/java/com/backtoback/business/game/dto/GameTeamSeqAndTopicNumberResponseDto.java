package com.backtoback.business.game.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ApiModel(value = "게임에 참여하는 홈/원정 팀 시퀀스 & 할당받은 토픽 번호")
public class GameTeamSeqAndTopicNumberResponseDto extends GameTeamSeqResponseDto {

	@ApiModelProperty(value = "할당받은 토픽 번호")
	private Integer topicNumber;

}
