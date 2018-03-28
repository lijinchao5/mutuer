package com.xuanli.oepcms.websocket;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.xuanli.oepcms.cache.StudentWebSocketMap;

@Component
public class StudentWebSocketHandler implements WebSocketHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	StudentWebSocketMap studentWebSocketMap;

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		Map<String, WebSocketSession> swsm = studentWebSocketMap.getStudentWebSocketMap();
		Map<String, Object> map = session.getAttributes();
		String userId = (String) map.get("userId");
		if (null != userId) {
			swsm.remove(userId);
		}
		logger.debug("afterConnectionClosed" + closeStatus.getReason());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Map<String, WebSocketSession> swsm = studentWebSocketMap.getStudentWebSocketMap();
		Map<String, Object> map = session.getAttributes();
		String userId = (String) map.get("userId");
		if (null != userId) {
			swsm.put(userId, session);
		}
		logger.debug("用户webSocket已经连接id:" + userId);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		session.sendMessage(new TextMessage(new Date() + ""));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		Map<String, WebSocketSession> swsm = studentWebSocketMap.getStudentWebSocketMap();
		Map<String, Object> map = session.getAttributes();
		String userId = (String) map.get("userId");
		if (null != userId) {
			swsm.remove(userId);
		}
		logger.debug("webSocket出现异常" + exception.getMessage());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	public void sendMessageToUsers(String userId, String message) {
		String[] userIds = userId.split(",");
		for (int i = 0; i < userIds.length; i++) {
			Map<String, WebSocketSession> swsm = studentWebSocketMap.getStudentWebSocketMap();
			WebSocketSession session = swsm.get(userIds[i]);
			try {
				if (null != session) {
					if (session.isOpen()) {
						session.sendMessage(new TextMessage(message));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
