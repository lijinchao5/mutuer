package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.UnitBookEntity;

public interface UnitBookEntityMapper {
	/**
	 * Title: deleteUnitBookEntity 
	 * Description:  
	 * @date 2018年5月3日 下午5:17:58
	 * @param id
	 * @return
	 */
	int deleteUnitBookEntity(Long id);

	/**
	 * Title: insertUnitBookEntity 
	 * Description:  
	 * @date 2018年5月3日 下午5:18:00
	 * @param record
	 * @return
	 */
	int insertUnitBookEntity(UnitBookEntity record);

	/**
	 * Title: selectById 
	 * Description:  
	 * @date 2018年5月3日 下午5:18:02
	 * @param id
	 * @return
	 */
	UnitBookEntity selectById(Long id);

	/**
	 * Title: updateUnitBookEntity 
	 * Description:  
	 * @date 2018年5月3日 下午5:18:04
	 * @param record
	 * @return
	 */
	int updateUnitBookEntity(UnitBookEntity record);

}