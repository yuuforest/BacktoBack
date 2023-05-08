package com.backtoback.point.betting.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BettingResultRes {

    //  homePercent awayPercent dividends

    @ApiModelProperty(name = "Home팀 Sequence ID")
    private Long homeSeq;

    @ApiModelProperty(name = "Home팀 베팅률")
    private double homePercent;

    @ApiModelProperty(name = "Away팀 Sequence ID")
    private Long awaySeq;

    @ApiModelProperty(name = "Away팀 베팅률")
    private double awayPercent;

    @ApiModelProperty(name = "예상 배당금")
    private double divdends;

}
