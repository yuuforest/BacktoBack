package com.backtoback.point.betting.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("StreamingRoomInfoReq")
public class StreamingRoomInfoReq {

    @ApiModelProperty(name = "경기 sequence ID")
    private Long gameSeq;

    @ApiModelProperty(name = "home team sequence ID")
    private Long homeSeq;

    @ApiModelProperty(name = "away team sequence ID")
    private Long awaySeq;
}
