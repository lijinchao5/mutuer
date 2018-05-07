/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.controller.BaseController;
import com.xuanli.oepcms.thirdapp.sdk.xl.service.SyncQuestionService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年3月15日 下午12:44:11
 */
@RestController
@RequestMapping(value = "/resourceSync/")
public class SyncQuestionController extends BaseController{
	@Autowired
	SyncQuestionService syncQuestionService;

	@ApiOperation(value = "同步试题库", notes = "同步试题库方法")
	@RequestMapping(value = "SyncQusetions.do", method = RequestMethod.GET)
	public RestResult<String> SyncQusetions() {
		try {
			String result = syncQuestionService.SyncQusetions();
			if (result.equals("1")) {
				return okNoResult("同步试题库成功");
			} else if (result.equals("0")) {
				return failed(ExceptionCode.SYNC_QUESTION_ERROR, "同步试题库失败,未获取到资源!");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误，请联系管理员!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.SYNC_QUESTION_ERROR, "同步试题库失败!");
		}
	}
}
