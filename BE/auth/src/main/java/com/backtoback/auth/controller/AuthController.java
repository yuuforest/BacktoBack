package com.backtoback.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backtoback.auth.common.CookieProvider;
import com.backtoback.auth.dto.response.MemberResp;
import com.backtoback.auth.service.RefreshTokenService;
import com.backtoback.auth.token.JwtTokenProvider;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	private final CookieProvider cookieProvider;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenService refreshTokenService;

	@GetMapping("/reissue")
	@ApiOperation(value="토큰 재발급", notes = "토큰 재발급")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, @CookieValue("refresh-token") String refreshToken){
		String accessToken = jwtTokenProvider.resolveToken(request);
		System.out.println(accessToken);

		MemberResp memberResp = refreshTokenService.refreshJwtToken(accessToken, refreshToken);
		ResponseCookie responseCookie = cookieProvider.createRefreshTokenCookie(refreshToken);


		return ResponseEntity.status(HttpStatus.OK)
			.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
			.body(memberResp);
	}

	@PostMapping(value = "/logout")
	@ApiOperation(value="로그아웃", notes="로그아웃")
	public ResponseEntity<?> logout(HttpServletRequest request){
		String token = jwtTokenProvider.resolveToken(request);
		refreshTokenService.logoutToken(token);
		return ResponseEntity.status(HttpStatus.OK).body("Success");
	}
}
