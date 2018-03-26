/**
 * @fileName:  SchoolService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月15日 下午5:00:36
 */
package com.xuanli.oepcms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.SchoolEntity;
import com.xuanli.oepcms.mapper.SchoolEntityMapper;

/**
 * @author QiaoYu
 */
@Service
public class SchoolService {
	@Autowired
	SchoolEntityMapper schoolEntityMapper;

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午5:13:46
	 */
	public List<SchoolEntity> selectSchoolEntity(SchoolEntity schoolEntity) {
		return schoolEntityMapper.selectSchoolEntity(schoolEntity);
	}
	
	public SchoolEntity selectSchoolId(String schoolId){
		return schoolEntityMapper.selectSchoolId(schoolId);
	}
	
	public SchoolEntity getSchoolEntity(String schoolId) {
		return schoolEntityMapper.selectSchoolId(schoolId);
	}
	
	public void updateUserSchool(Long userId) {
		SchoolEntity schoolEntity = new SchoolEntity();
		schoolEntity.setCreateId(userId.longValue()+"");
		schoolEntityMapper.deleteUserSchool(schoolEntity);
	}
	
	public int saveUserSchool(String schoolId,Long userId) {
		SchoolEntity schoolEntity = new SchoolEntity();
		schoolEntity.setSchoolId(schoolId);
		schoolEntity.setCreateId(userId.longValue()+"");
		return schoolEntityMapper.saveUserSchool(schoolEntity);
	}

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月8日 下午3:36:16
	 */
	public SchoolEntity getUserSchoolInfo(Long id) {
		return schoolEntityMapper.getUserSchoolInfo(id);
	}
	
	/**Title: updateSchool 
	 * Description:  
	 * @date 2018年2月23日 下午2:10:08
	 * @param schoolEntity
	 * @return  
	 */
	public int updateSchool(SchoolEntity schoolEntity) {
		return schoolEntityMapper.updateSchoolEntity(schoolEntity);
	}
	
	/**
	 * Title: saveSchool 
	 * Description:  
	 * @date 2018年2月23日 下午2:35:59
	 * @param schoolEntity
	 * @return
	 */
	public int saveSchool(SchoolEntity schoolEntity) {
		return schoolEntityMapper.insertSchoolEntity(schoolEntity);
	}
}
