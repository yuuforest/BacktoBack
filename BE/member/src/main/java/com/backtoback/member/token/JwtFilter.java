// package com.backtoback.member.token;
//
// import java.io.IOException;
//
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.util.ObjectUtils;
// import org.springframework.util.StringUtils;
// import org.springframework.web.filter.OncePerRequestFilter;
//
//
// import lombok.RequiredArgsConstructor;
//
// @RequiredArgsConstructor
// public class JwtFilter extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//
//    private final StringRedisTemplate stringRedisTemplate;
//    private final JwtTokenProvider jwtTokenProvider;
//
//
//    /*
//       Token의 인증정보를 SecurityContext에 저장
//     */
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//       HttpServletRequest httpServletRequest = request;
//       String jwt = resolveToken(httpServletRequest);
//       String requestURI = httpServletRequest.getRequestURI();
//
//       //Token이 정상적이면 authentication객체를 받아와서 SecurityContext에 저장
//       if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
//          String isLogout = stringRedisTemplate.opsForValue().get(jwt);
//          logger.info("jwt는 "+jwt);
//         if(ObjectUtils.isEmpty(isLogout)){
//             Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
//             SecurityContextHolder.getContext().setAuthentication(authentication);
//             logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
//         }
//       } else {
//          logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
//       }
//
//       filterChain.doFilter(request, response);
//    }
//
//
//    /*
//    Request header에서 토큰 정보를 추출하는 메소드
//     */
//    private String resolveToken(HttpServletRequest request) {
//       String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//
//       if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//          return bearerToken.substring(7);
//       }
//
//       return null;
//    }
// }
