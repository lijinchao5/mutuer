package com.xuanli.oepcms.websocket;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.xuanli.oepcms.util.MapUtil;
@Component
public class StudentWebSocketHandler implements WebSocketHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static CopyOnWriteArraySet<WebSocketSession> webSocketSet = new CopyOnWriteArraySet<WebSocketSession>();

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		webSocketSet.remove(session);  
        logger.debug("afterConnectionClosed" + closeStatus.getReason());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("ConnectionEstablished");
		webSocketSet.add(session);
		Map<String, Object> map= session.getAttributes();
		MapUtil.printMap(map);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println("handleMessage" + message.toString());
		logger.debug("handleMessage" + message.toString());
		session.sendMessage(new TextMessage(new Date() + ""));

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		webSocketSet.remove(session);
		logger.debug("handleTransportError" + exception.getMessage());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	public void sendMessageToUsers(TextMessage message) {  
        for (WebSocketSession user : webSocketSet) {  
            try {  
                if (user.isOpen()) {  
                    user.sendMessage(message);  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}
