/**
 * @fileName:  AreaService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月20日 上午9:46:24
 */ 
package com.xuanli.oepcms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.mapper.AreaEntityMapper;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu[www.codelion.cn]
 */
@Service
public class AreaService extends BaseService{
	@Autowired
	AreaEntityMapper areaEntityMapper;

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月20日 上午9:47:07
	 */
	public RestResult<List<Map<String, Object>>> getRegion(String level, String regionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("level", level);
		map.put("regionId", regionId);
		return ok(areaEntityMapper.getRegion(map));
	}

	/**Title: getProvince 
	 * Description:  
	 * @date 2018年5月7日 下午6:15:22
	 * @return  
	 */
	public RestResult<List<Map<String, Object>>> getProvince() {

		return ok(areaEntityMapper.getProvince());
	}
	
	
}
