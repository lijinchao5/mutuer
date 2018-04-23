package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SchoolDataEntity;
import com.xuanli.oepcms.entity.UserSchoolEntity;
@Mapper
public interface SchoolDataEntityMapper {
    int insertSelective(SchoolDataEntity record);

    /**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月20日 上午10:25:06
	 */
	List<Map<String,Object>> getSchoolByRegion(Map<String, Object> map);
	
	
	
	/**
	 * Title: getEndDateBySchoolId 
	 * Description:  
	 * @date 2018年3月26日 下午3:31:04
	 * @param userId
	 * @return
	 */
	List<UserSchoolEntity> getEndDateBySchoolId(Long userId);
	
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月8日 下午3:36:38
	 */
	SchoolDataEntity getUserSchoolInfo(Long id);

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月23日 下午4:22:42
	 */
	SchoolDataEntity selectSchoolId(String schoolId);
}