/**
 * 
 */
package com.xuanli.oepcms.controller.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.service.MobileUserService;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年5月3日 下午4:24:07
 */
@RestController
@RequestMapping(value = "/mobile/")
public class MobileLoginController extends BaseMobileController {

	@Autowired
	private MobileUserService mobileUserService;

	@ApiOperation(value = "登陆方法", notes = "登陆方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appId", value = "设备唯一标识", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appTokenId", value = "随机验证码关键Key", required = true, dataType = "String") })
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public RestResult<String> mobileLogin(String userName, String password, String appId, String appTokenId) {
		try {
			if (StringUtil.isEmpty(userName) && StringUtil.isEmpty(password) && StringUtil.isEmpty(appTokenId)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "登陆信息不完整");
			}
			if (StringUtil.isEmpty(appId)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "appId不能为空");
			}
			String result = mobileUserService.mobileLogin(userName, password, appId, appTokenId);
			if (StringUtil.isEmpty(result)) {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员.");
			} else {
				if (result.equals("0")) {
					// 用户名//或者密码错误
					return failed(ExceptionCode.USERINFO_ERROR_CODE, "用户名或者密码错误.");
				} else if (result.equals("1")) {
					return ok(result);
				} else {
					return failed(ExceptionCode.UNKNOW_CODE, "未知错误，请联系管理员!");
				}
			}
		} catch (Exception e) {
			logger.error("登陆异常,请联系管理员.", e);
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}
}
