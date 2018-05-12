/**
 * @fileName:  BaseService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 上午9:43:13
 */
package com.xuanli.oepcms.service;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.xuanli.oepcms.contents.Constants;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
public class BaseService {
	public <T> RestResult<T> ok(T result) {
		return RestResult.ok(result);
	}

	public <T> RestResult<T> failed(int code, String message, T result) {
		return RestResult.failed(code, message, result);
	}

	public <T> RestResult<T> failed(int code, String message) {
		return RestResult.failed(code, message);
	}

	public RestResult<String> okNoResult(String message) {
		return RestResult.okNoResult(message);
	}

	public String getTokenId(HttpServletRequest request) {
		Enumeration<String> enumeration = request.getHeaders(Constants.HEADER_X_AUTH_TOKEN);
		if (enumeration.hasMoreElements()) {
			String tokenId = (String) enumeration.nextElement();
			return tokenId;
		} else {
			return null;
		}
	}
}
