package com.backtoback.auth.service;

import com.backtoback.auth.common.CookieProvider;
import com.backtoback.auth.domain.Member;
import com.backtoback.auth.dto.request.MemberLoginReq;
import com.backtoback.auth.dto.response.TokenResp;
import com.backtoback.auth.repository.MemberRepository;
import com.backtoback.auth.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final MemberRepository memberRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieProvider cookieProvider;


    @Override
    public TokenResp login(MemberLoginReq request, HttpServletResponse response) {
        Member member = memberRepository.findByMemberId(request.getMemberId()).orElseThrow(null);
        log.info(member.getMemberId());

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getMemberId(), request.getMemberPassword());

        log.info("============ access authentication Token ==============");

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("============ access authentication ==============");

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenResp tokenInfo = jwtTokenProvider.generateToken(authentication, member, true, "");
        log.info("============ access token ==============");

        String refreshToken = tokenInfo.getRefreshToken();
        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        log.info(authentication.getName());
        stringRedisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), refreshToken, tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        ResponseCookie responseCookie = cookieProvider.createRefreshTokenCookie(refreshToken);
        tokenInfo.setMemberSeq(Long.valueOf(authentication.getName()));
        Cookie cookie = cookieProvider.of(responseCookie);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.addCookie(cookie);

        return tokenInfo;
    }
}
