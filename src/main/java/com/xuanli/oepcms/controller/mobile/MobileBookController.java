/**
 * 
 */
package com.xuanli.oepcms.controller.mobile;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.controller.BaseController;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.BookService;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年5月7日 上午11:57:07
 */
@RestController
@RequestMapping(value = "/mobile/book/")
public class MobileBookController extends BaseController {
	@Autowired
	BookService bookService;
	@Autowired
	UserService userService;

	/**
	 * Title: replaceBookVersion 
	 * Description:  
	 * @date 2018年5月7日 下午3:35:27
	 * @param bookId
	 * @return
	 */
	@ApiOperation(value = "切换教材", notes = "切换教材")
	@ApiImplicitParams({ @ApiImplicitParam(name = "bookId", value = "教材id", required = true, dataType = "Long") })
	@RequestMapping(value = "replaceBookVersion.do", method = RequestMethod.POST)
	public RestResult<String> replaceBookVersion(Long bookId, Integer grade, Integer bookVersion, Integer bookVolume) {
		if (null == grade || null == bookVersion || null == bookVolume) {
		} else {
			UserEntity userEntity = new UserEntity();
			userEntity.setGradeLevelId(grade);
			userEntity.setBookVersionId(bookVersion);
			userEntity.setBookVolume(bookVolume);
			int userInfo = userService.updateUserInfo(userEntity, null);
			if (userInfo > 0) {
				logger.info("更换教材已同步至PC端!");
			} else {
				logger.info("更换教材同步至PC端失败!");
			}
		}
		return bookService.replaceBookVersion(getCurrentUser().getId(), bookId);
	}

	/**
	 * Title: getMobileBookVersion 
	 * Description:  
	 * @date 2018年5月7日 下午3:35:29
	 * @param grade
	 * @return
	 */
	@ApiOperation(value = "根据年级获取教材版本信息", notes = "根据年级获取教材版本信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grade", value = "年级id", required = true, dataType = "Integer") })
	@RequestMapping(value = "getMobileBookVersion.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getMobileBookVersion(Integer grade) {
		return ok(bookService.getBookVersion(grade));
	}

	/**
	 * Title: getMobileBookVolume 
	 * Description:  
	 * @date 2018年5月7日 下午3:48:12
	 * @param grade
	 * @param bookVersion
	 * @return
	 */
	@ApiOperation(value = "根据年级与教材版本获取册别", notes = "根据年级与教材版本获取册别")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grade", value = "年级", required = true, dataType = "String"),
			@ApiImplicitParam(name = "bookVersion", value = "教材版本", required = true, dataType = "Integer") })
	@RequestMapping(value = "getMobileBookVolume.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getMobileBookVolume(String grade, Integer bookVersion) {
		return ok(bookService.getBookVolume(grade, bookVersion));
	}

	@ApiOperation(value = "根据年级或版本筛选教材", notes = "根据年级或版本筛选教材")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grade", value = "年级", required = true, dataType = "String"),
			@ApiImplicitParam(name = "bookVersion", value = "教材版本", required = true, dataType = "Integer") })
	@RequestMapping(value = "selectBook.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> selectBook(String grade, Integer bookVersion) {
		return bookService.selectBook(getCurrentUser().getId(), grade, bookVersion);
	}
}
