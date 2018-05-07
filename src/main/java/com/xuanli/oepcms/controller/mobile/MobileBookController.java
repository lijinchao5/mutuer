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

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.service.BookService;
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
public class MobileBookController extends BaseMobileController {
	@Autowired
	BookService bookService;

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
	public RestResult<String> replaceBookVersion(Long bookId) {
		String result = bookService.replaceBookVersion(getCurrentUser().getUserId(), bookId);
		if (result.equals("1")) {
			return ok("切换教材成功");
		} else if (result.equals("0")) {
			return failed(ExceptionCode.PERFECT_USERINFO_ERROR, "切换教材失败!");
		} else {
			return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员");
		}
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
}
