/**
 * @fileName:  WebSocketConfig.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月26日 上午10:58:59
 */
package com.xuanli.oepcms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.xuanli.oepcms.websocket.StudentWebSocketHandler;

/**
 * @author QiaoYu
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(systemWebSocketHandler(), "/studentWebSocket.do").addInterceptors(new SocketHandshakeInterceptor()).setAllowedOrigins("*");
		registry.addHandler(systemWebSocketHandler(), "/studentWebSocket/js.do").addInterceptors(new SocketHandshakeInterceptor()).setAllowedOrigins("*").withSockJS();
	}

	@Bean
	public WebSocketHandler systemWebSocketHandler() {
		return new StudentWebSocketHandler();
	}
}