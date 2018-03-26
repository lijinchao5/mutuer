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
import com.xuanli.oepcms.thirdapp.sdk.xl.service.SyncReadArticleService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年3月14日 下午4:45:14
 */
@RestController
@RequestMapping(value = "/resourceSync/")
public class SyncReadArticleController extends BaseController {
	@Autowired
	SyncReadArticleService syncReadArticleService;

	@ApiOperation(value = "同步练习文章", notes = "同步练习文章")
	@RequestMapping(value = "syncReadArticle.do", method = RequestMethod.GET)
	public RestResult<String> syncReadArticle() {
		try {
			String result = syncReadArticleService.syncReadArticle();
			if (result.equals("1")) {
				return okNoResult("同步练习文章成功");
			} else if (result.equals("2")) {
				return okNoResult("同步练习文章失败,获取文章信息为空!");
			} else if (result.equals("0")) {
				return failed(ExceptionCode.SYNC_ARTICLE_ERROR, "同步练习文章失败。");
			} else if (result.equals("3")) {
				return failed(ExceptionCode.SYNC_ARTICLE_ERROR, "同步练习文章,获取文章详情为空!");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误，请联系管理员!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.SYNC_ARTICLE_ERROR, "同步练习文章失败!");
		}
	}
}
