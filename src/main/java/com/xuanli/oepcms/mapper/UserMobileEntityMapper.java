package com.xuanli.oepcms.mapper;

import java.util.List;

import com.xuanli.oepcms.entity.UserMobileEntity;

public interface UserMobileEntityMapper {
	/**
	 * Title: deleteUserMobileEntity 
	 * Description:  
	 * @date 2018年5月3日 下午5:16:55
	 * @param id
	 * @return
	 */
	int deleteUserMobileEntity(Long id);

	/**
	 * Title: insertUserMobileEntity 
	 * Description:  
	 * @date 2018年5月3日 下午5:16:57
	 * @param record
	 * @return
	 */
	int insertUserMobileEntity(UserMobileEntity record);

	/**
	 * Title: selectById 
	 * Description:  
	 * @date 2018年5月3日 下午5:16:59
	 * @param id
	 * @return
	 */
	UserMobileEntity selectById(Long id);

	/**
	 * Title: updateUserMobileEntity 
	 * Description:  
	 * @date 2018年5月3日 下午5:17:02
	 * @param record
	 * @return
	 */
	int updateUserMobileEntity(UserMobileEntity record);

	/**
	 * Title: getUserMobileEntityByAppId 
	 * Description:  
	 * @date 2018年5月4日 上午9:49:16
	 * @param appId
	 * @return
	 */
	List<UserMobileEntity> mobileLogin(String appId);
}