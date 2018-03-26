package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.PaperSubjectDetailEntity;
@Mapper
public interface PaperSubjectDetailEntityMapper {
	/**
	 * Title: deletePaperSubjectDetailEntity 
	 * Description:   删除方法
	 * @date 2018年2月6日 下午2:11:58
	 * @param id
	 * @return
	 */
    int deletePaperSubjectDetailEntity(Long id);
    /**
     * Title: insertPaperSubjectDetailEntity 
     * Description:   添加方法
     * @date 2018年2月6日 下午2:12:06
     * @param record
     * @return
     */
    int insertPaperSubjectDetailEntity(PaperSubjectDetailEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:12:13
     * @param id
     * @return
     */
    PaperSubjectDetailEntity selectById(Long id);
    /**
     * Title: updatePaperSubjectDetailEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:12:19
     * @param record
     * @return
     */
    int updatePaperSubjectDetailEntity(PaperSubjectDetailEntity record);
	/**@Description:  TODO
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月5日 下午2:22:53
	 */
	List<PaperSubjectDetailEntity> findSubjectDetailBySubjectId(String subject);
	
	/**Title: selectByCmsId 
	 * Description:  
	 * @date 2018年3月6日 上午9:28:24
	 * @param id  
	 */
	PaperSubjectDetailEntity selectByCmsId(Long id);
	
	/**
	 * Title: updateSyncPaperSubjectDetailEntity 
	 * Description:  
	 * @date 2018年3月6日 上午9:52:31
	 * @param paperSubjectDetailEntity
	 * @return
	 */
	int updateSyncPaperSubjectDetailEntity(PaperSubjectDetailEntity paperSubjectDetailEntity);
}