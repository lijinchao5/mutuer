/**
 * 
 */
package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.UserMobileEntity;
import com.xuanli.oepcms.mapper.UserEntityMapper;
import com.xuanli.oepcms.mapper.UserMobileEntityMapper;
import com.xuanli.oepcms.util.PasswordUtil;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.util.StringUtil;

/**
 * @author lijinchao
 * @date 2018年5月3日 下午6:16:22
 */
@Service
@Transactional
public class MobileUserService {
	@Autowired
	private UserEntityMapper userDao;
	@Autowired
	private UserMobileEntityMapper userMobileDao;
	@Autowired
	SessionUtil sessionUtil;

	public String mobileLogin(String username, String password, String appId, String appTokenId) {
		// 如果用户名密码为空，自动登陆时
		if (StringUtil.isEmpty(username) && StringUtil.isEmpty(password)) {
			List<UserMobileEntity> UserMobileEntities = userMobileDao.mobileLogin(appId);
			if (null != UserMobileEntities && UserMobileEntities.size() > 0) {
				UserMobileEntity userMobileEntity = UserMobileEntities.get(0);
				if (null != userMobileEntity.getAppTokenId() && userMobileEntity.getAppTokenId().equals(appTokenId)) {
					UserMobileEntity um = new UserMobileEntity();
					um.setId(userMobileEntity.getId());
					um.setUserId(userMobileEntity.getId());
					um.setAppTokenId(appTokenId);
					userMobileDao.updateUserMobileEntity(um);
					// 返回用户信息

					return "1";
				} else {
					// 登陆超时
					return "2";
				}
			} else {
				// 用户名或密码错误
				return "0";
			}
		} else {
			// 手动登陆
			UserMobileEntity userMobileEntity = new UserMobileEntity();
			List<UserMobileEntity> UserMobileEntities = userMobileDao.mobileLogin(appId);
			if (null != UserMobileEntities && UserMobileEntities.size() > 0) {
				UserMobileEntity result = UserMobileEntities.get(0);
				if (PasswordUtil.verify(password, result.getPassword())) {
					UserMobileEntity ume = new UserMobileEntity();
					ume.setId(result.getId());
					// ume.setAppId(appId);
					ume.setAppTokenId(sessionUtil.getMobileRandomTokenId(appTokenId));
					userMobileDao.updateUserMobileEntity(ume);
					// 返回用户信息

					return "1";
				} else {
					// 用户名或密码错误
					return "0";
				}
			} else {
				// 用户名或密码错误
				return "0";
			}
		}
	}
}
