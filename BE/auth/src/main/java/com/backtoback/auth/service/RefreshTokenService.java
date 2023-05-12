package com.backtoback.auth.service;

import com.backtoback.auth.dto.response.MemberResp;

public interface RefreshTokenService {
	MemberResp refreshJwtToken(String accessToken, String refreshToken);
	void logoutToken(String accessToken);
}
