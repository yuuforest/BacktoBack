package com.backtoback.point.betting.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class TeamInfoReq {

    @ApiModelProperty(name = "팀 Sequence ID")
    private Long teamSeq;

    @ApiModelProperty(name = "팀 이름")
    private String teamName;
}
