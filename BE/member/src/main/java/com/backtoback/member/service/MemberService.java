package com.backtoback.member.service;

import com.backtoback.member.domain.Member;
import com.backtoback.member.dto.request.MemberSignUpReq;
import com.backtoback.member.dto.response.MemberResp;
import com.backtoback.member.dto.response.TokenResp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface MemberService {
    void singUp(MemberSignUpReq request);

    MemberResp member(Long memberSeq);

    // void update(MemberUpdateReq request);

    void isExistId(String memberId);
    void isExistNickname(String nickname);

}
