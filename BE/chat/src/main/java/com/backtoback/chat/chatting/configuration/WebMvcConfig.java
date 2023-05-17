package com.backtoback.chat.chatting.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOriginPatterns("http://k8a708.p.ssafy.io")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
			.allowCredentials(true);
	}
}
