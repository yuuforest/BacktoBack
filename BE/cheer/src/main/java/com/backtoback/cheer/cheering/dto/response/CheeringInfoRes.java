package com.backtoback.cheer.cheering.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CheeringInfoRes {

    @ApiModelProperty(name = "Home팀 응원수")
    private Integer homeCount;

    @ApiModelProperty(name = "Away팀 응원수")
    private Integer awayCount;
}
