/**
 * 文 件 名:  MapUtil.java 
 * 描    述:  <描述> 
 * 修 改 人:  QiaoShipeng 
 * 修改时间:  2014年7月10日 
 */
package com.xuanli.oepcms.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Multimap;

/**
 * 作者：QiaoShipeng 时间：2014年7月10日 上午11:14:45
 */
public class MapUtil {
	public static final Logger logger = LoggerFactory.getLogger(MapUtil.class);

	@SuppressWarnings("rawtypes")
	public static String MapToString(Map<String, String[]> map, Map<String, String> nextMap) {
		Set set = map.keySet();
		Map<String, String> resultMap = new HashMap<String, String>();
		Iterator iterator = set.iterator();
		String key = "";
		String[] value;
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = map.get(key);
			resultMap.put(key, value[0]);
		}
		for (String nextKey : nextMap.keySet()) {
			resultMap.put(nextKey, nextMap.get(nextKey));
		}
		String resultString = JSON.toJSONString(resultMap);
		logger.info("MapToString结果为:" + resultString);
		return resultString;
	}

	@SuppressWarnings("rawtypes")
	public static String requestParamToJson(Map<String, String[]> map) {
		Set set = map.keySet();
		Map<String, String> resultMap = new HashMap<String, String>();
		Iterator iterator = set.iterator();
		String key = "";
		String[] value;
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = map.get(key);
			resultMap.put(key, value[0]);
		}
		String resultString = JSON.toJSONString(resultMap);
		logger.info("MapToString结果为:" + resultString);
		return resultString;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> requestParamToMap(Map<String, String[]> map) {
		Set set = map.keySet();
		Map<String, String> resultMap = new HashMap<String, String>();
		Iterator iterator = set.iterator();
		String key = "";
		String[] value;
		logger.info("start##########################打印传递的map####################################################");
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = map.get(key);
			logger.info("key=" + key + " ：" + "value=" + value[0]);
			resultMap.put(key, value[0]);
		}
		logger.info("end############################打印传递的map####################################################");
		return resultMap;

	}

	
	
	@SuppressWarnings("rawtypes")
	public static void printRequestMapObjs(HttpServletRequest request) {
		
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		if (null == map || map.size() <= 0) {
		} else {
			Set set = map.keySet();
			Iterator iterator = set.iterator();
			String key = "";
			String value;
			logger.debug("请求头信息@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = map.get(key);
				logger.debug("key=" + key + " ：" + "value=" + value);
			}
		}
		Map<String, String[]> requestMap = request.getParameterMap();
		if (null == requestMap || requestMap.size() <= 0) {
		} else {
			Set set = requestMap.keySet();
			Iterator iterator = set.iterator();
			String key = "";
			String[] value;
			logger.debug("请求参数信息@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = requestMap.get(key);
				logger.debug("key=" + key + " ：" + "value=" + value[0]);
			}
			logger.debug("请求参数信息@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}
	}

	@SuppressWarnings("rawtypes")
	public static void printMapObjs(Map<String, String[]> map) {
		Set set = map.keySet();
		Iterator iterator = set.iterator();
		String key = "";
		String[] value;
		logger.info("start##########################打印传递的map####################################################");
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = map.get(key);
			logger.info("key=" + key + " ：" + "value=" + value[0]);
		}
		logger.info("end############################打印传递的map####################################################");
	}

	@SuppressWarnings("rawtypes")
	public static void printMap(Map map) {
		Set set = map.keySet();
		Iterator iterator = set.iterator();
		String key = "";
		Object value = "";
		logger.info("start##########################打印传递的map####################################################");
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = map.get(key);
			logger.info("key=" + key + " ：" + "value=" + value);
		}
		logger.info("end############################打印传递的map####################################################");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void printMap(Multimap map) {
		Set set = map.keySet();
		Iterator iterator = set.iterator();
		String key = "";
		Object value = "";
		logger.info("start##########################打印传递的map####################################################");
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = map.get(key);
			logger.info("            key=" + key + " ：" + "value=" + value);
		}
		logger.info("end############################打印传递的map####################################################");
	}

	public static Map<String, Object> requestMap(Map<String, String[]> map) {
		if (null != map && map.size() > 0) {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String values[] = map.get(key);
				if (null != values && values.length > 0) {
					returnMap.put(key, values[0]);
				}
			}
			return returnMap;
		} else {
			return new HashMap<String, Object>();
		}
	}

	/**
	 * 将返回的map结合保存到订单记录表的备注中
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String getMap2String(Map map) {
		if (null == map || map.size() <= 0) {
			return null;
		}
		StringBuffer mapString = new StringBuffer("{");
		Set set = map.keySet();
		Iterator iterator = set.iterator();
		String key = "";
		Object value = "";
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = map.get(key);
			mapString.append(key).append("=").append(value);
			mapString.append(",");
		}
		mapString.append("}");
		int markPosition = mapString.lastIndexOf(",");
		mapString.replace(markPosition, markPosition + 1, "");
		return mapString.toString();
	}

//	public static void main(String[] args) {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("1", "p1q");
//		map.put("2", "p2w");
//		map.put("3", "p3e");
//		map.put("4", "p4r");
//		map.put("5", "p5t");
//		map.put("6", "p6y");
//		map.put("7", "p7y");
//		System.out.println(getMap2String(map));
//	}
}
