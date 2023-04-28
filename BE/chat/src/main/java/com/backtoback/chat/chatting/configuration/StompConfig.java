package com.backtoback.chat.chatting.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

	/*
	클라이언트 측에서 연결할 URL Endpoint
	Stomp Websocket Endpoint: /chat
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat")
			.setAllowedOrigins("*")
			.withSockJS();
	}

	/*
	메시지 발행 요청: /topic (Application Destination Prefix)
	메시지 구독 요청: /kafka (enable Simple Broker)
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");	//한 명이 메시지를 발행했을 때 해당 토픽을 구독하고 있는 n명에게 메시지 뿌림
		registry.setApplicationDestinationPrefixes("/kafka");	//메시지 도착 경로에 대한 prefix 설정
	}
}
