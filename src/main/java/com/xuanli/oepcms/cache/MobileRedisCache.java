/**
 * 
 */
package com.xuanli.oepcms.cache;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author lijinchao
 * @date 2018年5月3日 下午4:29:23
 */
@Service
public class MobileRedisCache {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	private static int TIME_OUT = 7 * 24 * 60;
	public final Logger logger = Logger.getLogger(this.getClass());

	public void put(String key, String value) {
		logger.debug("MobileRedis中添加[" + key + "]---[" + value + "]");
		redisTemplate.opsForValue().set(key, value, TIME_OUT, TimeUnit.MINUTES);
	}

	public void put(String key, String value, Integer timeOut) {
		if (null == timeOut || timeOut.intValue() == 0) {
			timeOut = TIME_OUT;
		}
		logger.debug("MobileRedis中添加[" + key + "]---[" + value + "]");
		redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.MINUTES);
	}

	public String get(String key) {
		logger.debug("MobileRedis中获取[" + key + "]");
		String value = redisTemplate.opsForValue().get(key);
		if (null == value || value.trim().equals("")) {
			return null;
		} else {
			redisTemplate.expire(key, TIME_OUT, TimeUnit.MINUTES);
			return redisTemplate.opsForValue().get(key);
		}
	}

	public String getAndClean(String key) {
		logger.debug("MobileRedis中获取[" + key + "]");
		String value = redisTemplate.opsForValue().get(key);
		if (null == value || value.trim().equals("")) {
			return null;
		} else {
			String v = redisTemplate.opsForValue().get(key);
			String resultValue = "";
			BeanUtils.copyProperties(v, resultValue);
			redisTemplate.delete(key);
			return v;
		}
	}

	public void remove(String key) {
		logger.debug("MobileRedis中删除[" + key + "]");
		redisTemplate.delete(key);
	}
}
