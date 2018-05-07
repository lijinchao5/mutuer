package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.UserUnitEntity;

public interface UserUnitEntityMapper {
	/**
	 * Title: deleteUserUnitEntity 
	 * Description:  
	 * @date 2018年5月7日 下午12:30:03
	 * @param id
	 * @return
	 */
	int deleteUserUnitEntity(Long id);

	/**
	 * Title: insertUserUnitEntity 
	 * Description:  
	 * @date 2018年5月7日 下午12:30:05
	 * @param record
	 * @return
	 */
	int insertUserUnitEntity(UserUnitEntity record);

	/**
	 * Title: selectById 
	 * Description:  
	 * @date 2018年5月7日 下午12:30:07
	 * @param id
	 * @return
	 */
	UserUnitEntity selectById(Long id);

	/**
	 * Title: updateUserUnitEntity 
	 * Description:  
	 * @date 2018年5月7日 下午12:30:09
	 * @param record
	 * @return
	 */
	int updateUserUnitEntity(UserUnitEntity record);

}