package com.backtoback.auth.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@RedisHash("refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    private String memberSeq;
    private String refreshTokenId;

    public static RefreshToken of(String memberSeq, String refreshTokenId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.memberSeq = memberSeq;
        refreshToken.refreshTokenId = refreshTokenId;
        return refreshToken;
    }
}
