/**
 * @fileName:  HomeworkController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 下午3:24:28
 */
package com.xuanli.oepcms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.controller.bean.HomeworkBean;
import com.xuanli.oepcms.controller.bean.HomeworkScoreBean;
import com.xuanli.oepcms.entity.HomeworkEntity;
import com.xuanli.oepcms.entity.HomeworkStudentEntity;
import com.xuanli.oepcms.service.HomeworkService;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author QiaoYu
 */

@RestController()
// @RequestMapping(value = "/homework", method = RequestMethod.POST)
@RequestMapping(value = "/homework/")
public class HomeworkController extends BaseController {
	@Autowired
	private HomeworkService homeworkService;

	@ApiOperation(value = "教师作业列表", notes = "教师作业列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "status", value = "状态 1:进行中,2:已完成", required = true, dataType = "String"),
			@ApiImplicitParam(name = "clasId", value = "班级ids 使用,隔开", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "String"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "String") })
	@RequestMapping(value = "findHomeworkPage.do", method = RequestMethod.GET)
	public RestResult<PageBean> findHomeworkPage(String status, Long clasId, Integer rows, Integer page) {
		PageBean pageBean = initPageBean(page, rows);
		// 保证这个班是这个老师创建的
		HomeworkEntity homeworkEntity = new HomeworkEntity();
		homeworkEntity.setStatus(status);
		homeworkEntity.setClasId(clasId);
		homeworkEntity.setCreateId(getCurrentUser().getId());
		homeworkService.findHomeworkPage(homeworkEntity, pageBean);
		return ok(pageBean);
	}

	/**
	 * @Description: TODO 教师布置家庭作业
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月18日 下午3:31:48
	 */
	@ApiOperation(value = "布置作业", notes = "老师布置作业方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "name", value = "作业名称", required = true, dataType = "String"),
			@ApiImplicitParam(name = "classId", value = "班级id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "endTime", value = "作业完成时间 格式 yyyy-MM-dd HH:mm:ss", required = true, dataType = "String"),
			@ApiImplicitParam(name = "remark", value = "", required = true, dataType = "String"),
			@ApiImplicitParam(name = "homeworkBeans", value = "作业列表", required = true, dataType = "String") })
	@RequestMapping(value = "makeHomeWork.do", method = RequestMethod.POST)
	public RestResult<String> makeHomeWork(@RequestParam String name, @RequestParam String classId, @RequestParam Date endTime, @RequestParam String remark,
			@RequestParam String homeworkBeans) {
		Date now = new Date();
		if (StringUtil.isEmpty(name)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "请填写作业名称");
		}
		if (StringUtil.isEmpty(classId)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "请选择班级");
		}
		if (null == endTime || endTime.getTime() < now.getTime()) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "结束时间早于当前时间!");
		}
		if (StringUtil.isEmpty(name)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "请填写作业名称");
		}
		if (null == homeworkBeans) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "请布置作业内容");
		}
		try {
			Long createId = getCurrentUser().getId();
			List<HomeworkBean> hbBeans = JSONArray.parseArray(homeworkBeans, HomeworkBean.class);
			homeworkService.makeHomeWork(name, classId, endTime, remark, hbBeans, createId);
			return okNoResult("成功.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("布置家庭作业出现异常", e);
			return failed(ExceptionCode.UNKNOW_CODE, "布置家庭作业出现异常");
		}
	}

	@ApiOperation(value = "学生提交作业", notes = "学生提交作业方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "homeworkId", value = "作业id", required = true, dataType = "Long") })
	@RequestMapping(value = "submitHomework.do", method = RequestMethod.POST)
	public RestResult<String> doHomeWork(Long homeworkId) {
		try {
			Long studentId = getCurrentUser().getId();
			return homeworkService.submitHomework(studentId, homeworkId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("学生提交作业出现异常", e);
			return failed(ExceptionCode.UNKNOW_CODE, "学生提交作业出现异常");
		}
	}

	/**
	 * 学生做作业
	 * 
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月19日 上午9:32:12
	 */
	@ApiOperation(value = "学生做作业", notes = "学生做作业方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "sectionId", value = "作业详情id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "homeworkId", value = "作业id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "audioFile", value = "学生音频文件类答案", required = true, dataType = "String"),
			@ApiImplicitParam(name = "text", value = "学生文本类答案", required = true, dataType = "String"), })
	@RequestMapping(value = "doHomeWork.do", method = RequestMethod.POST)
	public RestResult<Map<String, Object>> doHomeWork(@RequestParam Long sectionId, @RequestParam Long homeworkId, @RequestParam(required = false) String file,
			@RequestParam(required = false) String text) {
		try {
			Long studentId = getCurrentUser().getId();
			return homeworkService.doHomeWork(studentId, sectionId, homeworkId, file, text, request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("学生做作业出现异常", e);
			return failed(ExceptionCode.UNKNOW_CODE, "学生做作业出现异常");
		}
	}

	@ApiOperation(value = "老师写评语", notes = "老师写作业评语方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userIds", value = "要写评语的学生id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "homeworkId", value = "作业id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "remark", value = "评语内容", required = true, dataType = "String") })
	@RequestMapping(value = "homeWorkRemark.do", method = RequestMethod.PUT)
	public RestResult<String> homeworkRemark(@RequestParam String userIds, @RequestParam Long homeworkId, @RequestParam String remark) {
		try {
			String result = homeworkService.updateHomewordStudentEntityRemark(userIds, homeworkId, remark);
			if (result.equals("0")) {
				return okNoResult("写评语成功");
			} else if (result.equals("2")) {
				return failed(ExceptionCode.UNKNOW_CODE, "写评语出现异常，请联系管理员");
			} else if (result.equals("1")) {
				return failed(ExceptionCode.UPDATE_BATCH_REMARK_ERROR, "请现选择学生");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "写评语出现异常，请联系管理员");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("写评语出现异常", e);
			return failed(ExceptionCode.UNKNOW_CODE, "写评语出现异常");
		}
	}

	@ApiOperation(value = "查看学生作业详情", notes = "查看学生作业详情方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "homeworkId", value = "作业id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "studentId", value = "学生id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "homeworkType", value = "作业类型", required = true, dataType = "String") })
	@RequestMapping(value = "studentHomeWorkDetail.do", method = RequestMethod.GET)
	public RestResult<List<HomeworkScoreBean>> studentHomeworkDetail(@RequestParam Long homeworkId, @RequestParam Long studentId, @RequestParam Integer homeworkType) {
		return ok(homeworkService.getStudentHomework(homeworkId, studentId, homeworkType));
	}

	@ApiOperation(value = "作业报告学生详情", notes = "查看作业报告学生详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "homeworkId", value = "作业id", required = true, dataType = "Long") })
	@RequestMapping(value = "selectStudentEntity.do", method = RequestMethod.GET)
	public RestResult<List<HomeworkStudentEntity>> selectStudentEntity(Long homeworkId) {
		if (null == homeworkId) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "作业id不能为空");
		}
		return ok(homeworkService.selectStudentEntity(homeworkId));
	}

	@ApiOperation(value = "查看布置作业详情", notes = "查看布置作业详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "homeworkId", value = "作业id", required = true, dataType = "Long") })
	@RequestMapping(value = "getHomeworkDetail.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> getHomeworkDetail(Long homeworkId) {
		Long studentId = getCurrentUser().getId();
		return homeworkService.getHomeworkDetail(homeworkId, studentId);
	}

	@ApiOperation(value = "查看布置作业详情", notes = "查看布置作业详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer") })
	@RequestMapping(value = "getStudentHomeWorkList.do", method = RequestMethod.GET)
	public RestResult<PageBean> getStudentHomeWorkList(Integer rows, Integer page, Long homeworkId, String over, Long classId) {
		PageBean pageBean = initPageBean(page, rows);
		Map<String, Object> requestMap = new HashMap<String, Object>();
		Map<String, String[]> requestMap1 = request.getParameterMap();
		requestMap = requestParamToMap(requestMap1);
		requestMap.put("studentId", getCurrentUser().getId());
		requestMap.put("over", over);
		requestMap.put("clasId", classId);
		homeworkService.getStudentHomeWorkList(requestMap, pageBean);
		return ok(pageBean);
	}

}
