/**
 * @fileName:  TestHttp.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年3月30日 上午11:12:07
 */
package com.xuanli.oepcms.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * @author QiaoYu[www.codelion.cn]
 */
public class TestHttp {
	public static Logger logger = Logger.getLogger(TestHttp.class);
	public static void main(String[] args) {
		try {
			URL url = new URL("http://www.aienglish.vip/oep-be/homework/getStudentHomeWorkList.do?rows=6&page=1");
			// 打开连接
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// 设置请求头
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("X-AUTH-TOKEN", "a29b3bf7867445b0b3a91832bd4771df");
			// 开启连接
			con.connect();
			// 将返回的数据转成StringBuffer对象
			StringBuffer resultBuffer = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
			logger.info(resultBuffer.toString());
			con.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
