/**
 * @fileName:  BeanUtil.java 
 * @Description:  TODO
 * @CreateName:  codelion[QiaoYu]
 * @CreateDate:  2018年3月5日 上午11:51:27
 */
package com.xuanli.oepcms.util;

import org.springframework.beans.BeanUtils;

/**
 * @author codelion[QiaoYu]
 */
public class BeanUtil {
	// 复制bean内容
	public static void copyBean(Object src, Object target) {
		BeanUtils.copyProperties(src, target);
	}
}
