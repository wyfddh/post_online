package com.tj720.controller.framework.auth;
import com.alibaba.fastjson.JSONObject;
import com.tj720.controller.springbeans.GetBeanByConfig;
import com.tj720.dao.ICacheDao;
import com.tj720.service.ICacheService;
import com.tj720.utils.common.IdUtils;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private GetBeanByConfig getBEanByConfig;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private Config config;

//	private String userId = Tools.getUserId();

	private static final ThreadLocal<Long>
			STARTTIME_THREAD_LOCAL = new NamedThreadLocal<Long>("ThreadLocal StartTime");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis(); //开始时间
		STARTTIME_THREAD_LOCAL.set(beginTime); //线程绑定变量（该数据只有当前请求的线程可见）
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			//			AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);


			// 未登陆用户唯一识别
			String uuid = MyCookie.getCookie(Const.COOKIE_UUID, false, request);
			if( MyString.isEmpty(uuid) ){
				MyCookie.addCookie(Const.COOKIE_UUID, System.currentTimeMillis() + Tools.getChar(10), response);
			}

			try{
				// 返回服务器ip
//				String hostAddress = InetAddress.getLocalHost().getHostAddress();
				String hostAddress = Tools.getIpAddress(request);
//				String ipAddress = IdUtils.getIPAddress(hostAddress);
				String regex = "[`~!@#$%^&*()\\+\\=\\{}|:\"?><【】\\/r\\/n]";
				Pattern pa = Pattern.compile(regex);
				Matcher ma = pa.matcher(hostAddress);
				if(ma.find()){
					hostAddress = ma.replaceAll("");
				}
				response.setHeader("serviceIp", hostAddress);
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
//			if(authPassport == null || authPassport.validate() == false) {
//				return true;
//			}

			//请求controller中的方法名

			HandlerMethod handlerMethod = (HandlerMethod)handler;

			//解析HandlerMethod
			//方法名
			String methodName = handlerMethod.getMethod().getName();
			//类路径
			String classPathName = handlerMethod.getBean().getClass().getName();

			//放行pc和移动端接口
			String appPath = "com.tj720.controller.appInterface";
			String webPath = "com.tj720.controller.webInterface";
			//放行登录
			String loginPath = "com.tj720.controller.login";
			//放行第三方藏品接口
			String collectPath = "com.tj720.controller.outerInterface";
			//放行上传
			String attachPath = "com.tj720.controller.attachment.AttachmentController";


			if (classPathName.contains(appPath) || classPathName.contains(webPath) || classPathName.contains(loginPath)
					|| classPathName.contains(collectPath) || classPathName.contains(attachPath) || methodName.contains("getAreaApp")) {
				return true;
			}

//			String token = MyCookie.getCookie(Const.COOKIE_TOKEN, false, request);
			String uid = MyCookie.getCookie(Const.COOKIE_USERID, false, request);
//			// 前端没有传递token，未登录
//			if(MyString.isEmpty(token) || MyString.isEmpty(uid) || !Aes.desEncrypt(token).equals(uid)){
//				String requrl = request.getRequestURI();
//				response.sendRedirect("/admin/backLogin/loginOut.do");
//				return false;
////				if(request.getRequestURI().endsWith("admin.do")){
////					response.sendRedirect("loginOrRegister.do#/login");
////				}
////				else{
////					String acceptHeader = request.getHeader("Accept");
////					String ajaxParam = request.getParameter("text/html;type=ajax");
////					if ("text/html;type=ajax".equals(acceptHeader) || StringUtils.hasText(ajaxParam)) {
////						throw new MyException("000021");
////					} else {
////						response.getWriter().write("<script>top.location='"+request.getContextPath()+"/toLogin.do'</script>");
////						return false;
////					}
////				}
//			}
			// 后端没登录信息：登录超时

			Object userId = request.getSession().getAttribute("uid");
			ICacheDao cacheDao = getBEanByConfig.getCacheDao();
			Object obj = cacheDao.getObj(Const.CACHE_USER + uid);

			//session或者cookie中userId为空就跳转登录
			if(userId == null || obj == null || !(uid.equals(userId.toString()))){
				// 删除cookie
				MyCookie.deleteCookie(Const.COOKIE_TOKEN, request, response);
				//删除登录缓存信息
				if (StringUtils.isEmpty(uid)) {
					cacheService.delObj(Const.CACHE_USER + uid);
				}
				boolean isAjaxRequest = false;
				if(!StringUtils.isEmpty(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
					isAjaxRequest = true;
				}

				if(isAjaxRequest){
					String acceptHeader = request.getHeader("Accept");
					String ajaxParam = request.getParameter("text/html;type=ajax");
					if ("text/html;type=ajax".equals(acceptHeader) || StringUtils.hasText(ajaxParam)) {
						throw new MyException("000021");
					} else {
						//						response.getWriter().write("<script>top.location='"+request.getContextPath()+"/toLogin.do'</script>");
						String msg = "url=/admin/login.html&Redirect";
						response.getWriter().write(msg);
						return false;
					}
				}else{
					response.sendRedirect("/admin/toLogin.do");
					return false;
				}
			}
			// 每次访问，将用户登录有效信息延长
			cacheDao.setObj(Const.CACHE_USER + uid, obj, config.getLoginInforTime());

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
		if(!org.apache.commons.lang3.StringUtils.isBlank(request.getHeader("x-requested-with")) && "XMLHttpRequest".equals(request.getHeader("x-requested-with"))){
			isAjaxRequest = true;
		}
		long beginTime = STARTTIME_THREAD_LOCAL.get(); //得到线程绑定的局部变量（开始时间）
		STARTTIME_THREAD_LOCAL.remove();
		long endTime = System.currentTimeMillis(); //结束时间
		String statusCode = "200";
		String errorInfo = "";
		if(ex != null) {
			statusCode = "500";
			errorInfo = JSON.toJSONString(ex);
		}
		SysLog log = new SysLog(request.getRequestURI(),Tools.getIpAddress(request),
				beginTime,endTime,JSON.toJSONString(request.getParameterMap()),Tools.getUserId(),
				isAjaxRequest?1:0, statusCode, errorInfo);
		sysLogService.create(log);
	}




}