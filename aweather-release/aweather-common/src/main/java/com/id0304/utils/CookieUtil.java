package com.id0304.common.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author WuZhengHua
 * @Description TODO cookie工具类
 * @Date 16:37 2019/9/12
 **/
public class CookieUtil {

    private CookieUtil() {
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 添加cookie
     * @Date 16:37 2019/9/12
     **/
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 移除cookie
     * @Date 16:37 2019/9/12
     **/
    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie uid = new Cookie(name, null);
        uid.setPath("/");
        uid.setMaxAge(0);
        response.addCookie(uid);
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 获取cookie
     * @Date 16:37 2019/9/12
     **/
    public static String getUid(HttpServletRequest request, String cookieName) {
        Cookie cookies[] = request.getCookies();
        if(cookies==null){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}