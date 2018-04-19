package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.AreaEntity;

public interface AreaEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(AreaEntity record);

    int insertSelective(AreaEntity record);

    AreaEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AreaEntity record);

    int updateByPrimaryKey(AreaEntity record);
}