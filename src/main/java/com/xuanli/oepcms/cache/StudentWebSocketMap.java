/**
 * @fileName:  SystemSubjectMap.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2017年4月28日 下午1:51:19
 */
package com.xuanli.oepcms.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author QiaoYu
 */
@Component
@Scope("singleton")
public class StudentWebSocketMap {

	public Map<String, WebSocketSession> studentWebSocketMap = new HashMap<String, WebSocketSession>();

	public Map<String, WebSocketSession> getStudentWebSocketMap() {
		return studentWebSocketMap;
	}

	public void setStudentWebSocketMap(Map<String, WebSocketSession> studentWebSocketMap) {
		this.studentWebSocketMap = studentWebSocketMap;
	}

}
