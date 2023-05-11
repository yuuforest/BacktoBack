package com.backtoback.member.service;

import com.backtoback.member.token.JwtTokenProvider;
import com.backtoback.member.common.CookieProvider;
import com.backtoback.member.domain.Member;
import com.backtoback.member.dto.request.MemberLoginReq;
import com.backtoback.member.dto.request.MemberSignUpReq;
import com.backtoback.member.dto.response.MemberResp;
import com.backtoback.member.exception.DuplicateMemberException;
import com.backtoback.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class MemberServiceImpl implements MemberService{

    private final StringRedisTemplate stringRedisTemplate;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieProvider cookieProvider;

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void singUp(MemberSignUpReq request) {
        isExistId(request.getMemberId());
        String password = passwordEncoder.encode(request.getMemberPassword());
        Member member = request.toEntity(password);
        memberRepository.save(member);
    }

    @Override
    public MemberResp login(MemberLoginReq request, HttpServletResponse response) {
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
        MemberResp tokenInfo = jwtTokenProvider.generateToken(authentication, member, true, "");
        log.info("============ access token ==============");

        String refreshToken = tokenInfo.getRefreshToken();
        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        log.info(authentication.getName());
        stringRedisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), refreshToken, tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        ResponseCookie responseCookie = cookieProvider.createRefreshTokenCookie(refreshToken);

        Cookie cookie = cookieProvider.of(responseCookie);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.addCookie(cookie);

        return tokenInfo;
    }

    @Override
    public void isExistId(String memberId) {
        if(memberRepository.existsByMemberId(memberId)) throw new DuplicateMemberException();
    }



}
