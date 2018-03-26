package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ExerciseEntity;

@Mapper
public interface ExerciseEntityMapper {
	/**
	 * Title: deleteExerciseEntity 
	 * Description:  
	 * @date 2018年3月9日 下午5:57:14
	 * @param id
	 * @return
	 */
	int deleteExerciseEntity(Long id);

	/**
	 * Title: insertExerciseEntity 
	 * Description:  
	 * @date 2018年3月9日 下午5:57:16
	 * @param record
	 * @return
	 */
	int insertExerciseEntity(ExerciseEntity record);

	/**
	 * Title: selectById 
	 * Description:  
	 * @date 2018年3月9日 下午5:57:19
	 * @param id
	 * @return
	 */
	ExerciseEntity selectById(Long id);

	/**
	 * Title: updateExerciseEntity 
	 * Description:  
	 * @date 2018年3月9日 下午5:57:21
	 * @param record
	 * @return
	 */
	int updateExerciseEntity(ExerciseEntity record);

	/**
	 * Title: findExercisePageTotal 
	 * Description:  
	 * @date 2018年3月9日 下午5:59:45
	 * @param exerciseEntity
	 * @return
	 */
	int findExercisePageTotal(Long studentId);

	/**Title: selectExerciseEntity 
	 * Description:  
	 * @date 2018年3月13日 下午8:02:46
	 * @param exerceseEntity
	 * @return  
	 */
	ExerciseEntity selectExerciseEntity(ExerciseEntity exerceseEntity);

	/**
	 * Title: findExercisePage 
	 * Description:  
	 * @date 2018年3月19日 下午3:14:26
	 * @param exerciseEntity
	 * @return
	 */
	List<Map<String, Object>> findExercisePage(ExerciseEntity exerciseEntity);

	/**
	 * Title: getStudentExerciseResult 
	 * Description:  
	 * @date 2018年3月19日 下午4:07:59
	 * @param studentId
	 * @return
	 */
	List<Map<String, Object>> getStudentExerciseResult(Long studentId);
}