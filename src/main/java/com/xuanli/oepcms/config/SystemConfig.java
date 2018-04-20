/**
 * @fileName:  SystemConfig.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月19日 上午9:51:54
 */
package com.xuanli.oepcms.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author QiaoYu
 */
@Component
public class SystemConfig implements EnvironmentAware {
	private static final Logger logger = LoggerFactory.getLogger(SystemConfig.class);
	private RelaxedPropertyResolver propertyResolver;

	public String FILE_BASEPATH = "";
	public String YUN_ZHI_APPKEY = "";
	public String YUN_ZHI_URL = "";
	public String BOOK_URL = "";
	public String BOOK_CONTENT = "";
	public String PAPER_URL = "";
	public String PAPER_CONTENT = "";
	public String ARTICLE_URL = "";
	public String SENTENCE_CONTENT_URL = "";
	public String QUESTION_URL = "";
	public String QUESTION_CONTENT_URL = "";
	public String OTHER_LINK_URL = "";

	@PostConstruct
	public void initMethod() {
		FILE_BASEPATH = propertyResolver.getProperty("file.base-path");
		YUN_ZHI_APPKEY = propertyResolver.getProperty("yunzhi.appkey");
		YUN_ZHI_URL = propertyResolver.getProperty("yunzhi.url");

		BOOK_URL = propertyResolver.getProperty("sync_url.book");
		BOOK_CONTENT = propertyResolver.getProperty("sync_url.book_content");
		PAPER_URL = propertyResolver.getProperty("sync_url.paper");
		PAPER_CONTENT = propertyResolver.getProperty("sync_url.paper_content");
		ARTICLE_URL = propertyResolver.getProperty("sync_url.article_url");
		SENTENCE_CONTENT_URL = propertyResolver.getProperty("sync_url.sentence_content_url");
		QUESTION_URL = propertyResolver.getProperty("sync_url.question_url");
		QUESTION_CONTENT_URL = propertyResolver.getProperty("sync_url.question_content_url");
		OTHER_LINK_URL = propertyResolver.getProperty("sync_url.other_link_url");
		logger.debug("初始化完成系统配置信息...");
	}

	/**
	 * @CreateUser:QiaoYu
	 * @CreateDate:2018年1月22日 下午4:23:54
	 */

	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "systemconfig.");
	}
}
