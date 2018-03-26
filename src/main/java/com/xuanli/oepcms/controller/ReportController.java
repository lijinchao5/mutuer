/**
 * @fileName:  ReportController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 上午9:32:57
 */ 
package com.xuanli.oepcms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.ReportService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
 * @author  QiaoYu 
 */
@RestController
@RequestMapping(value = "/report/")
public class ReportController extends BaseController{
	@Autowired
	ReportService reportService;
	/**
	 * @Description:  TODO 生成作业报告
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午9:40:32
	 */
	
	@ApiOperation(value = "生成作业报告", notes = "生成作业报告")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "homeworkId", value = "作业id", required = true, dataType = "Long")})
	@RequestMapping(value = "generatorHomeworkReport.do", method = RequestMethod.PUT)
	public RestResult<String> generatorHomeworkReport(@RequestParam Long homeworkId) {
		if(null==homeworkId) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"请先选择作业");
		}
		UserEntity userEntity = getCurrentUser();
		return reportService.generatorHomeworkReport(homeworkId,userEntity.getId());
	}
	/**
	 * @Description:  TODO 获取作业报告信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午9:40:43
	 */
	@ApiOperation(value = "获取作业报告信息", notes = "获取作业报告信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "homeworkId", value = "作业id", required = true, dataType = "Long")})
	@RequestMapping(value = "homeworkReport.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> homeworkReport(@RequestParam Long homeworkId) {
		if(null==homeworkId) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"请先选择作业");
		}
		return reportService.homeworkReport(homeworkId);
	}
	
	@ApiOperation(value = "获取学生作业报告信息[学生端使用]", notes = "获取学生作业报告信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "homeworkId", value = "作业id", required = true, dataType = "Long")})
	@RequestMapping(value = "getStudentHomeworkReport.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> getStudentHomeworkReport(@RequestParam Long homeworkId) {
		if(null==homeworkId) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"请先选择作业");
		}
		Long userId = getCurrentUser().getId();
		return reportService.getStudentHomeworkReport(userId,homeworkId);
	}

	@ApiOperation(value = "获取学生作业报告信息[教师端使用]", notes = "获取学生作业报告信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "homeworkId", value = "作业id", required = true, dataType = "Long"),
		@ApiImplicitParam(name = "studentId", value = "学生id", required = true, dataType = "Long")
	})
	@RequestMapping(value = "getStudentHomeworkReportMsg.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> getStudentHomeworkReportMsg(@RequestParam Long homeworkId,@RequestParam Long studentId) {
		if(null==homeworkId) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"请先选择作业");
		}
		return reportService.getStudentHomeworkReport(studentId,homeworkId);
	}
	
	
	
	
	
}
