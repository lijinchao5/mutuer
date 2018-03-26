package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.controller.bean.HomeworkSymbolScore;
import com.xuanli.oepcms.entity.HomeworkStudentScoreSymbolEntity;

@Mapper
public interface HomeworkStudentScoreSymbolEntityMapper {
	/**学生作业单词跟读删除方法*/
	int deleteHomeworkStudentScoreSymbolEntity(Long id);

	/**保存方法*/
	int insertHomeworkStudentScoreSymbolEntity(HomeworkStudentScoreSymbolEntity record);

	/**查询方法*/
	HomeworkStudentScoreSymbolEntity selectById(Long id);

	/**更新方法*/
	int updateHomeworkStudentScoreSymbolEntity(HomeworkStudentScoreSymbolEntity record);

	/**
	 * @Description:  TODO 获取音素音标的平均分
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月23日 下午2:47:28
	 */
	List<HomeworkSymbolScore> getHomeworkSymbolScore(Long homeworkId);

	/**Title: deleteHomeworkStudentScoreSymbol 
	 * Description:  
	 * @date 2018年2月28日 下午12:21:48
	 * @param homeworkId
	 * @param sectionId
	 * @param studentId  
	 */
	void deleteHomeworkStudentScoreSymbol(HomeworkStudentScoreSymbolEntity homeworkStudentScoreSymbolEntity);

	/**Title: insertHomeworkStudentScoreSymbolEntityBatch 
	 * Description:  
	 * @date 2018年3月19日 下午5:05:23
	 * @param homeworkStudentScoreSymbolEntities  
	 */
	int insertHomeworkStudentScoreSymbolEntityBatch(List<HomeworkStudentScoreSymbolEntity> homeworkStudentScoreSymbolEntities);

}