package com.tj720.service;

import com.tj720.utils.Const;
import com.tj720.utils.MyCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/20 16:53
 */
@Service
public class CookieService {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    /**
     * 获取cookie
     * @param key
     * @return
     */
    public String getCookie(String key){
        String cookie = MyCookie.getCookie(key, false, request);
        return cookie;
    }
    public void setCookie(String key,String value){
        MyCookie.addCookie(key,value,response);
    }

    public String getUserId() {
        return getCookie(Const.COOKIE_USERID);
    }
    public void setUserId(String value) {
        setCookie(Const.COOKIE_USERID,value);
    }
}
