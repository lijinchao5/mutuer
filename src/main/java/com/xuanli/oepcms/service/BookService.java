/**
 * @fileName:  BookService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 上午9:31:10
 */ 
package com.xuanli.oepcms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.BookVersionEntity;
import com.xuanli.oepcms.entity.UserBookEntity;
import com.xuanli.oepcms.mapper.BookEntityMapper;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu 
 */
@Service
public class BookService extends BaseService {
	@Autowired
	BookEntityMapper bookDao;
	
	/**
	 * @Description:  TODO 获取书本的信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午9:31:53
	 */
	public List<BookEntity> getBookEntity(BookEntity bookEntity) {
		return bookDao.getBookEntity(bookEntity);
	}

	/**Title: selectBooks 
	 * Description:  
	 * @date 2018年2月27日 下午8:10:39
	 * @return  
	 */
	public List<BookEntity> selectBooks() {
		return bookDao.selectBooks();
	}

	/**Title: getBookById 
	 * Description:  
	 * @date 2018年3月7日 下午2:27:03
	 * @param bookId  
	 */
	public BookEntity getBookById(Long bookId) {
		return bookDao.getBookById(bookId);
	}

	/**Title: getbookVolume 
	 * Description:  
	 * @date 2018年4月27日 下午12:32:57
	 * @return  
	 */
	public List<Map<String, Object>> getBookVersion(Integer grade) {
		return bookDao.getBookVersion(grade);
	}

	/**Title: getBookVolume 
	 * Description:  
	 * @date 2018年5月7日 上午11:29:31
	 * @param grade
	 * @param bookVersion
	 * @return  
	 */
	public List<Map<String, Object>> getBookVolume(String grade, Integer bookVersion) {
		BookVersionEntity bookVersionEntity = new BookVersionEntity();
		bookVersionEntity.setGradeVal(grade);
		bookVersionEntity.setVersionVal(bookVersion.intValue() + "");
		return bookDao.getBookVolume(bookVersionEntity);
	}

	/**Title: replaceBookVersion 
	 * Description:  
	 * @date 2018年5月7日 下午2:47:14
	 * @param id
	 * @param bookId  
	 */
	public RestResult<String> replaceBookVersion(Long userId, Integer grade, Integer bookVersion, Integer bookVolume) {
		UserBookEntity userBookEntity = new UserBookEntity();
		userBookEntity.setUserId(userId);
		userBookEntity.setGrade(grade.intValue() + "");
		userBookEntity.setBookVersion(bookVersion);
		userBookEntity.setBookVolume(bookVolume.intValue() + "");
		int result = bookDao.replaceBookVersion(userBookEntity);
		if (result > 0) {
			return ok("切换教材成功");
		} else {
			return failed(ExceptionCode.PERFECT_USERINFO_ERROR, "切换教材失败");
		}
	}

	/**Title: selectBook 
	 * Description:  
	 * @param bookVersion 
	 * @param grade 
	 * @date 2018年5月15日 上午10:10:04  
	 */
	public RestResult<List<Map<String, Object>>> selectBook(Long userId, String grade, Integer bookVersion) {
		BookEntity bookEntity = new BookEntity();
		bookEntity.setUserId(userId);
		bookEntity.setGrade(grade);
		bookEntity.setBookVersion(bookVersion);
		return ok(bookDao.selectBook(bookEntity));
	}

	/**Title: getBookUse 
	 * Description:  
	 * @date 2018年5月16日 上午9:37:50
	 * @param id
	 * @param bookUse
	 * @return  
	 */
	public RestResult<List<Map<String, Object>>> getBookUse(Long userId, Integer bookUse) {
		UserBookEntity userBookEntity = new UserBookEntity();
		userBookEntity.setUserId(userId);
		userBookEntity.setBookUse(bookUse);
		return ok(bookDao.getBookUse(userBookEntity));
	}
}
