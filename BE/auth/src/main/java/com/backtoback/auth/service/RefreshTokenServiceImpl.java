package com.backtoback.auth.service;

import com.backtoback.auth.domain.Member;
import com.backtoback.auth.dto.response.TokenResp;
import com.backtoback.auth.repository.MemberRepository;
import com.backtoback.auth.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService{
	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;

	private final CustomUserDetailsService customUserDetailsService;

	private final StringRedisTemplate stringRedisTemplate;


	@Override
	@Transactional
	public TokenResp refreshJwtToken(String accessToken, String refreshToken) {
		Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

		if(!jwtTokenProvider.validateToken(refreshToken)){
			if(stringRedisTemplate.opsForValue().get("RT:"+authentication.getName()) !=null){
				stringRedisTemplate.delete("RT:"+authentication.getName());
			}
			else log.info("refresh 없음");
			throw new RuntimeException();
		}

		Member member = memberRepository.findById(Long.valueOf(authentication.getName())).orElse(null);

		authentication = getAuthentication(member.getMemberId());

		TokenResp tokenInfo = jwtTokenProvider.generateToken(authentication, member, true, refreshToken);
		stringRedisTemplate.opsForValue()
			.set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

		return tokenInfo;



	}

	@Override
	public void logoutToken(String accessToken) {

		if(!jwtTokenProvider.validateToken(accessToken)){
			throw new BadCredentialsException("만료된 토큰");
		}
		Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
		log.info(authentication.getName());
		if(stringRedisTemplate.opsForValue().get("RT:"+authentication.getName()) !=null){
			stringRedisTemplate.delete("RT:"+authentication.getName());
		}
		else log.info("refresh 없음");

		Long expiration = jwtTokenProvider.getExpiration(accessToken);

		stringRedisTemplate.opsForValue()
			.set("AT:" + authentication.getName(), accessToken, expiration, TimeUnit.MILLISECONDS);

	}

	public Authentication getAuthentication(String memberId) {
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId);
		return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
	}
}
