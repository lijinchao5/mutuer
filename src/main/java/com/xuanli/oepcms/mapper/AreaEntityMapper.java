package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.AreaEntity;
@Mapper
public interface AreaEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(AreaEntity record);

    int insertSelective(AreaEntity record);

    AreaEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AreaEntity record);

    int updateByPrimaryKey(AreaEntity record);

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月19日 下午4:54:18
	 */
	List<AreaEntity> select1(String string);

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月20日 上午9:48:22
	 */
	List<Map<String, Object>> getRegion(Map<String, Object> map);
}