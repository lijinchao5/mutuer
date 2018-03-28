/**
 * @fileName:  TestController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月26日 上午11:10:57
 */
package com.xuanli.oepcms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import com.xuanli.oepcms.activemq.service.StudentMqService;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.websocket.StudentWebSocketHandler;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author QiaoYu
 */
@RestController
@RequestMapping(value = "/test/")
public class TestController {
	@Autowired
	StudentWebSocketHandler studentWebSocketHandler;
	@Autowired
	UserService userService;
	@Autowired
	StudentMqService studentMqService;
	
	@ApiIgnore
	@RequestMapping(value = "testWebSocket.do")
	public String testwebSocket() {
		try {
			studentWebSocketHandler.sendMessageToUsers(new TextMessage("12341321321"));
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	@ApiIgnore
	@RequestMapping(value = "login.do")
	public String login(String username) {
		String result = userService.loginTest(username);
		return result;
	}
	
	@ApiIgnore
	@RequestMapping(value = "testActivemq.do")
	public String testActivemq() {
		try {
			studentMqService.sendMsg("这是通过mq推送的消息");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
