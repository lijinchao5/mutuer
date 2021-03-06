package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuanli.oepcms.entity.QuestionSubjectDetailEntity;
@Mapper
public interface QuestionSubjectDetailEntityMapper {
	/**
	 * Title: deleteQuestionSubjectDetailEntity 
	 * Description:  删除试题库小题
	 * @date 2018年3月15日 上午10:14:48
	 * @param id
	 * @return
	 */
    int deleteQuestionSubjectDetailEntity(Long id);
    /**
     * Title: insertQuestionSubjectDetailEntity 
     * Description:  存入试题库小题
     * @date 2018年3月15日 上午10:14:50
     * @param record
     * @return
     */
    int insertQuestionSubjectDetailEntity(QuestionSubjectDetailEntity record);
    /**
     * Title: selectById 
     * Description:  查询试题库小题
     * @date 2018年3月15日 上午10:14:52
     * @param id
     * @return
     */
    QuestionSubjectDetailEntity selectById(Long id);
    /**
     * Title: updateByPrimaryKey 
     * Description:  更新试题库小题
     * @date 2018年3月15日 上午10:14:54
     * @param record
     * @return
     */
    int updateQuestionSubjectDetailEntity(QuestionSubjectDetailEntity record);
    /**
     * Title: selectByCmsId 
     * Description:  
     * @date 2018年3月15日 下午12:06:38
     * @param cmsId
     * @return
     */
    QuestionSubjectDetailEntity selectByCmsId(Long cmsId);
	/**Title: updateSyncQuestionSubjectDetailEntity 
	 * Description:  
	 * @date 2018年3月15日 下午12:28:27
	 * @param questionSubjectDetailEntity  
	 */
	int updateSyncQuestionSubjectDetailEntity(QuestionSubjectDetailEntity questionSubjectDetailEntity);
	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月20日 下午5:06:34
	 */
	List<QuestionSubjectDetailEntity> findSubjectDetailBySubjectId(String subject);
	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月4日 下午12:01:02
	 */
	List<Map<String, Object>> getSubjectDetailBySubjectId(@Param("ids")List<Long> ids);
}