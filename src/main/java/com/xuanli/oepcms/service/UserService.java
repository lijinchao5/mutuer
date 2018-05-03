package com.xuanli.oepcms.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xuanli.oepcms.activemq.bean.ActivemqMsgBean;
import com.xuanli.oepcms.activemq.service.StudentMqService;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.ClasEntity;
import com.xuanli.oepcms.entity.ExamEntity;
import com.xuanli.oepcms.entity.HomeworkEntity;
import com.xuanli.oepcms.entity.UserClasEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.mapper.UserEntityMapper;
import com.xuanli.oepcms.util.FileUtil;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.PasswordUtil;
import com.xuanli.oepcms.util.RanNumUtil;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.UsableUtil;
import com.xuanli.oepcms.vo.RestResult;

@Service
@Transactional
public class UserService extends BaseService {
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private UserEntityMapper userDao;
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	FileUtil fileUtil;
	@Autowired
	UsableUtil usableUtil;
	@Autowired
	HomeworkService homeworkService;
	@Autowired
	ExamService examService;
	@Autowired
	StudentMqService studentMqService;
	@Autowired
	ClasService clasService;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午2:13:52
	 */
	public String login(String username, String password, HttpServletRequest request) {
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(username);
		List<UserEntity> userEntities = userDao.login(userEntity);
		if (null != userEntities && userEntities.size() > 0) {
			UserEntity result = userEntities.get(0);
			if (result.getEnableFlag().equalsIgnoreCase("T")) {
				if (PasswordUtil.verify(password, result.getPassword())) {

					// 后期修改 不需要判断 时候过期问题
					// String roleId = result.getRoleId() + "";
					// if (roleId.equals("5") || roleId.equals("6") || roleId.equals("7")) {
					// if (!usableUtil.getEndDateByAreaId(result.getAreaid())) {
					// return "4";
					// }
					// }
					// if (roleId.equals("3") || roleId.equals("8")) {
					// if (!usableUtil.getEndDateBySchoolId(result.getId())) {
					// return "4";
					// }
					// }
					// if (roleId.equals("4")) {
					// if (!usableUtil.getEndDateByUserId(result.getId())) {
					// return "4";
					// }
					// }
					UserEntity up = new UserEntity();
					up.setUpdateDate(new Date());
					up.setId(result.getId());
					userDao.updateUserEntity(up);
					// 登陆成功
					String tokenId = RanNumUtil.getRandom();
					result.setTokenId(tokenId);
					sessionUtil.setSessionUser(tokenId, result);
					return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
				} else {
					// 用户名或者密码错误
					return "2";
				}
			} else {
				// 用户被禁用
				return "3";
			}
		} else {
			// 用户名或者密码错误
			return "2";
		}

	}

	/**
	 * @Description: TODO 保存用户
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午3:28:01
	 */
	public int saveUser(UserEntity userEntity) {
		userEntity.setCreateDate(new Date());
		userEntity.setEnableFlag("T");
		return userDao.insertUserEntity(userEntity);
	}

	/**
	 * @Description: TODO 查询用户
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午4:30:32
	 */
	public List<UserEntity> selectUserEntity(UserEntity userEntity) {
		return userDao.selectUserEntity(userEntity);
	}

	/**
	 * Title: selectUserEntity Description:
	 * 
	 * @date 2018年2月7日 下午8:30:55
	 * @param userEntity
	 * @return
	 */
	public List<UserEntity> getUsers() {
		return userDao.getUsers();
	}

	/**
	 * Title: updateUser Description: 修改用户信息
	 * 
	 * @date 2018年2月7日 下午6:44:05
	 * @param userId
	 */
	public int updateUser(UserEntity userEntity) {
		return userDao.updateUserInfo(userEntity);
	}

	/**
	 * Title: deleteUser Description: 删除用户
	 * 
	 * @date 2018年2月7日 下午8:24:47
	 * @param userId
	 */
	public int disableUser(Long userId) {
		return userDao.disableUser(userId);
	}

	/**
	 * @Description: TODO 教师注册
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午4:50:08
	 */
	public String teacherRegist(String mobile, String password) {
		// 判断手机号码是否已经注册
		UserEntity registUser = new UserEntity();
		registUser.setMobile(mobile);
		List<UserEntity> userEntities = userDao.selectUserEntity(registUser);
		if (null != userEntities && userEntities.size() <= 0) {
		} else {
			// 手机号码已经存在
			return "2";
		}

		UserEntity userEntity = new UserEntity();
		userEntity.setCreateDate(new Date());
		userEntity.setEnableFlag("T");
		userEntity.setPassword(PasswordUtil.generate(password));
		userEntity.setMobile(mobile);
		// 教师角色id为3
		userEntity.setRoleId(new Integer(3));
		userDao.insertUserEntity(userEntity);
		return userEntity.getId().longValue() + "";
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 上午9:35:07
	 */
	public String studentRegist(String mobile, String password) {
		// 判断手机号码是否已经注册
		UserEntity registUser = new UserEntity();
		registUser.setMobile(mobile);
		List<UserEntity> userEntities = userDao.selectUserEntity(registUser);
		if (null != userEntities && userEntities.size() <= 0) {
		} else {
			// 手机号码已经存在
			return "2";
		}
		// 班级存在
		UserEntity userEntity = new UserEntity();
		userEntity.setCreateDate(new Date());
		userEntity.setEnableFlag("T");
		userEntity.setPassword(PasswordUtil.generate(password));
		userEntity.setMobile(mobile);
		// 学生角色id为4
		userEntity.setRoleId(new Integer(4));
		userDao.insertUserEntity(userEntity);
		// 生成学生编号
		UserEntity userEntity2 = new UserEntity();
		userEntity2.setId(userEntity.getId());
		userEntity2.setNameNum(userEntity.getId().longValue() + StringUtil.getRandomZM(2));
		userDao.updateUserEntity(userEntity2);
		return userEntity.getId().longValue() + "";
	}

	/**
	 * @Description: TODO 学生的分页操作
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 下午2:05:25
	 */
	public void findStudentByPage(UserEntity userEntity, PageBean pageBean) {
		int total = userDao.findStudentByPageTotal(userEntity);
		pageBean.setTotal(total);
		userEntity.setStart(pageBean.getRowFrom());
		userEntity.setEnd(pageBean.getPageSize());
		List<UserEntity> userEntities = userDao.findStudentByPage(userEntity);
		pageBean.setRows(userEntities);
	}

	/**
	 * @Description: TODO 删除班级学生
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 下午2:42:05
	 */
	public void deleteStudent(UserClasEntity userClasEntity) {
		userDao.deleteUserClas(userClasEntity);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 下午2:50:20
	 */
	public void resetStudentPassword(UserEntity userEntity) {
		userEntity.setPassword(PasswordUtil.generate(userEntity.getPassword()));
		userDao.updateUserEntity(userEntity);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 下午4:08:24
	 */
	public int addClasStudentBatchTest(int size, Long clasId, Long userId) {
		UserEntity userEntity1 = new UserEntity();
		userEntity1.setClasId(clasId.longValue() + "");
		List<UserEntity> userEntities = userDao.exportNameNum(userEntity1);
		if (null != userEntities && userEntities.size() > 0) {
			return -1;
		}
		int j = 0;
		for (int i = 0; i < size; i++) {
			try {
				UserEntity userEntity = new UserEntity();
				userEntity.setCreateDate(new Date());
				userEntity.setCreateId(userId.longValue() + "");
				userEntity.setRoleId(new Integer(4));
				userEntity.setEnableFlag("T");
				userEntity.setPassword(PasswordUtil.generate("888888"));
				userEntity.setUserBatch(0);
				userDao.insertUserEntity(userEntity);
				UserClasEntity userClasEntity = new UserClasEntity();
				userClasEntity.setClasId(clasId);
				userClasEntity.setUserId(userEntity.getId());
				userDao.inserUserClas(userClasEntity);
				UserEntity userEntity2 = new UserEntity();
				userEntity2.setId(userEntity.getId());
				String nameNum = userEntity.getId().longValue() + StringUtil.getRandomZM(2);
				logger.info("教师id:[" + userId + "]批量生成学生账号:" + nameNum);
				userEntity2.setNameNum(nameNum);
				userEntity2.setUpdateDate(new Date());
				userDao.updateUserEntity(userEntity2);
				j++;
			} catch (Exception e) {
				logger.error("批量添加用户出现错误.");
				e.printStackTrace();
			}
		}
		return j;
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 下午4:08:24
	 */
	public int addClasStudentBatch(int size, Long clasId, Long userId) {
		UserEntity userEntity1 = new UserEntity();
		userEntity1.setClasId(clasId.longValue() + "");
		List<UserEntity> userEntities = userDao.exportNameNum(userEntity1);
		if (null != userEntities && userEntities.size() > 0) {
			return -1;
		}
		int j = 0;
		for (int i = 0; i < size; i++) {
			try {
				UserEntity userEntity = new UserEntity();
				userEntity.setCreateDate(new Date());
				userEntity.setCreateId(userId.longValue() + "");
				userEntity.setRoleId(new Integer(4));
				userEntity.setEnableFlag("T");
				userEntity.setPassword(PasswordUtil.generate("888888"));
				userEntity.setUserBatch(1);// 是批量生成的账号
				userDao.insertUserEntity(userEntity);
				UserClasEntity userClasEntity = new UserClasEntity();
				userClasEntity.setClasId(clasId);
				userClasEntity.setUserId(userEntity.getId());
				userDao.inserUserClas(userClasEntity);
				UserEntity userEntity2 = new UserEntity();
				userEntity2.setId(userEntity.getId());
				userEntity2.setNameNum(userEntity.getId().longValue() + StringUtil.getRandomZM(2));
				userDao.updateUserEntity(userEntity2);
				j++;
			} catch (Exception e) {
				logger.error("批量添加用户出现错误.");
				e.printStackTrace();
			}
		}
		return j;
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月17日 上午10:01:20
	 */
	public List<UserEntity> exportNameNum(Long clasId) {
		UserEntity userEntity = new UserEntity();
		userEntity.setClasId(clasId.longValue() + "");
		return userDao.exportNameNum(userEntity);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月18日 下午4:39:45
	 */
	public List<UserEntity> getClasUseStudent(UserEntity userEntity) {
		return userDao.getClasUserStudent(userEntity);
	}

	public UserEntity selectById(Long id) {
		return userDao.selectById(id);
	}

	/**
	 * 
	 * Title: perfectUserInfo Description: 更新个人信息
	 * 
	 * @date 2018年2月3日 下午2:16:20
	 * @param userEntity
	 * @return
	 */
	public int updateUserInfo(UserEntity userEntity, byte[] photoData) {
		String filePath = null;
		if (null != photoData && photoData.length > 0) {
			try (InputStream inputStream = new ByteArrayInputStream(photoData)) {
				filePath = fileUtil.uploadFile(inputStream, "teacher_photo", "jpg");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("上传文件失败!");
				filePath = null;
			}
		} else {
			logger.debug("用户头像是空的.....");
		}
		userEntity.setPhoto(filePath);
		return userDao.updateUserSchoolInfo(userEntity);
	}

	public String updateMobile(Long userId, String password, String newMobile, String mobileRandomStr, String randomKey) {
		UserEntity userEntity = userDao.selectById(userId);
		if (PasswordUtil.verify(password, userEntity.getPassword())) {
			UserEntity userEntity2 = new UserEntity();
			userEntity2.setId(userId);
			userEntity2.setMobile(newMobile);
			this.updateUserInfo(userEntity2, null);
			return "1";
		} else {
			return "0";
		}
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月9日 下午4:09:27
	 */
	public void pushMsgByClass(Long classId, Long homeworkId, String content, String type) {
		String studentId = homeworkService.getHomeworkStudent(homeworkId);
		HomeworkEntity homeworkEntity = homeworkService.getHomeworkName(homeworkId);
		ActivemqMsgBean activemqMsgBean = new ActivemqMsgBean();
		activemqMsgBean.setId("1");
		activemqMsgBean.setType("1");
		activemqMsgBean.setUsers(studentId);
		activemqMsgBean.setMsg(homeworkEntity.getName());
		studentMqService.sendMsg(activemqMsgBean);
		logger.info("发送消息" + classId);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月24日 下午2:10:57
	 */
	public RestResult<Map<String, Object>> getStudentInfo(UserEntity userEntity) {
		Map<String, Object> studentInfo = userDao.getStudentInfo(userEntity);
		return ok(studentInfo);
	}

	/**
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月15日 下午2:49:16
	 */
	public String loginTest(String username) {
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(username);
		List<UserEntity> userEntities = userDao.login(userEntity);
		if (null != userEntities && userEntities.size() > 0) {
			UserEntity result = userEntities.get(0);
			UserEntity up = new UserEntity();
			up.setUpdateDate(new Date());
			up.setId(result.getId());
			userDao.updateUserEntity(up);
			// 登陆成功
			String tokenId = RanNumUtil.getRandom();
			result.setTokenId(tokenId);
			sessionUtil.setSessionUser(tokenId, result);
			return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
		}
		return "用户没有找到";
	}

	/**
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月15日 下午3:25:52
	 */
	public void findStudentUsedByPage(UserEntity userEntity, PageBean pageBean) {
		int total = userDao.findStudentUsedByPageTotal(userEntity);
		pageBean.setTotal(total);
		userEntity.setStart(pageBean.getRowFrom());
		userEntity.setEnd(pageBean.getPageSize());
		List<Map<String, Object>> userEntities = userDao.findStudentUsedByPage(userEntity);
		pageBean.setRows(userEntities);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年3月17日 上午11:42:21
	 */
	public void pushMsgByExam(Long examId, String content, String type) {
		String studentId = examService.getExamStudent(examId);
		ExamEntity examEntity = examService.getExamName(examId);
		ActivemqMsgBean activemqMsgBean = new ActivemqMsgBean();
		activemqMsgBean.setId("1");
		activemqMsgBean.setType("2");
		activemqMsgBean.setUsers(studentId);
		activemqMsgBean.setMsg(examEntity.getName());
		studentMqService.sendMsg(activemqMsgBean);
	}

	/**
	 * Title: forgetPwd Description:
	 * 
	 * @date 2018年3月22日 下午3:47:00
	 * @param mobile
	 * @param randomStr
	 * @param password
	 * @param mobileRandomStr
	 * @param randomKey
	 * @param userId
	 * @return
	 */
	public RestResult<String> forgetPwd(String mobile, String password, String mobileRandomStr, String randomKey) {
		if (StringUtil.isEmpty(randomKey)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "随机验证码关键Key不能为空");
		}
		if (StringUtil.isEmpty(mobile)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "手机号不能为空");
		}
		if (StringUtil.isEmpty(password)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "密码不能为空");
		}
		if (StringUtil.isEmpty(mobileRandomStr)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "手机短信验证码不能为空");
		}
		logger.debug("对比手机短信验证码:" + mobileRandomStr + "===" + sessionUtil.getMobileMessageRandomNum(randomKey));
		if (!mobileRandomStr.equalsIgnoreCase(sessionUtil.getMobileMessageRandomNum(randomKey))) {
			return failed(ExceptionCode.MOBILE_MESSAGE_ERROR_CODE, "手机短信验证码错误.");
		}
		sessionUtil.removeMobileMessageRandomNum(randomKey);
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(mobile);
		userEntity.setPassword(PasswordUtil.generate(password));
		int result = userDao.forgetPwd(userEntity);
		if (result > 0) {
			return okNoResult("修改密码成功");
		} else {
			return failed(ExceptionCode.UPDATE_PASSWORD_ERROR, "修改密码失败");
		}
	}

	/**
	 * Title: inserUserClas 
	 * Description:  
	 * @date 2018年4月25日 上午10:26:58
	 * @param userId
	 * @param classId
	 * @return
	 */
	public String inserUserClas(Long userId, String classId) {
		ClasEntity clasEntity = clasService.selectByClassId2(classId);
		if (null == clasEntity) {
			// 该班级不存在
			return "2";
		} else if (StringUtil.isEmpty(clasEntity.getCreateId())) {
			// 班级已经解散
			return "3";
		}
		UserClasEntity userClasEntity = new UserClasEntity();
		userClasEntity.setUserId(userId);
		userClasEntity.setClasId(clasEntity.getId().longValue());
		int inserUserClas = userDao.inserUserClas(userClasEntity);
		if (inserUserClas > 0) {
			return "1";
		} else {
			return "0";
		}
	}

	public List<Map<String, Object>> getClassmate(Long classId) {
		return userDao.getClassmate(classId);
	}
}
