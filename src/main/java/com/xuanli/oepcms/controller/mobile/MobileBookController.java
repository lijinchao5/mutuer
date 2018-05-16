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
	public RestResult<String> replaceBookVersion(Integer grade, Integer bookVersion, Integer bookVolume) {
		return bookService.replaceBookVersion(getCurrentUser().getId(), grade, bookVersion, bookVolume);
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

	/**
	 * Title: selectBook 
	 * Description:  
	 * @date 2018年5月15日 下午5:29:47
	 * @param grade
	 * @param bookVersion
	 * @return
	 */
	@ApiOperation(value = "根据年级或版本筛选教材", notes = "根据年级或版本筛选教材")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grade", value = "年级", required = false, dataType = "String"),
			@ApiImplicitParam(name = "bookVersion", value = "教材版本", required = false, dataType = "Integer") })
	@RequestMapping(value = "selectBook.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> selectBook(String grade, Integer bookVersion) {
		return bookService.selectBook(getCurrentUser().getId(), grade, bookVersion);
	}

	/**
	 * Title: getBookDetailList 
	 * Description:  
	 * @date 2018年5月16日 上午9:59:46
	 * @param grade
	 * @param bookVersion
	 * @return
	 */
	@ApiOperation(value = "获取所有教材版本和教材册别", notes = "获取所有教材版本和教材册别")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grade", value = "年级", required = false, dataType = "String"),
			@ApiImplicitParam(name = "bookVersion", value = "教材版本", required = false, dataType = "Integer") })
	@RequestMapping(value = "getBookDetailList.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getBookDetailList(String grade, Integer bookVersion) {
		return bookService.selectBook(getCurrentUser().getId(), grade, bookVersion);
	}

	@ApiOperation(value = "获取使用教材记录", notes = "获取当前使用教材记录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "bookUse", value = "使用情况:默认不传全部使用记录,使用过:0,正在使用:1", required = false, dataType = "String") })
	@RequestMapping(value = "getBookUse.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getBookUse(Integer bookUse) {
		return bookService.getBookUse(getCurrentUser().getId(), bookUse);
	}
}
