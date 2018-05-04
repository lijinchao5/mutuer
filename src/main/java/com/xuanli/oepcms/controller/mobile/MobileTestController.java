/**
 * @fileName:  MobileTestController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年5月4日 上午9:50:30
 */ 
package com.xuanli.oepcms.controller.mobile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu[www.codelion.cn]
 */
@RestController()
@RequestMapping(value = "/mobile/test/")
public class MobileTestController extends BaseMobileController{
	
	@RequestMapping(value = "test1.do")
	public RestResult<String> test1(){
		return okNoResult("12356");
	}
	@RequestMapping(value = "test2.do")
	public RestResult<String> test2(){
		Long userId = getCurrentUser().getUserId();
		return okNoResult(userId+"12356");
	}
	
}
