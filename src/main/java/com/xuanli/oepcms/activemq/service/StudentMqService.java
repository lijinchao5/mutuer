/**
 * @fileName:  StudentMqService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年3月28日 下午12:22:02
 */
package com.xuanli.oepcms.activemq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.activemq.publish.MQPublisherServer;

/**
 * @author QiaoYu[www.codelion.cn]
 */
@Service
public class StudentMqService {
	@Autowired
	MQPublisherServer publisherServer;

	public void sendMsg(String message) {
		publisherServer.publish("student.aienglish.topic", message);
	}
}
