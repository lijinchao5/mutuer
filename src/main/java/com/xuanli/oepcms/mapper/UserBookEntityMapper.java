package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.UserBookEntity;

public interface UserBookEntityMapper {
	/**
	 * Title: deleteUserBookEntity 
	 * Description:  
	 * @date 2018年5月3日 下午5:17:29
	 * @param id
	 * @return
	 */
	int deleteUserBookEntity(Long id);

	/**
	 * Title: insertUserBookEntity 
	 * Description:  
	 * @date 2018年5月3日 下午5:17:31
	 * @param record
	 * @return
	 */
	int insertUserBookEntity(UserBookEntity record);

	/**
	 * Title: selectById 
	 * Description:  
	 * @date 2018年5月3日 下午5:17:33
	 * @param id
	 * @return
	 */
	UserBookEntity selectById(Long id);

	/**
	 * Title: updateUserBookEntity 
	 * Description:  
	 * @date 2018年5月3日 下午5:17:36
	 * @param record
	 * @return
	 */
	int updateUserBookEntity(UserBookEntity record);

}