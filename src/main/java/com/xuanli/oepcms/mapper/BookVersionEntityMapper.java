package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.BookVersionEntity;

@Mapper
public interface BookVersionEntityMapper {
    int insert(BookVersionEntity record);

    int insertSelective(BookVersionEntity record);
}