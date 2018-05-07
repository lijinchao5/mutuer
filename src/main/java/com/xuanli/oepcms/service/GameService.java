/**
 * @fileName:  GameService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年5月7日 下午2:31:05
 */ 
package com.xuanli.oepcms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.mapper.SectionDetailMapper;
import com.xuanli.oepcms.mapper.UserUnitEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.YunZhiSDK;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiSubWords;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiWords;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiline;

/** 
 * @author  QiaoYu[www.codelion.cn]
 */
@Service
public class GameService extends BaseService{
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	UserUnitEntityMapper userUnitEntityMapper;
	@Autowired
	YunZhiSDK yunZhiSDK;
	@Autowired
	SectionDetailMapper sectionDetailMapper;
	
	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月7日 下午2:32:39
	 */
	public List<Map<String, Object>> getWordList(Long userId, Long bookId) {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("userId", userId);
		requestMap.put("bookId", bookId);
		return userUnitEntityMapper.getWordList(requestMap);
	}

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月7日 下午2:40:04
	 */
	public List<Map<String, Object>> getWordDetailList(Long sectionDetailId) {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("sectionDetailId", sectionDetailId);
		return userUnitEntityMapper.getWordDetailList(requestMap);
	}

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月7日 下午2:42:27
	 */
	public Map<String, Object> doWordDetail(Long sectionDetailId, String fileId, Long userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> audioCheckList = new ArrayList<String>();
		Double wordScore = 0.0;
		SectionDetail sectionDetail = sectionDetailMapper.selectById(sectionDetailId);
		String json = yunZhiSDK.generatorWordGameScore(fileId,sectionDetail.getText());
		List<Map<String, Object>> wordScoresList = new ArrayList<Map<String, Object>>();
		if (null == json || json.trim().equals("")) {
			logger.error(sectionDetailId + "game评分---出现问题,不能计算");
		} else {
			YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
			double wsc = yunZhiBean.getScore();
			wordScore = new BigDecimal(wsc).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			audioCheckList = yunZhiSDK.checkAudio(yunZhiBean.getAudioCheck());
			List<YunZhiline> yunZhilines = yunZhiBean.getLines();
			if (null != yunZhilines && yunZhilines.size() > 0) {
				YunZhiline yunZhiline = yunZhilines.get(0);
				List<YunZhiWords> yunZhiWords = yunZhiline.getWords();
				for (YunZhiWords word : yunZhiWords) {
					// 正常读音
					if (word.getType() == 2) {
						List<YunZhiSubWords> yunZhiSubWords = word.getSubwords();
						for (YunZhiSubWords subWord : yunZhiSubWords) {
							if (subWord.getSubtext() != null) {
								if (subWord.getSubtext().trim().equals("") || subWord.getSubtext().trim().equals("'") || subWord.getSubtext().trim().equals("ˌ")) {
								} else {
									Map<String, Object> wordMap = new HashMap<String, Object>();
									double sc = subWord.getScore() * 10;
									sc = new BigDecimal(sc).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
									wordMap.put("word", subWord.getSubtext());
									wordMap.put("score", sc);
									wordScoresList.add(wordMap);
								}
							}
						}
					}
				}
			}
		}
		Map<String, Object> wordMap = new HashMap<String, Object>();
		wordMap.put("sectionDetailId", sectionDetailId);
		wordMap.put("score", wordScore);
		
		
		
		resultMap.put("wordInfo", wordMap);
		resultMap.put("wordScoresList", wordScoresList);
		resultMap.put("audioCheck", audioCheckList);
		return resultMap;
	}

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月7日 下午2:58:24
	 */
	public String submitWordUnit(Long unitId, Long userId,Double score) {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("unitId", unitId);
		requestMap.put("userId", userId);
		requestMap.put("score", score);
		requestMap.put("size", 0);
		userUnitEntityMapper.submitWordUnit(requestMap);
		return "操作成功.";
	}

}
