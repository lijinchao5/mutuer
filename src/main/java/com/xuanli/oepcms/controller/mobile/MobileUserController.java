/**
 * 
 */
package com.xuanli.oepcms.controller.mobile;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.BookService;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.util.ImageUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年5月10日 上午11:35:54
 */
@RestController
@RequestMapping(value = "/mobile/user/")
public class MobileUserController extends BaseMobileController {
	@Autowired
	UserService userService;
	@Autowired
	BookService bookService;

	/**
	 * Title: updatePersionalInfo 
	 * Description:  
	 * @date 2018年5月10日 上午11:39:17
	 * @param name
	 * @param sex
	 * @param birthDate
	 * @param picfile
	 * @param schoolId
	 * @param grade
	 * @param bookVersion
	 * @param bookVolume
	 * @return
	 */
	@ApiOperation(value = "修改用户个人信息", notes = "修改用户个人信息方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "name", value = "真实姓名", required = true, dataType = "String"),
			@ApiImplicitParam(name = "sex", value = "性别  W:女,M:男", required = true, dataType = "String"),
			@ApiImplicitParam(name = "birthDate", value = "出生日期 yyyy-MM-dd", required = true, dataType = "String"),
			@ApiImplicitParam(name = "schoolId", value = "学校id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "grade", value = "年级", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "bookVersion", value = "教材版本", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "bookVolume", value = "上下册:上册1,下册2", required = true, dataType = "Integer") })
	@RequestMapping(value = "updatePersionalInfo.do", method = RequestMethod.POST)
	public RestResult<String> updatePersionalInfo(@RequestParam(required = false) String name, @RequestParam(required = false) String sex,
			@RequestParam(required = false) Date birthDate, @RequestParam(required = false, value = "picfile") String picfile, @RequestParam(required = false) String schoolId,
			@RequestParam(required = false) Integer grade, @RequestParam(required = false) Integer bookVersion, @RequestParam(required = false) Integer bookVolume) {
		try {
			UserEntity userEntity = new UserEntity();
			userEntity.setId(getCurrentUser().getUserId());
			userEntity.setName(name);
			userEntity.setSex(sex);
			userEntity.setBirthDate(birthDate);
			userEntity.setSchoolid(schoolId);
			userEntity.setGradeLevelId(grade);
			userEntity.setBookVersionId(bookVersion);
			userEntity.setBookVolume(bookVolume);
			userService.updateUserInfo(userEntity, StringUtil.isEmpty(picfile) ? null : ImageUtil.decodeToBytes(picfile));
			// 更换教材数据同步至APP端
			if (null == grade || null == bookVersion || null == bookVolume) {
				logger.info("更换教材同步至APP端失败!");
			} else {
				bookService.replaceBookVersion(getCurrentUser().getUserId(), grade, bookVersion, bookVolume);
				logger.info("更换教材已同步至APP端!");
			}
			return okNoResult("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更改用户信息出现错误");
			return failed(ExceptionCode.UNKNOW_CODE, "修改个人信息出现错误");
		}
	}
}
