/**
 * @fileName:  QuestionController.java 
 * @Description:  TODO
 * @CreateName:  codelion[QiaoYu]
 * @CreateDate:  2018年3月15日 上午10:32:17
 */
package com.xuanli.oepcms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.service.QuestionService;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author codelion[QiaoYu]
 */
@RestController
@RequestMapping(value = "/question/")
public class QuestionController extends BaseController {
	@Autowired
	QuestionService questionService;
	
	
	@ApiOperation(value = "分页获取试题的详细信息", notes = "分页获取试题的详细信息")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "gradeLevelId", value = "年级", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "term", value = "学期", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "type", value = "题型", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "ob", value = "排序字段:usedCount,createDate", required = true, dataType = "String"),
			@ApiImplicitParam(name = "sb", value = "排序顺序 asc,desc", required = true, dataType = "String"),
			@ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "String"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "String") 
	})
	@RequestMapping(value = "findQuestionDetailByPage.do", method = RequestMethod.GET)
	public RestResult<PageBean> findQuestionDetailByPage(String ob, String sb, Integer gradeLevelId, Integer term, Integer type, Integer rows, Integer page) {
		PageBean pageBean = initPageBean(page, rows);
		Map<String, Object> requestMap = new HashMap<String, Object>();
		Map<String, String[]> requestMap1 = request.getParameterMap();
		requestMap = requestParamToMap(requestMap1);
		questionService.findQuestionDetailByPage(requestMap, pageBean);
		return ok(pageBean);
	}
	
	
	

}
