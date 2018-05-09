/**
 * @fileName:  YMLUtil.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年5月8日 上午9:52:56
 */
package com.xuanli.oepcms.util;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 * @author QiaoYu[www.codelion.cn]
 */
public class YMLUtil {
	public static Logger logger = Logger.getLogger(YMLUtil.class);
	@SuppressWarnings("rawtypes")
	public static String getSpringEnv() {
		try {
			Yaml yaml = new Yaml();
			URL url = YMLUtil.class.getClassLoader().getResource("application.yml");
			if (url != null) {
				Map map = (Map) yaml.load(new FileInputStream(url.getFile()));
				Map profiles = (Map) map.get("spring");
				Map active = (Map) profiles.get("profiles");
				String env = (String) active.get("active");
				return env;
			}else {
				logger.error("读取环境配置文件url错误");
				return "dev";
			}
		} catch (Exception e) {
			logger.error("读取环境配置文件错误返回dev环境信息");
			return "dev";
		}
	}
}
