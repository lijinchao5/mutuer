/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.config.SystemConfig;
import com.xuanli.oepcms.entity.QuestionOptionEntity;
import com.xuanli.oepcms.entity.QuestionSubjectDetailEntity;
import com.xuanli.oepcms.entity.QuestionSubjectEntity;
import com.xuanli.oepcms.mapper.QuestionOptionEntityMapper;
import com.xuanli.oepcms.mapper.QuestionSubjectDetailEntityMapper;
import com.xuanli.oepcms.mapper.QuestionSubjectEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.QuestionOptionBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.QuestionSubjectBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.QuestionSubjectDetailBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncQuestionSubjectBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncQuestionSubjectDetailBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.SyncUtil;
import com.xuanli.oepcms.util.ThirdAliOSSUtil;

/**
 * @author lijinchao
 * @date 2018年3月15日 上午11:42:58
 */
@Service
@Transactional
public class SyncQuestionService {
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	QuestionSubjectEntityMapper QuestionSubjectDao;
	@Autowired
	QuestionSubjectDetailEntityMapper QuestionSubjectDetailDao;
	@Autowired
	QuestionOptionEntityMapper QuestionOptionDao;
	@Autowired
	ThirdAliOSSUtil thirdAliOSSUtil;

	public String SyncQusetions() {
		String questionJson = SyncUtil.sendPostUTF8(systemConfig.QUESTION_URL, null);
		SyncQuestionSubjectBean syncQuestionSubjectBean = JSONObject.parseObject(questionJson, SyncQuestionSubjectBean.class);
		if (null != syncQuestionSubjectBean && syncQuestionSubjectBean.getCode() == 0) {
			for (QuestionSubjectBean questionSubjectBean : syncQuestionSubjectBean.getResult()) {
				QuestionSubjectEntity questionSubjectEntity = new QuestionSubjectEntity();
				questionSubjectEntity.setAudio(questionSubjectBean.getAudio());
				questionSubjectEntity.setCmsId(questionSubjectBean.getId());
				questionSubjectEntity.setCreateDate(questionSubjectBean.getCreateDate());
				questionSubjectEntity.setCreateId((long) 0);
				questionSubjectEntity.setEnableFlag(questionSubjectBean.getEnableFlag());
				questionSubjectEntity.setGradeLevelId(questionSubjectBean.getGradeLevelId());
				questionSubjectEntity.setQuestionScore(questionSubjectBean.getQuestionScore());
				questionSubjectEntity.setSubject(questionSubjectBean.getSubject());
				questionSubjectEntity.setSubjectNum(questionSubjectBean.getSubjectNum());
				questionSubjectEntity.setTerm(questionSubjectBean.getTerm());
				questionSubjectEntity.setTotalScore(questionSubjectBean.getTotalScore());
				questionSubjectEntity.setTotalTime(questionSubjectBean.getTotalTime());
				questionSubjectEntity.setType(questionSubjectBean.getType());
				questionSubjectEntity.setUpdateDate(questionSubjectBean.getUpdateDate());
				questionSubjectEntity.setUpdateId(questionSubjectBean.getUpdateId());
				QuestionSubjectEntity subjectEntity = QuestionSubjectDao.selectByCmsId(questionSubjectBean.getId());
				if (null != subjectEntity) {
					if (StringUtil.compareStr(questionSubjectBean.getAudio(), subjectEntity.getAudio())) {
						thirdAliOSSUtil.converterFile(questionSubjectBean.getAudio());
					}
					QuestionSubjectDao.updateSyncQuestionSubjectEntity(questionSubjectEntity);
				} else {
					thirdAliOSSUtil.converterFile(questionSubjectBean.getAudio());
					QuestionSubjectDao.insertQuestionSubjectEntity(questionSubjectEntity);
				}
				String questionDetilsJson = SyncUtil.sendPostUTF8(systemConfig.QUESTION_CONTENT_URL + "?subjectId=" + questionSubjectBean.getId().longValue(), null);
				SyncQuestionSubjectDetailBean syncQuestionSubjectDetailBean = JSONObject.parseObject(questionDetilsJson, SyncQuestionSubjectDetailBean.class);
				for (QuestionSubjectDetailBean questionSubjectDetailBean : syncQuestionSubjectDetailBean.getResult().getDetails()) {
					if (null != questionSubjectDetailBean.getSubjectId() && null != questionSubjectBean.getId()
							&& questionSubjectDetailBean.getSubjectId().longValue() == questionSubjectBean.getId().longValue()) {
						QuestionSubjectDetailEntity questionSubjectDetailEntity = new QuestionSubjectDetailEntity();
						questionSubjectDetailEntity.setCmsId(questionSubjectDetailBean.getId());
						questionSubjectDetailEntity.setCreateDate(questionSubjectDetailBean.getCreateDate());
						questionSubjectDetailEntity.setCreateId((long) 0);
						questionSubjectDetailEntity.setEnableFlag(questionSubjectDetailBean.getEnableFlag());
						questionSubjectDetailEntity.setGuide(questionSubjectDetailBean.getGuide());
						questionSubjectDetailEntity.setGuideAudio(questionSubjectDetailBean.getGuideAudio());
						questionSubjectDetailEntity.setOriginalText(questionSubjectDetailBean.getOriginalText());
						questionSubjectDetailEntity.setQuestion(questionSubjectDetailBean.getQuestion());
						questionSubjectDetailEntity.setQuestionAudio(questionSubjectDetailBean.getQuestionAudio());
						questionSubjectDetailEntity.setQuestionNo(questionSubjectDetailBean.getQuestionNo());
						questionSubjectDetailEntity.setReadTime(questionSubjectDetailBean.getReadTime());
						questionSubjectDetailEntity.setRepeatCount(questionSubjectDetailBean.getRepeatCount());
						questionSubjectDetailEntity.setScore(questionSubjectDetailBean.getScore());
						questionSubjectDetailEntity.setSubjectId(questionSubjectEntity.getId());
						questionSubjectDetailEntity.setType(questionSubjectDetailBean.getType());
						questionSubjectDetailEntity.setUpdateDate(questionSubjectDetailBean.getUpdateDate());
						questionSubjectDetailEntity.setUpdateId(questionSubjectDetailBean.getUpdateId());
						questionSubjectDetailEntity.setWriteTime(questionSubjectDetailBean.getWriteTime());
						QuestionSubjectDetailEntity subjectDetailEntity = QuestionSubjectDetailDao.selectByCmsId(questionSubjectDetailBean.getId());
						if (null != subjectDetailEntity) {
							if (StringUtil.compareStr(questionSubjectDetailBean.getGuideAudio(), subjectDetailEntity.getGuideAudio())) {
								thirdAliOSSUtil.converterFile(questionSubjectDetailBean.getGuideAudio());
							}
							if (StringUtil.compareStr(questionSubjectDetailBean.getQuestionAudio(), subjectDetailEntity.getQuestionAudio())) {
								thirdAliOSSUtil.converterFile(questionSubjectDetailBean.getQuestionAudio());
							}
							if (StringUtil.compareStr(questionSubjectDetailBean.getQuestion(), subjectDetailEntity.getQuestion())
									&& questionSubjectDetailBean.getQuestion().indexOf(".jpg") > 0) {
								thirdAliOSSUtil.converterFile(questionSubjectDetailBean.getQuestion());
							}
							QuestionSubjectDetailDao.updateSyncQuestionSubjectDetailEntity(questionSubjectDetailEntity);
						} else {
							thirdAliOSSUtil.converterFile(questionSubjectDetailBean.getGuideAudio());
							thirdAliOSSUtil.converterFile(questionSubjectDetailBean.getQuestionAudio());
							if (null != questionSubjectDetailBean.getQuestion() && questionSubjectDetailBean.getQuestion().indexOf(".jpg") > 0) {
								thirdAliOSSUtil.converterFile(questionSubjectDetailBean.getQuestion());
							}
							QuestionSubjectDetailDao.insertQuestionSubjectDetailEntity(questionSubjectDetailEntity);
						}
						for (QuestionOptionBean questionOptionBean : syncQuestionSubjectDetailBean.getResult().getOptions()) {
							if (null != questionOptionBean.getDetailId() && null != questionSubjectDetailBean.getId()
									&& questionOptionBean.getDetailId().longValue() == questionSubjectDetailBean.getId().longValue()) {
								QuestionOptionEntity questionOptionEntity = new QuestionOptionEntity();
								questionOptionEntity.setCmsId(questionOptionBean.getId());
								questionOptionEntity.setCorrectResult(questionOptionBean.getCorrectResult());
								questionOptionEntity.setCreateDate(questionOptionBean.getCreateDate());
								questionOptionEntity.setCreateId((long) 0);
								questionOptionEntity.setDetailId(questionSubjectDetailEntity.getId());
								System.out.println(questionSubjectDetailEntity.getId());
								questionOptionEntity.setEnableFlag(questionOptionBean.getEnableFlag());
								questionOptionEntity.setPointResult(questionOptionBean.getPointResult());
								questionOptionEntity.setResult(questionOptionBean.getResult());
								questionOptionEntity.setUpdateDate(questionOptionBean.getUpdateDate());
								questionOptionEntity.setUpdateId(questionOptionBean.getUpdateId());
								QuestionOptionEntity optionEntity = QuestionOptionDao.selectByCmsId(questionOptionBean.getId());
								if (null != optionEntity) {
									QuestionOptionDao.updateSyncQuestionOptionEntity(questionOptionEntity);
								} else {
									QuestionOptionDao.insertQuestionOptionEntity(questionOptionEntity);
								}
							}
						}
					}
				}
			}
			return "1";
		} else {
			System.out.println("syncQuestionSubjectBean是空的");
			return "0";
		}
	}
}
