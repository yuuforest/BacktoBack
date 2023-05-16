package com.backtoback.media.video.feignclient;

import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "flask-service", url = "http://k8a7081.p.ssafy.io:5000")
public interface FlaskServiceClient {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadFiles(@RequestPart(value = "files") List<MultipartFile> files,@RequestPart(value="gameSeq") Long gameSeq);

}

