package com.threeping.syncday.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  // (sub)구독자들은  이 경로로 수신받음(SimpleBroker가 이 경로로 메세지 전달)
        // "/queue"는 prefix전달로 1:1 메세지 전달. "/topic"은 1:다
        registry.setApplicationDestinationPrefixes("/app"); // (pub)메세지가 보내질 때
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 웹소켓 핸드쉐이크를 위한 주소. handler 처리 안 할 수 있음
                .setAllowedOriginPatterns("http://localhost:5173")
//                .setAllowedOriginPatterns("*")  // 일단 모두 허용
                .withSockJS();  // SockJs 설정
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(8192)
                .setSendTimeLimit(15 * 1000)
                .setSendBufferSizeLimit(3 * 512 * 1024);
    }
}
