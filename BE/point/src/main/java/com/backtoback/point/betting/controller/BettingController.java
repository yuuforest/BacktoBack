package com.backtoback.point.betting.controller;

import com.backtoback.point.betting.dto.request.BettingInfoReq;
import com.backtoback.point.betting.dto.response.BettingResultRes;
import com.backtoback.point.betting.service.BettingService;
import com.backtoback.point.member.service.MemberService;
import com.backtoback.point.pointlog.service.PointLogService;
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
    final MemberService memberService;
    final PointLogService pointLogService;

    /**
     * 스트리밍 방 생성
     */
    @PostMapping("betting/start")
    @ApiOperation(value = "베팅을 위한 환경 설정", notes = "베팅을 시작하기 위해, Redis에 환경 설정")
    public ResponseEntity<?> readyToStartBetting(){
        bettingService.readyToStartBetting();
        return ResponseEntity.status(200).body("Success");  // 변경 가능성 있음
    }

    @PostMapping("member/{memberSeq}/betting")
    @ApiOperation(value = "베팅", notes = "회원 각자의 베팅 시작")
    public ResponseEntity<?> startBetting(@PathVariable("memberSeq") Long memberSeq,
                                          @RequestBody BettingInfoReq bettingInfoReq) {
        bettingService.startBetting(memberSeq, bettingInfoReq);
        memberService.updateByBetting(memberSeq, bettingInfoReq.getBettingPoint());
        pointLogService.createPointLog(bettingInfoReq.getBettingPoint(), memberSeq);
        return ResponseEntity.status(200).body("Success");
    }

    @GetMapping("member/{memberSeq}/betting")
    @ApiOperation(value = "베팅 예상 결과 전달", notes = "각 팀의 베팅률과 회원의 예상 배당금 전달")
    public ResponseEntity<BettingResultRes> anticipateBettingResult (@PathVariable("memberSeq") Long memberSeq,
                                                                     @RequestParam("gameID") Long gameSeq){
        BettingResultRes response = bettingService.anticipateBettingResult(memberSeq, gameSeq);
        return ResponseEntity.status(200).body(response);
    }
}
