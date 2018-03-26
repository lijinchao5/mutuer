package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SectionDetail;
@Mapper
public interface SectionDetailMapper {
	/**删除方法，根据id删除*/
    int deleteSectionDetail(Long id);

    /**增加方法*/
    int insertSectionDetailEntity(SectionDetail record);

    SectionDetail selectById(Long id);
    /**查询方法,根据id查询*/
    
    /**更新方法*/
    int updateSectionDetail(SectionDetail record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午10:15:32
	 */
	List<SectionDetail> getSectionDetails(SectionDetail sectionDetail);

	/**
	 * @Description:  TODO 该方法 单独使用.切莫修改
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月27日 下午4:12:59
	 */
	List<SectionDetail> getSectionDetailsDialogs(SectionDetail sectionDetail);

	/**Title: selectByCmsId 
	 * Description:  
	 * @date 2018年2月28日 上午9:28:15
	 * @param id
	 * @return  
	 */
	SectionDetail selectByCmsId(Long id);

	/**Title: updateSyncSectionEntity 
	 * Description:  
	 * @date 2018年2月28日 上午9:28:22
	 * @param sectionDetail  
	 */
	void updateSyncSectionDetail(SectionDetail sectionDetail);
}