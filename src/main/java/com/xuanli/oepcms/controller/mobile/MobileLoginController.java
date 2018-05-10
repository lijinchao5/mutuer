/**
 * 
 */
package com.xuanli.oepcms.controller.mobile;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.service.MobileUserService;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.util.SessionUtil;
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
	SessionUtil sessionUtil;
	@Autowired
	UserService userService;
	@Autowired
	private MobileUserService mobileUserService;

	@ApiOperation(value = "登陆方法", notes = "登陆方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appId", value = "设备唯一标识", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appTokenId", value = "随机验证码关键Key", required = true, dataType = "String") })
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public RestResult<String> mobileLogin(String userName, String password, String appId, String appTokenId,String roleId) {
		try {
			if (StringUtil.isEmpty(userName) && StringUtil.isEmpty(password) && StringUtil.isEmpty(appTokenId)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "登陆信息不完整");
			}
			if (StringUtil.isEmpty(appId)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "appId不能为空");
			}
			if (StringUtil.isEmpty(roleId)) {
				roleId = "4";//学生登录
			}
			String result = mobileUserService.mobileLogin(userName, password, appId, appTokenId,roleId);
			if (StringUtil.isEmpty(result)) {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员.");
			} else {
				if (result.equals("0")) {
					// 用户名//或者密码错误
					return failed(ExceptionCode.USERINFO_ERROR_CODE, "用户名或者密码错误.");
				} else if (result.equals("2")) {
					return failed(ExceptionCode.UNKNOW_CODE, "登陆超时!");
				} else if (result.equals("1")) {
					return failed(ExceptionCode.UNKNOW_CODE, "您的账户角色不正确!");
				}else {
					return ok(result);
				}
			}
		} catch (Exception e) {
			logger.error("登陆异常,请联系管理员.", e);
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}
	@RequestMapping(value = "appRegist.do", method = RequestMethod.POST)
	public RestResult<Map<String,Object>> regist(String mobile,String password,String randomValue,String appId){
		if (!StringUtil.isMobile(mobile)) {
			return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机号码错误.");
		}
		if (StringUtil.isEmpty(password)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "密码不能为空.");
		}
		if (StringUtil.isEmpty(appId)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "appId不能为空.");
		}
		if (StringUtil.isEmpty(randomValue)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "密验证码不能为空.");
		}
		
		String randomStr = sessionUtil.getAppMobileMessage(mobile);
		logger.info("redis获取出来的value是:["+randomStr+"]对比:["+randomValue+"]");
		if (null != randomStr && randomStr.equals(randomValue)) {
			//对比通过
			Map<String,Object> map = userService.appRegist(mobile,password,appId);
			return ok(map);
		}else {
			return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机验证码错误");
		}
	}
}
