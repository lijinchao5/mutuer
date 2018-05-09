/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.config.SystemConfig;
import com.xuanli.oepcms.entity.OtherLinkEntity;
import com.xuanli.oepcms.mapper.OtherLinkEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.OtherLinkBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncOtherLinkBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.SyncUtil;
import com.xuanli.oepcms.util.ThirdAliOSSUtil;

/**
 * @author lijinchao
 * @date 2018年3月21日 上午9:55:34
 */
@Service
@Transactional
public class SyncOtherLinkService {
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	OtherLinkEntityMapper otherLinkDao;
	@Autowired
	ThirdAliOSSUtil thirdAliOSSUtil;
	@Transactional(readOnly = false)
	public String SyncOtherLink() {
		String otherLinkJson = SyncUtil.sendPostUTF8(systemConfig.OTHER_LINK_URL, null);
		SyncOtherLinkBean syncOtherLinkBean = JSONObject.parseObject(otherLinkJson, SyncOtherLinkBean.class);
		if (null != syncOtherLinkBean && syncOtherLinkBean.getCode() == 0) {
			List<OtherLinkBean> otherLinkBeans = syncOtherLinkBean.getResult();
			if (null != otherLinkBeans) {
				for (OtherLinkBean otherLinkBean : otherLinkBeans) {
					OtherLinkEntity otherLinkEntity = new OtherLinkEntity();
					otherLinkEntity.setId(otherLinkBean.getId());
					otherLinkEntity.setName(otherLinkBean.getName());
					otherLinkEntity.setDesp1(otherLinkBean.getDesp1());
					otherLinkEntity.setDesp2(otherLinkBean.getDesp2());
					otherLinkEntity.setDesp3(otherLinkBean.getDesp3());
					otherLinkEntity.setPic1(otherLinkBean.getPic1());
					otherLinkEntity.setPic2(otherLinkBean.getPic2());
					otherLinkEntity.setPic3(otherLinkBean.getPic3());
					otherLinkEntity.setLink1(otherLinkBean.getLink1());
					otherLinkEntity.setLink2(otherLinkBean.getLink2());
					otherLinkEntity.setLink3(otherLinkBean.getLink3());
					otherLinkEntity.setType(otherLinkBean.getType());
					otherLinkEntity.setOrderby(otherLinkBean.getOrderby());
					otherLinkEntity.setCreateId(otherLinkBean.getCreateId());
					otherLinkEntity.setCreateDate(otherLinkBean.getCreateDate());
					otherLinkEntity.setUpdateId(otherLinkBean.getUpdateId());
					otherLinkEntity.setUpdateDate(otherLinkBean.getUpdateDate());
					otherLinkEntity.setEnableFlag(otherLinkBean.getEnableFlag());
					OtherLinkEntity syncOtherLinkEntity = otherLinkDao.selectById(otherLinkBean.getId());
					if (null != syncOtherLinkEntity) {
						if (StringUtil.compareStr(otherLinkBean.getPic1(), syncOtherLinkEntity.getPic1())) {
							thirdAliOSSUtil.converterFile(otherLinkBean.getPic1());
						}
						if (StringUtil.compareStr(otherLinkBean.getPic2(), syncOtherLinkEntity.getPic2())) {
							thirdAliOSSUtil.converterFile(otherLinkBean.getPic2());
						}
						if (StringUtil.compareStr(otherLinkBean.getPic3(), syncOtherLinkEntity.getPic3())) {
							thirdAliOSSUtil.converterFile(otherLinkBean.getPic3());
						}

						otherLinkDao.updateOtherLinkEntity(syncOtherLinkEntity);
					} else {
						thirdAliOSSUtil.converterFile(otherLinkBean.getPic1());
						thirdAliOSSUtil.converterFile(otherLinkBean.getPic2());
						thirdAliOSSUtil.converterFile(otherLinkBean.getPic3());
						otherLinkDao.insertOtherLinkEntity(otherLinkEntity);
					}
				}
			} else {
			}
			return "1";
		} else {
			// 链接获取内容失败
			return "0";
		}
	}
}
