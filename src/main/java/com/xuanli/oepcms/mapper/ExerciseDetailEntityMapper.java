package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ExerciseDetailEntity;
import com.xuanli.oepcms.entity.ExerciseDetailWordEntity;
@Mapper
public interface ExerciseDetailEntityMapper {
	/**
	 * Title: deleteExerciseDetailEntity 
	 * Description:  
	 * @date 2018年3月9日 下午5:57:52
	 * @param id
	 * @return
	 */
    int deleteExerciseDetailEntity(Long id);
    /**
     * Title: insertExerciseDetailEntity 
     * Description:  
     * @date 2018年3月9日 下午5:57:55
     * @param record
     * @return
     */
    int insertExerciseDetailEntity(ExerciseDetailEntity record);
    /**
     * Title: selectById 
     * Description:  
     * @date 2018年3月9日 下午5:57:57
     * @param id
     * @return
     */
    ExerciseDetailEntity selectById(Long id);
    /**
     * Title: updateExerciseDetailEntity 
     * Description:  
     * @date 2018年3月9日 下午5:58:00
     * @param record
     * @return
     */
    int updateExerciseDetailEntity(ExerciseDetailEntity record);
    /**
     * Title: selectExerciseDetailEntity 
     * Description:  
     * @date 2018年3月13日 下午3:41:00
     * @param exerciseDetailEntity
     * @return
     */
    ExerciseDetailEntity selectExerciseDetailEntity(ExerciseDetailEntity exerciseDetailEntity);
	/**Title: getExerciseDetailScore 
	 * Description:  
	 * @param articleId 
	 * @param studentId 
	 * @date 2018年3月13日 下午6:07:35
	 * @return  
	 */
	List<ExerciseDetailEntity> getExerciseDetailScore(Long studentId, Long articleId);
	/**Title: getExerciseDetailScore 
	 * Description:  
	 * @date 2018年3月13日 下午6:08:49
	 * @param exerciseDetailEntity
	 * @return  
	 */
	ExerciseDetailEntity getExerciseDetailScore(ExerciseDetailEntity exerciseDetailEntity);
	
}