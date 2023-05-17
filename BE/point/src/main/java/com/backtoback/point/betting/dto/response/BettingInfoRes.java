package com.backtoback.point.betting.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BettingInfoRes {

    @ApiModelProperty(name = "베팅한 팀 Sequence ID")
    private Long teamSeq;

    @ApiModelProperty(name = "베팅 포인트")
    private Integer bettingPoint;

}
