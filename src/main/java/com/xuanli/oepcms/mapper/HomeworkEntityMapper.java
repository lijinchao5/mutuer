package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.HomeworkEntity;
@Mapper
public interface HomeworkEntityMapper {
	/**删除方法，根据id删除*/
    int deleteHomeworkEntity(Long id);
    /**增加方法*/
    int insertHomeworkEntity(HomeworkEntity record);
    /**查询方法,根据id查询*/
    HomeworkEntity selectById(Long id);
    /**更新方法*/
    int updateHomeworkEntity(HomeworkEntity record);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月6日 上午9:52:05
	 */
	int findHomeworkPageTotal(HomeworkEntity homeworkEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月6日 上午9:52:09
	 */
	List<HomeworkEntity> findHomeworkPage(HomeworkEntity homeworkEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月13日 下午4:00:20
	 */
	HomeworkEntity getById(Long homeworkId);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月26日 下午8:46:54
	 */
	int findStudentHomeworkByPageTotal(Map<String, Object> requestMap);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月26日 下午8:46:58
	 */
	List<Map<String, Object>> findStudentHomeworkByPage(Map<String, Object> requestMap);
	/**
	 * Title: getTimeOut 
	 * Description:  作业超时
	 * @date 2018年3月5日 下午2:41:13
	 * @return
	 */
	int getTimeOutCount(Long homeworkId);
}