package com.backtoback.member.controller;

import com.backtoback.member.dto.request.MemberSignUpReq;
import com.backtoback.member.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "회원관리", tags = "member")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/{memberSeq}")
    @ApiOperation(value="회원정보 조회", notes = "회원정보 조회")
    public ResponseEntity<?> select(@PathVariable("memberSeq") Long memberSeq){
        return ResponseEntity.ok(memberService.member(memberSeq));
    }

//    @PutMapping("/member")
//    public ResponseEntity<?> update(HttpServletRequest request){
//        return ResponseEntity.ok(memberService.update(MemberUpdateReq));
//    }

    @PostMapping("/signup")
    @ApiOperation(value="회원가입", notes = "회원가입")
    public ResponseEntity<?> signUp(@Validated @RequestBody MemberSignUpReq request) {
        memberService.singUp(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }


    @GetMapping("/existId/{memberId}")
    @ApiOperation(value="아이디 중복 확인", notes = "아이디 중복 확인")
    public ResponseEntity<?> existId(@PathVariable("memberId") String memberId){

        memberService.isExistId(memberId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .build();

    }

    @GetMapping("/existNickname/{nickname}")
    @ApiOperation(value="닉네임 중복 확인", notes = "닉네임 중복 확인")
    public ResponseEntity<?> existNickname(@PathVariable("nickname") String nickname){
        memberService.isExistNickname(nickname);
        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }





    }
