/**
 * @fileName:  RedisCache.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月29日 上午9:30:01
 */
package com.xuanli.oepcms.cache;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author QiaoYu
 */
@Service
public class MyRedisCache {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private static int TIME_OUT = 5 * 60;

	public final Logger logger = Logger.getLogger(this.getClass());

	public void put(String key, String value) {
		logger.debug("redis中添加[" + key + "]---[" + value + "]");
		redisTemplate.opsForValue().set(key, value, TIME_OUT, TimeUnit.MINUTES);
	}
	public void put(String key, String value, Integer timeOut) {
		if (null == timeOut || timeOut.intValue() == 0) {
			timeOut = TIME_OUT;
		}
		logger.debug("redis中添加[" + key + "]---[" + value + "]");
		redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.MINUTES);
	}

	public String get(String key) {
		logger.debug("redis中获取[" + key + "]");
		String value = redisTemplate.opsForValue().get(key);
		if (null == value || value.trim().equals("")) {
			return null;
		} else {
			redisTemplate.expire(key, TIME_OUT, TimeUnit.MINUTES);
			return redisTemplate.opsForValue().get(key);
		}
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月2日 下午4:10:23
	 */
	public void remove(String key) {
		logger.debug("redis中删除[" + key + "]");
		redisTemplate.delete(key);
	}
}
