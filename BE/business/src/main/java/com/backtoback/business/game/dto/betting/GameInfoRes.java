package com.backtoback.business.game.dto.betting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GameInfoRes {

    @ApiModelProperty(name = "게임 Sequence ID")
    private Long gameSeq;

    @ApiModelProperty(name = "경기 시간")
    private LocalDateTime gameDatetime;

    @ApiModelProperty(name = "경기 장소")
    private String place;

    @ApiModelProperty(name = "경기 상태")
    private String gameActiveType;

    @ApiModelProperty(name = "Home팀 Sequence ID")
    private Long homeTeamSeq;

    @ApiModelProperty(name = "Away팀 Sequence ID")
    private Long awayTeamSeq;

}
