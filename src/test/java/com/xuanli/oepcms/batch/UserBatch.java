/**
 * @fileName:  UserBatch.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月11日 上午10:05:44
 */
package com.xuanli.oepcms.batch;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.BaseTest;
import com.xuanli.oepcms.entity.ClasEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.ClasService;
import com.xuanli.oepcms.service.UserService;

/**
 * @author QiaoYu[www.codelion.cn]
 */
public class UserBatch extends BaseTest {
	public static Logger logger = Logger.getLogger(UserBatch.class);
	@Autowired
	UserService userService;
	@Autowired
	ClasService clasService;
	
	
	//批量生成教师和学生
	// @Test
	public void addUserBatch() {
		int teacherNum = 10;
		int picStudentNum = 3;
		for (int i = 0; i < teacherNum; i++) {
			String mobile = "131333333" + (i < 10 ? "0" + i : "" + i);
			logger.info("教师账号为:"+mobile);
			String teacherRegist = userService.teacherRegist(mobile, "888888");
			if (teacherRegist.equals("2")) {
				logger.info("生成老师账号失败!");
				break;
			}
			String a = userService.loginTest(mobile);
			UserEntity userEntity = JSONObject.parseObject(a,UserEntity.class);
			ClasEntity clasEntity = new ClasEntity();
			clasEntity.setGrade("1");
			clasEntity.setName("测试班级");
			clasService.saveClas(clasEntity, userEntity.getId());
			Long clasId = clasEntity.getId();
			userService.addClasStudentBatchTest(picStudentNum, clasId, userEntity.getId());
		}

	}
}
