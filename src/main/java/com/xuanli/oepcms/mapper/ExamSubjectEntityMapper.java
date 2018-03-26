package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ExamSubjectEntity;
@Mapper
public interface ExamSubjectEntityMapper {
	/**
	 * Title: deleteExamSubjectEntity 
	 * Description:   删除方法
	 * @date 2018年2月6日 下午2:03:46
	 * @param id
	 * @return
	 */
    int deleteExamSubjectEntity(Long id);
    /**
     * Title: insertExamSubjectEntity 
     * Description:   添加方法
     * @date 2018年2月6日 下午2:03:56
     * @param record
     * @return
     */
    int insertExamSubjectEntity(ExamSubjectEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:04:03
     * @param id
     * @return
     */
    ExamSubjectEntity selectById(Long id);
    /**
     * Title: updateExamSubjectEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:04:10
     * @param record
     * @return
     */
    int updateExamSubjectEntity(ExamSubjectEntity record);
}