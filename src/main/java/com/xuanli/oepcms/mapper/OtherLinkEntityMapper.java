package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.OtherLinkEntity;

@Mapper
public interface OtherLinkEntityMapper {
	/**
	 * Title: deleteOtherLinkEntity 
	 * Description:  链接删除
	 * @date 2018年3月21日 上午9:59:10
	 * @param id
	 * @return
	 */
	int deleteOtherLinkEntity(Integer id);

	/**
	 * Title: insertOtherLinkEntity 
	 * Description:  链接增加
	 * @date 2018年3月21日 上午9:59:12
	 * @param record
	 * @return
	 */
	int insertOtherLinkEntity(OtherLinkEntity record);

	/**
	 * Title: selectById 
	 * Description:  链接查询
	 * @date 2018年3月21日 上午9:59:15
	 * @param id
	 * @return
	 */
	OtherLinkEntity selectById(Integer id);

	/**
	 * Title: updateOtherLinkEntity 
	 * Description:  链接更新
	 * @date 2018年3月21日 上午9:59:17
	 * @param record
	 * @return
	 */
	int updateOtherLinkEntity(OtherLinkEntity record);

	/**Title: getOtherLink 
	 * Description:  
	 * @date 2018年3月21日 下午5:30:53
	 * @return  
	 */
	List<OtherLinkEntity> getOtherLink();

}