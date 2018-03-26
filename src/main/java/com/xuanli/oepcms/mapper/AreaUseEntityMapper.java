package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.AreaUseEntity;
@Mapper
public interface AreaUseEntityMapper {
	/**
	 * Title: deleteAreaUseEntity 
	 * Description:  删除方法
	 * @date 2018年2月23日 下午5:16:47
	 * @param id
	 * @return
	 */
    int deleteAreaUseEntity(String id);
    /**
     * Title: insertAreaUseEntity 
     * Description:  新增方法
     * @date 2018年2月23日 下午5:16:57
     * @param record
     * @return
     */
    int insertAreaUseEntity(AreaUseEntity record);
    /**
     * Title: selectById 
     * Description:  查询方法
     * @date 2018年2月23日 下午5:17:05
     * @param id
     * @return
     */
    AreaUseEntity selectById(String id);
    /**
     * Title: updateAreaUseEntity 
     * Description:  更新方法
     * @date 2018年2月23日 下午5:17:11
     * @param record
     * @return
     */
    int updateAreaUseEntity(AreaUseEntity record);

	AreaUseEntity getEndDateByAreaId(String areaId);
}