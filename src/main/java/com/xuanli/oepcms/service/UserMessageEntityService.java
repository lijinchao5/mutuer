/**
 * 
 */
package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.UserMessageEntity;
import com.xuanli.oepcms.mapper.UserMessageEntityMapper;

/**
 * @author lijinchao
 * @date 2018年4月9日 下午2:32:07
 */
@Service
public class UserMessageEntityService {
	@Autowired
	UserMessageEntityMapper userMessageEntityMapper;

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月8日 下午8:34:24
	 */
	public void insertUserMessageEntity(UserMessageEntity ume) {
		userMessageEntityMapper.insertSelective(ume);
	}

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月8日 下午9:37:41
	 */
	public void deleteMsgByUser(Long user) {
		userMessageEntityMapper.deleteMsgByUser(user);
	}

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月8日 下午9:46:37
	 */
	public List<UserMessageEntity> getUserMessageByUser(Long userId) {

		return userMessageEntityMapper.getUserMessageByUser(userId);
	}
}
