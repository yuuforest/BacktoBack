//package com.backtoback.member.token;
//
//import java.security.Key;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Date;
//import java.util.stream.Collectors;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import com.backtoback.member.domain.Member;
//import com.backtoback.member.dto.response.TokenResp;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//@ComponentScan(basePackages={"com.backtoback.token"})
//public class JwtTokenProvider {
//
//    private static final String AUTHORIZATION_HEADER = "Authorization";
//    private static final String MEMBER_KEY = "memberID";
//    private static final String AUTHORITIES_KEY = "auth";
//    private static final String BEARER_TYPE = "Bearer";
//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 2 * 60 * 60 * 1000L;              // 2시간
//    //    private static final long ACCESS_TOKEN_EXPIRE_TIME = 3 * 60 * 1000L;              // 3분
////    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;              // 30분
//    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;    // 7일
//
//    private final Key key;
//
//    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
//    public TokenResp generateToken(Authentication authentication, Member member, boolean isChangeRT, String rt) {
//        // 권한 가져오기
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//
//        long now = (new Date()).getTime();
//        // Access Token 생성
//        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
//        String accessToken = Jwts.builder()
//                .setSubject(member.getMemberSeq().toString())
//                .claim(MEMBER_KEY, authentication.getName())
//                .claim(AUTHORITIES_KEY, authorities)
//                .setExpiration(accessTokenExpiresIn)
//                .signWith(SignatureAlgorithm.HS256, key)
//                .compact();
//
//        String refreshToken = rt;
//        if (isChangeRT) {
//            // Refresh Token 생성
//            refreshToken = Jwts.builder()
//                    .setSubject(member.getMemberSeq().toString())
//                    .claim(MEMBER_KEY, authentication.getName())
//                    .claim(AUTHORITIES_KEY, authorities)
//                    .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
//                    .signWith(SignatureAlgorithm.HS256, key)
//                    .compact();
//        }
//
//        return TokenResp.builder()
//                .grantType(BEARER_TYPE)
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
//                .build();
//    }
//
//    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
//    public Authentication getAuthentication(String accessToken) {
//        // 토큰 복호화
//        Claims claims = parseClaims(accessToken);
//
//        if (claims.get(AUTHORITIES_KEY) == null) {
//            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
//        }
//
//        // 클레임에서 권한 정보 가져오기
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//        // UserDetails 객체를 만들어서 Authentication 리턴
//        UserDetails principal = new User(claims.getSubject(), "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
//    }
//
//
//    private Claims parseClaims(String accessToken) {
//        try {
//            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
//        } catch (ExpiredJwtException e) {
//            return e.getClaims();
//        }
//    }
//
//    public String resolveToken(HttpServletRequest request){
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//
//}
