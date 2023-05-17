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
	Websocket 연결을 등록하는 데 사용. 클라이언트와 서버간의 Websocket 연결을 설정한다.
	"/ws-chat"경로로 웹소켓 연결을 등록
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws-chat")
			.setAllowedOrigins("http://k8a708.p.ssafy.io")
			.withSockJS();
	}

	/*
	Websocket 메시지 브로커를 구성하는 데 사용.
	Websocket 클라이언트가 메시지를 발행하고 구독할 수 있는 대상 토픽(prefix)을 설정
	메시지 브로커를 "/topic" 토픽으로 설정 => /topic/yyy로 브로드캐스트된다. yyy는 consumer에서 결정
	클라이언트에서 "/kafka/xxx" 경로로 메시지를 보내면, 서버에서 @MessageMapping("xxx")에서 메시지 라우팅
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");	//한 명이 메시지를 발행했을 때 해당 토픽을 구독하고 있는 n명에게 메시지 뿌림
		registry.setApplicationDestinationPrefixes("/api/chat/kafka");
	}
}
