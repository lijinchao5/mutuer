/**
 * @fileName:  AreaController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月20日 上午9:44:43
 */ 
package com.xuanli.oepcms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.service.AreaService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
 * @author  QiaoYu[www.codelion.cn]
 */
@RestController()
@RequestMapping(value = "/area/")
public class AreaController extends BaseController{
	@Autowired
	AreaService areaService;
	
	
	@ApiOperation(value = "获取地域信息", notes = "获取地区信息")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "level", value = "级别 1:省2:市3:地区", required = false, dataType = "String"),
			@ApiImplicitParam(name = "regionId", value = "该区域id", required = false, dataType = "String"), 
	})
	@RequestMapping(value = "getRegion.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getRegion(String level,String regionId){
		return areaService.getRegion(level,regionId);
	}

}
