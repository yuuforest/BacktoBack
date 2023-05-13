package com.backtoback.member.service;

import com.backtoback.member.dto.request.MemberLoginReq;
import com.backtoback.member.dto.request.MemberSignUpReq;
import com.backtoback.member.dto.response.MemberResp;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface MemberService {
    void singUp(MemberSignUpReq request);
    MemberResp login(MemberLoginReq request, HttpServletResponse response);

    void isExistId(String memberId);

}
