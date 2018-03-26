package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuanli.oepcms.entity.ExamStudentScoreWordEntity;
@Mapper
public interface ExamStudentScoreWordEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExamStudentScoreWordEntity record);

    int insertSelective(ExamStudentScoreWordEntity record);

    ExamStudentScoreWordEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExamStudentScoreWordEntity record);

    int updateByPrimaryKey(ExamStudentScoreWordEntity record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月24日 上午11:49:27
	 */
	List<ExamStudentScoreWordEntity> getExamStudentWords(ExamStudentScoreWordEntity examStudentScoreWordEntity);

	/**Title: deleteExamStudentScoreWord 
	 * Description:  
	 * @date 2018年3月8日 下午5:02:44
	 * @param examStudentScoreWordEntity  
	 */
	void deleteExamStudentScoreWord(ExamStudentScoreWordEntity examStudentScoreWordEntity);

	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年3月16日 下午5:45:53
	 */
	List<ExamStudentScoreWordEntity> findByDetailId(@Param("allIds")List<ExamStudentScoreWordEntity> allIds,@Param("studentId") Long studentId,@Param("examId") Long examId);
}