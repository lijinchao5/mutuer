/**
 * 
 */
package com.xuanli.oepcms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.ExerciseEntity;
import com.xuanli.oepcms.entity.ReadArticleEntity;
import com.xuanli.oepcms.service.ExerciseService;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年3月9日 下午5:16:16
 */
@RestController()
@RequestMapping(value = "/exercise/")
public class ExerciseController extends BaseController {
	@Autowired
	ExerciseService exerciseService;

	@ApiOperation(value = "文章列表", notes = "文章列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "level", value = "级别:A,B,C", required = false, dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "String"),
			@ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer") })
	@RequestMapping(value = "findExercisePage.do", method = RequestMethod.POST)
	public RestResult<PageBean> findExercisePage(String level, String type, Integer rows, Integer page) {
		PageBean pageBean = initPageBean(page, rows);
		ReadArticleEntity readArticleEntity = new ReadArticleEntity();
		readArticleEntity.setType(type);
		readArticleEntity.setLevel(level);
		readArticleEntity.setUserId(getCurrentUser().getId());
		exerciseService.findReadArticlePage(readArticleEntity, pageBean);
		return ok(pageBean);
	}

	// 获取文章的详细信息
	@RequestMapping(value = "findArtcileEntityById.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> findArtcileEntityById(Long id) {
		return exerciseService.findArtcileEntityById(id);
	}

	@ApiOperation(value = "段落详情", notes = "段落详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "level", value = "级别:A,B,C", required = false, dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "String"),
			@ApiImplicitParam(name = "studentId", value = "学生id", required = true, dataType = "Long"), })
	@RequestMapping(value = "getExerciseDetail.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getReadSentence(Long articleId) {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("articleId", articleId);
		requestMap.put("studentId", getCurrentUser().getId());
		return ok(exerciseService.getReadSentence(requestMap));
	}

	@ApiOperation(value = "学生做练习", notes = "学生做练习")
	@ApiImplicitParams({ @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "sentenceId", value = "段落id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "audioFile", value = "学生音频文件类答案", required = true, dataType = "String"), })
	@RequestMapping(value = "doExercise.do", method = RequestMethod.POST)
	public RestResult<Map<String, Object>> doExercise(@RequestParam Long articleId, @RequestParam Long sentenceId, @RequestParam String file) {
		try {
			Long studentId = getCurrentUser().getId();
			return exerciseService.doExercise(studentId, articleId, sentenceId, file);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("练习出现异常", e);
			return failed(ExceptionCode.UNKNOW_CODE, "练习出现异常");
		}
	}

	@ApiOperation(value = "学生提交练习", notes = "学生提交练习")
	@ApiImplicitParams({ @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Long") })
	@RequestMapping(value = "submitExercise.do", method = RequestMethod.POST)
	public RestResult<ExerciseEntity> submitExercise(@RequestParam Long articleId) {
		if (null == articleId) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "文章id不能为空");
		}
		try {
			Long studentId = getCurrentUser().getId();
			return exerciseService.submitExercise(studentId, articleId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("提交练习出现异常", e);
			return failed(ExceptionCode.UNKNOW_CODE, "提交练习出现异常");
		}
	}

	@ApiOperation(value = "书架列表", notes = "书架列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer") })
	@RequestMapping(value = "getArtcileRead.do", method = RequestMethod.GET)
	public RestResult<PageBean> getArtcileRead(Integer rows, Integer page) {
		Long studentId = getCurrentUser().getId();
		PageBean pageBean = initPageBean(page, rows);
		exerciseService.getExerciseRead(studentId, pageBean);
		return ok(pageBean);
	}

	@ApiOperation(value = "练习报告", notes = "练习报告")
	@RequestMapping(value = "getStudentExerciseResult.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getStudentExerciseResult() {
		Long studentId = getCurrentUser().getId();
		return exerciseService.getStudentExerciseResult(studentId);
	}
}
