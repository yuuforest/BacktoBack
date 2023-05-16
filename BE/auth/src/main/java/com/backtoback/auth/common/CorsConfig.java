package com.backtoback.auth.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {
   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();

      config.setAllowedOriginPatterns(Arrays.asList("*"));
      config.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
      config.setAllowedHeaders(Arrays.asList("*"));
      config.setAllowCredentials(true);

      source.registerCorsConfiguration("/**", config);
      return new CorsFilter(source);
   }
}
