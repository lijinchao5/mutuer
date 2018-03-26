package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.PaperEntity;
@Mapper
public interface PaperEntityMapper {
	/**
	 * Title: deletePaperEntity 
	 * Description:   删除试卷方法
	 * @date 2018年2月6日 下午2:09:41
	 * @param id
	 * @return
	 */
    int deletePaperEntity(Long id);
    /**
     * Title: insertPaperEntity 
     * Description:   添加试卷方法
     * @date 2018年2月6日 下午2:09:55
     * @param record
     * @return
     */
    int insertPaperEntity(PaperEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:10:04
     * @param id
     * @return
     */
    PaperEntity selectById(Long id);
    /**
     * Title: updatePaperEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:10:12
     * @param record
     * @return
     */
    int updatePaperEntity(PaperEntity record);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月26日 下午12:06:41
	 */
	int findPaperByPageTotal(Map<String, Object> requestMap);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月26日 下午12:06:51
	 */
	List<Map<String, Object>> findPaperByPage(Map<String, Object> requestMap);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月27日 上午9:41:14
	 */
	List<Map<String, Object>> getPaperDetail(Map<String, Object> requestMap);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月27日 上午9:41:14
	 */
	List<Map<String, Object>> getPaperDetail1(Long paperId);
	/**@Description:  TODO
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月5日 上午9:58:49
	 */
	int findPaperDetailByPageCount(Map<String, Object> requestMap);
	/**@Description:  TODO
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月5日 上午9:58:54
	 */
	List<Map<String, Object>> findPaperDetailByPage(Map<String, Object> requestMap);
	
	/**
	 * Title: selectByCmsId 
	 * Description:  
	 * @date 2018年3月5日 下午5:27:26
	 * @param id
	 * @return
	 */
	PaperEntity selectByCmsId(Long id);
	/**
	 * Title: updateSyncPaperEntity 
	 * Description:  根据cmsId更新试卷
	 * @date 2018年3月5日 下午5:27:35
	 * @param record
	 * @return
	 */
	int updateSyncPaperEntity(PaperEntity record);
	/**
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月12日 上午9:55:08
	 */
	List<Map<String, Object>> getPaperDetailByTeacher(Long paperId);
	/**
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年3月16日 下午12:42:27
	 */
	List<Map<String, Object>> getPaperDetailAndScore(Map<String, Object> map2);
}