package com.backtoback.media.video.config;

import org.kurento.client.KurentoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  @Value("${sock.url}")
  private String kurentoUrl;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/video").setAllowedOrigins("http://localhost:3000","http://k8a708.p.ssafy.io","http://k8a7081.p.ssafy.io:3000").withSockJS();
  }

  @Bean
  public KurentoClient kurentoClient() {
    return KurentoClient.create(kurentoUrl);
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/pub");
    registry.enableSimpleBroker("/sub","/user");
    registry.setUserDestinationPrefix("/user");
  }

}