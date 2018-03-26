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
import com.xuanli.oepcms.thirdapp.sdk.xl.service.SyncOtherLinkService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年3月21日 上午10:01:47
 */
@RestController
@RequestMapping(value = "/resourceSync/")
public class SyncOtherLinkController extends BaseController {
	@Autowired
	SyncOtherLinkService syncOtherLinkService;

	@ApiOperation(value = "同步链接", notes = "同步链接方法")
	@RequestMapping(value = "syncOtherLink.do", method = RequestMethod.GET)
	public RestResult<String> SyncOtherLink() {
		try {
			String result = syncOtherLinkService.SyncOtherLink();
			if (result.equals("1")) {
				return okNoResult("同步链接成功");
			} else if (result.equals("0")) {
				return failed(ExceptionCode.SYNC_OTHER_LINK_ERROR, "同步链接失败,未获取到资源");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.SYNC_OTHER_LINK_ERROR, "同步链接失败");
		}
	}
}
