/**
 * @fileName:  ActiveMqConfig.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年3月28日 下午12:01:22
 */
package com.xuanli.oepcms.activemq;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;

/**
 * @author QiaoYu[www.codelion.cn]
 */
@Configuration
public class ActiveMqConfig {
	private static final Logger logger = LoggerFactory.getLogger(ActiveMqConfig.class);
	@Bean
	public JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setPubSubDomain(true);
		logger.info("初始化Activemq的订阅模式.........");
		return factory;
	}
}
