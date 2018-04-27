/**
 * @fileName:  BaseMobileController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月27日 上午9:45:15
 */ 
package com.xuanli.oepcms.controller.mobile;

import org.apache.log4j.Logger;

import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu[www.codelion.cn]
 */

public class BaseMobileController {
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
}
