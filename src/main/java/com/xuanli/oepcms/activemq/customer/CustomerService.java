/**
 * @fileName:  CustomerService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年3月28日 下午12:02:28
 */
package com.xuanli.oepcms.activemq.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * @author QiaoYu[www.codelion.cn]
 */
@Service
public class CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@JmsListener(destination = "student.aienglish.topic", containerFactory = "myJmsContainerFactory")
	public void subscribe(String text) {
		logger.info("客户端接收到topic为[student.aienglish.topic]内容为:" + text);
	}
}
