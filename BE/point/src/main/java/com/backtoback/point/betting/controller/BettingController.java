package com.backtoback.point.betting.controller;


import com.backtoback.point.betting.dto.request.StreamingRoomInfoReq;
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

//    @PostMapping("/register")
//    @ApiOperation(value = "회원가입", notes = "사용자 정보를 입력받아 회원가입 진행")
//    public ResponseEntity<?> registerMember() {
//        return ResponseEntity.status(200).body("EEE");
//    }

    final BettingService bettingService;

    /**
     * 스트리밍 방 생성
     */
    @PostMapping("/betting/room-create")
    @ApiOperation(value = "스트리밍 방 생성", notes = "스트리밍 방이 처음 생성되면, Redis에 베팅 준비")
    public ResponseEntity<?> createStreamingRoom(@RequestBody StreamingRoomInfoReq streamingRoomInfoReq) {
        bettingService.readyToStartBetting(streamingRoomInfoReq);
        return ResponseEntity.status(200).body("Success");
    }
}
