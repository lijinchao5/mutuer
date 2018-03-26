/**
 * @fileName:  DicService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月30日 上午9:43:59
 */ 
package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.DicDetailEntity;
import com.xuanli.oepcms.entity.DicEntity;
import com.xuanli.oepcms.mapper.DicDetailEntityMapper;


/** 
 * @author  QiaoYu 
 */
@Service
public class DicService {
	@Autowired
	DicDetailEntityMapper dicDetailEntityMapper;

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月30日 上午9:46:00
	 */
	public List<DicDetailEntity> findDicByType(String type) {
		DicEntity dicEntity = new DicEntity();
		dicEntity.setType(type);
		
		if (type.endsWith("9")) {
			return dicDetailEntityMapper.findDicByType9(dicEntity);
		}
		
		return dicDetailEntityMapper.findDicByType(dicEntity);
	}
	
}
