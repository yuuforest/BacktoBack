package com.backtoback.auth.service;

import com.backtoback.auth.dto.response.TokenResp;

public interface RefreshTokenService {
	TokenResp refreshJwtToken(String accessToken, String refreshToken);
	void logoutToken(String accessToken);
}
