package com.backtoback.media.video.dto;


import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class HighLightRequestDto {

    private String gameSeq;

    private List<MultipartFile> files;

}
