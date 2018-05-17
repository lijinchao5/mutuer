package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.DicDetailEntity;
import com.xuanli.oepcms.entity.DicEntity;
@Mapper
public interface DicDetailEntityMapper {

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月30日 上午9:48:49
	 */
	public List<DicDetailEntity> findDicByType(DicEntity dicEntity);

	public List<DicDetailEntity> findDicByType9(DicEntity dicEntity);

	/**Title: getBookVersion 
	 * Description:  
	 * @date 2018年4月26日 上午10:35:35
	 * @param grade
	 * @return  
	 */
	public List<DicDetailEntity> getBookVersion(String grade);

	/**
	 * Title: getVolume 
	 * Description:  
	 * @date 2018年5月16日 下午5:58:11
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> getVolume();
	
}