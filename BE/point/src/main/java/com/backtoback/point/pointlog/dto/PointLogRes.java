package com.backtoback.point.pointlog.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PointLogRes {

    @ApiModelProperty(name = "순서")
    private Long num;

    @ApiModelProperty(name = "포인트")
    private Integer point;

    @ApiModelProperty(name = "사용 내역")
    private String detail;

    @ApiModelProperty(name = "시간")
    private LocalDateTime time;
}
