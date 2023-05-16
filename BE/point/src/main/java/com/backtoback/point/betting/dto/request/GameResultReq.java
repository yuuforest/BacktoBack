package com.backtoback.point.betting.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class GameResultReq {

    @ApiModelProperty(name = "Home팀 Sequence ID")
    private Long homeTeamSeq;

    @ApiModelProperty(name = "Away팀 Sequence ID")
    private Long awayTeamSeq;

    @ApiModelProperty(name = "이긴 팀 Sequence ID")
    private Long winTeamSeq;
}
