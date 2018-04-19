package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SchoolDataEntity;
@Mapper
public interface SchoolDataEntityMapper {
    int insertSelective(SchoolDataEntity record);
}