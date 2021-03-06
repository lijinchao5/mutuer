package com.xuanli.oepcms.controller;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.util.ABCUtils;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.VerificationCodeImgUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController()
@RequestMapping(value = "/")
public class LoginController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    SessionUtil sessionUtil;

    @ApiOperation(value = "登陆方法", notes = "登陆方法")
    @ApiImplicitParams({ @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String") })
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public RestResult<String> login(String userName, String password) {
        try {
            if (StringUtil.isEmpty(userName)) {
                return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "用户名不能为空");
            }
            if (StringUtil.isEmpty(password)) {
                return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "密码不能为空");
            }
            // 验证通过
            String result = userService.login(userName, password);
            if (StringUtil.isEmpty(result)) {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员");
            } else {
                if (result.equals("2")) {
                    // 用户名//或者密码错误
					return failed(ExceptionCode.USERINFO_ERROR_CODE, "手机号或者密码错误");
                } else if (result.equals("3")) {
                    // 用户名//或者密码错误
					return failed(ExceptionCode.USERINFO_NOUSE_ERROR, "用户被禁用，请联系管理员");
                } else if (result.equals("4")) {
                    // 用户使用到期
					return failed(ExceptionCode.USERINFO_NOUSE_ERROR, "已超出使用期限，请联系管理员");
                } else {
                    return ok(result);
                }
            }
        } catch (Exception e) {
            logger.error("登陆异常,请联系管理员.", e);
            e.printStackTrace();
            return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
        }
    }

    @ApiOperation(value = "获取图片验证码", notes = "获取图片验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "图片类型1:登陆注册2:手机图片验证码,默认为1", required = false, dataType = "String"),
            @ApiImplicitParam(name = "randomKey", value = "用户id/用户手机号/用户名", required = false, dataType = "String"), })
    @RequestMapping(value = "picture.do", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void getCaptcha(HttpServletResponse response, String type, String randomKey) {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        try {
            if (StringUtil.isEmpty(randomKey)) {
            }
            // 生产验证码字符串并保存到session中
            String createText = StringUtil.getRandomAll(4);
            VerificationCodeImgUtil verificationCodeImgUtil = VerificationCodeImgUtil.getInstance();
            verificationCodeImgUtil.initVerificationCode(createText);
            if (StringUtil.isEmpty(type) || type.equals("1")) {
                // 图片验证码
                logger.debug("产生图片验证码:" + createText);
                sessionUtil.setRandomNum(randomKey, createText);
            } else if (type.equals("2")) {
                // 手机短信图片验证码
                logger.debug("产生手机短信图片验证码:" + createText);
                sessionUtil.setMobileRandomNum(randomKey, createText);
            }
            ImageIO.write(verificationCodeImgUtil.getImage(), "JPEG", response.getOutputStream());
        } catch (IllegalArgumentException | IOException e) {
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
			logger.error("获取随机数图片出现错误", e);
        }
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ApiOperation(value = "登出方法", notes = "用户登出")
    public RestResult<String> logout() {
        try {
            String tokenId = getTokenId();
            if (StringUtil.isNotEmpty(tokenId)) {
                sessionUtil.removeSessionUser(tokenId);
            }
            return okNoResult("成功登出");
        } catch (Exception e) {
            return okNoResult("成功登出");
        }
    }

    @RequestMapping(value = "index.do")
    public RestResult<String> index() {
        return okNoResult(ABCUtils.z());
    }

}
