/**
 * @fileName:  DicController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月30日 上午9:40:49
 */ 
package com.xuanli.oepcms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.DicDetailEntity;
import com.xuanli.oepcms.service.DicService;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
	
	@ApiOperation(value = "年级对应教材版本", notes = "年级对应教材版本")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grade", value = "年级", required = true, dataType = "String") })
	@RequestMapping(value = "getBookVersion.do", method = RequestMethod.GET)
	public  RestResult<List<DicDetailEntity>> getBookVersion(String grade) {
		return ok(dicService.getBookVersion(grade));
	}
}
