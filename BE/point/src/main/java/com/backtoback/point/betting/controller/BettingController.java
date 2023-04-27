package com.backtoback.point.betting.controller;


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

    /**
     * 스트리밍 방 생성
     * 1. 각 팀의 베팅 수 저장 준비 (redis)
     * 2. 각 팀의 총 베팅 포인트 저장 준비 (redis)
     */

//    @PostMapping("/betting/room-create")
//    @ApiOperation(value = "스트리밍 방 생성", notes = "스트리밍 방이 처음 생성되면, Redis에 베팅 준비")
//    public ResponseEntity<?>
}
