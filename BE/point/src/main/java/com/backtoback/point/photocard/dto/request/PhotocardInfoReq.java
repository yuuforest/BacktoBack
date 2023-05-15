package com.backtoback.point.photocard.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("StreamingRoomInfoReq")
public class PhotocardInfoReq {

    @ApiModelProperty(name = "경기 sequence ID")
    private Long gameSeq;

    @ApiModelProperty(name = "베팅팀 sequence ID")
    private Long teamSeq;

    @ApiModelProperty(name = "베팅 포인트")
    private Integer bettingPoint;

}
