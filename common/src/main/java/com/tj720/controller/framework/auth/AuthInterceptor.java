package com.tj720.controller.framework.auth;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tj720.controller.framework.MyException;
import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.LoginInfoDto;
import com.tj720.model.common.SysLog;
import com.tj720.service.SysLogService;
import com.tj720.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;



/**
 * 对登录状态进行拦截
 * @author
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private Config config;

//	private String userId = Tools.getUserId();
	private String userId = "sysadmin";

	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);

			long beginTime = System.currentTimeMillis(); //开始时间
			startTimeThreadLocal.set(beginTime); //线程绑定变量（该数据只有当前请求的线程可见）

			// 未登陆用户唯一识别
			String uuid = MyCookie.getCookie(Const.COOKIE_UUID, false, request);
			if( MyString.isEmpty(uuid) ){
				MyCookie.addCookie(Const.COOKIE_UUID, System.currentTimeMillis() + Tools.getChar(10), response);
			}

			try{
				// 返回服务器ip
				response.setHeader("serviceIp", InetAddress.getLocalHost().getHostAddress());
			}catch(Exception e){
				e.printStackTrace();
				response.setHeader("serviceIp", "服务器配置异常，无法获取服务器IP");
			}
			// 指定允许其他域名访问
			response.setHeader("Access-Control-Allow-Origin", "*");
			// 指定允许其他域名访问
			response.setHeader("Access-Control-Allow-Credentials", "true");
			// 响应类型
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");
			// 响应头设置
			response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, HaiYi-Access-Token");
			if(authPassport == null || authPassport.validate() == false) {
				return true;
			}


			String token = MyCookie.getCookie(Const.COOKIE_TOKEN, false, request);
			String uid = MyCookie.getCookie(Const.COOKIE_USERID, false, request);
			// 前端没有传递token，未登录
			if(MyString.isEmpty(token) || MyString.isEmpty(uid) || !Aes.desEncrypt(token).equals(uid)){
				String requrl = request.getRequestURI();
				if(request.getRequestURI().endsWith("admin.do")){
					response.sendRedirect("loginOrRegister.do#/login");
				}
				else{
					String acceptHeader = request.getHeader("Accept");
					String ajaxParam = request.getParameter("text/html;type=ajax");
					if ("text/html;type=ajax".equals(acceptHeader) || StringUtils.hasText(ajaxParam)) {
						throw new MyException("000021");
					} else {
						response.getWriter().write("<script>top.location='"+request.getContextPath()+"/toLogin.do'</script>");
						return false;
					}
				}
			}
			return true;
		}
		else{
			return true;
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//判断是否为ajax请求，默认不是  
		boolean isAjaxRequest = false;
		if(!org.apache.commons.lang3.StringUtils.isBlank(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
			isAjaxRequest = true;
		}
		long beginTime = startTimeThreadLocal.get(); //得到线程绑定的局部变量（开始时间） 
		long endTime = System.currentTimeMillis(); //结束时间
		String statusCode = "200";
		String errorInfo = "";
		if(ex != null) {
			statusCode = "500";
			errorInfo = JSON.toJSONString(ex);
		}
		Integer  id  = null;
		SysLog log = new SysLog(id,request.getRequestURI(),getIpAddress(request),
				beginTime,endTime,JSON.toJSONString(request.getParameterMap()),userId,
				isAjaxRequest?1:0, statusCode, errorInfo);
		sysLogService.create(log);
	}

	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.contains(",")) {
			return ip.split(",")[0];
		} else {
			return ip;
		}
	}


}