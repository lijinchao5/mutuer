package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.PaperOptionEntity;
@Mapper
public interface PaperOptionEntityMapper {
	/**
	 * Title: deletePaperOptionEntity 
	 * Description:   删除方法
	 * @date 2018年2月6日 下午2:10:38
	 * @param id
	 * @return
	 */
    int deletePaperOptionEntity(Long id);
    /**
     * Title: insertPaperOptionEntity 
     * Description:   添加方法
     * @date 2018年2月6日 下午2:11:11
     * @param record
     * @return
     */
    int insertPaperOptionEntity(PaperOptionEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:11:24
     * @param id
     * @return
     */
    PaperOptionEntity selectById(Long id);
    /**
     * Title: updatePaperOptionEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:11:30
     * @param record
     * @return
     */
    int updatePaperOptionEntity(PaperOptionEntity record);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月6日 下午4:26:50
	 */
	PaperOptionEntity selectByDetailId(PaperOptionEntity record);
	/**@Description:  TODO
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月5日 下午3:21:20
	 */
	List<PaperOptionEntity> getSubjectOptionByDetailId(Long id);
	/**
	 * Title: selectByCmsId 
	 * Description:  
	 * @date 2018年3月6日 上午9:51:48
	 * @param id
	 * @return
	 */
	PaperOptionEntity selectByCmsId(Long id);
	/**
	 * Title: updateSyncPaperOptionEntity 
	 * Description:  
	 * @date 2018年3月6日 上午9:52:04
	 * @param record
	 * @return
	 */
	int updateSyncPaperOptionEntity(PaperOptionEntity record);
}