// package com.backtoback.member.token;
//
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.DefaultSecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
// import lombok.RequiredArgsConstructor;
//
// /**
//  * TokenProvider, JwTfilter를 SecurityConfig에 적용하는 클래스
//  */
// @RequiredArgsConstructor
// public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//     private StringRedisTemplate stringRedisTemplate;
//     private JwtTokenProvider jwtTokenProvider;
//
// //Jwt필터를 Security로직에 필터로 등록
//     @Override
//     public void configure(HttpSecurity http) {
//         http.addFilterBefore(
//             new JwtFilter(stringRedisTemplate, jwtTokenProvider),
//             UsernamePasswordAuthenticationFilter.class
//         );
//     }
// }
