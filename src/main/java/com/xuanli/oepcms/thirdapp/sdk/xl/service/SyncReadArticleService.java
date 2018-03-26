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
import com.xuanli.oepcms.entity.ReadArticleEntity;
import com.xuanli.oepcms.entity.ReadSentenceEntity;
import com.xuanli.oepcms.mapper.ReadArticleEntityMapper;
import com.xuanli.oepcms.mapper.ReadSentenceEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.ReadArticleBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.ReadSentenceBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncReadArticleBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncReadSentenceBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.SyncUtil;
import com.xuanli.oepcms.util.ThirdAliOSSUtil;

/**
 * @author lijinchao
 * @date 2018年3月14日 下午3:41:18
 */
@Service
@Transactional
public class SyncReadArticleService {
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	ReadArticleEntityMapper readArticleDao;
	@Autowired
	ReadSentenceEntityMapper readSentenceDao;
	@Autowired
	ThirdAliOSSUtil thirdAliOSSUtil;

	@Transactional(readOnly = false)
	public String syncReadArticle() {
		String articleJson = SyncUtil.sendPostUTF8(systemConfig.ARTICLE_URL, null);
		SyncReadArticleBean syncReadArticleBean = JSONObject.parseObject(articleJson, SyncReadArticleBean.class);
		if (null != syncReadArticleBean && syncReadArticleBean.getCode() == 0) {
			List<ReadArticleBean> readArticleBeans = syncReadArticleBean.getResult();
			if (null != readArticleBeans && readArticleBeans.size() > 0) {
				for (ReadArticleBean readArticleBean : readArticleBeans) {
					ReadArticleEntity readArticleEntity = new ReadArticleEntity();
					readArticleEntity.setId(readArticleBean.getId());
					readArticleEntity.setName(readArticleBean.getName());
					readArticleEntity.setType(readArticleBean.getType());
					readArticleEntity.setLevel(readArticleBean.getLevel());
					readArticleEntity.setPicturePath(readArticleBean.getPicturePath());
					readArticleEntity.setWordNum(readArticleBean.getWordNum());
					readArticleEntity.setCreateId(readArticleBean.getCreateId());
					readArticleEntity.setCreateDate(readArticleBean.getCreateDate());
					readArticleEntity.setUpdateId(readArticleBean.getUpdateId());
					readArticleEntity.setUpdateDate(readArticleBean.getUpdateDate());
					readArticleEntity.setEnableFlag(readArticleBean.getEnableFlag());
					ReadArticleEntity resultArticleEntity = readArticleDao.selectById(readArticleBean.getId());
					if (null != resultArticleEntity) {
						if (StringUtil.compareStr(readArticleBean.getPicturePath(), resultArticleEntity.getPicturePath())) {
							thirdAliOSSUtil.converterFile(readArticleBean.getPicturePath());
						}
						readArticleDao.updateReadArticleEntity(readArticleEntity);
					} else {
						thirdAliOSSUtil.converterFile(readArticleBean.getPicturePath());
						readArticleDao.insertReadArticleEntity(readArticleEntity);
					}
					String ReadSentenceJson = SyncUtil.sendPostUTF8(systemConfig.SENTENCE_CONTENT_URL + "?readId=" + readArticleBean.getId().longValue(), null);
					SyncReadSentenceBean syncReadSentenceBean = JSONObject.parseObject(ReadSentenceJson, SyncReadSentenceBean.class);
					if (null != syncReadSentenceBean && syncReadSentenceBean.getCode() == 0) {
						List<ReadSentenceBean> readSentenceBeans = syncReadSentenceBean.getResult().getSections();
						for (ReadSentenceBean readSentenceBean : readSentenceBeans) {
							ReadSentenceEntity readSentenceEntity = new ReadSentenceEntity();
							readSentenceEntity.setId(readSentenceBean.getId());
							readSentenceEntity.setArticleId(readSentenceBean.getArticleId());
							readSentenceEntity.setAudioPath(readSentenceBean.getAudioPath());
							readSentenceEntity.setCreateDate(readSentenceBean.getCreateDate());
							readSentenceEntity.setCreateId(readSentenceBean.getCreateId());
							readSentenceEntity.setEnableFlag(readSentenceBean.getEnableFlag());
							readSentenceEntity.setName(readSentenceBean.getName());
							readSentenceEntity.setOrderNum(readSentenceBean.getOrderNum());
							readSentenceEntity.setPicturePath(readSentenceBean.getPicturePath());
							readSentenceEntity.setSentenceCont(readSentenceBean.getSentenceCont());
							readSentenceEntity.setSentenceMean(readSentenceBean.getSentenceMean());
							readSentenceEntity.setUpdateDate(readSentenceBean.getUpdateDate());
							readSentenceEntity.setUpdateId(readSentenceBean.getUpdateId());
							readSentenceEntity.setWordNum(readSentenceBean.getWordNum());
							ReadSentenceEntity resultSentenceEntity = readSentenceDao.selectById(readSentenceBean.getId());
							if (null != resultSentenceEntity) {
								if (StringUtil.compareStr(readSentenceBean.getPicturePath(), resultSentenceEntity.getPicturePath())) {
									thirdAliOSSUtil.converterFile(readSentenceBean.getPicturePath());
								}
								if (StringUtil.compareStr(readSentenceBean.getAudioPath(), resultSentenceEntity.getAudioPath())) {
									thirdAliOSSUtil.converterFile(readSentenceBean.getAudioPath());
								}
								readSentenceDao.updateReadSentenceEntity(readSentenceEntity);
							} else {
								thirdAliOSSUtil.converterFile(readSentenceBean.getPicturePath());
								thirdAliOSSUtil.converterFile(readSentenceBean.getAudioPath());
								readSentenceDao.insertReadSentenceEntity(readSentenceEntity);
							}
						}
					} else {
						System.out.println("syncReadSentenceBean是空的");
					}
				}
			} else {
				System.out.println("readArticleBean是空的");
			}
			return "1";
		} else {
			return "0";
		}
	}
}
