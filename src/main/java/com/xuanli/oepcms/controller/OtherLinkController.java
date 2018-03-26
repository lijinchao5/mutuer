/**
 * 
 */
package com.xuanli.oepcms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.entity.OtherLinkEntity;
import com.xuanli.oepcms.service.OtherLinkService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年3月21日 下午5:47:57
 */
@RestController
@RequestMapping(value = "/otherLink/")
public class OtherLinkController extends BaseController {
	@Autowired
	OtherLinkService otherLinkService;

	@ApiOperation(value = "获取链接", notes = "获取链接")
	@RequestMapping(value = "getOtherLink.do", method = RequestMethod.GET)
	public RestResult<List<OtherLinkEntity>> getOtherLink() {
		return ok(otherLinkService.getOtherLink());
	}
}
