/**
 * @fileName:  ReportService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 上午9:36:28
 */
package com.xuanli.oepcms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.controller.bean.HomeworkPicScoreBean;
import com.xuanli.oepcms.controller.bean.HomeworkScoreBean;
import com.xuanli.oepcms.controller.bean.HomeworkSymbolScore;
import com.xuanli.oepcms.entity.HomeworkStudentEntity;
import com.xuanli.oepcms.entity.HomeworkStudentScoreEntity;
import com.xuanli.oepcms.entity.HomeworkStudentScoreWordEntity;
import com.xuanli.oepcms.mapper.HomeworkStudentScoreEntityMapper;
import com.xuanli.oepcms.mapper.HomeworkStudentScoreWordEntityMapper;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@Service
public class ReportService extends BaseService {
	@Autowired
	HomeworkService homeworkService;
	@Autowired
	HomeworkStudentScoreEntityMapper homeworkStudentScoreDao;
	@Autowired
	HomeworkStudentScoreWordEntityMapper homeworkStudentScoreWordEntityDao;
	public static final int TOTALSCORE = 100;
	/**
	 * @Description: TODO 获取作业报告
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 上午9:39:06
	 */
	public RestResult<Map<String, Object>> homeworkReport(long homeworkId) {
		//获取统计信息
		List<HomeworkStudentScoreEntity> studentScores = homeworkService.getHomeworkStudentScoreByHomeworkId(homeworkId);
		//获取学情分析数据
		//获取音素平均分
		List<HomeworkSymbolScore> symbolScores = homeworkService.getHomeworkSymbolScore(homeworkId);
		//获取每个题的平均分
		List<HomeworkPicScoreBean> subjectScores = homeworkService.getHomeworkPicScore(homeworkId);
		//获取每个类型题平均分
		List<HomeworkPicScoreBean> subjectTypeScores = homeworkService.getHomeworkPicTypeScore(homeworkId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("studentScore", studentScores);
		resultMap.put("symbolScore", symbolScores);
		resultMap.put("subjectScore", subjectScores);
		resultMap.put("subjectTypeScore", subjectTypeScores);
		return ok(resultMap);
	}

	/**
	 * @Description: TODO 生成作业报告
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 上午9:41:19
	 */
	public RestResult<String> generatorHomeworkReport(Long homeworkId, Long id) {
		homeworkStudentScoreDao.deleteHomeworkStudentScore(homeworkId);
		// 生成学生作业分数
		// --获取题目数量
		int subjectSize = homeworkService.reportHomeworkDetail(homeworkId);// 一共十个题
		// --获取学生总得分
		List<HomeworkStudentScoreEntity> studentScoreEntities = homeworkService.reportStudentScore(homeworkId);
		for (HomeworkStudentScoreEntity homeworkStudentScoreEntity : studentScoreEntities) {
			Long studentId = homeworkStudentScoreEntity.getStudentId();
			Double score = homeworkStudentScoreEntity.getScore();
			double studentScore = score / subjectSize;
			// 更新数据库
			HomeworkStudentEntity studentEntity = new HomeworkStudentEntity();
			studentEntity.setHomeworkId(homeworkId);
			studentEntity.setStudentId(studentId);
			//updateID在查询中特殊使用了一下,因为没有预留字段
			studentEntity.setWorkTime(homeworkStudentScoreEntity.getUpdateId());
			studentEntity.setUpdateDate(homeworkStudentScoreEntity.getUpdateDate());
			BigDecimal b = new BigDecimal(studentScore);
			studentScore = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			studentEntity.setScore(studentScore);
			//studentEntity.setWorkComplate("T");
			// 保存本次学生的分数
			homeworkService.updateHomeworkStudentScoreEntityBatch(studentEntity);
		}
		return ok("生成完成");
	}

	/**
	 * @Description:  TODO 获取学生作业报告信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月27日 下午12:19:40
	 */
	public RestResult<Map<String, Object>> getStudentHomeworkReport(Long userId, Long homeworkId) {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("studentId", userId);
		requestMap.put("homeworkId", homeworkId);
		Map<String, Object> studentHomeworkInfo = homeworkService.getStudentHomeworkInfo(requestMap);
		List<HomeworkScoreBean> studentHomeworkDetail = homeworkService.getStudentHomework(homeworkId, userId, null);
		
		HomeworkStudentScoreWordEntity homeworkStudentScoreWordEntity = new HomeworkStudentScoreWordEntity();
		homeworkStudentScoreWordEntity.setStudentId(userId);
		homeworkStudentScoreWordEntity.setHomeworkId(homeworkId);
		List<HomeworkStudentScoreWordEntity> homeworkStudentScoreWordEntities = homeworkStudentScoreWordEntityDao.getHomeworkStudentScoreWord(homeworkStudentScoreWordEntity);
		for (HomeworkScoreBean hsb : studentHomeworkDetail) {
			List<HomeworkStudentScoreWordEntity> tempList = new ArrayList<HomeworkStudentScoreWordEntity>();
			for (HomeworkStudentScoreWordEntity hsswe : homeworkStudentScoreWordEntities) {
				if (hsswe.getHomeworkDetailId().longValue() == hsb.getHomeworkDetailId().longValue()
						&& hsswe.getStudentId().longValue() == userId
						&& hsswe.getHomeworkId().longValue() == homeworkId) {
					tempList.add(hsswe);
				}
			}
			hsb.setHomeworkStudentScoreWordEntities(tempList);
		}
		
		
		//算出来平均分
		List<Map<String, Object>> studentAvgScore = homeworkService.getStudentAvgScore(requestMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("studentHomeworkInfo", studentHomeworkInfo);
		resultMap.put("studentHomeworkDetail", studentHomeworkDetail);
		resultMap.put("studentAvgScore", studentAvgScore);
		return ok(resultMap);
	}

}
