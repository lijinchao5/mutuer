/**
 * @fileName:  QuartzConfig.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年3月1日 上午10:18:07
 */
package com.xuanli.oepcms.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author QiaoYu
 */
@Component
@PropertySource(value = { "classpath:quartz.properties" }, encoding = "utf-8")
public class QuartzConfig {
//	@Value("${quartz.id}")
//	public String id;
//
//	@PostConstruct
//	public void init() {
//		System.out.println("读取配置文件id:" + id);
//	}
}
