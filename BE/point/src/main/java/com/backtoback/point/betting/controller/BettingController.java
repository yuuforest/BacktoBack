package com.backtoback.point.betting.controller;

import com.backtoback.point.betting.dto.request.BettingInfoReq;
import com.backtoback.point.betting.dto.response.BettingInfoRes;
import com.backtoback.point.betting.dto.response.BettingResultRes;
import com.backtoback.point.betting.service.BettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "베팅 API", tags = "betting")
@RestController
@RequiredArgsConstructor
//@CrossOrigin("*")
@RequestMapping("/betting")
public class BettingController {

    private final BettingService bettingService;

    @PostMapping("/start")
    @ApiOperation(value = "베팅을 위한 환경 설정", notes = "베팅을 시작하기 위해, Redis에 환경 설정")
    public ResponseEntity<?> readyToStartBetting(){
        bettingService.readyToStartBetting();
        return ResponseEntity.status(200).body("Success");  // 변경 가능성 있음
    } // OK

    @GetMapping("/member/{memberSeq}/info")
    @ApiOperation(value = "유저의 베팅 정보 조회", notes = "해당 경기에 대한 유저의 베팅 정보 조회")
    public ResponseEntity<BettingInfoRes> getBettingInfo(@PathVariable("memberSeq") Long memberSeq,
                                                         @RequestParam("gameID") Long gameSeq) {
        BettingInfoRes bettingInfo = bettingService.getBettingInfo(memberSeq, gameSeq);
        return ResponseEntity.status(200).body(bettingInfo);
    } // OK

    @PostMapping("/member/{memberSeq}")
    @ApiOperation(value = "베팅", notes = "회원 각자 베팅")
    public ResponseEntity<?> startBetting(@PathVariable("memberSeq") Long memberSeq,
                                          @RequestBody BettingInfoReq bettingInfoReq) {
        bettingService.startBetting(memberSeq, bettingInfoReq);
        return ResponseEntity.status(200).body("Success");
    } // OK

    @GetMapping("/member/{memberSeq}")
    @ApiOperation(value = "베팅 예상 결과 전달", notes = "각 팀의 베팅률과 회원의 예상 배당금 전달")
    public ResponseEntity<BettingResultRes> anticipateBettingResult (@PathVariable("memberSeq") Long memberSeq,
                                                                     @RequestParam("gameID") Long gameSeq){
        BettingResultRes response = bettingService.anticipateBettingResult(memberSeq, gameSeq);
        return ResponseEntity.status(200).body(response);
    } // OK

//    @PostMapping("betting/result")
//    @ApiOperation(value = "베팅 결과 반영해서 처리", notes = "이거 사라질 예정 왜냐면 kafka로 할거니까")
//    public ResponseEntity<?> getBettingResult (@RequestBody KafkaReq kafkaReq){
//        bettingService.getBettingResult(kafkaReq);
//        return ResponseEntity.status(200).body("Success");
//    }
}
