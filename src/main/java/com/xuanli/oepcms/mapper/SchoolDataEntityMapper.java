package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SchoolDataEntity;
@Mapper
public interface SchoolDataEntityMapper {
    int insertSelective(SchoolDataEntity record);

    /**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月20日 上午10:25:06
	 */
	List<Map<String,Object>> getSchoolByRegion(Map<String, Object> map);
}