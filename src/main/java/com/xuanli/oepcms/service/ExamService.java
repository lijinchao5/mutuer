/**
 * @fileName:  ExamService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月6日 下午3:01:28
 */
package com.xuanli.oepcms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.controller.bean.ExamAnswerBean;
import com.xuanli.oepcms.controller.bean.ExamBean;
import com.xuanli.oepcms.controller.bean.ExamStudentBean;
import com.xuanli.oepcms.controller.bean.ExamStudentScoreBean;
import com.xuanli.oepcms.entity.ExamEntity;
import com.xuanli.oepcms.entity.ExamStudentEntity;
import com.xuanli.oepcms.entity.ExamStudentScoreEntity;
import com.xuanli.oepcms.entity.ExamStudentScoreWordEntity;
import com.xuanli.oepcms.entity.ExamSubjectEntity;
import com.xuanli.oepcms.entity.PaperOptionEntity;
import com.xuanli.oepcms.entity.PaperSubjectDetailEntity;
import com.xuanli.oepcms.entity.PaperSubjectEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.mapper.ExamEntityMapper;
import com.xuanli.oepcms.mapper.ExamStudentEntityMapper;
import com.xuanli.oepcms.mapper.ExamStudentScoreEntityMapper;
import com.xuanli.oepcms.mapper.ExamStudentScoreWordEntityMapper;
import com.xuanli.oepcms.mapper.ExamSubjectEntityMapper;
import com.xuanli.oepcms.mapper.PaperEntityMapper;
import com.xuanli.oepcms.mapper.PaperOptionEntityMapper;
import com.xuanli.oepcms.mapper.PaperSubjectDetailEntityMapper;
import com.xuanli.oepcms.mapper.PaperSubjectEntityMapper;
import com.xuanli.oepcms.quartz.QuartzUtil;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.YunZhiSDK;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiWords;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiline;
import com.xuanli.oepcms.util.AliOSSUtil;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@Service
public class ExamService extends BaseService {
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	PaperSubjectDetailEntityMapper paperSubjectDetailEntityMapper;
	@Autowired
	ExamStudentScoreEntityMapper examStudentScoreEntityMapper;
	@Autowired
	PaperOptionEntityMapper paperOptionEntityMapper;
	@Autowired
	AliOSSUtil ossUtils;
	@Autowired
	ExamEntityMapper examEntityMapper;
	@Autowired
	YunZhiSDK yunZhiSDK;
	@Autowired
	ExamStudentEntityMapper examStudentEntityMapper;
	@Autowired
	UserService userService;
	@Autowired
	PaperEntityMapper paperEntityMapper;
	@Autowired
	PaperSubjectEntityMapper paperSubjectEntityMapper;
	@Autowired
	ExamSubjectEntityMapper examSubjectEntityMapper;
	@Autowired
	ExamStudentScoreWordEntityMapper examStudentScoreWordEntityMapper;
	@Autowired
	private Scheduler scheduler;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月6日 下午3:03:03
	 */
	public RestResult<String> submitExam(Long studentId, Long examId, List<ExamAnswerBean> answers, Integer timeout) {
		int timeOutCount = examEntityMapper.getTimeOutCount(examId);
		if (timeOutCount == 0) {
			return failed(ExceptionCode.HOME_WORK_TIME_OUT, "现已超出考试时限,无法提交");
		}
		for (int i = 0; i < answers.size(); i++) {
			ExamAnswerBean answer = answers.get(i);
			long detailId = answer.getKey();
			// 获取题目详情
			PaperSubjectDetailEntity paperSubjectDetailEntity = paperSubjectDetailEntityMapper.selectById(detailId);
			PaperOptionEntity record = new PaperOptionEntity();
			record.setDetailId(detailId);
			PaperOptionEntity paperOptionEntity = paperOptionEntityMapper.selectByDetailId(record);
			// 获取考试详情
			// ExamEntity examEntity = examEntityMapper.selectById(examId);
			// double pointScore = examEntity.getPointScore().doubleValue();
			ExamStudentScoreEntity examStudentScoreEntity = new ExamStudentScoreEntity();
			examStudentScoreEntity.setSubjectDetailId(detailId);
			examStudentScoreEntity.setExamId(examId);
			examStudentScoreEntity.setStudentId(studentId);
			/** TODO 生成分数前删除旧score分数 **/
			examStudentScoreEntityMapper.deleteExamStudentScore(examStudentScoreEntity);
			// 听后回答
			String fileId = answer.getValue();
			String text = null;
			Double score = 0.00;
			// 听后回答是有音频的---- type --1(听后回答)
			if (paperSubjectDetailEntity.getType().intValue() == 1) {
				double realScore = 0.00;
				try {
					// 计算分数
					// 获取本地答案的详细信息
					double qScore = paperSubjectDetailEntity.getScore();
					String correnctResult = paperOptionEntity.getCorrectResult();
					String pointResult = paperOptionEntity.getPointResult();
					String json = yunZhiSDK.generatorStudentExamScoreJSGF(fileId, pointResult, correnctResult, "A");
					if (null == json || json.trim().equals("")) {
						System.out.println(paperOptionEntity.getId() + "---出现问题,不能计算");
					} else {
						YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
						score = yunZhiBean.getScore();
					}
					realScore = score;
					score = score * qScore / 100;
				} catch (Exception e) {
					e.printStackTrace();
				}
				examStudentScoreEntity.setScore(score);
				examStudentScoreEntity.setPercentScore(realScore);
				examStudentScoreEntity.setAudioPath(fileId);
				examStudentScoreEntity.setText(text);
				examStudentScoreEntity.setEnableFlag("T");
				examStudentScoreEntity.setCreateDate(new Date());
				examStudentScoreEntity.setCreateId(studentId);
				examStudentScoreEntityMapper.insertExamStudentScoreEntity(examStudentScoreEntity);
				// 听后选择---- type --2(选择题)
			} else if (paperSubjectDetailEntity.getType().intValue() == 2) {
				// 传入的为ABCDEF这种参数-
				String textStr = answer.getValue();
				if (paperOptionEntity.getCorrectResult().trim().equalsIgnoreCase(textStr.trim())) {
					score = 100.00;
				} else {
					score = 0.00;
				}
				double realScore = score;
				score = score * paperSubjectDetailEntity.getScore() / 100;
				examStudentScoreEntity.setScore(score);
				examStudentScoreEntity.setPercentScore(realScore);
				examStudentScoreEntity.setText(textStr);
				examStudentScoreEntity.setEnableFlag("T");
				examStudentScoreEntity.setCreateDate(new Date());
				examStudentScoreEntity.setCreateId(studentId);
				examStudentScoreEntityMapper.insertExamStudentScoreEntity(examStudentScoreEntity);
				// 听后填空题---- type --3(填空题)
			} else if (paperSubjectDetailEntity.getType().intValue() == 3) {
				// 传入的为填空题的答案
				String textStr = answer.getValue();
				if (StringUtil.isEmpty(textStr)) {
					score = 0.00;
				} else {
					String[] rStrings = paperOptionEntity.getCorrectResult().split("\\|\\|");
					score = 0.00;
					for (String r : rStrings) {
						if (r.trim().equalsIgnoreCase(textStr.trim())) {
							score = 100.00;
							break;
						}
					}
				}
				double realScore = score;
				score = score * paperSubjectDetailEntity.getScore() / 100;
				examStudentScoreEntity.setPercentScore(realScore);
				examStudentScoreEntity.setScore(score);
				examStudentScoreEntity.setText(textStr);
				examStudentScoreEntity.setEnableFlag("T");
				examStudentScoreEntity.setCreateDate(new Date());
				examStudentScoreEntity.setCreateId(studentId);
				examStudentScoreEntityMapper.insertExamStudentScoreEntity(examStudentScoreEntity);
				// 听后转述---- type --4(听后转述)
			} else if (paperSubjectDetailEntity.getType().intValue() == 4) {
				double realScore = 0.00;
				try {
					// 计算分数
					// 获取本地答案的详细信息
					double qScore = paperSubjectDetailEntity.getScore();
					String correnctResult = paperOptionEntity.getCorrectResult();
					String pointResult = paperOptionEntity.getPointResult();
					String json = yunZhiSDK.generatorStudentExamScoreJSGF(fileId, pointResult, correnctResult, "A");
					;
					if (null == json || json.trim().equals("")) {
						System.out.println(paperOptionEntity.getId() + "---出现问题,不能计算");
					} else {
						YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
						score = yunZhiBean.getScore();
					}
					realScore = score;
					score = score * qScore / 100;
				} catch (Exception e) {
					e.printStackTrace();
				}
				score = score / 100;
				examStudentScoreEntity.setScore(score);
				examStudentScoreEntity.setPercentScore(realScore);
				examStudentScoreEntity.setAudioPath(fileId);
				examStudentScoreEntity.setText(text);
				examStudentScoreEntity.setEnableFlag("T");
				examStudentScoreEntity.setCreateDate(new Date());
				examStudentScoreEntity.setCreateId(studentId);
				examStudentScoreEntityMapper.insertExamStudentScoreEntity(examStudentScoreEntity);
			} else if (paperSubjectDetailEntity.getType().intValue() == 5) {
				// 朗读短文---- type --5(朗读短文)
				// 阅读是有音频的
				try {
					// 阅读 直接把问题text传入即可
					String json = yunZhiSDK.generatorStudentExamScore(fileId, paperSubjectDetailEntity.getQuestion(), "E");
					if (null == json || json.trim().equals("")) {
						System.out.println(paperSubjectDetailEntity.getId() + "---出现问题,不能计算");
					} else {
						YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
						score = yunZhiBean.getScore();
						// 这里要弄一下流利度流畅度完整度三个信息
						if (null != yunZhiBean.getLines() && yunZhiBean.getLines().size() > 0) {
							/** TODO 生成分数前删除旧word分数 **/
							ExamStudentScoreWordEntity examStudentScoreWordEntity1 = new ExamStudentScoreWordEntity();
							examStudentScoreWordEntity1.setExamId(examId);
							examStudentScoreWordEntity1.setStudentId(studentId);
							examStudentScoreWordEntity1.setExamDetailId(paperSubjectDetailEntity.getId());
							examStudentScoreWordEntityMapper.deleteExamStudentScoreWord(examStudentScoreWordEntity1);
							YunZhiline yunZhiline = yunZhiBean.getLines().get(0);
							examStudentScoreEntity.setFluency(yunZhiline.getFluency());
							examStudentScoreEntity.setIntegrity(yunZhiline.getIntegrity());
							examStudentScoreEntity.setPronunciation(yunZhiline.getPronunciation());
							List<YunZhiWords> yunZhiWords = yunZhiline.getWords();
							for (YunZhiWords yunZhiWord : yunZhiWords) {
								// yunZhiWord.
								ExamStudentScoreWordEntity examStudentScoreWordEntity = new ExamStudentScoreWordEntity();
								examStudentScoreWordEntity.setExamDetailId(paperSubjectDetailEntity.getId());
								examStudentScoreWordEntity.setExamId(examId);
								examStudentScoreWordEntity.setScore(yunZhiWord.getScore() * 10);
								examStudentScoreWordEntity.setType(yunZhiWord.getType() + "");
								examStudentScoreWordEntity.setText(yunZhiWord.getText());
								examStudentScoreWordEntity.setStudentId(studentId);
								examStudentScoreWordEntityMapper.insertSelective(examStudentScoreWordEntity);
							}

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				double realScore = score;
				score = score * paperSubjectDetailEntity.getScore() / 100;
				examStudentScoreEntity.setPercentScore(realScore);
				examStudentScoreEntity.setScore(score);
				examStudentScoreEntity.setAudioPath(fileId);
				examStudentScoreEntity.setText(text);
				examStudentScoreEntity.setEnableFlag("T");
				examStudentScoreEntity.setCreateDate(new Date());
				examStudentScoreEntity.setCreateId(studentId);
				examStudentScoreEntityMapper.insertExamStudentScoreEntity(examStudentScoreEntity);
			}
		}
		// 更新学生做题信息
		ExamStudentEntity examStudentEntity = new ExamStudentEntity();
		examStudentEntity.setExamId(examId);
		examStudentEntity.setStudentId(studentId);
		examStudentEntity.setUpdateDate(new Date());
		examStudentEntity.setTimeOut(timeout);
		examStudentEntityMapper.updateExamStudentEntityByExamId(examStudentEntity);
		return okNoResult("完成考试");
	}

	/**
	 * @Description: TODO 分页展示考试列表
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月7日 上午10:44:41
	 */
	public void findExamByPage(ExamEntity examEntity, PageBean pageBean) {
		int total = examEntityMapper.findExamByPageTotal(examEntity);
		pageBean.setTotal(total);
		examEntity.setStart(pageBean.getRowFrom());
		examEntity.setEnd(pageBean.getPageSize());
		List<ExamEntity> examEntities = examEntityMapper.findExamByPage(examEntity);
		pageBean.setRows(examEntities);
	}

	/**
	 * @Description: TODO 生成作业报告--其实就是计算学生总分数
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月9日 上午10:25:55
	 */
	public RestResult<String> generatorExamReport(Long examId, Long studentId) {
		//
		ExamStudentEntity examStudentEntity = new ExamStudentEntity();
		examStudentEntity.setExamId(examId);
		List<ExamStudentEntity> examStudentEntities = examStudentEntityMapper.generatorExamReport(examStudentEntity);
		for (ExamStudentEntity ese : examStudentEntities) {
			ese.setComplate("E");
			ese.setExamId(examId);
			ese.setEnableFlag("T");
			examStudentEntityMapper.updateExamStudentEntityByExamId(ese);
		}
		// TODO 更新名次信息
		List<ExamStudentBean> examStudentBeans = examStudentEntityMapper.getExamStudentRank(examStudentEntity);
		for (ExamStudentBean examStudentBean : examStudentBeans) {
			ExamStudentEntity examStudentEntity2 = new ExamStudentEntity();
			examStudentEntity2.setId(examStudentBean.getId());
			examStudentEntity2.setStudentRank(examStudentBean.getRank());
			examStudentEntityMapper.updateExamStudentEntity(examStudentEntity2);
		}
		return okNoResult("成功");
	}

	/**
	 * @Description: TODO //布置模拟考试
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月23日 下午2:53:36
	 */
	public RestResult<String> genteratorExam(Long userId, String name, String notice, String classIds, Date startTime, Date endTime, Long paperId) {
		String clasIds[] = classIds.split(",");
		for (int i = 0; i < clasIds.length; i++) {
			String clasId = clasIds[i];
			// 考试信息
			ExamEntity examEntity = new ExamEntity();
			examEntity.setEnableFlag("T");
			examEntity.setNotice(notice);
			examEntity.setName(name);
			examEntity.setStartTime(startTime);
			examEntity.setEndTime(endTime);
			examEntity.setPaperId(paperId);
			examEntity.setClassId(Long.parseLong(clasId));
			// 模拟考试
			examEntity.setType("1");
			examEntity.setCreateId(userId);
			examEntity.setCreateDate(new Date());
			examEntityMapper.insertExamEntity(examEntity);
			Long examId = examEntity.getId();
			// 考题信息
			// PaperEntity paperEntity = paperEntityMapper.selectById(paperId); // 获取试卷信息
			// Integer timeOut = paperEntity.getTotalTime();
			PaperSubjectEntity paperSubjectEntity = new PaperSubjectEntity();
			paperSubjectEntity.setPaperId(paperId);
			List<PaperSubjectEntity> paperSubjectEntities = paperSubjectEntityMapper.getPaperSubjectEntity(paperSubjectEntity); // 获取试卷详细信息
			for (PaperSubjectEntity paperSubjectEntity2 : paperSubjectEntities) {
				ExamSubjectEntity examSubjectEntity = new ExamSubjectEntity();
				examSubjectEntity.setCreateDate(new Date());
				examSubjectEntity.setCreateId(userId);
				examSubjectEntity.setEnableFlag("T");
				examSubjectEntity.setSubjectId(paperSubjectEntity2.getId());
				examSubjectEntity.setExamId(examEntity.getId());
				examSubjectEntityMapper.insertExamSubjectEntity(examSubjectEntity);
			}
			// 考生信息
			UserEntity userEntity1 = new UserEntity();
			userEntity1.setClasId(clasId);
			List<UserEntity> userEntities = userService.getClasUseStudent(userEntity1);
			for (UserEntity userEntity : userEntities) {
				ExamStudentEntity examStudentEntity = new ExamStudentEntity();
				examStudentEntity.setComplate("F");
				examStudentEntity.setEnableFlag("T");
				examStudentEntity.setCreateId(userId);
				examStudentEntity.setExamId(examEntity.getId());
				examStudentEntity.setStudentId(userEntity.getId());
				examStudentEntity.setTimeOut(0);
				examStudentEntity.setScore(0.00);
				examStudentEntityMapper.insertExamStudentEntity(examStudentEntity);
			}
			// 添加一个定时化任务到指定的时间点后 执行该操作
			String cron = QuartzUtil.cron(endTime);
			try {
				QuartzUtil.addExamJob(scheduler, "com.xuanli.oepcms.quartz.job.ExamJob", "examReport_" + examId.longValue() + "_" + UUID.randomUUID().toString(), cron, examId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("布置作业定时化任务失败.出现错误.", e);
			}
		}
		return okNoResult("成功布置模拟考试!");
	}

	/**
	 * 考试预览题目信息
	 * 
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月9日 上午10:15:41
	 */
	public RestResult<ExamBean> getExamInfo(Long examId) {
		ExamBean examBean = examEntityMapper.getExamInfo(examId);
		return ok(examBean);
	}

	/**
	 * 获取学生考试的详细信息
	 * 
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月9日 上午10:15:35
	 */
	public RestResult<Map<String, Object>> getStudentExamInfo(Long examId, Long studentId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取学生考试的分数信息
		ExamStudentBean examStudentBean = new ExamStudentBean();
		examStudentBean.setExamId(examId);
		examStudentBean.setStudentId(studentId);
		List<ExamStudentBean> examStudentBeans = examStudentEntityMapper.getStudentExamScore(examStudentBean);
		// 获取学生考试的详细信息
		List<ExamStudentScoreBean> examStudentScoreBeans = examStudentEntityMapper.getStudentExamScoreDetail(examStudentBean);
		// 获取学生朗读短文的每个句子的得分
		ExamStudentScoreWordEntity examStudentScoreWordEntity = new ExamStudentScoreWordEntity();
		examStudentScoreWordEntity.setExamId(examId);
		examStudentScoreWordEntity.setStudentId(studentId);
		List<ExamStudentScoreWordEntity> examStudentScoreWordEntities = examStudentScoreWordEntityMapper.getExamStudentWords(examStudentScoreWordEntity);
		// 获取到学生按照题型的分数(包括学生未做题目的分数)
		resultMap.put("examStudentScores", examStudentBeans);
		resultMap.put("examStudentScoreDetails", examStudentScoreBeans);
		resultMap.put("examStudentWordsScore", examStudentScoreWordEntities);
		return ok(resultMap);
	}

	/**
	 * 获取报告内容
	 * 
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月9日 上午10:15:48
	 */
	public RestResult<Map<String, Object>> getExamReport(Long examId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ExamStudentEntity examStudentEntity = new ExamStudentEntity();
		examStudentEntity.setExamId(examId);
		List<ExamStudentBean> examStudentBeans = examStudentEntityMapper.getExamStudentRank(examStudentEntity);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("examId", examId);
		List<Map<String, Object>> examSubjectTypeScore = examStudentScoreEntityMapper.getExamSubjectTypeScore(map1);
		List<Map<String, Object>> examSubjectDetailScore = examStudentScoreEntityMapper.examSubjectDetailScore(map1);
		ExamEntity examEntity = examEntityMapper.selectById(examId);
		List<Map<String, Object>> paperDetails = paperEntityMapper.getPaperDetailByTeacher(examEntity.getPaperId());
		// 获取本次考试的所有题型信息
		resultMap.put("paperDetails", paperDetails);
		// 获取每个小题平均得分
		resultMap.put("examSubjectDetailScore", examSubjectDetailScore);
		// 获取每个大题型平均得分--需求以外部分
		resultMap.put("examSubjectTypeScore", examSubjectTypeScore);
		// 学生排名等信息=-===参加考试的学生信息
		resultMap.put("examStudents", examStudentBeans);
		// 获取考试的详细信息
		resultMap.put("examDetail", examEntity);
		return ok(resultMap);
	}

	/**
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月28日 上午9:43:34
	 */
	public RestResult<Map<String, Object>> getExamDetail(Long examId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ExamEntity examEntity = examEntityMapper.selectById(examId);
		map.put("examDetail", examEntity);
		List<Map<String, Object>> maps1 = paperEntityMapper.getPaperDetail1(examEntity.getPaperId());
		map.put("paperDetail", maps1);
		map.put("paperInfo", paperEntityMapper.selectById(examEntity.getPaperId()));
		return ok(map);
	}

	/**
	 * 获取学生考试列表
	 * 
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月7日 下午3:38:05
	 */
	public void findStudentExamByPage(Long studentId, PageBean pageBean, String state) {
		Map<String, Object> requiredMap = new HashMap<String, Object>();
		requiredMap.put("studentId", studentId);
		requiredMap.put("state", state);
		int total = examEntityMapper.findStudentExamByPageTotal(requiredMap);
		pageBean.setTotal(total);
		requiredMap.put("start", pageBean.getRowFrom());
		requiredMap.put("end", pageBean.getPageSize());
		List<Map<String, Object>> resultMap = examEntityMapper.findStudentExamByPage(requiredMap);
		pageBean.setRows(resultMap);
	}

	/**
	 * 获取学生做题的详细信息
	 * 
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月28日 上午9:43:34
	 */
	public RestResult<Map<String, Object>> findStudentExamDetail(Long examId, Long studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ExamEntity examEntity = examEntityMapper.selectById(examId);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("paperId", examEntity.getPaperId());
		map2.put("examId", examId);
		map2.put("studentId", studentId);
		List<Map<String, Object>> maps1 = paperEntityMapper.getPaperDetail(map2);
		for (Map<String, Object> map1 : maps1) {
			Object object = map1.get("correntResult");
			if (null == object) {
			} else {
				String correntResult = (String) object;
				int size = correntResult.split("\\|\\|").length;
				map1.put("correntResult", size);
			}
		}
		// 学生做题的信息
		ExamStudentEntity examStudentEntity = new ExamStudentEntity();
		examStudentEntity.setStudentId(studentId);
		examStudentEntity.setExamId(examId);
		ExamStudentEntity examStudentInfo = examStudentEntityMapper.getExamStudentInfo(examStudentEntity);
		map.put("examStudentInfo", examStudentInfo);
		map.put("paperDetail", maps1);
		map.put("paperInfo", paperEntityMapper.selectById(examEntity.getPaperId()));
		map.put("examDetail", examEntity);
		return ok(map);
	}

	/**
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月12日 下午5:37:08
	 */
	public RestResult<String> commitExam(Long studentId, Long examId) {
		int timeOutCount = examEntityMapper.getTimeOutCount(examId);
		if (timeOutCount == 0) {
			return failed(ExceptionCode.HOME_WORK_TIME_OUT, "现已超出考试时限,无法提交");
		}
		ExamStudentEntity examStudentEntity = new ExamStudentEntity();
		examStudentEntity.setStudentId(studentId);
		examStudentEntity.setExamId(examId);
		List<ExamStudentEntity> examStudentEntities = examStudentEntityMapper.getExamStudentEntityByStudent(examStudentEntity);
		for (ExamStudentEntity ese : examStudentEntities) {
			ese.setComplate("S");
			ese.setExamId(examId);
			ese.setEnableFlag("T");
			examStudentEntityMapper.updateExamStudentEntityByExamId(ese);
		}
		return okNoResult("作业提交");
	}

	/**
	 * 获取学生考试报告
	 * 
	 * @CreateName: QiaoYu[www.codelion.cn]
	 * @CreateDate: 2018年3月16日 上午9:56:53
	 */
	public RestResult<Map<String, Object>> getStudentExamReport(Long examId, Long studentId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取学生基本信息
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("examId", examId);
		map1.put("studentId", studentId);
		ExamEntity examEntity = examEntityMapper.selectById(examId);
		Map<String, Object> examStudentMap = examStudentEntityMapper.getStudentExamReport(map1);
		List<Map<String, Object>> examStudentTypeMap = examStudentEntityMapper.getStudentExamTypeReport(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("paperId", examEntity.getPaperId());
		map2.put("examId", examId);
		map2.put("studentId", studentId);
		List<Map<String, Object>> examStudentScoreMap = paperEntityMapper.getPaperDetailAndScore(map2);
		List<ExamStudentScoreWordEntity> allIds = new ArrayList<ExamStudentScoreWordEntity>();
		for (Map<String, Object> map : examStudentScoreMap) {
			ExamStudentScoreWordEntity e = new ExamStudentScoreWordEntity();
			Long detailId = (Long) map.get("paperSubjectDetailId");
			e.setExamDetailId(detailId);
			allIds.add(e);
		}
		List<ExamStudentScoreWordEntity> examStudentScoreWordEntities = examStudentScoreWordEntityMapper.findByDetailId(allIds, studentId, examId);

		for (Map<String, Object> map : examStudentScoreMap) {
			Long detailId = (Long) map.get("paperSubjectDetailId");
			List<ExamStudentScoreWordEntity> ews = new ArrayList<ExamStudentScoreWordEntity>();
			for (ExamStudentScoreWordEntity examStudentScoreWordEntity : examStudentScoreWordEntities) {
				if (detailId.longValue() == examStudentScoreWordEntity.getExamDetailId().longValue()) {
					ews.add(examStudentScoreWordEntity);
				}
			}
			map.put("words", ews);
		}
		// 考试学生信息
		resultMap.put("examStudentScore", examStudentScoreMap);
		// 考试学生信息
		resultMap.put("examStudentInfo", examStudentMap);
		// 考试类型信息
		resultMap.put("examTypeScoreList", examStudentTypeMap);
		return ok(resultMap);
	}

	public String updateExamStudentEntityRemark(String studentIds, Long examId, String remark) {
		String[] studentId = studentIds.split(",");
		ExamStudentEntity examStudentEntity = new ExamStudentEntity();
		examStudentEntity.setExamId(examId);
		examStudentEntity.setRemark(remark);
		examStudentEntity.setStuIds(studentId);
		int result = examStudentEntityMapper.updateExamStudentEntityRemark(examStudentEntity);
		if (result > 0) {
			return "1";
		} else {
			return "0";
		}
	}

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年3月28日 下午3:46:57
	 */
	public String getExamStudent(Long examId) {
		return examEntityMapper.getExamStudent(examId);
	}

	public ExamEntity getExamName(Long examId) {
		return examEntityMapper.selectById(examId);
	}
}
