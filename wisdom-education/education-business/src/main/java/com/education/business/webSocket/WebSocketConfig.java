package com.education.business.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author wangxue
 * @version 1.0
 * @create 2018-08-14 22:24
 **/
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	private SystemWebSocketHandler systemWebSocketHandler;
	@Autowired
	private HandshakeInterceptor handshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //前台 可以使用webSocket环境
        registry.addHandler(systemWebSocketHandler, "/webSocket")
                .addInterceptors(handshakeInterceptor)
                .setAllowedOrigins("*"); // 允许跨域访问
        registry.addHandler(systemWebSocketHandler, "/sockJs/webSocket")
                .addInterceptors(handshakeInterceptor)
                .setAllowedOrigins("*").withSockJS();
    }
}
