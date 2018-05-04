/**
 * @fileName:  SessionUtil.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月15日 上午9:48:16
 */
package com.xuanli.oepcms.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.cache.MobileRedisCache;
import com.xuanli.oepcms.cache.MyRedisCache;
import com.xuanli.oepcms.contents.SystemContents;
import com.xuanli.oepcms.entity.UserEntity;

/**
 * @author QiaoYu
 */
@Service
public class SessionUtil {
	@Autowired
	private MyRedisCache myRedisCache;
	@Autowired
	private MobileRedisCache mobileRedisCache;

	public Logger logger = Logger.getLogger(SessionUtil.class);

	public UserEntity getSessionUser(String key) {
		String user = myRedisCache.get(key);
		UserEntity userEntity = JSONObject.parseObject(user, UserEntity.class);
		return userEntity;
	}

	public void setSessionUser(String key, UserEntity userEntity) {
		myRedisCache.put(key, JSONObject.toJSONString(userEntity));
	}

	public void removeSessionUser(String key) {
		myRedisCache.remove(key);
	}

	public String getRandomNum(String key) {
		String value = myRedisCache.get(key + "_" + SystemContents.RANDOM_NUM);
		return value;
	}

	public void setRandomNum(String key, String num) {
		myRedisCache.put(key + "_" + SystemContents.RANDOM_NUM, num, 5);
	}

	public String getMobileRandomNum(String key) {
		String value = myRedisCache.get(key + "_" + SystemContents.MOBILE_RANDOM_NUM);
		return value;
	}

	public void setMobileRandomNum(String key, String num) {
		myRedisCache.put(key + "_" + SystemContents.MOBILE_RANDOM_NUM, num, 5);
	}

	public String getMobileMessageRandomNum(String key) {
		String value = myRedisCache.get(key + "_" + SystemContents.MOBILE_MESSAGE_RANDOM_NUM);
		logger.debug("获取出来的value为:" + value);
		return value;
	}

	public void setMobileMessageRandomNum(String key, String num) {
		myRedisCache.put(key + "_" + SystemContents.MOBILE_MESSAGE_RANDOM_NUM, num, 5);
	}

	public void removeMobileRandomNum(String key) {
		myRedisCache.remove(key + "_" + SystemContents.MOBILE_RANDOM_NUM);
	}

	public void removeMobileMessageRandomNum(String key) {
		myRedisCache.remove(key + "_" + SystemContents.MOBILE_MESSAGE_RANDOM_NUM);
	}

	public String getMobileRandomTokenId(String key) {
		String value = mobileRedisCache.get(key + "_" + SystemContents.MOBILE_RANDOM_TOKEN_ID);
		return value;
	}

	public void setMobileRandomTokenId(String key, String num) {
		mobileRedisCache.put(key + "_" + SystemContents.MOBILE_RANDOM_TOKEN_ID, num, 5);
	}

	public void removeMobileRandomTokenId(String key) {
		mobileRedisCache.remove(key + "_" + SystemContents.MOBILE_RANDOM_TOKEN_ID);
	}
}
