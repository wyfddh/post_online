package com.tj720.controller.framework;

import com.tj720.controller.springbeans.GetBeanByConfig;

import java.io.IOException;
    
import javax.servlet.Filter;     
import javax.servlet.FilterChain;     
import javax.servlet.FilterConfig;     
import javax.servlet.ServletException;     
import javax.servlet.ServletRequest;     
import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;     
import javax.servlet.http.HttpServletResponse;     
import javax.servlet.http.HttpSession;     
/**   
 * 用户访问权限的过滤器   
 * @author viano   
 */    
public class UsersFilter implements Filter {
    @Override
    public void destroy() {     
        // TODO Auto-generated method stub     
             
    }     
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,     
            FilterChain chain) throws IOException, ServletException {     
        // 设置请求的字符编码     
        request.setCharacterEncoding("UTF-8");     
        // 设置返回请求的字符编码     
        response.setCharacterEncoding("UTF-8");     
        // 转换ServletRequest为 HttpServletRequest     
        HttpServletRequest req = (HttpServletRequest) request;     
        // 转换ServletResponse为HttpServletRequest     
        HttpServletResponse res = (HttpServletResponse) response;     
        // 获取Session     
        HttpSession session = req.getSession();     
        // 获取Session中存储的对象
        Object userId = session.getAttribute("uid");
        // 获取当前请求的URI     
        String url = req.getRequestURI();     
        // 判断Session中的对象是否为空；判断请求的URI是否为不允许过滤的URI     
        if (url.endsWith(".js")||url.endsWith(".css")) {
            if (userId != null || url.indexOf("layer")>0||url.indexOf("layui")>0||url.indexOf("login")>0||url.indexOf("common.js")>0||url.indexOf("md5.js")>0||url.indexOf("jquery")>0){
                chain.doFilter(request, response);
                res.setHeader("Cache-Control","no-store");
                res.setDateHeader("Expires",0);
                res.setHeader("Pragma","no-cache");
                res.flushBuffer();
            }else {
                res.sendRedirect("/toLogin.do");
            }
        } else {
            chain.doFilter(request, response);
            res.setHeader("Cache-Control","no-store");
            res.setDateHeader("Expires",0);
            res.setHeader("Pragma","no-cache");
            res.flushBuffer();
        }
             
    }     
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {     
        // TODO Auto-generated method stub     
             
    }     
    
}    