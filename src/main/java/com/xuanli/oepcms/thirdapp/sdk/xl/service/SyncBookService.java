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
import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.entity.SectionEntity;
import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.mapper.BookEntityMapper;
import com.xuanli.oepcms.mapper.SectionDetailMapper;
import com.xuanli.oepcms.mapper.SectionEntityMapper;
import com.xuanli.oepcms.mapper.UnitEntityMapper;
import com.xuanli.oepcms.service.BaseService;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.BookBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SectionBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SectionDetailBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncBookBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncBookDetailBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.UnitBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.SyncUtil;
import com.xuanli.oepcms.util.ThirdAliOSSUtil;

/**
 * @author lijinchao
 * @date 2018年2月26日 下午9:03:05
 */
@Service
@Transactional
public class SyncBookService extends BaseService {
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	BookEntityMapper bookDao;
	@Autowired
	UnitEntityMapper unitEntityDao;
	@Autowired
	SectionEntityMapper sectionEntityDao;
	@Autowired
	SectionDetailMapper sectionDetailDao;
	@Autowired
	ThirdAliOSSUtil thirdAliOSSUtil;

	/**
	 * Title: getBookBean Description:
	 * 
	 * @param sectionDetailBeans
	 * @param sectionBeans
	 * @date 2018年2月26日 下午9:06:57
	 */
	@Transactional(readOnly = false)
	public String syncBooks() {
		String bookjson = SyncUtil.sendPostUTF8(systemConfig.BOOK_URL, null);
		SyncBookBean syncBookBean = JSONObject.parseObject(bookjson, SyncBookBean.class);
		if (null != syncBookBean && syncBookBean.getCode() == 0) {
			List<BookBean> bookBeans = syncBookBean.getResult();
			if (null != bookBeans) {
				for (BookBean bookBean : bookBeans) {
					BookEntity bookEntity = new BookEntity();
					bookEntity.setId(bookBean.getId());
					bookEntity.setName(bookBean.getName());
					bookEntity.setGrade(bookBean.getGrade());
					bookEntity.setBookVersion(bookBean.getBookVersion());
					bookEntity.setBookVolume(bookBean.getBookVolume());
					bookEntity.setCreateId(bookBean.getCreateId());
					bookEntity.setCreateDate(bookBean.getCreateDate());
					bookEntity.setUpdateId(bookBean.getUpdateId());
					bookEntity.setUpdateDate(bookBean.getUpdateDate());
					bookEntity.setEnableFlag(bookBean.getEnableFlag());
					BookEntity resultBookEntity = bookDao.selectById(bookEntity.getId());
					if (null != resultBookEntity) {
						bookDao.updateBookEntity(bookEntity);
					} else {
						bookDao.insertBookEntity(bookEntity);
					}
					String bookDetailJson = SyncUtil.sendPostUTF8(systemConfig.BOOK_CONTENT + "?bookId=" + bookBean.getId().longValue(), null);
					SyncBookDetailBean syncBookDetailBean = JSONObject.parseObject(bookDetailJson, SyncBookDetailBean.class);
					if (null != syncBookDetailBean && syncBookDetailBean.getCode() == 0) {
						// 更新unit内容
						for (UnitBean unitBean : syncBookDetailBean.getResult().getUnits()) {
							UnitEntity unitEntity = new UnitEntity();
							unitEntity.setId(unitBean.getId());
							unitEntity.setName(unitBean.getName());
							unitEntity.setCreateId(unitBean.getCreateId());
							unitEntity.setCreateDate(unitBean.getCreateDate());
							unitEntity.setUpdateId(unitBean.getUpdateId());
							unitEntity.setUpdateDate(unitBean.getCreateDate());
							unitEntity.setEnableFlag(unitBean.getEnableFlag());
							unitEntity.setBookId(unitBean.getBookId());
							UnitEntity syncUnitEntity = unitEntityDao.selectById(unitBean.getId());
							if (null != syncUnitEntity) {
								unitEntityDao.updateUnitEntity(unitEntity);
							} else {
								unitEntityDao.insertUnitEntity(unitEntity);
							}
						}
						for (SectionBean sectionBean : syncBookDetailBean.getResult().getSections()) {
							SectionEntity sectionEntity = new SectionEntity();
							sectionEntity.setId(sectionBean.getId());
							sectionEntity.setName(sectionBean.getName());
							sectionEntity.setUnitId(sectionBean.getUnitId());
							sectionEntity.setCreateId(sectionBean.getCreateId());
							sectionEntity.setCreateDate(sectionBean.getCreateDate());
							sectionEntity.setUpdateId(sectionBean.getUpdateId());
							sectionEntity.setUpdateDate(sectionBean.getUpdateDate());
							sectionEntity.setEnableFlag(sectionBean.getEnableFlag());
							SectionEntity syncSectionEntity = sectionEntityDao.selectById(sectionBean.getId());
							if (null != syncSectionEntity) {
								sectionEntityDao.updateSectionEntity(sectionEntity);
							} else {
								sectionEntityDao.insertSectionEntity(sectionEntity);
							}
						}
						// 更新教材详情
						for (SectionDetailBean sectionDetailBean : syncBookDetailBean.getResult().getSectiondetails()) {
							SectionDetail sectionDetail = new SectionDetail();
							sectionDetail.setId(sectionDetailBean.getId());
							sectionDetail.setName(sectionDetailBean.getName());
							sectionDetail.setPersonName(sectionDetailBean.getPersonName());
							sectionDetail.setSectionId(sectionDetailBean.getSectionId());
							sectionDetail.setType(Integer.parseInt(sectionDetailBean.getType()));
							sectionDetail.setText(sectionDetailBean.getText());
							sectionDetail.setAudioPath(sectionDetailBean.getAudioPath());
							sectionDetail.setCreateId(sectionDetailBean.getCreateId());
							sectionDetail.setCreateDate(sectionDetailBean.getCreateDate());
							sectionDetail.setUpdateId(sectionDetailBean.getUpdateId());
							sectionDetail.setUpdateDate(sectionDetailBean.getUpdateDate());
							sectionDetail.setEnableFlag(sectionDetailBean.getEnableFlag());
							sectionDetail.setmAudioPath(sectionDetailBean.getmAudioPath());
							sectionDetail.setwAudioPath(sectionDetailBean.getwAudioPath());
							sectionDetail.setPicturePath(sectionDetailBean.getPicturePath());
							sectionDetail.setWordType(sectionDetailBean.getWordType());
							sectionDetail.setWordMean(sectionDetailBean.getWordMean());
							sectionDetail.setPointType(sectionDetailBean.getPointType());
							sectionDetail.setOrderNum(sectionDetailBean.getOrderNum());
							sectionDetail.setDialogNum(sectionDetailBean.getDialogNum());
							SectionDetail syncSectionDetail = sectionDetailDao.selectById(sectionDetailBean.getId());
							if (null != syncSectionDetail) {
								if (StringUtil.compareStr(sectionDetailBean.getAudioPath(), syncSectionDetail.getAudioPath())) {
									thirdAliOSSUtil.converterFile(sectionDetailBean.getAudioPath());
								}
								if (StringUtil.compareStr(sectionDetailBean.getmAudioPath(), syncSectionDetail.getmAudioPath())) {
									thirdAliOSSUtil.converterFile(sectionDetailBean.getmAudioPath());
								}
								if (StringUtil.compareStr(sectionDetailBean.getwAudioPath(), syncSectionDetail.getwAudioPath())) {
									thirdAliOSSUtil.converterFile(sectionDetailBean.getwAudioPath());
								}
								if (StringUtil.compareStr(sectionDetailBean.getPicturePath(), syncSectionDetail.getPicturePath())) {
									thirdAliOSSUtil.converterFile(sectionDetailBean.getPicturePath());
								}
								sectionDetailDao.updateSectionDetail(sectionDetail);
							} else {
								thirdAliOSSUtil.converterFile(sectionDetailBean.getAudioPath());
								thirdAliOSSUtil.converterFile(sectionDetailBean.getmAudioPath());
								thirdAliOSSUtil.converterFile(sectionDetailBean.getwAudioPath());
								thirdAliOSSUtil.converterFile(sectionDetailBean.getPicturePath());
								sectionDetailDao.insertSectionDetailEntity(sectionDetail);
							}
						}
					}
				}
			} else {
				// System.out.println("bookBean是空的!");
			}
			return "1";
		} else {
			// 失败
			return "0";
		}
	}
}
