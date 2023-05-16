package com.backtoback.auth.service;

import com.backtoback.auth.dto.request.MemberLoginReq;
import com.backtoback.auth.dto.response.TokenResp;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    TokenResp login(MemberLoginReq request, HttpServletResponse response);

}
