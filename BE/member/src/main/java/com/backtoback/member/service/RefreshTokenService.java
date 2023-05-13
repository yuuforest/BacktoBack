package com.backtoback.member.service;

import com.backtoback.member.dto.response.MemberResp;

public interface RefreshTokenService {
	MemberResp refreshJwtToken(String accessToken, String refreshToken);
	void logoutToken(String accessToken);
}
