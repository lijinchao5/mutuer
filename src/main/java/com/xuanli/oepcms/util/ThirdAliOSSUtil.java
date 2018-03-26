/**
 * @fileName:  AliOSSUtil.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月2日 上午9:53:24
 */
package com.xuanli.oepcms.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.xuanli.oepcms.config.AliOSSPool;
import com.xuanli.oepcms.config.ThirdAliOSSPool;

/**
 * @author QiaoYu
 */
@Service
public class ThirdAliOSSUtil {
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	AliOSSPool aliOSSPool;
	@Autowired
	ThirdAliOSSPool thirdAliOSSPool;

	/**
	 * 上传文件
	 * 
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月2日 上午10:01:09
	 */
	public String uploadFile(InputStream is, String path, String suffix) {
		try {
			String uuid = path + "_" + UUID.randomUUID().toString().replace("-", "") + "." + suffix;
			OSSClient ossClient = thirdAliOSSPool.ossClient;
			ossClient.putObject(new PutObjectRequest(thirdAliOSSPool.BUCKET_NAME, uuid, is));
			logger.debug("阿里oss文件服务上传成功" + uuid);
			return uuid;
		} catch (OSSException e) {
			e.printStackTrace();
			return "";
		} catch (ClientException e) {
			e.printStackTrace();
			return "";
		} finally {
			if (null!=is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月2日 上午10:01:14
	 */
	public InputStream downloadFile(String osId) {
		OSSClient ossClient = thirdAliOSSPool.ossClient;
		boolean exists = ossClient.doesObjectExist(thirdAliOSSPool.BUCKET_NAME, osId);
		if (exists) {
			// 存在
			OSSObject object = ossClient.getObject(thirdAliOSSPool.BUCKET_NAME, osId);
			InputStream is = object.getObjectContent();
			try {
				logger.info("文件OSID:[" + osId + "]文件Content-Type:" + object.getObjectMetadata().getContentType() + "文件大小为:" + is.available());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return is;
		} else {
			logger.error("文件OSID:[" + osId + "]文件不存在");
			return null;
		}
	}

	/**
	 * 转换文件
	 * 
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月2日 上午10:01:09
	 */
	public void converterFile(String source) {
		if (StringUtil.isEmpty(source)) {
			logger.error("文件OSID是空的,不需要上传!");
		} else {
			if (thirdAliOSSPool.SAME.equals("1")) {
				logger.error("文件OSID:[" + source + "]在同一个oss服务,不需要重新上传!");
			} else {
				logger.info("文件OSID:[" + source + "]从玄历科技oss同步到第三方的oss");
				OSSClient ossClient = aliOSSPool.ossClient;
				boolean exists = ossClient.doesObjectExist(aliOSSPool.BUCKET_NAME, source);
				InputStream is = null;
				if (exists) {
					try {
						OSSObject object = ossClient.getObject(aliOSSPool.BUCKET_NAME, source);
						is = object.getObjectContent();
						OSSClient thirdClient = thirdAliOSSPool.ossClient;
						thirdClient.putObject(new PutObjectRequest(thirdAliOSSPool.BUCKET_NAME, source, is));
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (null != is) {
							try {
								is.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else {
					logger.error("文件OSID:[" + source + "]文件不存在");
				}
			}
		}

	}

}
