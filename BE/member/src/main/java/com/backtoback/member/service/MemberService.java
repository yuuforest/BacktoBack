package com.backtoback.member.service;

import com.backtoback.member.domain.Member;
import com.backtoback.member.dto.request.MemberLoginReq;
import com.backtoback.member.dto.request.MemberSignUpReq;
import com.backtoback.member.dto.request.MemberUpdateReq;
import com.backtoback.member.dto.response.MemberResp;
import com.backtoback.member.dto.response.TokenResp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface MemberService {
    void singUp(MemberSignUpReq request);
    TokenResp login(MemberLoginReq request, HttpServletResponse response);

    MemberResp member(HttpServletRequest request);

    void update(MemberUpdateReq request);

    void isExistId(String memberId);

}
