/**
 * 
 */
package com.xuanli.oepcms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.entity.UserMobileEntity;
import com.xuanli.oepcms.mapper.UserEntityMapper;
import com.xuanli.oepcms.mapper.UserMobileEntityMapper;
import com.xuanli.oepcms.util.PasswordUtil;
import com.xuanli.oepcms.util.RanNumUtil;
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
		// 如果用户名密码为空,appTokenId不为空,自动登陆
		if (StringUtil.isNotEmpty(appTokenId)) {
			UserMobileEntity userMobileEntity = new UserMobileEntity();
			userMobileEntity.setAppId(appId);
			userMobileEntity.setAppTokenId(appTokenId);
			List<UserMobileEntity> UserMobileEntities = userMobileDao.mobileLogin(userMobileEntity);
			if (null != UserMobileEntities && UserMobileEntities.size() > 0) {
				UserMobileEntity umEntity =  sessionUtil.getMobileRandomTokenId(appTokenId);
				if (null == umEntity) {
					//超时
					return "2";
				}else {
					// umEntity.getUserId();
					// umEntity.getAppId();
					//返回用户信息
					Map<String, Object> resultMap = userMobileDao.getUserMessage(umEntity);
					return JSONObject.toJSONString(resultMap, SerializerFeature.WriteMapNullValue);
				}
			} else {
				// 登陆超时
				return "2";
			}
		} else {
			// 手动登陆
			UserEntity userEntity = new UserEntity();
			userEntity.setMobile(username);
			List<UserEntity> userEntities = userDao.login(userEntity);
			if (null != userEntities && userEntities.size() > 0) {
				UserEntity result = userEntities.get(0);
				if (PasswordUtil.verify(password, result.getPassword())) {
					String tokenId = RanNumUtil.getRandom();
					UserMobileEntity ume = new UserMobileEntity();
					ume.setUserId(result.getId());
					ume.setAppId(appId);
					ume.setAppTokenId(tokenId);
					userMobileDao.updateUserMobileEntityByLogin(ume);
					sessionUtil.setMobileRandomTokenId(tokenId,JSONObject.toJSONString(ume));
					// 返回用户信息
					Map<String, Object> resultMap = userMobileDao.getUserMessage(ume);
					return JSONObject.toJSONString(resultMap, SerializerFeature.WriteMapNullValue);
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
