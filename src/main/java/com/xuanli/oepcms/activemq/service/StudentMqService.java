/**
 * @fileName:  StudentMqService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年3月28日 下午12:22:02
 */
package com.xuanli.oepcms.activemq.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.activemq.bean.ActivemqMsgBean;
import com.xuanli.oepcms.activemq.publish.MQPublisherServer;
import com.xuanli.oepcms.entity.UserMessageEntity;
import com.xuanli.oepcms.service.UserMessageEntityService;

/**
 * @author QiaoYu[www.codelion.cn]
 */
@Service
public class StudentMqService {
	@Autowired
	MQPublisherServer publisherServer;
	@Autowired
	UserMessageEntityService userMessageEntityService;

	public void sendMsg(ActivemqMsgBean activemqMsgBean) {
		String users = activemqMsgBean.getUsers();
		List<UserMessageEntity> userMessageEntities = new ArrayList<UserMessageEntity>();
		String[] userIds = users.split(",");
		for (int i = 0; i < userIds.length; i++) {
			UserMessageEntity ume = new UserMessageEntity();
			ume.setText(activemqMsgBean.getMsg());
			if ("all".equals(userIds[i])) {
				continue;
			}
			ume.setUserId(Long.parseLong(userIds[i]));
			ume.setFlags(false);
			ume.setType("1");
			userMessageEntities.add(ume);
			userMessageEntityService.insertUserMessageEntity(ume);
		}
		publisherServer.publish("student.aienglish.topic", JSONObject.toJSONString(activemqMsgBean));
	}
}
