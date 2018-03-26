package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuanli.oepcms.entity.HomeworkStudentEntity;
import com.xuanli.oepcms.entity.HomeworkStudentScoreEntity;
@Mapper
public interface HomeworkStudentEntityMapper {
	/**删除方法，根据id删除*/
    int deleteHomeworkStudentEntity(Long id);
    /**增加方法*/
    int insertHomeworkStudentEntity(HomeworkStudentEntity record);
    /**查询方法,根据id查询*/
    HomeworkStudentEntity selectById(Long id);
    /**更新方法*/
    int updateHomeworkStudentEntity(HomeworkStudentEntity record);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 下午4:09:46
	 */
	void insertHomeworkStudentEntityBatch(List<HomeworkStudentEntity> homeworkStudentEntities);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午11:06:52
	 */
	void updateHomeworkStudentEntityBatch(HomeworkStudentEntity homeworkStudentEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月23日 下午3:13:07
	 */
	List<HomeworkStudentScoreEntity> selectHomeworkStudentEntity(HomeworkStudentEntity homeworkStudentEntity);
	
	/**
	 * Title: updateHomewordStudentEntityRemark 
	 * Description:  老师写评语
	 * @date 2018年2月10日 下午3:08:16
	 * @param userIds
	 * @param homeworkId
	 * @param remark
	 * @return
	 */
	int updateHomewordStudentEntityRemark(@Param("userIds")String[] userIds,@Param("homeworkId")Long homeworkId,@Param("remark")String remark);
	
	/**Title: SelectStudentDetail 
	 * Description:  作业报告查看学生详情
	 * @date 2018年2月10日 下午3:04:29
	 * @return  
	 */
	List<HomeworkStudentEntity> selectStudentEntity(HomeworkStudentEntity homeworkStudentEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月27日 下午12:30:31
	 */
	Map<String, Object> getStudentHomeworkInfo(Map<String, Object> requestMap);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月27日 下午12:33:47
	 */
	List<Map<String, Object>> getStudentHomeworkDetail(Map<String, Object> requestMap);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月28日 下午6:06:02
	 */
	List<Map<String, Object>> getStudentAvgScore(Map<String, Object> requestMap);
	
}