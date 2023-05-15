package com.backtoback.point.betting.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class GameSimpleInfoReq {

    @ApiModelProperty(name = "게임 Sequence ID")
    private Long gameSeq;

    @ApiModelProperty(name = "홈팀 Sequence ID")
    private Long homeSeq;

    @ApiModelProperty(name = "게임 Sequence ID")
    private Long awaySeq;
}
