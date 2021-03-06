/**
 * 
 */
package com.xuanli.oepcms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.service.ClasService;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年4月25日 上午9:49:21
 */
@RestController()
@RequestMapping(value = "/studentClass/")
public class StudentClassController extends BaseController {
	@Autowired
	ClasService classService;
	@Autowired
	UserService userService;

	@ApiOperation(value = "查询学生班级", notes = "查询学生班级")
	@ApiImplicitParams({})
	@RequestMapping(value = "getStudentClass.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getStudentClass() {
		Long studentId = getCurrentUser().getId();
		return ok(classService.getStudentClass(studentId));
	}

	/**
	 * Title: addStudentClass 
	 * Description:  
	 * @date 2018年4月25日 上午10:26:42
	 * @param classId
	 * @return
	 */
	@ApiOperation(value = "增加学生班级", notes = "增加学生班级")
	@ApiImplicitParams({ @ApiImplicitParam(name = "classId", value = "班级id", required = true, dataType = "String") })
	@RequestMapping(value = "addStudentClass.do", method = RequestMethod.POST)
	public RestResult<String> addStudentClass(String classId) {
		String result = userService.inserUserClas(getCurrentUser().getId(), classId);
		if (result.equals("1")) {
			return ok("添加班级成功");
		} else if (result.equals("0")) {
			return failed(ExceptionCode.ADD_STUDENT_CLASS_ERROR, "添加班级出现错误");
		} else if (result.equals("2")) {
			return failed(ExceptionCode.ADD_STUDENT_CLASS_ERROR, "班级编号不正确，该班级不存在");
		} else if (result.equals("3")) {
			return failed(ExceptionCode.ADD_STUDENT_CLASS_ERROR, "该班级已经解散");
		} else {
			return failed(ExceptionCode.UNKNOW_CODE, "未知错误，请联系管理员");
		}
	}

	/**
	 * Title: deleteStudentClass 
	 * Description:  
	 * @date 2018年4月25日 上午10:26:39
	 * @param studentId
	 * @return
	 */
	@ApiOperation(value = "删除学生班级", notes = "删除学生班级")
	@ApiImplicitParams({ @ApiImplicitParam(name = "classId", value = "班级id", required = true, dataType = "Long") })
	@RequestMapping(value = "deleteStudentClass.do", method = RequestMethod.POST)
	public RestResult<String> deleteStudentClass(Long classId) {
		String result = classService.deleteStudentClass(getCurrentUser().getId(), classId);
		if (result.equals("1")) {
			return ok("删除班级成功");
		} else if (result.equals("0")) {
			return failed(ExceptionCode.DELETE_STUDENT_ERROR, "删除班级失败");
		} else {
			return failed(ExceptionCode.UNKNOW_CODE, "未知错误，请联系管理员");
		}
	}

	/**
	 */
	@ApiOperation(value = "获取同班同学", notes = "获取同班同学")
	@ApiImplicitParams({})
	@RequestMapping(value = "getClassmate.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getClassmate(Long classId) {
		return ok(userService.getClassmate(classId));
	}
}
