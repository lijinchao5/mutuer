package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.QuestionSubjectEntity;
@Mapper
public interface QuestionSubjectEntityMapper {
	/**
	 * Title: deleteQuestionSubjectEntity 
	 * Description:  删除试题库大题
	 * @date 2018年3月15日 上午10:16:31
	 * @param id
	 * @return
	 */
    int deleteQuestionSubjectEntity(Long id);
    /**
     * Title: insertQuestionSubjectEntity 
     * Description:  存入试题库大题
     * @date 2018年3月15日 上午10:16:33
     * @param record
     * @return
     */
    int insertQuestionSubjectEntity(QuestionSubjectEntity record);
    /**
     * Title: selectById 
     * Description:  查询试题库大题
     * @date 2018年3月15日 上午10:16:35
     * @param id
     * @return
     */
    QuestionSubjectEntity selectById(Long id);
    /**
     * Title: updateQuestionSubjectEntity 
     * Description:  更新试题库大题
     * @date 2018年3月15日 上午10:16:37
     * @param record
     * @return
     */
    int updateQuestionSubjectEntity(QuestionSubjectEntity record);
	/**
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月15日 上午10:39:57
	 */
	int findQuestionDetailByPageCount(Map<String, Object> requestMap);
	/**
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月15日 上午10:40:02
	 */
	List<Map<String, Object>> findQuestionDetailByPage(Map<String, Object> requestMap);
	/**
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月15日 上午10:49:21
	 */
	void updateQuestionSubjectUsedCount(Long id);
	
	QuestionSubjectEntity selectByCmsId(Long cmsId);
	/**Title: updateSyncQuestionSubjectEntity 
	 * Description:  
	 * @date 2018年3月15日 下午12:09:36
	 * @param questionSubjectEntity  
	 */
	int updateSyncQuestionSubjectEntity(QuestionSubjectEntity questionSubjectEntity);
}