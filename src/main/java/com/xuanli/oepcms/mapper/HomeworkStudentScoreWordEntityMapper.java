package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.HomeworkStudentScoreWordEntity;
@Mapper
public interface HomeworkStudentScoreWordEntityMapper {
	/**删除学生作业单词分数方法*/
    int deleteHomeworkStudentScoreWordEntity(Long id);
    /**保存学生作业单词分数方法*/
    int insertHomeworkStudentScoreWordEntity(HomeworkStudentScoreWordEntity record);
    /**查询学生作业单词分数方法*/
    HomeworkStudentScoreWordEntity selectById(Long id);
    /**更新学生作业单词分数方法*/
    int updateHomeworkStudentScoreWordEntity(HomeworkStudentScoreWordEntity record);
    /**
     * Title: deleteHomeworkStudentScoreWord 
     * Description:  再次评分之前删除本次作业单词分数
     * @date 2018年2月28日 下午12:23:36
     * @param homeworkId
     * @param homeworkDetailId
     * @param studentId
     * @return
     */
    int deleteHomeworkStudentScoreWord(HomeworkStudentScoreWordEntity homeworkStudentScoreWordEntity);
	/**@param homeworkStudentScoreWordEntity 
	 * @Description:  TODO
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月5日 下午4:01:36
	 */
	List<HomeworkStudentScoreWordEntity> getHomeworkStudentScoreWord(HomeworkStudentScoreWordEntity homeworkStudentScoreWordEntity);
	/**Title: insertHomeworkStudentScoreWordEntityBatch 
	 * Description:  
	 * @date 2018年3月19日 下午4:42:43
	 * @param homeworkStudentScoreWordEntities  
	 */
	int insertHomeworkStudentScoreWordEntityBatch(List<HomeworkStudentScoreWordEntity> homeworkStudentScoreWordEntities);
}