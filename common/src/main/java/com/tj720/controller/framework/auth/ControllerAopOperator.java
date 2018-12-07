package com.tj720.controller.framework.auth;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tj720.controller.base.controller.BaseController;
import com.tj720.model.common.system.user.MipUser;
import com.tj720.model.common.system.userlog.SysUserlog;
import com.tj720.service.SysUserlogService;
import com.tj720.service.UserService;
import com.tj720.utils.OrgUtil;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 操作日志记录处理
 *
 * @author xiaoao
 */

@Aspect
@Component
public class ControllerAopOperator extends BaseController{


    @Autowired
    private SysUserlogService sysUserlogService;


	HttpServletRequest request = null;
	
	//声明AOP切入点，凡是使用了XXXOperateLog的方法均被拦截
    @Pointcut("@annotation(com.tj720.controller.framework.auth.ControllerAop)")
    public void log() {
//        System.out.println("我是一个切入点");
    }


    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "log()")
    public void doBefore(JoinPoint joinPoint)
        {
            handleLog(joinPoint, null);
    }

    @AfterThrowing(value = "log()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e);
    }


    private void handleLog(JoinPoint joinPoint, Exception e)
    {
        try
        {
            // 获得注解
            ControllerAop controllerAop = giveController(joinPoint);
            if (controllerAop== null)
            {
                return;
            }
            System.out.println(controllerAop.action()+">>>>>>>>>>>>>>>>>>>>>>>");
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();

            // *========数据库日志=========*//
            SysUserlog sysUserlog = new SysUserlog();
            // 请求的IP
            //sysUserlog.setUserName(Tools.getUserName());
            sysUserlog.setUserName("sysadmin");
            sysUserlog.setLoginTime(new Date());
            String clientIp = getIpAddr(request);
            sysUserlog.setLoginIp(clientIp);
            sysUserlog.setUrl(request.getRequestURI());
            sysUserlog.setRequestParam(JSON.toJSONString(request.getParameterMap()));



            // 处理设置注解上的参数
            getControllerMethodDescription(controllerAop,sysUserlog,request);
            // 保存数据库
            sysUserlogService.insertUserlog(sysUserlog);
        }
        catch (Exception ex)
        {
            // 记录本地异常日志
            ex.printStackTrace();
        }
    }


    /**
     * 是否存在注解，如果存在就记录日志
     *
     * @param joinPoint
     * @param  //controllerAop
     * @return
     * @throws Exception
     */
    private static ControllerAop giveController(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(ControllerAop.class);
        }
        return null;
    }


    /**
     * 在所有标注@Log的地方切入
     * @param joinPoint
     */
   /* @Before("log()")
    public void beforeExec(JoinPoint joinPoint) {
        request=  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        ControllerAop annotation = method.getAnnotation(ControllerAop.class);
        Map<String, Integer> btnList = OrgUtil.getBtnList(annotation.url(), request);
        session.setAttribute("btn", btnList);
    }
*/


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param  /joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static void getControllerMethodDescription(ControllerAop  controllerAop, SysUserlog sysUserlog,
                                                      HttpServletRequest request) throws Exception
    {
        // 设置action动作
        sysUserlog.setAction(controllerAop.action());

    }



    /**
     * 获取请求的参数，放到log中
     *
     * @param sysUserlog
     * @param request
     */
    @SuppressWarnings("all")
    private static void setRequestValue(SysUserlog sysUserlog, HttpServletRequest request)
    {
        if (sysUserlog == null){
            sysUserlog = new SysUserlog();
            Map map = request.getParameterMap();
            String params = JSONObject.toJSONString(map);
            sysUserlog.setRequestParam(params);
        }

    }

}
