package com.backtoback.member.service;

import com.backtoback.member.dto.response.MemberResp;
import com.backtoback.member.dto.response.TokenResp;
import com.backtoback.member.domain.Member;
import com.backtoback.member.dto.request.MemberSignUpReq;
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
import javax.servlet.http.HttpServletRequest;
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
    public MemberResp member(Long memberSeq) {
 		Member member = memberRepository.findById(memberSeq).orElse(null);
        return MemberResp.fromEntity(member);
    }

    // @Override
    // public void update(MemberUpdateReq request) {
    //
    // }


    @Override
    public void isExistId(String memberId) {
        if(memberRepository.existsByMemberId(memberId)) throw new DuplicateMemberException();
    }

    @Override
    public void isExistNickname(String nickname) {
        if(memberRepository.existsByNickname(nickname)) throw new DuplicateMemberException();

    }

}
