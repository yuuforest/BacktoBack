package com.backtoback.business.team.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamInfoRes {

    @ApiModelProperty(name = "팀 Sequence ID")
    private Long teamSeq;

    @ApiModelProperty(name = "팀 이름")
    private String teamName;
}
