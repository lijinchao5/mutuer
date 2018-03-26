/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.config.SystemConfig;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.controller.BaseController;
import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.entity.SectionEntity;
import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.mapper.BookEntityMapper;
import com.xuanli.oepcms.mapper.SectionDetailMapper;
import com.xuanli.oepcms.mapper.SectionEntityMapper;
import com.xuanli.oepcms.mapper.UnitEntityMapper;
import com.xuanli.oepcms.service.BookService;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.BookBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SectionBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SectionDetailBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncBookBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncBookDetailBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.UnitBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.service.SyncBookService;
import com.xuanli.oepcms.util.SyncUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年2月26日 下午7:55:07
 */
@RestController
@RequestMapping(value = "/resourceSync/")
public class SyncBookController extends BaseController {
	@Autowired
	SyncBookService syncBookService;

	@ApiOperation(value = "同步教材", notes = "同步教材方法")
	@RequestMapping(value = "syncBook.do", method = RequestMethod.GET)
	public RestResult<String> syncBook() {
		try {
			String result = syncBookService.syncBooks();
			if (result.equals("1")) {
				return okNoResult("同步教材成功");
			} else if (result.equals("2")) {
				return okNoResult("同步失败,获取教材信息为空!");
			} else if (result.equals("0")) {
				return failed(ExceptionCode.SYNC_BOOK_ERROR, "同步教材失败!");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误，请联系管理员!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.SYNC_BOOK_ERROR, "同步教材失败!");
		}
	}

}
