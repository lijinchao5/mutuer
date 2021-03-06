package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.QuestionOptionEntity;
@Mapper
public interface QuestionOptionEntityMapper {
	/**
	 * Title: deleteQuestionOptionEntity 
	 * Description:  删除试题小题答案
	 * @date 2018年3月15日 上午10:13:07
	 * @param id
	 * @return
	 */
    int deleteQuestionOptionEntity(Long id);
    /**
     * Title: insertQuestionOptionEntity 
     * Description:  存入试题小题答案
     * @date 2018年3月15日 上午10:13:10
     * @param record
     * @return
     */
    int insertQuestionOptionEntity(QuestionOptionEntity record);
    /**
     * Title: selectById 
     * Description:  查询试题小题答案
     * @date 2018年3月15日 上午10:13:12
     * @param id
     * @return
     */
    QuestionOptionEntity selectById(Long id);
    /**
     * Title: updateQuestionOptionEntity 
     * Description:  更新试题小题答案
     * @date 2018年3月15日 上午10:13:15
     * @param record
     * @return
     */
    int updateQuestionOptionEntity(QuestionOptionEntity record);
    
	/**
	 * Title: selectByCmsId 
	 * Description:  
	 * @date 2018年3月15日 下午12:06:05
	 * @param id
	 * @return
	 */
	QuestionOptionEntity selectByCmsId(Long cmsId);
	/**Title: updateSyncQuestionOptionEntity 
	 * Description:  
	 * @date 2018年3月15日 下午12:38:11
	 * @param questionOptionEntity  
	 */
	int updateSyncQuestionOptionEntity(QuestionOptionEntity questionOptionEntity);
	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年4月20日 下午5:08:27
	 */
	List<QuestionOptionEntity> getSubjectOptionByDetailId(Long id);
}