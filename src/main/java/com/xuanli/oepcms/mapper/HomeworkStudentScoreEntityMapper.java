package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuanli.oepcms.controller.bean.HomeworkPicScoreBean;
import com.xuanli.oepcms.controller.bean.HomeworkScoreBean;
import com.xuanli.oepcms.entity.HomeworkStudentScoreEntity;
@Mapper
public interface HomeworkStudentScoreEntityMapper {
    int deleteHomeworkStudentScoreEntity(Long id);

    int insertHomeworkStudentScoreEntity(HomeworkStudentScoreEntity record);

    HomeworkStudentScoreEntity selectById(Long id);

    int updateHomeworkStudentScoreEntity(HomeworkStudentScoreEntity record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午10:47:32
	 */
	List<HomeworkStudentScoreEntity> reportStudentScore(long homeworkId);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 下午4:12:04
	 */
	List<HomeworkScoreBean> getStudentHomework(HomeworkScoreBean homeworkScoreBean);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月23日 下午2:48:56
	 */
	List<HomeworkPicScoreBean> getHomeworkPickScore(long homeworkId);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月23日 下午2:52:43
	 */
	List<HomeworkPicScoreBean> getHomeworkPicTypeScore(long homeworkId);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月23日 下午2:58:29
	 */
	List<HomeworkStudentScoreEntity> selectHomeworkStudentScore(HomeworkStudentScoreEntity homeworkStudentScoreEntity);
	
	List<HomeworkScoreBean> getStudentHomework(@Param("homeworkId")Long homeworkId,@Param("studentId")Long studentId, @Param("homeworkType")Integer homeworkType);

	/**Title: updateHomeworkStudentScore 
	 * Description:  
	 * @date 2018年2月28日 下午3:04:12
	 * @param scoreEntity  
	 */
	void updateHomeworkStudentScore(HomeworkStudentScoreEntity scoreEntity);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月28日 下午5:10:29
	 */
	List<HomeworkScoreBean> getStudentHomework1(HomeworkScoreBean homeworkScoreBean);
	
	/**
	 * Title: getTimeOut 
	 * Description:  
	 * @date 2018年3月5日 下午2:58:24
	 * @param homeworkId
	 * @return
	 */
	int deleteHomeworkStudentScore(Long homeworkId);

	/**@Description:  TODO
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月6日 上午11:43:06
	 */
	List<HomeworkStudentScoreEntity> reportStudentScoreByStudent(@Param("homeworkId")Long homeworkId,@Param("studentId")Long studentId);
}