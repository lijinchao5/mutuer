package com.xuanli.oepcms.other;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.JSGFBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.JSGFMapBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.JSGFWeiBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.YMLUtil;

public class StringTest {
	public static Logger logger = Logger.getLogger(StringTest.class);
	public static void main(String[] args) {
		getJSGF("favorite||like best", "My science teacher.||The science teacher.||My favorite teacher is my science teacher.||My science teacher is my favorite teacher.||I like my science teacher best.");
	}

	public static String getJSGF(String pointResult, String currentResult) {
		if (StringUtil.isEmpty(currentResult)) {
			logger.info("句式为空,不能评分!");
			return null;
		}
		String[] pointResults = pointResult.split("@@");
		int pointSize = pointResults.length;
		Object[] objects = new Object[pointSize];
		for (int i = 0; i < pointSize; i++) {
			String[] ps = pointResults[i].split("\\|\\|");
			int psSize = ps.length;
			Object[] object2s = new Object[psSize];
			for (int j = 0; j < psSize; j++) {
				JSGFMapBean jsgfMapBean = new JSGFMapBean();
				jsgfMapBean.setWeight(0.5);
				jsgfMapBean.setKey(ps[j]);
				object2s[j] = jsgfMapBean;
			}
			objects[i] = object2s;
		}
		JSGFWeiBean jsgfWeiBean = new JSGFWeiBean();
		jsgfWeiBean.setWeight_struct(objects);
		
		currentResult = currentResult.replaceAll("\\|\\|", "|");
		
		JSGFBean jsgfBean = new JSGFBean(JSON.toJSONString(jsgfWeiBean), currentResult);
		logger.info(JSON.toJSONString(jsgfBean));
		return JSON.toJSONString(jsgfBean);
	}

//	public static String getJSGF1(String pointResult, String currentResult) {
//		if (StringUtil.isEmpty(currentResult)) {
//			return null;
//		}
//		String[] pointResults = pointResult.split("@@");
//
//		JSGFWeiBean jsgfWeiBean = new JSGFWeiBean();
//		Object[] objects = new Object[2];
//
//		Object[] object1s = new Object[2];
//		JSGFMapBean jsgfMapBean = new JSGFMapBean();
//		jsgfMapBean.setWeight(0.5);
//		jsgfMapBean.setKey("have");
//		object1s[0] = jsgfMapBean;
//		JSGFMapBean jsgfMapBean1 = new JSGFMapBean();
//		jsgfMapBean1.setWeight(0.5);
//		jsgfMapBean1.setKey("has");
//		object1s[1] = jsgfMapBean1;
//
//		Object[] object2s = new Object[1];
//		JSGFMapBean jsgfMapBean2 = new JSGFMapBean();
//		jsgfMapBean2.setWeight(0.5);
//		jsgfMapBean2.setKey("apple");
//		object2s[0] = jsgfMapBean2;
//
//		objects[0] = object1s;
//		objects[1] = object2s;
//		jsgfWeiBean.setWeight_struct(objects);
//
//		JSGFBean jsgfBean = new JSGFBean(JSON.toJSONString(jsgfWeiBean), "i have a apple|i has two apple");
//
//		return JSON.toJSONString(jsgfBean);
//	}
}
