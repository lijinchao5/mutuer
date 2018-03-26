/**
 * @fileName:  DicController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月30日 上午9:40:49
 */ 
package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.service.DicService;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu 
 */
@RestController()
@RequestMapping(value = "/dic/")
public class DicController extends BaseController{
	@Autowired
	DicService dicService;
	
	@RequestMapping(value = "findDicByType.do", method = RequestMethod.GET)
	public RestResult<Object> findDicByType(String type){
		if (StringUtil.isEmpty(type)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "type不能为空");
		}
		return ok(dicService.findDicByType(type));
	}
}
