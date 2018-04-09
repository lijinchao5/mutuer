/**
 * 
 */
package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.UserMessageEntity;

/**
 * @author lijinchao
 * @date 2018年4月9日 下午2:31:06
 */
@Mapper
public interface UserMessageEntityMapper {
	int deleteByPrimaryKey(Long id);

	int insert(UserMessageEntity record);

	int insertSelective(UserMessageEntity record);

	UserMessageEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UserMessageEntity record);

	int updateByPrimaryKey(UserMessageEntity record);

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月8日 下午9:38:07
	 */
	void deleteMsgByUser(Long user);

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月8日 下午9:46:56
	 */
	List<UserMessageEntity> getUserMessageByUser(Long userId);
}
