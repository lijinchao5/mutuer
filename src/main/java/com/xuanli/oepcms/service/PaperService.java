/**
 * @fileName:  PaperService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月26日 上午11:48:45
 */
package com.xuanli.oepcms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.controller.bean.PaperBean;
import com.xuanli.oepcms.entity.PaperEntity;
import com.xuanli.oepcms.entity.PaperOptionEntity;
import com.xuanli.oepcms.entity.PaperSubjectDetailEntity;
import com.xuanli.oepcms.entity.PaperSubjectEntity;
import com.xuanli.oepcms.entity.QuestionOptionEntity;
import com.xuanli.oepcms.entity.QuestionSubjectDetailEntity;
import com.xuanli.oepcms.entity.QuestionSubjectEntity;
import com.xuanli.oepcms.mapper.PaperEntityMapper;
import com.xuanli.oepcms.mapper.PaperOptionEntityMapper;
import com.xuanli.oepcms.mapper.PaperSubjectDetailEntityMapper;
import com.xuanli.oepcms.mapper.PaperSubjectEntityMapper;
import com.xuanli.oepcms.mapper.QuestionOptionEntityMapper;
import com.xuanli.oepcms.mapper.QuestionSubjectDetailEntityMapper;
import com.xuanli.oepcms.mapper.QuestionSubjectEntityMapper;
import com.xuanli.oepcms.util.BeanUtil;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@Service
@Transactional
public class PaperService extends BaseService {
	@Autowired
	PaperEntityMapper paperEntityMapper;
	@Autowired
	PaperSubjectDetailEntityMapper paperSubjectDetailEntityMapper;
	@Autowired
	PaperSubjectEntityMapper paperSubjectEntityMapper;
	@Autowired
	PaperOptionEntityMapper paperOptionEntityMapper;
	
	@Autowired
	QuestionSubjectEntityMapper questionSubjectEntityMapper;
	@Autowired
	QuestionSubjectDetailEntityMapper questionSubjectDetailEntityMapper;
	@Autowired
	QuestionOptionEntityMapper questionOptionEntityMapper;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月26日 上午11:59:39
	 */
	public void findPaperByPage(Map<String, Object> requestMap, PageBean pageBean) {
		int total = paperEntityMapper.findPaperByPageTotal(requestMap);
		pageBean.setTotal(total);
		requestMap.put("start", pageBean.getRowFrom());
		requestMap.put("end", pageBean.getPageSize());
		List<Map<String, Object>> resultMap = paperEntityMapper.findPaperByPage(requestMap);
		pageBean.setRows(resultMap);
	}

	/**
	 * @Description: TODO 获取试卷信息信息方法
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月27日 上午9:40:42
	 */
	public RestResult<Map<String, Object>> getPaperDetail(Long paperId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paperDetail", paperEntityMapper.getPaperDetail1(paperId));
		map.put("paperInfo", paperEntityMapper.selectById(paperId));
		return ok(map);
	}

	/**
	 * @Description: TODO
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月5日 上午9:57:08
	 */
	public void findPaperDetailByPage(Map<String, Object> requestMap, PageBean pageBean) {
		int total = paperEntityMapper.findPaperDetailByPageCount(requestMap);
		pageBean.setTotal(total);
		requestMap.put("start", pageBean.getRowFrom());
		requestMap.put("end", pageBean.getPageSize());
		List<Map<String, Object>> resultMap = paperEntityMapper.findPaperDetailByPage(requestMap);
		pageBean.setRows(resultMap);

	}

	/**
	 * @Description: TODO 生成组卷的内容信息
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月5日 上午11:41:45
	 */
	@Transactional
	public RestResult<String> generatorPaper(Long userId, String name, String notice, Integer totalTime, String paperInfo) {
		try {
			PaperEntity paperEntity = new PaperEntity();
			paperEntity.setCreateDate(new Date());
			paperEntity.setCreateId(userId);
			paperEntity.setEnableFlag("T");
			paperEntity.setNotice(notice);
			paperEntity.setName(name);
			paperEntity.setTotalTime(totalTime);
			paperEntityMapper.insertPaperEntity(paperEntity);
			List<PaperBean> paperBeans = JSONArray.parseArray(paperInfo, PaperBean.class);
			int questionNo = 0;
			for (PaperBean paperBean : paperBeans) {
				// 可以保存试卷信息了
				// 获取papersubject信息
				QuestionSubjectEntity resultQSE = questionSubjectEntityMapper.selectById(paperBean.getSubjectId());
				PaperSubjectEntity paperSubjectEntity = new PaperSubjectEntity();
				BeanUtil.copyBean(resultQSE, paperSubjectEntity);
				paperSubjectEntity.setId(null);
				paperSubjectEntity.setCreateDate(new Date());
				paperSubjectEntity.setCreateId(userId);
				paperSubjectEntity.setPaperId(paperEntity.getId());
				// 保存subject信息
				paperSubjectEntityMapper.insertPaperSubjectEntity(paperSubjectEntity);
				questionSubjectEntityMapper.updateQuestionSubjectUsedCount(resultQSE.getId());
				// 获取paperdetailInfo
				List<QuestionSubjectDetailEntity> questionSubjectDetailEntities = questionSubjectDetailEntityMapper.findSubjectDetailBySubjectId(resultQSE.getId().longValue()+"");
				for (QuestionSubjectDetailEntity resultPSDE : questionSubjectDetailEntities) {
					String[] score = paperBean.getScore().split(",");
					PaperSubjectDetailEntity paperSubjectDetailEntity = new PaperSubjectDetailEntity();
					BeanUtil.copyBean(resultPSDE, paperSubjectDetailEntity);
					if (resultQSE.getType().intValue() == 3) {
						// 听后填空 听后回答
						if (resultPSDE.getType().intValue() == 3) {
							Double typeScore1 = Double.parseDouble(score[0]);
							paperSubjectDetailEntity.setScore(typeScore1);
							questionNo = questionNo + 1;
							paperSubjectDetailEntity.setQuestionNo(questionNo);
							questionNo = 0;
						}else if (resultPSDE.getType().intValue() == 4) {
							Double typeScore2 = Double.parseDouble(score[1]);
							paperSubjectDetailEntity.setScore(typeScore2);
							questionNo = questionNo + 1;
							paperSubjectDetailEntity.setQuestionNo(questionNo);
							questionNo = 0;
						}
					} else {
						Double typeScore = Double.parseDouble(score[0]);
						paperSubjectDetailEntity.setScore(typeScore);
						questionNo = questionNo + 1;
						paperSubjectDetailEntity.setQuestionNo(questionNo);
					}
					paperSubjectDetailEntity.setId(null);
					paperSubjectDetailEntity.setSubjectId(paperSubjectEntity.getId());
					paperSubjectDetailEntity.setEnableFlag("T");
					paperSubjectDetailEntity.setCreateDate(new Date());
					paperSubjectDetailEntity.setCreateId(userId);
					paperSubjectDetailEntityMapper.insertPaperSubjectDetailEntity(paperSubjectDetailEntity);
					List<QuestionOptionEntity> questionOptionEntities = questionOptionEntityMapper.getSubjectOptionByDetailId(resultPSDE.getId());
					for (QuestionOptionEntity resultPOE : questionOptionEntities) {
						PaperOptionEntity paperOptionEntity = new PaperOptionEntity();
						BeanUtil.copyBean(resultPOE, paperOptionEntity);
						paperOptionEntity.setId(null);
						paperOptionEntity.setCreateDate(new Date());
						paperOptionEntity.setCreateId(userId);
						paperOptionEntity.setDetailId(paperSubjectDetailEntity.getId());
						paperOptionEntity.setEnableFlag("T");
						paperOptionEntity.setCreateDate(new Date());
						paperOptionEntity.setCreateId(userId);
						paperOptionEntityMapper.insertPaperOptionEntity(paperOptionEntity);
					}
				}
				questionNo = 0;
			}
			return okNoResult("成功");
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, "出现错误===:"+e.getMessage());
		}
	}

}
