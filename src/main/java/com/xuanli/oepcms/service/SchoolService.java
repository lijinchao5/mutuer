/**
 * @fileName:  SchoolService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月15日 下午5:00:36
 */
package com.xuanli.oepcms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.SchoolDataEntity;
import com.xuanli.oepcms.mapper.SchoolDataEntityMapper;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@Service
public class SchoolService extends BaseService{
	@Autowired
	SchoolDataEntityMapper schoolDataEntityMapper;
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月8日 下午3:36:16
	 */
	public SchoolDataEntity getUserSchoolInfo(Long id) {
		return schoolDataEntityMapper.getUserSchoolInfo(id);
	}
	
	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月20日 上午10:24:36
	 */
	public RestResult<List<Map<String, Object>>> getSchoolByRegion(String regionId,String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("level", type);
		map.put("region", regionId);
		return ok(schoolDataEntityMapper.getSchoolByRegion(map));
	}

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月23日 下午4:15:34
	 */
	public SchoolDataEntity getSchoolEntity(String schoolId) {
		return schoolDataEntityMapper.selectSchoolId(schoolId);
	}
}
