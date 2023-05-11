package com.backtoback.cheer.cheering.controller;

import com.backtoback.cheer.cheering.service.CheerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "응원 API", tags = "Cheer")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/")
public class CheerController {

    private final CheerService cheerService;

    @PostMapping("cheer/start")
    @ApiOperation(value = "응원을 위한 환경 설정", notes = "응원을 시작하기 위해, Redis에 환경 설정")
    public ResponseEntity<?> readyToStartCheer(){
        cheerService.readyToStartCheer();
        return ResponseEntity.status(200).body("Success");  // 변경 가능성 있음
    } // NOT YET
}
