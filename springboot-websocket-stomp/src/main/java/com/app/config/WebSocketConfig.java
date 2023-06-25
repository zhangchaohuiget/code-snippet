package com.app.config;

import com.app.handler.SocketHandler;
import com.app.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocketConfig
 *
 * @author ch
 * @date 2023/6/25 14:50
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketHandler(), "/app")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");
    }
}
