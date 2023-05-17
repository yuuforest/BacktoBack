package com.backtoback.point.team.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamInfoRes {

    @ApiModelProperty(name = "팀 Sequence")
    private Long teamSeq;

    @ApiModelProperty(name = "팀 이름")
    private String teamName;

}
