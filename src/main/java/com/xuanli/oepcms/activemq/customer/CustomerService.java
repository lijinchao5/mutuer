/**
 * @fileName:  CustomerService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年3月28日 下午12:02:28
 */
package com.xuanli.oepcms.activemq.customer;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.activemq.bean.ActivemqMsgBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.websocket.StudentWebSocketHandler;

/**
 * @author QiaoYu[www.codelion.cn]
 */
@Component
public class CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	@Autowired
	StudentWebSocketHandler studentWebSocketHandler;

	@JmsListener(destination = "student.aienglish.topic", containerFactory = "myJmsContainerFactory")
	public void subscribe(TextMessage message) {
		try {
			String text = message.getText();
			ActivemqMsgBean activemqMsgBean = JSONObject.parseObject(text, ActivemqMsgBean.class);
			logger.info("客户端接收到topic为[student.aienglish.topic]内容为:" + text);
			if (StringUtil.isEmpty(activemqMsgBean.getType()) || activemqMsgBean.getType().equals("1")) {
				logger.info("作业考试推送");
				studentWebSocketHandler.sendMessageToUsers(activemqMsgBean.getUsers(), activemqMsgBean.getMsg());
			} else {
				logger.info("模拟考试推送");
			}
			message.acknowledge();
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
}
