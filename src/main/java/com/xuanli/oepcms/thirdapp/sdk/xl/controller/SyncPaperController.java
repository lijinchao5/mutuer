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
import com.xuanli.oepcms.thirdapp.sdk.xl.service.SyncPaperService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年3月5日 下午12:31:53
 */
@RestController
@RequestMapping(value = "/resourceSync/")
public class SyncPaperController extends BaseController {
	@Autowired
	SyncPaperService syncPaperService;

	@ApiOperation(value = "同步试卷", notes = "同步试卷方法")
	@RequestMapping(value = "syncPaper.do", method = RequestMethod.GET)
	public RestResult<String> syncPaper() {
		try {
			String result = syncPaperService.syncPapers();
			if (result.equals("1")) {
				return okNoResult("同步试卷成功");
			} else if (result.equals("2")) {
				return okNoResult("同步试卷失败,获取试卷信息为空!");
			} else if (result.equals("0")) {
				return failed(ExceptionCode.SYNC_PAPER_ERROR, "同步试卷失败,未获取到资源");
			} else if (result.equals("3")) {
				return failed(ExceptionCode.SYNC_PAPER_ERROR, "同步试卷失败,获取试卷详情为空!");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误，请联系管理员!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.SYNC_PAPER_ERROR, "同步试卷失败!");
		}
	}
}
