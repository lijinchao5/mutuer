package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.UserBookEntity;
@Mapper
public interface BookEntityMapper {
	/**删除方法，根据id删除*/
    int deleteBookEntity(Long id);
    /**增加方法*/
    int insertBookEntity(BookEntity record);
    /**查询方法,根据id查询*/
    BookEntity selectById(Long id);
    /**更新方法*/
    int updateBookEntity(BookEntity record);
	/**
	 * @Description:  TODO 获取书籍信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午9:36:17
	 */
	List<BookEntity> getBookEntity(BookEntity bookEntity);
	
	/**
	 * Title: selectByCmsId 
	 * Description:  根据cmsId获取教材信息
	 * @date 2018年2月27日 上午11:45:52
	 * @param cmsId
	 * @return
	 */
    BookEntity selectByCmsId(Long cmsId);

    /**
     * Title: updateSyncBookEntity 
     * Description:  如果cmsId已存在，更新教材
     * @date 2018年2月27日 上午11:47:33
     * @param record
     * @return
     */
    int updateSyncBookEntity(BookEntity record);
    
    List<BookEntity> selectBooks();
	/**Title: getBookById 
	 * Description:  
	 * @date 2018年3月7日 下午2:27:27
	 * @param bookId
	 * @return  
	 */
	BookEntity getBookById(Long bookId);

	/**Title: getbookVolume 
	 * Description:  
	 * @date 2018年4月27日 下午12:34:32  
	 */
	List<Map<String, Object>> getBookVersion(Integer grade);

	/**Title: getBookVolume 
	 * Description:  
	 * @date 2018年5月7日 上午11:32:31
	 * @param bookEntity
	 * @return  
	 */
	List<Map<String, Object>> getBookVolume(BookEntity bookEntity);

	/**Title: replaceBookVersion 
	 * Description:  
	 * @date 2018年5月7日 下午2:48:16  
	 */
	int replaceBookVersion(UserBookEntity userBookEntity);
}