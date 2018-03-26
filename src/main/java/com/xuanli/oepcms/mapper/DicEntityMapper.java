package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.DicEntity;
@Mapper
public interface DicEntityMapper {
    int deleteById(Integer id);

    int insertDicEntity(DicEntity record);

    DicEntity selectById(Integer id);

    int updateById(DicEntity record);

}