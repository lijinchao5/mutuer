package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ExerciseDetailWordEntity;

@Mapper
public interface ExerciseDetailWordEntityMapper {
	/**
	 * Title: deleteExerciseDetailWordEntity 
	 * Description:  删除方法
	 * @date 2018年3月12日 上午11:45:42
	 * @param id
	 * @return
	 */
	int deleteExerciseDetailWordEntity(Long id);

	/**
	 * Title: insertExerciseDetailWordEntity 
	 * Description:  插入方法
	 * @date 2018年3月12日 上午11:45:48
	 * @param record
	 * @return
	 */
	int insertExerciseDetailWordEntity(ExerciseDetailWordEntity record);

	/**
	 * Title: selectById 
	 * Description:  查询方法
	 * @date 2018年3月12日 上午11:46:16
	 * @param id
	 * @return
	 */
	ExerciseDetailWordEntity selectById(Long id);

	/**
	 * Title: updateExerciseDetailWordEntity 
	 * Description:  更新方法
	 * @date 2018年3月12日 上午11:46:27
	 * @param record
	 * @return
	 */
	int updateExerciseDetailWordEntity(ExerciseDetailWordEntity record);

	/**
	 * Title: deleteDetailWordEntity 
	 * Description:  
	 * @date 2018年3月16日 下午12:48:51
	 * @param exerciseDetailWordEntity
	 * @return
	 */
	int deleteDetailWordEntity(ExerciseDetailWordEntity exerciseDetailWordEntity);

	/**
	 * Title: getExerciseDetailWord 
	 * Description:  
	 * @date 2018年3月16日 下午2:25:17
	 * @param exerciseDetailWordEntity
	 * @return
	 */
	List<ExerciseDetailWordEntity> getExerciseDetailWord(ExerciseDetailWordEntity exerciseDetailWordEntity);

	/**Title: insertExerciseDetailWordEntityBatch 
	 * Description:  
	 * @date 2018年3月20日 上午11:36:26
	 * @param exerciseDetailWordEntities  
	 */
	int insertExerciseDetailWordEntityBatch(List<ExerciseDetailWordEntity> exerciseDetailWordEntities);
}