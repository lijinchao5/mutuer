package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.UserUnitEntity;
@Mapper
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

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月7日 下午2:34:45
	 */
	List<Map<String, Object>> getWordList(Map<String, Object> requestMap);

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月7日 下午2:40:21
	 */
	List<Map<String, Object>> getWordDetailList(Map<String, Object> requestMap);

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月7日 下午3:03:13
	 */
	void submitWordUnit(Map<String, Object> requestMap);

}