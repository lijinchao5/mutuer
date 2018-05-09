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
import com.xuanli.oepcms.entity.PaperEntity;
import com.xuanli.oepcms.entity.PaperOptionEntity;
import com.xuanli.oepcms.entity.PaperSubjectDetailEntity;
import com.xuanli.oepcms.entity.PaperSubjectEntity;
import com.xuanli.oepcms.mapper.PaperEntityMapper;
import com.xuanli.oepcms.mapper.PaperOptionEntityMapper;
import com.xuanli.oepcms.mapper.PaperSubjectDetailEntityMapper;
import com.xuanli.oepcms.mapper.PaperSubjectEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.PaperBeans;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.PaperOptionBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.PaperSubjectBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.PaperSubjectDetailBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncPaperBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncPaperDetailBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.SyncUtil;
import com.xuanli.oepcms.util.ThirdAliOSSUtil;

/**
 * @author lijinchao
 * @date 2018年3月5日 下午12:32:05
 */
@Service
@Transactional
public class SyncPaperService {
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	PaperEntityMapper paperDao;
	@Autowired
	PaperSubjectEntityMapper PaperSubjectDao;
	@Autowired
	PaperSubjectDetailEntityMapper PaperSubjectDetailDao;
	@Autowired
	PaperOptionEntityMapper PaperOptionDao;
	@Autowired
	ThirdAliOSSUtil thirdAliOSSUtil;

	@Transactional(readOnly = false)
	public String syncPapers() {
		String paperJson = SyncUtil.sendPostUTF8(systemConfig.PAPER_URL, null);
		SyncPaperBean syncPaperBean = JSONObject.parseObject(paperJson, SyncPaperBean.class);
		if (null != syncPaperBean && syncPaperBean.getCode() == 0) {
			List<PaperBeans> paperBeans = syncPaperBean.getResult();
			if (null != paperBeans && paperBeans.size() > 0) {
				for (PaperBeans paperBean : paperBeans) {
					PaperEntity paperEntity = new PaperEntity();
					paperEntity.setCmsId(paperBean.getId());
					paperEntity.setPaperNum(paperBean.getPaperNum());
					paperEntity.setName(paperBean.getName());
					paperEntity.setNotice(paperBean.getNotice());
					paperEntity.setTotalScore(paperBean.getTotalScore());
					paperEntity.setTotalTime(paperBean.getTotalTime());
					paperEntity.setGradeLevelId(paperBean.getGradeLevelId());
					paperEntity.setAddressProvince(paperBean.getAddressProvince());
					paperEntity.setTerm(paperBean.getTerm());
					paperEntity.setPaperType(paperBean.getPaperType());
					paperEntity.setQuestionType(paperBean.getQuestionType());
					paperEntity.setCreateId(paperBean.getCreateId());
					paperEntity.setCreateDate(paperBean.getCreateDate());
					paperEntity.setUpdateId(paperBean.getUpdateId());
					paperEntity.setUpdateDate(paperBean.getUpdateDate());
					paperEntity.setEnableFlag(paperBean.getEnableFlag());
					paperEntity.setAddressCity(paperBean.getAddressCity());
					paperEntity.setAddressArea(paperBean.getAddressArea());
					paperEntity.setPaperProvince(paperBean.getPaperProvince());
					PaperEntity resultPaperEntity = paperDao.selectByCmsId(paperBean.getId());
					if (null != resultPaperEntity) {
						paperDao.updateSyncPaperEntity(paperEntity);
					} else {
						paperDao.insertPaperEntity(paperEntity);
					}
					String paperDetailJson = SyncUtil.sendPostUTF8(systemConfig.PAPER_CONTENT + "?paperId=" + paperBean.getId().longValue(), null);
					SyncPaperDetailBean syncPaperDetailBean = JSONObject.parseObject(paperDetailJson, SyncPaperDetailBean.class);
					if (null != syncPaperDetailBean && syncPaperDetailBean.getCode() == 0) {
						for (PaperSubjectBean paperSubjectBean : syncPaperDetailBean.getResult().getSubjects()) {
							PaperSubjectEntity paperSubject = new PaperSubjectEntity();
							paperSubject.setCmsId(paperSubjectBean.getId());
							paperSubject.setPaperId(paperEntity.getId());
							paperSubject.setSubjectNum(paperSubjectBean.getSubjectNum());
							paperSubject.setSubject(paperSubjectBean.getSubject());
							paperSubject.setAudio(paperSubjectBean.getAudio());
							paperSubject.setTotalTime(paperSubjectBean.getTotalTime());
							paperSubject.setType(paperSubjectBean.getType());
							paperSubject.setCreateId(paperSubjectBean.getCreateId());
							paperSubject.setCreateDate(paperSubjectBean.getCreateDate());
							paperSubject.setUpdateId(paperSubjectBean.getUpdateId());
							paperSubject.setUpdateDate(paperSubjectBean.getUpdateDate());
							paperSubject.setEnableFlag(paperSubjectBean.getEnableFlag());
							PaperSubjectEntity SyncPaperSubjectEntity = PaperSubjectDao.selectByCmsId(paperSubjectBean.getId());
							if (null != SyncPaperSubjectEntity) {
								if (StringUtil.compareStr(paperSubjectBean.getAudio(), SyncPaperSubjectEntity.getAudio())) {
									thirdAliOSSUtil.converterFile(paperSubjectBean.getAudio());
								}
								PaperSubjectDao.updateSyncPaperSubjectEntity(paperSubject);
							} else {
								thirdAliOSSUtil.converterFile(paperSubjectBean.getAudio());
								PaperSubjectDao.insertPaperSubjectEntity(paperSubject);
							}
							for (PaperSubjectDetailBean subjectDetailBean : syncPaperDetailBean.getResult().getDetails()) {
								if (null != subjectDetailBean.getSubjectId() && null != paperSubjectBean.getId()
										&& subjectDetailBean.getSubjectId().longValue() == paperSubjectBean.getId().longValue()) {
									PaperSubjectDetailEntity subjectDetail = new PaperSubjectDetailEntity();
									subjectDetail.setCmsId(subjectDetailBean.getId());
									subjectDetail.setSubjectId(paperSubject.getId());
									subjectDetail.setType(subjectDetailBean.getType());
									subjectDetail.setGuide(subjectDetailBean.getGuide());
									subjectDetail.setQuestion(subjectDetailBean.getQuestion());
									subjectDetail.setGuideAudio(subjectDetailBean.getGuideAudio());
									subjectDetail.setReadTime(subjectDetailBean.getReadTime());
									subjectDetail.setQuestionAudio(subjectDetailBean.getQuestionAudio());
									subjectDetail.setWriteTime(subjectDetailBean.getWriteTime());
									subjectDetail.setScore(subjectDetailBean.getScore());
									subjectDetail.setOriginalText(subjectDetailBean.getOriginalText());
									subjectDetail.setCreateId(subjectDetailBean.getCreateId());
									subjectDetail.setCreateDate(subjectDetailBean.getCreateDate());
									subjectDetail.setUpdateId(subjectDetailBean.getUpdateId());
									subjectDetail.setUpdateDate(subjectDetailBean.getUpdateDate());
									subjectDetail.setEnableFlag(subjectDetailBean.getEnableFlag());
									subjectDetail.setQuestionNo(subjectDetailBean.getQuestionNo());
									subjectDetail.setRepeatCount(subjectDetailBean.getRepeatCount());
									PaperSubjectDetailEntity paperSubjectDetailEntity = PaperSubjectDetailDao.selectByCmsId(subjectDetailBean.getId());
									if (null != paperSubjectDetailEntity) {
										if (StringUtil.compareStr(subjectDetailBean.getGuideAudio(), paperSubjectDetailEntity.getGuideAudio())) {
											thirdAliOSSUtil.converterFile(subjectDetailBean.getGuideAudio());
										}
										if (StringUtil.compareStr(subjectDetailBean.getQuestionAudio(), paperSubjectDetailEntity.getQuestionAudio())) {
											thirdAliOSSUtil.converterFile(subjectDetailBean.getQuestionAudio());
										}
										if (null != subjectDetailBean.getQuestion()
												&& StringUtil.compareStr(subjectDetailBean.getQuestion(), paperSubjectDetailEntity.getQuestion())
												&& subjectDetailBean.getQuestion().indexOf(".jpg") > 0) {
											thirdAliOSSUtil.converterFile(subjectDetailBean.getQuestion());
										}
										PaperSubjectDetailDao.updateSyncPaperSubjectDetailEntity(subjectDetail);
									} else {
										thirdAliOSSUtil.converterFile(subjectDetailBean.getGuideAudio());
										thirdAliOSSUtil.converterFile(subjectDetailBean.getQuestionAudio());
										if (null != subjectDetailBean.getQuestion() && subjectDetailBean.getQuestion().indexOf(".jpg") > 0) {
											thirdAliOSSUtil.converterFile(subjectDetailBean.getQuestion());
										}
										PaperSubjectDetailDao.insertPaperSubjectDetailEntity(subjectDetail);
									}
									for (PaperOptionBean paperOptionBean : syncPaperDetailBean.getResult().getOptions()) {
										if (subjectDetailBean.getId().longValue() == paperOptionBean.getDetailId().longValue()) {
											PaperOptionEntity paperOption = new PaperOptionEntity();
											paperOption.setCmsId(paperOptionBean.getId());
											paperOption.setDetailId(subjectDetail.getId());
											paperOption.setPointResult(paperOptionBean.getPointResult());
											paperOption.setResult(paperOptionBean.getResult());
											paperOption.setCorrectResult(paperOptionBean.getCorrectResult());
											paperOption.setCreateId(paperOptionBean.getCreateId());
											paperOption.setCreateDate(paperOptionBean.getCreateDate());
											paperOption.setUpdateId(paperOptionBean.getUpdateId());
											paperOption.setUpdateDate(paperOptionBean.getUpdateDate());
											paperOption.setEnableFlag(paperOptionBean.getEnableFlag());
											PaperOptionEntity SyncPaperOption = PaperOptionDao.selectByCmsId(paperOptionBean.getId());
											if (null != SyncPaperOption) {
												PaperOptionDao.updateSyncPaperOptionEntity(paperOption);
											} else {
												PaperOptionDao.insertPaperOptionEntity(paperOption);
											}
										}
									}
								}
							}
						}
					} else {
					}
				}
			} else {
			}
			return "1";
		} else {
			// 失败
			return "0";
		}
	}
}
