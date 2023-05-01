package com.backtoback.point.betting.controller;

import com.backtoback.point.betting.dto.request.BettingInfoReq;
import com.backtoback.point.betting.service.BettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "베팅 API", tags = "betting")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/")
public class BettingController {

    final BettingService bettingService;

    /**
     * 스트리밍 방 생성
     */
    @PostMapping("betting/start")
    @ApiOperation(value = "베팅을 위한 환경 설정", notes = "베팅을 시작하기 위해, Redis에 환경 설정")
    public ResponseEntity<?> readyToStartBetting(){
        bettingService.readyToStartBetting();
        return ResponseEntity.status(200).body("Success");
    }

    @PostMapping("member/{memberSeq}/betting")
    @ApiOperation(value = "베팅", notes = "회원 각자의 베팅 시작")
    public ResponseEntity<?> startBetting(@PathVariable("memberSeq") Long memberSeq,
                                          @RequestBody BettingInfoReq bettingInfoReq) {
        bettingService.startBetting(memberSeq, bettingInfoReq);
        return ResponseEntity.status(200).body("Success");
    }
}
