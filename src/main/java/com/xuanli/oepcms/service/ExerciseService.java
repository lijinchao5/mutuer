/**
 * 
 */
package com.xuanli.oepcms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.entity.ExerciseDetailEntity;
import com.xuanli.oepcms.entity.ExerciseDetailWordEntity;
import com.xuanli.oepcms.entity.ExerciseEntity;
import com.xuanli.oepcms.entity.ReadArticleEntity;
import com.xuanli.oepcms.entity.ReadSentenceEntity;
import com.xuanli.oepcms.mapper.ExerciseDetailEntityMapper;
import com.xuanli.oepcms.mapper.ExerciseDetailWordEntityMapper;
import com.xuanli.oepcms.mapper.ExerciseEntityMapper;
import com.xuanli.oepcms.mapper.ReadArticleEntityMapper;
import com.xuanli.oepcms.mapper.ReadSentenceEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.YunZhiSDK;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiWords;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiline;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author lijinchao
 * @date 2018年3月9日 下午4:23:50
 */
@Service
public class ExerciseService extends BaseService {
	@Autowired
	ExerciseEntityMapper exerciseDao;
	@Autowired
	ReadArticleEntityMapper readArticleDao;
	@Autowired
	ReadSentenceEntityMapper readSentenceDao;
	@Autowired
	ExerciseDetailEntityMapper exerciseDetailDao;
	@Autowired
	ExerciseDetailWordEntityMapper exerciseDetailWordDao;
	@Autowired
	YunZhiSDK yunZhiSDK;

	/**
	 * Title: findReadArticlePage Description: 练习文章列表
	 * 
	 * @date 2018年3月13日 上午11:29:46
	 * @param readArticleEntity
	 * @param pageBean
	 */
	public void findReadArticlePage(ReadArticleEntity readArticleEntity, PageBean pageBean) {
		if (StringUtil.isNotEmpty(readArticleEntity.getType())) {
			String[] types = readArticleEntity.getType().split(",");
			String type = "";
			for (String result : types) {
				type += "'" + result + "',";
			}
			if (type.length() > 0) {
				type = type.substring(0, type.length() - 1);
			}
			readArticleEntity.setType(type);
		}
		int findReadArticlePageTotal = readArticleDao.findReadArticlePageTotal(readArticleEntity);
		pageBean.setTotal(findReadArticlePageTotal);
		readArticleEntity.setStart(pageBean.getRowFrom());
		readArticleEntity.setEnd(pageBean.getPageSize());
		List<Map<String, Object>> findReadArticlePage = readArticleDao.findReadArticlePage(readArticleEntity);
		pageBean.setRows(findReadArticlePage);
	}

	/**
	 * Title: getReadSentence Description: 文章段落
	 * 
	 * @date 2018年3月13日 上午11:30:08
	 * @param articleId
	 * @param studentId
	 * @return
	 */
	public List<Map<String, Object>> getReadSentence(Map<String, Object> requestMap) {
		List<Map<String, Object>> readSentence = readSentenceDao.getReadSentence(requestMap);
		List<Map<String, Object>> readSentenceScore = readSentenceDao.getReadSentenceScore(requestMap);
		for (Map<String, Object> map : readSentence) {
			String id = map.get("id") + "";
			List<Map<String, Object>> list = new ArrayList<>();
			for (Map<String, Object> map2 : readSentenceScore) {
				String sentenceId = map2.get("sentence_id") + "";
				if (id.equals(sentenceId)) {
					list.add(map2);
				}
			}
			map.put("readSentenceScore", list);
		}
		return readSentence;
	}

	/**
	 * Title: doExercise Description: 做练习
	 * 
	 * @date 2018年3月13日 上午11:36:08
	 * @param studentId
	 * @param articleId
	 * @param sentenceId
	 * @param file
	 * @return
	 */
	public RestResult<Map<String, Object>> doExercise(Long studentId, Long articleId, Long sentenceId, String file) {
		Map<String, Object> map = new HashMap<String, Object>();
		ExerciseDetailEntity exerciseDetailEntity = new ExerciseDetailEntity();
		exerciseDetailEntity.setStudentId(studentId);
		exerciseDetailEntity.setCreateId(studentId);
		exerciseDetailEntity.setCreateDate(new Date());
		exerciseDetailEntity.setUpdateDate(new Date());
		exerciseDetailEntity.setEnableFlag("T");
		exerciseDetailEntity.setStudentAudioPath(file);
		exerciseDetailEntity.setArticleId(articleId);
		exerciseDetailEntity.setReadSentenceId(sentenceId);
		// 如果作业已做过，更新
		ExerciseDetailEntity exerciseDetailEntity1 = exerciseDetailDao.selectExerciseDetailEntity(exerciseDetailEntity);
		if (null != exerciseDetailEntity1) {
			exerciseDetailEntity.setId(exerciseDetailEntity1.getId());
			exerciseDetailDao.updateExerciseDetailEntity(exerciseDetailEntity);
		} else {
			exerciseDetailDao.insertExerciseDetailEntity(exerciseDetailEntity);
		}

		ReadSentenceEntity readSentenceEntity = readSentenceDao.selectById(sentenceId);
		String text = readSentenceEntity.getSentenceCont();
		String json = yunZhiSDK.generatorExerciseScore(text, file);
		System.out.println(json);
		if (null == json || json.trim().equals("")) {
			System.out.println(readSentenceEntity.getId() + "---出现问题,不能计算");
		} else {
			YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
			System.out.println(JSON.toJSONString(yunZhiBean));
			// 开始分析作业分数并且更新到数据库中
			// 设置分数
			exerciseDetailEntity.setScore(yunZhiBean.getScore());
			// 这里有句子的流畅度完整度流利度
			List<YunZhiline> yunZhilines = yunZhiBean.getLines();
			if (null != yunZhilines && yunZhilines.size() > 0) {
				YunZhiline yunZhiline = yunZhilines.get(0);
				exerciseDetailEntity.setFluency(yunZhiline.getFluency());
				exerciseDetailEntity.setIntegrity(yunZhiline.getIntegrity());
				exerciseDetailEntity.setPronunciation(yunZhiline.getPronunciation());
				// 删除word评分
				ExerciseDetailWordEntity exerciseDetailWordEntity = new ExerciseDetailWordEntity();
				exerciseDetailWordEntity.setArticleId(articleId);
				exerciseDetailWordEntity.setStudentId(studentId);
				exerciseDetailWordEntity.setSentenceId(sentenceId);
				exerciseDetailWordDao.deleteDetailWordEntity(exerciseDetailWordEntity);
				for (YunZhiline line : yunZhilines) {
					List<ExerciseDetailWordEntity> exerciseDetailWordEntities = new ArrayList<ExerciseDetailWordEntity>();
					List<YunZhiWords> yunZhiSubWords = line.getWords();
					for (YunZhiWords word : yunZhiSubWords) {
						double score = word.getScore();
						String text1 = word.getText();
						ExerciseDetailWordEntity detailWordEntity = new ExerciseDetailWordEntity();
						detailWordEntity.setArticleId(articleId);
						detailWordEntity.setSentenceId(sentenceId);
						detailWordEntity.setDetailId(exerciseDetailEntity.getId());
						detailWordEntity.setStudentId(studentId);
						detailWordEntity.setWord(text1);
						double sc = score * 10;
						sc = new BigDecimal(sc).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
						detailWordEntity.setScore(sc);
						exerciseDetailWordEntities.add(detailWordEntity);
					}
					exerciseDetailWordDao.insertExerciseDetailWordEntityBatch(exerciseDetailWordEntities);
				}
			}
		}
		ExerciseDetailWordEntity detailWordEntity = new ExerciseDetailWordEntity();
		detailWordEntity.setArticleId(articleId);
		detailWordEntity.setSentenceId(sentenceId);
		detailWordEntity.setStudentId(studentId);
		List<ExerciseDetailWordEntity> exerciseDetailWords = exerciseDetailWordDao.getExerciseDetailWord(detailWordEntity);
		exerciseDetailDao.updateExerciseDetailEntity(exerciseDetailEntity);
		map.put("exerciseDetailEntity", exerciseDetailEntity);
		map.put("exerciseDetailWords", exerciseDetailWords);
		return ok(map);
	}

	/**
	 * Title: submitExercise Description: 提交练习
	 * 
	 * @date 2018年3月13日 下午9:06:48
	 * @param studentId
	 * @param articleId
	 * @return
	 */
	public RestResult<ExerciseEntity> submitExercise(Long studentId, Long articleId) {
		ExerciseEntity exerceseEntity = new ExerciseEntity();
		exerceseEntity.setEnableFlag("T");
		exerceseEntity.setCreateId(studentId);
		exerceseEntity.setReadArticleId(articleId);
		exerceseEntity.setStudentId(studentId);
		exerceseEntity.setEndTime(new Date());
		exerceseEntity.setUpdateDate(new Date());

		ExerciseDetailEntity exerciseDetailEntity = new ExerciseDetailEntity();
		exerciseDetailEntity.setStudentId(studentId);
		exerciseDetailEntity.setArticleId(articleId);
		// 统计平均分
		ExerciseDetailEntity exerciseDetailEntitity = exerciseDetailDao.getExerciseDetailScore(exerciseDetailEntity);
		exerceseEntity.setScore(exerciseDetailEntitity.getScore());
		exerceseEntity.setIntegrity(exerciseDetailEntitity.getIntegrity());
		exerceseEntity.setFluency(exerciseDetailEntitity.getFluency());
		exerceseEntity.setPronunciation(exerciseDetailEntitity.getPronunciation());

		ExerciseEntity exerciseEntity1 = exerciseDao.selectExerciseEntity(exerceseEntity);
		if (null != exerciseEntity1) {
			exerceseEntity.setId(exerciseEntity1.getId());
			exerciseDao.updateExerciseEntity(exerceseEntity);
		} else {
			exerciseDao.insertExerciseEntity(exerceseEntity);
		}
		return ok(exerceseEntity);
	}

	/**
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月14日 下午12:12:44
	 */
	public RestResult<Map<String, Object>> findArtcileEntityById(Long artId) {
		ReadArticleEntity readArticleEntity = new ReadArticleEntity();
		readArticleEntity.setId(artId);
		Map<String, Object> result = readArticleDao.findArtcileEntityById(readArticleEntity);
		return ok(result);
	}

	/**
	 * Title: findArtcileEntityById 
	 * Description:  
	 * @date 2018年3月19日 下午2:24:03
	 * @param artId
	 * @return
	 */
	public void getExerciseRead(Long studentId, PageBean pageBean) {
		int findExercisePageTotal = exerciseDao.findExercisePageTotal(studentId);
		pageBean.setTotal(findExercisePageTotal);
		ExerciseEntity exerciseEntity = new ExerciseEntity();
		exerciseEntity.setStart(pageBean.getRowFrom());
		exerciseEntity.setEnd(pageBean.getPageSize());
		exerciseEntity.setStudentId(studentId);
		List<Map<String, Object>> findExercisePage = exerciseDao.findExercisePage(exerciseEntity);
		pageBean.setRows(findExercisePage);
	}

	/**
	 * Title: getStudentExerciseResult 
	 * Description:  
	 * @date 2018年3月19日 下午4:08:45
	 * @param studentId
	 */
	public RestResult<List<Map<String, Object>>> getStudentExerciseResult(Long studentId) {
		List<Map<String, Object>> studentExerciseResult = exerciseDao.getStudentExerciseResult(studentId);
		return ok(studentExerciseResult);
	}
}
