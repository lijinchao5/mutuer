/**
 * @fileName:  PaperController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月26日 上午11:46:14
 */
package com.xuanli.oepcms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.service.PaperService;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author QiaoYu
 */
@RestController
@RequestMapping(value = "/paper/")
public class PaperController extends BaseController {
	@Autowired
	PaperService paperService;

	@ApiOperation(value = "分页查询试卷信息", notes = "分页查询试卷信息方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "gradeLevelId", value = "年级", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "term", value = "学期", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "addressArea", value = "地区", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "questionType", value = "题型", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "paperType", value = "试卷类型", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "ob", value = "排序字段:size,create_date", required = true, dataType = "String"),
			@ApiImplicitParam(name = "sb", value = "排序顺序 asc,desc", required = true, dataType = "String"),
			@ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "String"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "String") })
	@RequestMapping(value = "findPaperByPage.do", method = RequestMethod.GET)
	public RestResult<PageBean> findPaperByPage(String ob, String sb, Integer rows, Integer page, Integer gradeLevelId, Integer term, Integer addressArea, Integer questionType,
			Integer paperType) {
		PageBean pageBean = initPageBean(page, rows);
		Map<String, Object> requestMap = new HashMap<String, Object>();
		Map<String, String[]> requestMap1 = request.getParameterMap();
		requestMap = requestParamToMap(requestMap1);
		requestMap.put("createId", getCurrentUser().getId());
		// requestMap.put("gradeLevelId", gradeLevelId);
		// requestMap.put("term", term);
		// requestMap.put("addressArea", addressArea);
		// requestMap.put("questionType", questionType);
		// requestMap.put("paperType", paperType);
		paperService.findPaperByPage(requestMap, pageBean);
		return ok(pageBean);
	}
	@ApiOperation(value = "获取试卷详细信息", notes = "获取试卷信息信息方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "paperId", value = "试卷id", required = true, dataType = "Long") })
	@RequestMapping(value = "getPaperDetail.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> getPaperDetail(Long paperId) {
		return paperService.getPaperDetail(paperId);
	}

	/**
	 * --------------------------------------------------------下面是组卷的接口支持--------------------------------------------------
	 **/
	/**
	 * @Description: TODO 获取试卷的详细信息---组卷题目展示
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月5日 上午9:50:33
	 */
	@ApiOperation(value = "分页获取试卷的详细信息", notes = "分页获取试卷的详细信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "gradeLevelId", value = "年级", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "term", value = "学期", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "addressArea", value = "地区", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "questionType", value = "题型", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "paperType", value = "试卷类型", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "ob", value = "排序字段:usedCount,createDate", required = true, dataType = "String"),
			@ApiImplicitParam(name = "sb", value = "排序顺序 asc,desc", required = true, dataType = "String"),
			@ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "String"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "String") })
	@RequestMapping(value = "findPaperDetailByPage.do", method = RequestMethod.GET)
	public RestResult<PageBean> findPaperDetailByPage(String ob, String sb, Integer gradeLevelId, Integer term, Integer addressArea, Integer questionType, Integer paperType,
			Integer rows, Integer page) {
		PageBean pageBean = initPageBean(page, rows);
		Map<String, Object> requestMap = new HashMap<String, Object>();
		Map<String, String[]> requestMap1 = request.getParameterMap();
		requestMap = requestParamToMap(requestMap1);
		paperService.findPaperDetailByPage(requestMap, pageBean);
		return ok(pageBean);
	}

	// 组卷
	@ApiOperation(value = "组卷", notes = "组卷")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "name", value = "试卷名字", required = true, dataType = "String"),
			@ApiImplicitParam(name = "notice", value = "试卷须知", required = false, dataType = "String"),
			@ApiImplicitParam(name = "paperInfo", value = "组卷信息 \"[{\"subjectId\":1,\"score\":\"1\"},{\"subjectId\":2,\"score\":\"1,3\"}]\"", required = true, dataType = "String"),
			@ApiImplicitParam(name = "totalTime", value = "总时长", required = true, dataType = "Integer") })
	public RestResult<String> generatorPaper(String name, String notice, Integer totalTime, String paperInfo) {
		Long userId = getCurrentUser().getId();
		return paperService.generatorPaper(userId, name, notice, totalTime, paperInfo);
	}

}
