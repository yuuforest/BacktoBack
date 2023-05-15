package com.backtoback.media.video.feignclient;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class FileUploadDto {

    private long gameSeq;

    private List<MultipartFile> files;

}
