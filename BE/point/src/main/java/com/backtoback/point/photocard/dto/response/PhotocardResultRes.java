package com.backtoback.point.photocard.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PhotocardResultRes {
    @ApiModelProperty(name = "포토카드 Sequence ID")
    private Long photocardSeq;
}
