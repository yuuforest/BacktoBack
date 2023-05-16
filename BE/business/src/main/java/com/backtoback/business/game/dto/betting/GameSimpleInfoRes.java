package com.backtoback.business.game.dto.betting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GameSimpleInfoRes {

    @ApiModelProperty(name = "게임 Sequence ID")
    private Long gameSeq;

    @ApiModelProperty(name = "홈팀 Sequence ID")
    private Long homeSeq;

    @ApiModelProperty(name = "게임 Sequence ID")
    private Long awaySeq;
}
