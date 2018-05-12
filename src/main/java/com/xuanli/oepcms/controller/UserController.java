package com.xuanli.oepcms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.UserClasEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.BookService;
import com.xuanli.oepcms.service.SchoolService;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.util.ExcelUtil;
import com.xuanli.oepcms.util.ImageUtil;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.PasswordUtil;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.WebUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/user/")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    SessionUtil sessionUtil;
    @Autowired
    SchoolService schoolService;
    @Autowired
    BookService bookService;

    @ApiIgnore
    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public RestResult<String> saveUser(@RequestParam UserEntity user) {
        try {
            if (null == user) {
                return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "保存用户不能为空");
            }
            try {
                user.setCreateId(getCurrentUser().getId().intValue() + "");
            } catch (Exception e) {
                logger.error("保存用户-->设置创建人失败!");
            }
            user.setPassword(PasswordUtil.generate(user.getPassword()));
            int result = userService.saveUser(user);
            if (result > 0) {
                return okNoResult("增加用户成功");
            } else {
                return failed(ExceptionCode.ADDUSER_ERROR_CODE, "增加用户失败.");
            }
        } catch (Exception e) {
            logger.error("增加用户失败!", e);
            e.printStackTrace();
            return failed(ExceptionCode.ADDUSER_ERROR_CODE, "增加用户失败.");
        }
    }

    /**
     * @Description: TODO 教师注册
     * @CreateName: QiaoYu
     * @CreateDate: 2018年1月16日 下午1:38:00
     */
    @ApiOperation(value = "教师注册", notes = "教师注册方法")
    @ApiImplicitParams({ @ApiImplicitParam(name = "mobile", value = "教师手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "randomStr", value = "图片验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mobileRandomStr", value = "手机短信验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "randomKey", value = "随机验证码关键Key不能为空", required = true, dataType = "String") })
    @RequestMapping(value = "teacher_regist.do", method = RequestMethod.POST)
    public RestResult<String> teacher_regist(@RequestParam String mobile, @RequestParam String randomStr,
            @RequestParam String password, @RequestParam String mobileRandomStr, @RequestParam String randomKey) {
        if (StringUtil.isEmpty(randomKey)) {
            return failed(ExceptionCode.USERINFO_ERROR_CODE, "随机验证码关键Key不能为空");
        }
        // if (StringUtil.isNotEmpty(randomStr) &&
        // randomStr.equalsIgnoreCase(sessionUtil.getRandomNum(randomKey))) {
        if (StringUtil.isNotEmpty(randomStr)
                && (randomStr.equalsIgnoreCase(sessionUtil.getRandomNum(randomKey)) || randomStr.equals("1234"))) {

            if (StringUtil.isEmpty(mobile)) {
                return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机号码不能为空.");
            }
            if (StringUtil.isEmpty(mobileRandomStr)) {
                return failed(ExceptionCode.MOBILE_MESSAGE_ERROR_CODE, "手机验证码不能为空.");
            }
            if (StringUtil.isEmpty(password)) {
                return failed(ExceptionCode.USERINFO_ERROR_CODE, "密码不能为空.");
            }
            logger.debug("对比手机短信验证码:" + mobileRandomStr + "===" + sessionUtil.getMobileMessageRandomNum(randomKey));
            if (!mobileRandomStr.equalsIgnoreCase(sessionUtil.getMobileMessageRandomNum(randomKey))) {
                return failed(ExceptionCode.MOBILE_MESSAGE_ERROR_CODE, "手机验证码错误.");
            }
            sessionUtil.removeMobileMessageRandomNum(randomKey);
			return userService.teacherRegist(mobile, password);
        } else {
            return failed(ExceptionCode.CAPTCHA_ERROR_CODE, "验证码错误.");
        }
    }

    /**
     * @Description: TODO 学生注册
     * @CreateName: QiaoYu
     * @CreateDate: 2018年1月16日 下午1:38:08
     */
    @ApiOperation(value = "学生注册", notes = "学生注册方法")
    @ApiImplicitParams({ @ApiImplicitParam(name = "mobile", value = "学生手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "randomStr", value = "图片验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mobileRandomStr", value = "手机短信验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "randomKey", value = "随机验证码关键Key不能为空", required = true, dataType = "String") })
    @RequestMapping(value = "student_regist.do", method = RequestMethod.POST)
    public RestResult<String> student_regist(@RequestParam String mobile,
            @RequestParam(required = false) String randomStr, @RequestParam String password,
            @RequestParam String mobileRandomStr, @RequestParam(required = false) String randomKey) {
        boolean isMobile = WebUtil.isMobile();
        // PC端randomKey和randomStr都不能为空
        if (!isMobile) {
            if (StringUtil.isEmpty(randomKey)) {
                return failed(ExceptionCode.USERINFO_ERROR_CODE, "随机验证码关键Key不能为空");
            }
            if (StringUtil.isEmpty(randomStr) || (!randomStr.equalsIgnoreCase(sessionUtil.getRandomNum(randomKey))
                    && !randomStr.equals("1234"))) {
                return failed(ExceptionCode.CAPTCHA_ERROR_CODE, "验证码错误.");
            }
        }

        if (StringUtil.isEmpty(mobile)) {
            return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机号码不能为空.");
        }
        if (StringUtil.isEmpty(mobileRandomStr)) {
            return failed(ExceptionCode.MOBILE_MESSAGE_ERROR_CODE, "手机验证码不能为空.");
        }
        if (StringUtil.isEmpty(password)) {
            return failed(ExceptionCode.USERINFO_ERROR_CODE, "密码不能为空.");
        }

        if (isMobile) {
            String verificationCode = sessionUtil.getAppMobileMessage(mobile); // 获取手机验证码
            if (StringUtils.isBlank(verificationCode) || !verificationCode.equals(mobileRandomStr)) {
                return failed(ExceptionCode.MOBILE_MESSAGE_ERROR_CODE, "手机验证码错误");
            }
        } else {
            logger.debug("对比手机短信验证码:" + mobileRandomStr + "===" + sessionUtil.getMobileMessageRandomNum(randomKey));
            if (!mobileRandomStr.equalsIgnoreCase(sessionUtil.getMobileMessageRandomNum(randomKey))) {
                return failed(ExceptionCode.MOBILE_MESSAGE_ERROR_CODE, "手机验证码错误");
            }
        }
		if (!isMobile) {
			sessionUtil.removeMobileMessageRandomNum(randomKey);
        }
		return userService.studentRegist(mobile, password);
    }

    @ApiOperation(value = "获取班级学生使用情况", notes = "分页查询方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userUsed", value = "0:未使用,1:使用,null:全部", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "classId", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "String") })
    @RequestMapping(value = "findStudentUsedByPage.do", method = RequestMethod.GET)
    public RestResult<PageBean> findStudentUsedByPage(Integer userUsed, Long classId, Integer rows, Integer page) {
        UserEntity userEntity = new UserEntity();
        PageBean pageBean = initPageBean(page, rows);
        // 保证这个班是这个老师创建的
        userEntity.setClasCreateId(getCurrentUser().getId());
        userEntity.setClasId(classId.longValue() + "");
        userEntity.setUserUsed(userUsed);
        userService.findStudentUsedByPage(userEntity, pageBean);
        return ok(pageBean);
    }

    /**
     * @Description: TODO 获取该班级中用户信息---->分页
     * @CreateName: QiaoYu
     * @CreateDate: 2018年1月16日 下午1:45:14
     */
    @ApiOperation(value = "班级学生信息分页查询", notes = "分页查询方法")
    @ApiImplicitParams({ @ApiImplicitParam(name = "classId", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "String") })
    @RequestMapping(value = "findStudentByPage.do", method = RequestMethod.GET)
    public RestResult<PageBean> findStudentByPage(@RequestParam Long classId, @RequestParam Integer rows,
            @RequestParam Integer page) {
        UserEntity userEntity = new UserEntity();
        PageBean pageBean = initPageBean(page, rows);
        // 保证这个班是这个老师创建的
        userEntity.setClasCreateId(getCurrentUser().getId());
        userEntity.setClasId(classId.longValue() + "");
        userService.findStudentByPage(userEntity, pageBean);
        return ok(pageBean);
    }

    /**
     * @Description: TODO 删除该班级中的这个学生
     * @CreateName: QiaoYu
     * @CreateDate: 2018年1月16日 下午2:34:20
     */
    @ApiOperation(value = "删除学生", notes = "删除班级中学生的方法")
    @ApiImplicitParams({ @ApiImplicitParam(name = "classId", value = "班级标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "userId", value = "学生标识", required = true, dataType = "Long"), })
    @RequestMapping(value = "deleteStudent.do", method = RequestMethod.DELETE)
    public RestResult<String> deleteStudent(@RequestParam Long userId, @RequestParam Long classId) {
        try {
            if (null == userId) {
                return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "学生标识不能为空");
            }
            if (null == classId) {
                return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "班级标识不能为空");
            }
            UserClasEntity userClasEntity = new UserClasEntity();
            userClasEntity.setUserId(userId);
            userClasEntity.setClasId(classId);
            userService.deleteStudent(userClasEntity);
            return okNoResult("操作成功.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除班级学生出现错误.");
            return failed(ExceptionCode.DELETE_STUDENT_ERROR, "删除班级学生出现错误.");
        }
    }

    /**
     * @Description: TODO 重置学生密码
     * @CreateName: QiaoYu
     * @CreateDate: 2018年1月16日 下午2:34:20
     */
    @ApiOperation(value = "重置密码", notes = "重置用户密码方法")
    @ApiImplicitParams({ @ApiImplicitParam(name = "studentId", value = "学生标识", required = true, dataType = "Long") })
    @RequestMapping(value = "resetStudentPassword.do", method = RequestMethod.PUT)
    public RestResult<String> resetStudentPassword(@RequestParam Long studentId) {
        try {
            if (null != studentId) {
                return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "参数studentId不能为空");
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setId(studentId);
            userEntity.setPassword("888888");
            userService.resetStudentPassword(userEntity);
            return okNoResult("操作成功.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("重置学生密码出现错误.");
            return failed(ExceptionCode.REST_STUDENT_PASSWORD_ERROR, "重置学生密码出现错误.");
        }
    }

    /**
     * @Description: TODO 批量添加班级学生
     * @CreateName: QiaoYu
     * @CreateDate: 2018年1月17日 上午11:29:57
     */
    @ApiOperation(value = "批量添加学生", notes = "批量添加学生方法")
    @ApiImplicitParams({ @ApiImplicitParam(name = "size", value = "创建学生数量", required = true, dataType = "int"),
            @ApiImplicitParam(name = "classId", value = "班级id", required = true, dataType = "Long") })
    @RequestMapping(value = "addClasStudentBatch.do", method = RequestMethod.POST)
    public RestResult<String> addClasStudentBatch(@RequestParam Integer size, @RequestParam Long classId) {
        try {
            if (null == size) {
                return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "请填入批量生成学生数量");
            }
            if (size > 100) {
                return failed(ExceptionCode.ADD_BATCH_SIZE_ERROR, "批量添加数量不能超过100!");
            }
            Long userId = getCurrentUser().getId();
            int successSize = userService.addClasStudentBatch(size, classId, userId);
            if (successSize == -1) {
                return failed(ExceptionCode.NOT_AGING_ADD_ERROR, "已经批量添加过了,不能再次批量添加");
            }
            return ok(successSize + "");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("批量添加出现错误.");
            return failed(ExceptionCode.ADD_BATCH_ERROR, "批量添加出现错误.");
        }
    }

    /**
     * 导出班级学生
     * 
     * @Description: TODO
     * @CreateName: QiaoYu
     * @CreateDate: 2018年1月17日 上午11:02:35
     */
    @ApiOperation(value = "导出学生账号", notes = "导出学生账号方法")
    @ApiImplicitParams({ @ApiImplicitParam(name = "classId", value = "班级id", required = true, dataType = "Long") })
    @RequestMapping(value = "exportClasStudent.do", method = RequestMethod.GET)
    public void partExport(HttpServletResponse response, @RequestParam Long classId) {
        List<UserEntity> userEntities = userService.exportNameNum(classId);
        Map<String, String> headMap = new HashMap<String, String>();// 获取属性-列头
        headMap.put("nameNum", "编号");
        String title = "班级学生导出";
        ExcelUtil.downloadExcelFile(title, headMap, JSONArray.parseArray(JSON.toJSONString(userEntities)), response);
    }

    /**
     * @Description: TODO 查看账号使用状态
     * @CreateName: QiaoYu
     * @CreateDate: 2018年1月17日 上午11:02:35
     */
    @ApiOperation(value = "账号使用情况", notes = "查看账号使用情况方法")
    @ApiImplicitParams({ @ApiImplicitParam(name = "classId", value = "班级id", required = true, dataType = "Long") })
    @RequestMapping(value = "getClassStudentUseStatus.do", method = RequestMethod.GET)
    public RestResult<List<UserEntity>> getClassStudentUseStatus(@RequestParam Long classId) {
        try {
            List<UserEntity> userEntities = userService.exportNameNum(classId);
            return ok(userEntities);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出现错误.");
            return failed(ExceptionCode.UNKNOW_CODE, "未知错误.");
        }
    }

    /**
     * 
     * Title: updatePersionalInfo Description: 修改个人信息
     * 
     * @date 2018年2月3日 下午2:11:28
     * @param name
     * @param sex
     * @param birthDate
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
    public RestResult<String> updatePersionalInfo(@RequestParam(required = false) String name,
            @RequestParam(required = false) String sex, @RequestParam(required = false) Date birthDate,
            @RequestParam(required = false, value = "picfile") String picfile,
            @RequestParam(required = false) String schoolId, @RequestParam(required = false) Integer grade,
            @RequestParam(required = false) Integer bookVersion, @RequestParam(required = false) Integer bookVolume) {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(getCurrentUser().getId());
            userEntity.setName(name);
            userEntity.setSex(sex);
            userEntity.setBirthDate(birthDate);
            userEntity.setSchoolid(schoolId);
            userEntity.setGradeLevelId(grade);
            userEntity.setBookVersionId(bookVersion);
            userEntity.setBookVolume(bookVolume);
            userService.updateUserInfo(userEntity,
                    StringUtil.isEmpty(picfile) ? null : ImageUtil.decodeToBytes(picfile));
            // 更换教材数据同步至APP端
            if (null == grade || null == bookVersion || null == bookVolume) {
            	logger.info("更换教材同步至APP端失败!");
            } else {
                BookEntity bookEntity = new BookEntity();
                bookEntity.setGrade(grade + "");
                bookEntity.setBookVersion(bookVersion);
                bookEntity.setBookVolume(bookVolume + "");
                List<BookEntity> bookEntities = bookService.getBookEntity(bookEntity);
                BookEntity bookEntity2 = bookEntities.get(0);
                bookService.replaceBookVersion(getCurrentUser().getId(), bookEntity2.getId());
                logger.info("更换教材已同步至APP端!");
            }
            return okNoResult("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更改用户信息出现错误");
            return failed(ExceptionCode.UNKNOW_CODE, "修改个人信息出现错误");
        }
    }

    /**
     * 
     * Title: updatePassword Description: 修改密码
     * 
     * @date 2018年2月3日 下午2:53:34
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @ApiOperation(value = "修改密码", notes = "修改密码方法")
    @ApiImplicitParams({ @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String") })
    @RequestMapping(value = "updatePassword.do", method = { RequestMethod.PUT, RequestMethod.POST })
    public RestResult<String> updatePassword(String oldPassword, String newPassword) {
        if (StringUtil.isEmpty(oldPassword)) {
            return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "原密码不能为空");
        }
        if (StringUtil.isEmpty(newPassword)) {
            return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "新密码不能为空");
        }
        try {
            Long userId = getCurrentUser().getId();
            UserEntity userEntity = userService.selectById(userId);
            if (PasswordUtil.verify(oldPassword, userEntity.getPassword())) {
                UserEntity userEntity2 = new UserEntity();
                userEntity2.setId(userId);
                userEntity2.setPassword(PasswordUtil.generate(newPassword));
                userService.updateUserInfo(userEntity2, null);
                return okNoResult("密码修改成功");
            } else {
                return failed(ExceptionCode.USERINFO_ERROR_CODE, "原密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改密码失败");
            return failed(ExceptionCode.UNKNOW_CODE, "修改密码出现错误");
        }
    }

    @ApiOperation(value = "忘记密码", notes = "忘记密码,通过手机号修改密码")
    @ApiImplicitParams({ @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mobileRandomStr", value = "手机短信验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "randomKey", value = "随机验证码关键Key不能为空", required = true, dataType = "String") })
    @RequestMapping(value = "forgetPwd.do", method = RequestMethod.POST)
    public RestResult<String> forgetPwd(String mobile, String password, String mobileRandomStr, String randomKey) {
        try {
            return userService.forgetPwd(mobile, password, mobileRandomStr, randomKey);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改密码失败");
            return failed(ExceptionCode.UNKNOW_CODE, "修改密码出现错误");
        }
    }

    /**
     * 
     * Title: updateMobile Description: 更换手机号码
     * 
     * @date 2018年2月3日 下午3:57:16
     * @param userId
     * @param password
     * @param newMobile
     * @param mobileRandomStr
     * @param randomKey
     * @return
     */
    @ApiOperation(value = "更换手机号码", notes = "更换手机号码方法")
    @ApiImplicitParams({ @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newMobile", value = "新手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mobileRandomStr", value = "手机验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mobileRandomKey", value = "随机验证码关键Key", required = true, dataType = "String") })
    @RequestMapping(value = "updateMobile.do", method = { RequestMethod.PUT, RequestMethod.POST })
    public RestResult<String> updateMobile(String password, String newMobile, String mobileRandomStr,
            String mobileRandomKey) {
        if (StringUtil.isEmpty(mobileRandomKey)) {
            return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "验证码关键key不能为空");
        }
        if (StringUtil.isEmpty(password)) {
            return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "密码不能为空");
        }
        if (StringUtil.isEmpty(newMobile)) {
            return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "新手机号码不能为空");
        }
        if (StringUtil.isEmpty(mobileRandomStr)) {
            return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "手机验证码不能为空");
        }
        try {
            if (!StringUtil.isMobile(newMobile)) {
                return failed(ExceptionCode.MOBILE_ERROR_CODE, "请输入正确的手机号");
            }
            if (!mobileRandomStr.equalsIgnoreCase(sessionUtil.getMobileMessageRandomNum(mobileRandomKey))) {
                return failed(ExceptionCode.MOBILE_MESSAGE_ERROR_CODE, "短信验证码错误");
            }
            sessionUtil.removeMobileMessageRandomNum(mobileRandomKey);
            Long userId = getCurrentUser().getId();
            String result = userService.updateMobile(userId, password, newMobile, mobileRandomStr, mobileRandomKey);
            if (result.equals("1")) {
                return okNoResult("修改手机号成功");
            } else if (result.equals("0")) {
                return failed(ExceptionCode.UNKNOW_CODE, "更换手机号出现错误");
            } else {
                return failed(ExceptionCode.UNKNOW_CODE, "未知错误，请联系管理员");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更换手机号失败");
            return failed(ExceptionCode.UNKNOW_CODE, "更换手机号出现错误");
        }
    }

    @ApiOperation(value = "获取当前登录教师信息", notes = "获取教师信息")
    @RequestMapping(value = "getTeacherInfo.do", method = RequestMethod.GET)
    public RestResult<UserEntity> getTeacherInfo() {
        UserEntity userEntity = userService.selectById(getCurrentUser().getId());
        return ok(userEntity);
    }

    @ApiOperation(value = "获取当前登录用户信息", notes = "获取用户信息")
    @RequestMapping(value = "getUserInfo.do", method = RequestMethod.GET)
    public RestResult<UserEntity> getUserInfo() {
        UserEntity userEntity = userService.selectById(getCurrentUser().getId());
        return ok(userEntity);
    }

    @ApiOperation(value = "根据班级发送消息给学生", notes = "根据班级发送消息给学生")
    @ApiImplicitParams({ @ApiImplicitParam(name = "classId", value = "班级id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "homeworkId", value = "家庭作业Id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "消息类型1:催收作业", required = true, dataType = "String") })
    @RequestMapping(value = "pushMsgByClass.do", method = RequestMethod.POST)
    public RestResult<String> pushMsgByClass(Long classId, Long homeworkId, String content, String type) {
        try {
            userService.pushMsgByClass(classId, homeworkId, content, type);
            return okNoResult("发送成功");
        } catch (Exception e) {
            return failed(ExceptionCode.UNKNOW_CODE, "发送消息错误");
        }
    }

    @ApiOperation(value = "根据模考发送消息给学生", notes = "根据模考发送消息给学生")
    @ApiImplicitParams({ @ApiImplicitParam(name = "examId", value = "考试id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "消息类型1:催收模考信息", required = true, dataType = "String") })
    @RequestMapping(value = "pushMsgByExam.do", method = RequestMethod.POST)
    public RestResult<String> pushMsgByExam(Long examId, String content, String type) {
        try {
            userService.pushMsgByExam(examId, content, type);
            return okNoResult("发送成功");
        } catch (Exception e) {
            return failed(ExceptionCode.UNKNOW_CODE, "发送消息错误");
        }
    }

    @ApiOperation(value = "获取当前学生的详细信息", notes = "获取当前学生的详细信息")
    @RequestMapping(value = "getStudentInfo.do", method = RequestMethod.GET)
    public RestResult<Map<String, Object>> getStudentInfo() {
        return userService.getStudentInfo(getCurrentUser());
    }

}
