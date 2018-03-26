/**
 * 
 */
package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.OtherLinkEntity;
import com.xuanli.oepcms.mapper.OtherLinkEntityMapper;

/**
 * @author lijinchao
 * @date 2018年3月21日 下午5:17:36
 */
@Service
public class OtherLinkService {
	@Autowired
	OtherLinkEntityMapper otherLinkDao;

	public List<OtherLinkEntity> getOtherLink() {
		return otherLinkDao.getOtherLink();
	}
}
