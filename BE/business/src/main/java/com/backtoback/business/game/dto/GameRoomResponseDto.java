package com.backtoback.business.game.dto;

import com.backtoback.business.game.domain.GameActiveType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "경기룸 입장 시 프론트에서 필요한 데이터들")
public class GameRoomResponseDto {

	@ApiModelProperty(value = "게임 시퀀스 넘버")
	private Long gameSeq;

	@ApiModelProperty(value = "홈팀 시퀀스 넘버")
	private Long homeSeq;

	@ApiModelProperty(value = "원정팀 시퀀스 넘버")
	private Long awaySeq;

	@ApiModelProperty(value = "홈팀 이름")
	private String homeName;

	@ApiModelProperty(value = "원정팀 이름")
	private String awayName;

	@ApiModelProperty(value = "게임 상태")
	private GameActiveType gameActiveType;

	@ApiModelProperty(value = "게임 상태: true면 게임 진행중")
	private Boolean isActive;

	@ApiModelProperty(value = "경기 카프카 토픽 넘버")
	private Integer topicNumber;

	public GameRoomResponseDto(Long gameSeq, Long homeSeq, Long awaySeq, String homeName, String awayName,
		GameActiveType gameActiveType, Integer topicNumber) {
		this.gameSeq = gameSeq;
		this.homeSeq = homeSeq;
		this.awaySeq = awaySeq;
		this.homeName = homeName;
		this.awayName = awayName;
		this.gameActiveType = gameActiveType;
		this.topicNumber = topicNumber;
	}

}
