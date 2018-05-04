/**
 * @fileName:  BaseMobileController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月27日 上午9:45:15
 */ 
package com.xuanli.oepcms.controller.mobile;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuanli.oepcms.entity.UserMobileEntity;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu[www.codelion.cn]
 */

public class BaseMobileController {
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	protected HttpServletResponse response;
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected SessionUtil sessionUtil;
	public RestResult<String> okNoResult(String message) {
		return RestResult.okNoResult(message);
	}
	public <T> RestResult<T> ok(T result) {
		return RestResult.ok(result);
	}

	public <T> RestResult<T> failed(int code, String message, T result) {
		return RestResult.failed(code, message, result);
	}

	public <T> RestResult<T> failed(int code, String message) {
		return RestResult.failed(code, message);
	}
	protected PageBean initPageBean(Integer page, Integer rows) {
		if (null == page || page.intValue() == 0) {
			page = 1;
		}
		if (null == rows || rows.intValue() == 0) {
			rows = 10;
		}
		PageBean pageBean = new PageBean(page, rows);
		return pageBean;
	}
	
	
	public UserMobileEntity getCurrentUser() {
		Enumeration<String> enumeration = request.getHeaders("X-AUTH-MOBILE-TOKEN");
		if (enumeration.hasMoreElements()) {
			String tokenId = (String) enumeration.nextElement();
			UserMobileEntity user = sessionUtil.getMobileRandomTokenId(tokenId);
			return user;
		} else {
			return null;
		}
	}
}
