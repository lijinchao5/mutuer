/**
 * @fileName:  HomeworkService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 下午3:34:28
 */
package com.xuanli.oepcms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.controller.bean.HomeworkBean;
import com.xuanli.oepcms.controller.bean.HomeworkPicScoreBean;
import com.xuanli.oepcms.controller.bean.HomeworkScoreBean;
import com.xuanli.oepcms.controller.bean.HomeworkSymbolScore;
import com.xuanli.oepcms.entity.HomeworkDetailEntity;
import com.xuanli.oepcms.entity.HomeworkEntity;
import com.xuanli.oepcms.entity.HomeworkStudentEntity;
import com.xuanli.oepcms.entity.HomeworkStudentScoreEntity;
import com.xuanli.oepcms.entity.HomeworkStudentScoreSymbolEntity;
import com.xuanli.oepcms.entity.HomeworkStudentScoreWordEntity;
import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.mapper.HomeworkDetailEntityMapper;
import com.xuanli.oepcms.mapper.HomeworkEntityMapper;
import com.xuanli.oepcms.mapper.HomeworkStudentEntityMapper;
import com.xuanli.oepcms.mapper.HomeworkStudentScoreEntityMapper;
import com.xuanli.oepcms.mapper.HomeworkStudentScoreSymbolEntityMapper;
import com.xuanli.oepcms.mapper.HomeworkStudentScoreWordEntityMapper;
import com.xuanli.oepcms.mapper.SectionDetailMapper;
import com.xuanli.oepcms.quartz.QuartzUtil;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.YunZhiSDK;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiSubWords;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiWords;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.YunZhiline;
import com.xuanli.oepcms.util.FileUtil;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@Service
@Transactional
public class HomeworkService extends BaseService {
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	HomeworkEntityMapper homeworkDao;
	@Autowired
	HomeworkDetailEntityMapper homeworkDetailDao;
	@Autowired
	HomeworkStudentEntityMapper homeworkStudentDao;
	@Autowired
	HomeworkStudentScoreEntityMapper homeworkStudentScoreDao;
	@Autowired
	UserService userService;
	@Autowired
	FileUtil fileUtil;
	@Autowired
	YunZhiSDK yunZhiSDK;
	@Autowired
	HomeworkStudentScoreSymbolEntityMapper homeworkStudentScoreSymbolEntityDao;
	@Autowired
	HomeworkStudentScoreWordEntityMapper homeworkStudentScoreWordEntityDao;
	@Autowired
	SectionDetailMapper sectionDetailMapper;
	@Autowired
	private Scheduler scheduler;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月18日 下午3:43:08
	 */
	public void makeHomeWork(String name, String clasId, Date endTime, String remark, List<HomeworkBean> homeworkBeans, Long createId) {
		Date createDate = new Date();
		// 获取所有的班级id--可能是多个班级
		String[] clasIds = clasId.split(",");
		for (String classId : clasIds) {
			HomeworkEntity homeworkEntity = new HomeworkEntity();
			homeworkEntity.setClasId(Long.parseLong(classId));
			homeworkEntity.setName(name);
			homeworkEntity.setEndTime(endTime);
			homeworkEntity.setRemark(remark);
			homeworkEntity.setCreateId(createId);
			homeworkEntity.setCreateDate(createDate);
			// 保存作业信息
			homeworkDao.insertHomeworkEntity(homeworkEntity);
			Long homeworkId = homeworkEntity.getId();

			// 保存本次作业的学生
			UserEntity userEntity1 = new UserEntity();
			userEntity1.setClasId(classId);
			List<UserEntity> userEntities = userService.getClasUseStudent(userEntity1);
			List<HomeworkStudentEntity> homeworkStudentEntities = new ArrayList<HomeworkStudentEntity>();
			for (UserEntity userEntity : userEntities) {
				HomeworkStudentEntity homeworkStudentEntity = new HomeworkStudentEntity();
				homeworkStudentEntity.setCreateId(createId);
				homeworkStudentEntity.setHomeworkId(homeworkId);
				homeworkStudentEntity.setCreateDate(createDate);
				homeworkStudentEntity.setWorkTime(new Long(0));
				homeworkStudentEntity.setStudentId(userEntity.getId());
				homeworkStudentEntity.setWorkComplate("F");
				homeworkStudentEntities.add(homeworkStudentEntity);
			}
			if (null != homeworkStudentEntities && homeworkStudentEntities.size() > 0) {
				homeworkStudentDao.insertHomeworkStudentEntityBatch(homeworkStudentEntities);
			}
			// 保存作业详细信息
			List<HomeworkDetailEntity> homeworkDetailEntities = new ArrayList<HomeworkDetailEntity>();
			for (HomeworkBean homeworkBean : homeworkBeans) {
				String[] sections = homeworkBean.getSubjects().split(",");
				if (homeworkBean.getType() == 4) {
					// 查看对话有几个.分别插入到数据库中
					// 获取全部对话 type为4
					for (String sec : sections) {
						SectionDetail sectionDetail = new SectionDetail();
						sectionDetail.setType(4);
						sectionDetail.setId(Long.parseLong(sec));
						List<SectionDetail> sectionDetails = sectionDetailMapper.getSectionDetailsDialogs(sectionDetail);
						for (SectionDetail sd : sectionDetails) {
							HomeworkDetailEntity homeworkDetailEntity = new HomeworkDetailEntity();
							homeworkDetailEntity.setHomeworkId(homeworkId);
							homeworkDetailEntity.setSectionDetailId(sd.getId());
							homeworkDetailEntity.setDialogName("F");
							homeworkDetailEntity.setHomeworkType("4");
							homeworkDetailDao.insertHomeworkDetailEntityDialog(homeworkDetailEntity);
						}
						HomeworkDetailEntity homeworkDetailEntity = new HomeworkDetailEntity();
						homeworkDetailEntity.setCreateId(createId);
						homeworkDetailEntity.setCreateDate(createDate);
						homeworkDetailEntity.setHomeworkId(homeworkId);
						homeworkDetailEntity.setHomeworkType("4");
						homeworkDetailEntity.setSectionDetailId(Long.parseLong(sec));
						homeworkDetailEntity.setDialogName("T");
						homeworkDetailDao.updateHomeworkDetailEntityDialog(homeworkDetailEntity);
					}
				} else {
					for (String sec : sections) {
						HomeworkDetailEntity homeworkDetailEntity = new HomeworkDetailEntity();
						homeworkDetailEntity.setCreateId(createId);
						homeworkDetailEntity.setCreateDate(createDate);
						homeworkDetailEntity.setHomeworkId(homeworkId);
						homeworkDetailEntity.setHomeworkType(homeworkBean.getType() + "");
						homeworkDetailEntity.setSectionDetailId(Long.parseLong(sec));
						homeworkDetailEntities.add(homeworkDetailEntity);
					}
				}
			}
			if (null != homeworkDetailEntities && homeworkDetailEntities.size() > 0) {
				homeworkDetailDao.inserHomeworkDetailBatch(homeworkDetailEntities);
			}
			// 添加一个定时化任务到指定的时间点后 执行该操作
			String cron = QuartzUtil.cron(endTime);
			try {
				QuartzUtil.addHomeworkJob(scheduler, "com.xuanli.oepcms.quartz.job.HomeWorkJob", "homeworkReport_" + homeworkId + "_" + UUID.randomUUID().toString(), cron,
						homeworkId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("布置作业定时化任务失败.出现错误.", e);
			}
		}

	}

	/**
	 * @Description: TODO 做家庭作业
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月19日 上午9:41:12
	 */
	public RestResult<Map<String, Object>> doHomeWork(Long studentId, Long sectionId, Long homeworkId, String file, String text, HttpServletRequest request) {

		int timeOutCount = homeworkDao.getTimeOutCount(homeworkId);
		if (timeOutCount == 0) {
			return failed(ExceptionCode.HOME_WORK_TIME_OUT, "现已超出作业提交时限,无法提交!");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		HomeworkStudentScoreEntity scoreEntity = new HomeworkStudentScoreEntity();
		scoreEntity.setAudioPath(file);
		scoreEntity.setEnableFlag("F");
		scoreEntity.setCreateId(studentId);
		scoreEntity.setStudentId(studentId);
		scoreEntity.setSectionId(sectionId);
		scoreEntity.setHomeworkId(homeworkId);
		scoreEntity.setUpdateDate(new Date());
		scoreEntity.setText(text);
		// 如果作业已做过，更新
		List<HomeworkStudentScoreEntity> homeworkStudentScore = homeworkStudentScoreDao.selectHomeworkStudentScore(scoreEntity);
		if (null != homeworkStudentScore && homeworkStudentScore.size() > 0) {
			homeworkStudentScoreDao.updateHomeworkStudentScore(scoreEntity);
		} else {
			homeworkStudentScoreDao.insertHomeworkStudentScoreEntity(scoreEntity);
		}
		// 开始调用SDK计算分数
		// ----获取题目的详细信息
		HomeworkScoreBean homeworkScoreBean = new HomeworkScoreBean();
		homeworkScoreBean.setHomeworkId(homeworkId);
		homeworkScoreBean.setStudentId(studentId);
		homeworkScoreBean.setSectionId(sectionId);
		List<HomeworkScoreBean> homeworkScoreBeans = homeworkStudentScoreDao.getStudentHomework1(homeworkScoreBean);
		// 开始评分
		for (HomeworkScoreBean result : homeworkScoreBeans) {
			// 如果题型是听写的那么就不用让sdk评分了
			HomeworkStudentScoreEntity homeworkStudentScoreEntity = new HomeworkStudentScoreEntity();
			homeworkStudentScoreEntity.setId(result.getId());
			if (null != result.getHomeworkType() && result.getHomeworkType().intValue() == 5) {
				if (result.getStanderText().trim().equalsIgnoreCase(result.getStudentText())) {
					homeworkStudentScoreEntity.setScore(new Double(100));
				} else {
					homeworkStudentScoreEntity.setScore(new Double(0));
				}
			} else {
				String json = yunZhiSDK.generatorStudentScore(result);
				if (null == json || json.trim().equals("")) {
					System.out.println(result.getId() + "---出现问题,不能计算");
				} else {
					YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
					System.out.println(JSON.toJSONString(yunZhiBean));
					// 开始分析作业分数并且更新到数据库中
					// 设置分数
					homeworkStudentScoreEntity.setScore(yunZhiBean.getScore());
					if (null != result.getHomeworkType() && result.getHomeworkType().intValue() != 1) {
						// 这里有句子的流畅度完整度流利度
						List<YunZhiline> yunZhilines = yunZhiBean.getLines();
						if (null != yunZhilines && yunZhilines.size() > 0) {
							YunZhiline yunZhiline = yunZhilines.get(0);
							homeworkStudentScoreEntity.setFluency(yunZhiline.getFluency());
							homeworkStudentScoreEntity.setIntegrity(yunZhiline.getIntegrity());
							homeworkStudentScoreEntity.setPronunciation(yunZhiline.getPronunciation());
							// 删除word评分
							HomeworkStudentScoreWordEntity homeworkStudentScoreWordEntity1 = new HomeworkStudentScoreWordEntity();
							homeworkStudentScoreWordEntity1.setHomeworkId(homeworkId);
							homeworkStudentScoreWordEntity1.setHomeworkDetailId(result.getSectionId());
							homeworkStudentScoreWordEntity1.setStudentId(studentId);
							homeworkStudentScoreWordEntityDao.deleteHomeworkStudentScoreWord(homeworkStudentScoreWordEntity1);
							for (YunZhiline line : yunZhilines) {
								List<YunZhiWords> yunZhiSubWords = line.getWords();
								List<HomeworkStudentScoreWordEntity> homeworkStudentScoreWordEntities = new ArrayList<HomeworkStudentScoreWordEntity>();
								for (YunZhiWords word : yunZhiSubWords) {
									int type = word.getType();
									String text1 = word.getText();
									double score = word.getScore();
									HomeworkStudentScoreWordEntity homeworkStudentScoreWordEntity = new HomeworkStudentScoreWordEntity();
									homeworkStudentScoreWordEntity.setHomeworkId(homeworkId);
									homeworkStudentScoreWordEntity.setHomeworkDetailId(result.getSectionId());
									homeworkStudentScoreWordEntity.setStudentId(studentId);
									homeworkStudentScoreWordEntity.setType(type + "");
									homeworkStudentScoreWordEntity.setText(text1);
									double sc = score * 10;
									sc = new BigDecimal(sc).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
									homeworkStudentScoreWordEntity.setScore(sc);
									homeworkStudentScoreWordEntities.add(homeworkStudentScoreWordEntity);
								}
								homeworkStudentScoreWordEntityDao.insertHomeworkStudentScoreWordEntityBatch(homeworkStudentScoreWordEntities);
							}
						}
					} else {
						// 删除音标评分
						HomeworkStudentScoreSymbolEntity homeworkStudentScoreSymbolEntity1 = new HomeworkStudentScoreSymbolEntity();
						homeworkStudentScoreSymbolEntity1.setHomeworkId(homeworkId);
						homeworkStudentScoreSymbolEntity1.setHomeworkDetailId(result.getSectionId());
						homeworkStudentScoreSymbolEntity1.setStudentId(studentId);
						homeworkStudentScoreSymbolEntityDao.deleteHomeworkStudentScoreSymbol(homeworkStudentScoreSymbolEntity1);
						// 这里有音标的东西
						List<YunZhiline> yunZhilines = yunZhiBean.getLines();
						if (null != yunZhilines && yunZhilines.size() > 0) {
							YunZhiline yunZhiline = yunZhilines.get(0);
							List<YunZhiWords> yunZhiWords = yunZhiline.getWords();
							for (YunZhiWords word : yunZhiWords) {
								// 正常读音
								if (word.getType() == 2) {
									List<YunZhiSubWords> yunZhiSubWords = word.getSubwords();
									List<HomeworkStudentScoreSymbolEntity> homeworkStudentScoreSymbolEntities = new ArrayList<HomeworkStudentScoreSymbolEntity>();
									for (YunZhiSubWords subWord : yunZhiSubWords) {
										HomeworkStudentScoreSymbolEntity homeworkStudentScoreSymbolEntity = new HomeworkStudentScoreSymbolEntity();
										homeworkStudentScoreSymbolEntity.setHomeworkId(homeworkId);
										homeworkStudentScoreSymbolEntity.setHomeworkDetailId(result.getSectionId());
										homeworkStudentScoreSymbolEntity.setStudentId(studentId);
										if (subWord.getSubtext() != null) {
											if (subWord.getSubtext().trim().equals("") || subWord.getSubtext().trim().equals("'") || subWord.getSubtext().trim().equals("ˌ")) {
											} else {
												homeworkStudentScoreSymbolEntity.setSymbol(subWord.getSubtext());
												double sc = subWord.getScore() * 10;
												sc = new BigDecimal(sc).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
												homeworkStudentScoreSymbolEntity.setScore(sc);
												homeworkStudentScoreSymbolEntities.add(homeworkStudentScoreSymbolEntity);
											}
										}
									}
									homeworkStudentScoreSymbolEntityDao.insertHomeworkStudentScoreSymbolEntityBatch(homeworkStudentScoreSymbolEntities);
								}
							}
						}
					}

				}
			}
			homeworkStudentScoreEntity.setEnableFlag("F");
			homeworkStudentScoreEntity.setCreateDate(new Date());
			homeworkStudentScoreEntity.setCreateId(studentId);
			homeworkStudentScoreEntity.setUpdateDate(new Date());
			homeworkStudentScoreEntity.setUpdateId(studentId);
			homeworkStudentScoreDao.updateHomeworkStudentScoreEntity(homeworkStudentScoreEntity);
			HomeworkStudentScoreWordEntity homeworkStudentScoreWordEntity = new HomeworkStudentScoreWordEntity();
			homeworkStudentScoreWordEntity.setStudentId(studentId);
			homeworkStudentScoreWordEntity.setHomeworkDetailId(sectionId);
			homeworkStudentScoreWordEntity.setHomeworkId(homeworkId);
			List<HomeworkStudentScoreWordEntity> homeworkStudentScoreWordEntities = homeworkStudentScoreWordEntityDao.getHomeworkStudentScoreWord(homeworkStudentScoreWordEntity);
			// 返回句子等信息
			map.put("homeworkStudentScoreWordEntities", homeworkStudentScoreWordEntities);
			// 返回分数等信息
			map.put("homeworkStudentScoreEntity", homeworkStudentScoreEntity);
			return ok(map);
		}
		return failed(ExceptionCode.UNKNOW_CODE, "未知错误.");
	}

	public void test(long homeworkId, long studentId) {
		// 开始调用SDK计算分数
		// ----获取题目的详细信息
		HomeworkScoreBean homeworkScoreBean = new HomeworkScoreBean();
		homeworkScoreBean.setHomeworkId(homeworkId);
		homeworkScoreBean.setStudentId(studentId);
		List<HomeworkScoreBean> homeworkScoreBeans = homeworkStudentScoreDao.getStudentHomework(homeworkScoreBean);
		// 开始评分
		for (HomeworkScoreBean result : homeworkScoreBeans) {
			// 如果题型是听写的那么就不用让sdk评分了
			HomeworkStudentScoreEntity homeworkStudentScoreEntity = new HomeworkStudentScoreEntity();
			homeworkStudentScoreEntity.setId(result.getId());
			if (null != result.getHomeworkType() && result.getHomeworkType().intValue() == 5) {
				if (result.getStanderText().trim().equalsIgnoreCase(result.getStudentText())) {
					homeworkStudentScoreEntity.setScore(new Double(100));
				} else {
					homeworkStudentScoreEntity.setScore(new Double(0));
				}
			} else {
				String json = yunZhiSDK.generatorStudentScore(result);
				if (null == json || json.trim().equals("")) {
					System.out.println(result.getId() + "---出现问题,不能计算");
				} else {
					YunZhiBean yunZhiBean = JSONObject.parseObject(json, YunZhiBean.class);
					System.out.println(JSON.toJSONString(yunZhiBean));
					// 开始分析作业分数并且更新到数据库中
					// 设置分数
					homeworkStudentScoreEntity.setScore(yunZhiBean.getScore());
					if (null != result.getHomeworkType() && result.getHomeworkType().intValue() != 1) {
						// 这里有句子的流畅度完整度流利度
						List<YunZhiline> yunZhilines = yunZhiBean.getLines();
						if (null != yunZhilines && yunZhilines.size() > 0) {
							YunZhiline yunZhiline = yunZhilines.get(0);
							homeworkStudentScoreEntity.setFluency(yunZhiline.getFluency());
							homeworkStudentScoreEntity.setIntegrity(yunZhiline.getIntegrity());
							homeworkStudentScoreEntity.setPronunciation(yunZhiline.getPronunciation());
							// 删除word评分
							HomeworkStudentScoreWordEntity homeworkStudentScoreWordEntity1 = new HomeworkStudentScoreWordEntity();
							homeworkStudentScoreWordEntity1.setHomeworkId(homeworkId);
							homeworkStudentScoreWordEntity1.setHomeworkDetailId(result.getSectionId());
							homeworkStudentScoreWordEntity1.setStudentId(studentId);
							homeworkStudentScoreWordEntityDao.deleteHomeworkStudentScoreWord(homeworkStudentScoreWordEntity1);
							for (YunZhiline line : yunZhilines) {
								List<YunZhiWords> yunZhiSubWords = line.getWords();
								for (YunZhiWords word : yunZhiSubWords) {
									int type = word.getType();
									String text1 = word.getText();
									double score = word.getScore() * 10.0;
									HomeworkStudentScoreWordEntity homeworkStudentScoreWordEntity = new HomeworkStudentScoreWordEntity();
									homeworkStudentScoreWordEntity.setHomeworkId(homeworkId);
									homeworkStudentScoreWordEntity.setHomeworkDetailId(result.getSectionId());
									homeworkStudentScoreWordEntity.setStudentId(studentId);
									homeworkStudentScoreWordEntity.setType(type + "");
									homeworkStudentScoreWordEntity.setText(text1);
									homeworkStudentScoreWordEntity.setScore(score);
									homeworkStudentScoreWordEntityDao.insertHomeworkStudentScoreWordEntity(homeworkStudentScoreWordEntity);
								}
							}
						}
					} else {
						// 删除音标评分
						HomeworkStudentScoreSymbolEntity homeworkStudentScoreSymbolEntity1 = new HomeworkStudentScoreSymbolEntity();
						homeworkStudentScoreSymbolEntity1.setHomeworkId(homeworkId);
						homeworkStudentScoreSymbolEntity1.setHomeworkDetailId(result.getSectionId());
						homeworkStudentScoreSymbolEntity1.setStudentId(studentId);
						homeworkStudentScoreSymbolEntityDao.deleteHomeworkStudentScoreSymbol(homeworkStudentScoreSymbolEntity1);
						// 这里有音标的东西
						List<YunZhiline> yunZhilines = yunZhiBean.getLines();
						if (null != yunZhilines && yunZhilines.size() > 0) {
							YunZhiline yunZhiline = yunZhilines.get(0);
							List<YunZhiWords> yunZhiWords = yunZhiline.getWords();
							for (YunZhiWords word : yunZhiWords) {
								// 正常读音
								if (word.getType() == 2) {
									List<YunZhiSubWords> yunZhiSubWords = word.getSubwords();
									for (YunZhiSubWords subWord : yunZhiSubWords) {
										HomeworkStudentScoreSymbolEntity homeworkStudentScoreSymbolEntity = new HomeworkStudentScoreSymbolEntity();
										homeworkStudentScoreSymbolEntity.setHomeworkId(homeworkId);
										homeworkStudentScoreSymbolEntity.setHomeworkDetailId(result.getSectionId());
										homeworkStudentScoreSymbolEntity.setStudentId(studentId);
										if (subWord.getSubtext() != null) {
											if (subWord.getSubtext().trim().equals("") || subWord.getSubtext().trim().equals("'") || subWord.getSubtext().trim().equals("ˌ")) {
											} else {
												homeworkStudentScoreSymbolEntity.setSymbol(subWord.getSubtext());
												double sc = subWord.getScore() * 10;
												sc = new BigDecimal(sc).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
												homeworkStudentScoreSymbolEntity.setScore(sc);
												homeworkStudentScoreSymbolEntityDao.insertHomeworkStudentScoreSymbolEntity(homeworkStudentScoreSymbolEntity);
											}
										}
									}
								}
							}
						}
					}

				}
			}
			homeworkStudentScoreDao.updateHomeworkStudentScoreEntity(homeworkStudentScoreEntity);
		}
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 上午10:46:49
	 */
	public List<HomeworkStudentScoreEntity> reportStudentScore(long homeworkId) {
		return homeworkStudentScoreDao.reportStudentScore(homeworkId);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 上午10:48:55
	 */
	public void updateHomeworkStudentScoreEntityBatch(HomeworkStudentEntity homeworkStudentEntity) {
		homeworkStudentDao.updateHomeworkStudentEntityBatch(homeworkStudentEntity);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 上午11:12:47
	 */
	public int reportHomeworkDetail(long homeworkId) {
		return homeworkDetailDao.reportHomeworkDetail(homeworkId);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月23日 下午2:45:54
	 */
	public List<HomeworkSymbolScore> getHomeworkSymbolScore(long homeworkId) {
		return homeworkStudentScoreSymbolEntityDao.getHomeworkSymbolScore(homeworkId);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月23日 下午2:46:21
	 */
	public List<HomeworkPicScoreBean> getHomeworkPicScore(long homeworkId) {
		return homeworkStudentScoreDao.getHomeworkPickScore(homeworkId);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月23日 下午2:52:15
	 */
	public List<HomeworkPicScoreBean> getHomeworkPicTypeScore(long homeworkId) {
		return homeworkStudentScoreDao.getHomeworkPicTypeScore(homeworkId);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月23日 下午2:57:29
	 */
	public List<HomeworkStudentScoreEntity> getHomeworkStudentScoreByHomeworkId(long homeworkId) {
		HomeworkStudentEntity homeworkStudentEntity = new HomeworkStudentEntity();
		homeworkStudentEntity.setHomeworkId(homeworkId);
		return homeworkStudentDao.selectHomeworkStudentEntity(homeworkStudentEntity);
	}

	/**
	 * 老师写评语
	 * 
	 * @return
	 */
	public String updateHomewordStudentEntityRemark(String userIds, Long homeworkId, String remark) {
		HomeworkStudentEntity homeworkStudentEntity1 = new HomeworkStudentEntity();
		homeworkStudentEntity1.setHomeworkId(homeworkId);
		homeworkStudentEntity1.setRemark(remark);
		// 1.验证参数的有效性
		if (StringUtil.isEmpty(userIds)) {
			return "1";
		}
		// 2.执行更新操作
		String[] userId = userIds.split(",");
		int rows = homeworkStudentDao.updateHomewordStudentEntityRemark(userId, homeworkId, remark);

		// 3.验证更新结果(成功,失败)
		if (rows <= 0) {
			return "2";
		} else {
			return "0";
		}
	}

	/**
	 * Title: getStudentHomeworkDetail Description: 查看学生作业详情
	 * 
	 * @date 2018年2月10日 下午3:10:27
	 * @param homeworkId
	 * @param studentId
	 * @param homeworkType
	 * @return
	 */
	public List<HomeworkScoreBean> getStudentHomework(Long homeworkId, Long studentId, Integer homeworkType) {
		List<HomeworkScoreBean> homeworkScoreBeans = homeworkStudentScoreDao.getStudentHomework(homeworkId, studentId, homeworkType);
		return homeworkScoreBeans;
	}

	/**
	 * Title: selectStudentDetail Description: 作业报告,学生详情
	 * 
	 * @date 2018年2月10日 下午3:10:39
	 * @param homeworkId
	 * @return
	 */
	public List<HomeworkStudentEntity> selectStudentEntity(Long homeworkId) {
		HomeworkStudentEntity homeworkStudentEntity = new HomeworkStudentEntity();
		homeworkStudentEntity.setHomeworkId(homeworkId);
		return homeworkStudentDao.selectStudentEntity(homeworkStudentEntity);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月6日 上午9:32:14
	 */
	public void findHomeworkPage(HomeworkEntity homeworkEntity, PageBean pageBean) {
		int total = homeworkDao.findHomeworkPageTotal(homeworkEntity);
		pageBean.setTotal(total);
		homeworkEntity.setStart(pageBean.getRowFrom());
		homeworkEntity.setEnd(pageBean.getPageSize());
		List<HomeworkEntity> homeworkEntities = homeworkDao.findHomeworkPage(homeworkEntity);
		pageBean.setRows(homeworkEntities);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月9日 下午3:54:08
	 */
	public RestResult<Map<String, Object>> getHomeworkDetail(Long homeworkId, Long studentId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 查询作业信息信息
		HomeworkEntity homeworkEntity = homeworkDao.getById(homeworkId);
		// 获取作业详细信息
		HomeworkScoreBean homeworkScoreBean = new HomeworkScoreBean();
		homeworkScoreBean.setHomeworkId(homeworkId);
		homeworkScoreBean.setStudentId(studentId);
		List<HomeworkScoreBean> homeworkDetails = homeworkDetailDao.getHomeworkDetail(homeworkScoreBean);

		HomeworkStudentScoreWordEntity homeworkStudentScoreWordEntity = new HomeworkStudentScoreWordEntity();
		homeworkStudentScoreWordEntity.setStudentId(studentId);
		homeworkStudentScoreWordEntity.setHomeworkId(homeworkId);
		List<HomeworkStudentScoreWordEntity> homeworkStudentScoreWordEntities = homeworkStudentScoreWordEntityDao.getHomeworkStudentScoreWord(homeworkStudentScoreWordEntity);
		for (HomeworkScoreBean hsb : homeworkDetails) {
			List<HomeworkStudentScoreWordEntity> tempList = new ArrayList<HomeworkStudentScoreWordEntity>();
			for (HomeworkStudentScoreWordEntity hsswe : homeworkStudentScoreWordEntities) {
				if (hsswe.getHomeworkDetailId().longValue() == hsb.getSectionDetailId().longValue() && hsswe.getStudentId().longValue() == studentId
						&& hsswe.getHomeworkId().longValue() == homeworkId) {
					tempList.add(hsswe);
				}
			}
			hsb.setHomeworkStudentScoreWordEntities(tempList);
		}
		resultMap.put("homeworkEntity", homeworkEntity);
		resultMap.put("homeworkDetails", homeworkDetails);
		return ok(resultMap);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月26日 下午8:45:12
	 */
	public void getStudentHomeWorkList(Map<String, Object> requestMap, PageBean pageBean) {
		int total = homeworkDao.findStudentHomeworkByPageTotal(requestMap);
		pageBean.setTotal(total);
		requestMap.put("start", pageBean.getRowFrom());
		requestMap.put("end", pageBean.getPageSize());
		List<Map<String, Object>> resultMap = homeworkDao.findStudentHomeworkByPage(requestMap);
		pageBean.setRows(resultMap);
	}

	/**
	 * @Description: TODO 获取学生家庭作业信息
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月27日 下午12:29:44
	 */
	public Map<String, Object> getStudentHomeworkInfo(Map<String, Object> requestMap) {
		return homeworkStudentDao.getStudentHomeworkInfo(requestMap);
	}

	/**
	 * @Description: TODO 获取学生家庭作业详细信息
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月27日 下午12:33:17
	 */
	public List<Map<String, Object>> getStudentHomeworkDetail(Map<String, Object> requestMap) {
		return homeworkStudentDao.getStudentHomeworkDetail(requestMap);
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月28日 下午6:05:40
	 */
	public List<Map<String, Object>> getStudentAvgScore(Map<String, Object> requestMap) {
		return homeworkStudentDao.getStudentAvgScore(requestMap);
	}

	/**
	 * @Description: TODO
	 * @CreateName: codelion[QiaoYu]
	 * @CreateDate: 2018年3月4日 下午4:21:33
	 */
	public RestResult<String> submitHomework(Long studentId, Long homeworkId) {
		int timeOutCount = homeworkDao.getTimeOutCount(homeworkId);
		if (timeOutCount == 0) {
			return failed(ExceptionCode.HOME_WORK_TIME_OUT, "现已超出作业提交时限,无法提交!");
		}
		HomeworkStudentScoreEntity homeworkStudentScoreEntity = new HomeworkStudentScoreEntity();
		homeworkStudentScoreEntity.setEnableFlag("T");
		homeworkStudentScoreEntity.setCreateId(studentId);
		homeworkStudentScoreEntity.setHomeworkId(homeworkId);
		homeworkStudentScoreEntity.setStudentId(studentId);
		homeworkStudentScoreDao.updateHomeworkStudentScore(homeworkStudentScoreEntity);

		List<HomeworkStudentScoreEntity> studentScoreEntities = homeworkStudentScoreDao.reportStudentScoreByStudent(homeworkId, studentId);
		int subjectSize = reportHomeworkDetail(homeworkId);// 一共十个题
		for (HomeworkStudentScoreEntity hsse : studentScoreEntities) {
			Long sdId = hsse.getStudentId();
			Double score = hsse.getScore();
			double studentScore = score / subjectSize;
			// 更新数据库
			HomeworkStudentEntity studentEntity = new HomeworkStudentEntity();
			studentEntity.setHomeworkId(homeworkId);
			studentEntity.setStudentId(sdId);
			// updateID在查询中特殊使用了一下,因为没有预留字段
			studentEntity.setWorkTime(hsse.getUpdateId());
			studentEntity.setUpdateDate(hsse.getUpdateDate());
			BigDecimal b = new BigDecimal(studentScore);
			studentScore = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			studentEntity.setScore(studentScore);
			studentEntity.setWorkComplate("T");
			// 保存本次学生的分数
			updateHomeworkStudentScoreEntityBatch(studentEntity);
		}

		return okNoResult("");
	}

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年3月28日 下午3:43:45
	 */
	public String getHomeworkStudent(Long homeworkId) {
		return homeworkDao.getHomeworkStudent(homeworkId);
	}
}
