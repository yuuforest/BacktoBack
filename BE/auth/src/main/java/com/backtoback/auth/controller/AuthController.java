package com.backtoback.auth.controller;

import com.backtoback.auth.common.CookieProvider;
import com.backtoback.auth.dto.request.MemberLoginReq;
import com.backtoback.auth.dto.response.TokenResp;
import com.backtoback.auth.service.AuthService;
import com.backtoback.auth.service.RefreshTokenService;
import com.backtoback.auth.token.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/")
public class AuthController {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	private final CookieProvider cookieProvider;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenService refreshTokenService;
	private final AuthService authService;

	@PostMapping("/login")
	@ApiOperation(value="로그인", notes = "로그인")
	public ResponseEntity<TokenResp> login(@Validated @RequestBody MemberLoginReq request, HttpServletResponse response){
		TokenResp memberResp = authService.login(request, response);

		return ResponseEntity
				.status(HttpStatus.OK)
				.header(memberResp.getAccessToken())
				.body(memberResp);
	}
	@GetMapping("/reissue")
	@ApiOperation(value="토큰 재발급", notes = "토큰 재발급")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, @CookieValue("refresh-token") String refreshToken){
		String accessToken = jwtTokenProvider.resolveToken(request);
		System.out.println(accessToken);

		TokenResp tokenResp = refreshTokenService.refreshJwtToken(accessToken, refreshToken);
		ResponseCookie responseCookie = cookieProvider.createRefreshTokenCookie(refreshToken);


		return ResponseEntity.status(HttpStatus.OK)
			.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
			.body(tokenResp);
	}

	@PostMapping(value = "/logout")
	@ApiOperation(value="로그아웃", notes="로그아웃")
	public ResponseEntity<?> logout(HttpServletRequest request){
		String token = jwtTokenProvider.resolveToken(request);
		refreshTokenService.logoutToken(token);
		return ResponseEntity.status(HttpStatus.OK).body("Success");
	}
}
