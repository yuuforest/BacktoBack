package com.backtoback.member.controller;

import com.backtoback.member.token.JwtTokenProvider;
import com.backtoback.member.common.CookieProvider;
import com.backtoback.member.dto.request.MemberLoginReq;
import com.backtoback.member.dto.request.MemberSignUpReq;
import com.backtoback.member.dto.response.MemberResp;
import com.backtoback.member.service.MemberService;
import com.backtoback.member.service.RefreshTokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

@Api(value = "회원관리", tags = "member")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/")
public class MemberController {
    private final MemberService memberService;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final CookieProvider cookieProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("member/signup")
    @ApiOperation(value="회원가입", notes = "회원가")
    public ResponseEntity<MemberLoginReq> signUp(@Validated @RequestBody MemberSignUpReq request) {
        memberService.singUp(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("member/login")
    @ApiOperation(value="로그인", notes = "로그인")
    public ResponseEntity<MemberResp> login(@Validated @RequestBody MemberLoginReq request, HttpServletResponse response){
        MemberResp memberResp = memberService.login(request, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(memberResp.getAccessToken())
                .body(memberResp);
    }

    @GetMapping("member/reissue")
    @ApiOperation(value="토큰 재발급", notes = "토큰 재발급")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, @CookieValue("refresh-token") String refreshToken){
        String accessToken = jwtTokenProvider.resolveToken(request);
        MemberResp memberResp = refreshTokenService.refreshJwtToken(accessToken, refreshToken);

        ResponseCookie responseCookie = cookieProvider.createRefreshTokenCookie(refreshToken);


        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
            .body(memberResp);
    }

    @PostMapping(value = "member/logout")
    @ApiOperation(value="로그아웃", notes="로그아웃")
    public ResponseEntity<?> logout(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        refreshTokenService.logoutToken(token);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    }
