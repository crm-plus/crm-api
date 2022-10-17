package com.main.server.configuration;

import com.main.server.security.JwtTokenProvider;
import com.main.server.security.exception.JwtAuthenticationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
@Slf4j
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Register Stomp endpoints: the url to open the WebSocket connection.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/stomp").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if(accessor == null) {
                    return message;
                }
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {

                    List<String> headers = accessor.getNativeHeader("Authorization");

                    String token = null;

                    if (headers != null && headers.size() > 0) {
                        token = headers.get(0);
                    }

                    try {
                        if (token != null && jwtTokenProvider.validateToken(token)) {
                            Authentication authentication = jwtTokenProvider.getAuthentication(token);

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    } catch (JwtAuthenticationException e) {
                        SecurityContextHolder.clearContext();
                        String error = "JWT token is expired or invalid";
                        log.info(error);
                        throw new JwtAuthenticationException(error);
                    }
                }
                return message;
            }
        });

    }
}
