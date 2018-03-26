package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.PaperSubjectEntity;
@Mapper
public interface PaperSubjectEntityMapper {
	/**
	 * Title: deletePaperSubjectEntity 
	 * Description:   删除方法
	 * @date 2018年2月6日 下午2:12:44
	 * @param id
	 * @return
	 */
    int deletePaperSubjectEntity(Long id);
    /**
     * Title: insertPaperSubjectEntity 
     * Description:   添加方法
     * @date 2018年2月6日 下午2:12:54
     * @param record
     * @return
     */
    int insertPaperSubjectEntity(PaperSubjectEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:13:06
     * @param id
     * @return
     */
    PaperSubjectEntity selectById(Long id);
    /**
     * Title: updatePaperSubjectEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:13:13
     * @param record
     * @return
     */
    int updatePaperSubjectEntity(PaperSubjectEntity record);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月23日 下午4:17:58
	 */
	List<PaperSubjectEntity> getPaperSubjectEntity(PaperSubjectEntity paperSubjectEntity);
	/**@Description:  TODO
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月5日 下午2:42:35
	 */
	void updatePaperSubjectUsedCount(Long id);
	/**Title: selectByCmsId 
	 * Description:  
	 * @date 2018年3月5日 下午6:31:28  
	 */
	PaperSubjectEntity selectByCmsId(Long id);
	
	int updateSyncPaperSubjectEntity(PaperSubjectEntity record);
}