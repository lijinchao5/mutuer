package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ReadArticleEntity;

@Mapper
public interface ReadArticleEntityMapper {
	/**
	 * Title: deleteReadArticleEntity 
	 * Description:  
	 * @date 2018年3月13日 上午10:58:48
	 * @param id
	 * @return
	 */
	int deleteReadArticleEntity(Long id);

	/**
	 * Title: insertReadArticleEntity 
	 * Description:  
	 * @date 2018年3月13日 上午10:58:50
	 * @param record
	 * @return
	 */
	int insertReadArticleEntity(ReadArticleEntity record);

	/**
	 * Title: selectById 
	 * Description:  
	 * @date 2018年3月13日 上午10:58:52
	 * @param id
	 * @return
	 */
	ReadArticleEntity selectById(Long id);

	/**
	 * Title: updateReadArticleEntity 
	 * Description:  
	 * @date 2018年3月13日 上午10:58:54
	 * @param record
	 * @return
	 */
	int updateReadArticleEntity(ReadArticleEntity record);

	/**Title: findReadArticlePageTotal 
	 * Description:  
	 * @date 2018年3月13日 上午11:04:37
	 * @param readArticleEntity
	 * @return  
	 */
	int findReadArticlePageTotal(ReadArticleEntity readArticleEntity);

	/**Title: findReadArticlePage 
	 * Description:  
	 * @date 2018年3月13日 上午11:04:43
	 * @param readArticleEntity
	 * @return  
	 */
	List<Map<String, Object>> findReadArticlePage(ReadArticleEntity readArticleEntity);

	/**
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月14日 下午12:14:29
	 */
	Map<String, Object> findArtcileEntityById(ReadArticleEntity readArticleEntity);

}