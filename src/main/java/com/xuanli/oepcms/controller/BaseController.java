package com.xuanli.oepcms.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.vo.RestResult;

@Service
public abstract class BaseController {
	@Autowired
	protected HttpServletResponse response;
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected SessionUtil sessionUtil;
	public final Logger logger = Logger.getLogger(this.getClass());

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

	public String getTokenId() {
		Enumeration<String> enumeration = request.getHeaders("X-AUTH-TOKEN");
		if (enumeration.hasMoreElements()) {
			String tokenId = (String) enumeration.nextElement();
			return tokenId;
		} else {
			return null;
		}
	}

	public UserEntity getCurrentUser() {
		Enumeration<String> enumeration = request.getHeaders("X-AUTH-TOKEN");
		if (enumeration.hasMoreElements()) {
			String tokenId = (String) enumeration.nextElement();
			UserEntity user = sessionUtil.getSessionUser(tokenId);
			return user;
		} else {
			return null;
		}
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
	
	public static Map<String, Object> requestParamToMap(Map<String, String[]> map) {
		Set<String> set = map.keySet();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Iterator<String> iterator = set.iterator();
		String key = "";
		String[] value;
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = map.get(key);
			resultMap.put(key, value[0]);
		}
		return resultMap;

	}
}
