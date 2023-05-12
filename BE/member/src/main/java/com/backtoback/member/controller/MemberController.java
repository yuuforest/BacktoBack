package com.backtoback.member.controller;

import com.backtoback.member.token.JwtTokenProvider;
import com.backtoback.member.common.CookieProvider;
import com.backtoback.member.dto.request.MemberLoginReq;
import com.backtoback.member.dto.request.MemberSignUpReq;
import com.backtoback.member.dto.response.MemberResp;
import com.backtoback.member.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Api(value = "회원관리", tags = "member")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final CookieProvider cookieProvider;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    @ApiOperation(value="회원가입", notes = "회원가")
    public ResponseEntity<MemberLoginReq> signUp(@Validated @RequestBody MemberSignUpReq request) {
        memberService.singUp(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/login")
    @ApiOperation(value="로그인", notes = "로그인")
    public ResponseEntity<MemberResp> login(@Validated @RequestBody MemberLoginReq request, HttpServletResponse response){
        MemberResp memberResp = memberService.login(request, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(memberResp.getAccessToken())
                .body(memberResp);
    }

    @GetMapping("/mypage")
    public ResponseEntity<MemberResp> mypage(){
        return null;
    }

    @PostMapping("/mypage")
    public ResponseEntity<?> changeInfo(){
        return null;
    }

    }
