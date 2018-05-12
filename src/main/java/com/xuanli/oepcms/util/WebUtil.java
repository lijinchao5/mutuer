package com.xuanli.oepcms.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xuanli.oepcms.contents.Constants;

public final class WebUtil {
    private WebUtil() {
        // Do nothing
    }

    /**
     * 请求是否来自App（Android或者iOS）。
     * 
     * @param request
     * @return
     */
    public static boolean isApp(HttpServletRequest request) {
        if (request != null) {
            String platform = request.getHeader(Constants.HEADER_PLATFORM);
            return StringUtils.equalsIgnoreCase(Constants.PLATFORM_ANDROID, platform)
                    || StringUtils.equalsIgnoreCase(Constants.PLATFORM_IOS, platform);
        }
        return false;
    }

    /**
     * 当前请求是否来自App（Android或者iOS）。
     * 
     * @return
     */
    public static boolean isApp() {
        return isApp(getRequest());
    }

    /**
     * 请求是否来自移动端。
     * 
     * @param request
     * @return
     */
    public static boolean isMobile(HttpServletRequest request) {
        if (request != null) {
            String terminal = request.getHeader(Constants.HEADER_TERMINAL);
            return StringUtils.equalsIgnoreCase(Constants.TERMINAL_MOBILE, terminal);
        }
        return false;
    }

    /**
     * 当前请求是否来自移动端。
     * 
     * @return
     */
    public static boolean isMobile() {
        return isMobile(getRequest());
    }

    private static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
