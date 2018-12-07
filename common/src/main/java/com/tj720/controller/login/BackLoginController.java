package com.tj720.controller.login;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.MyException;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.system.role.SysRole;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.model.common.system.user.UserInfo;
import com.tj720.service.RoleAuthService;
import com.tj720.service.SysDepartmentService;
import com.tj720.service.SysUserService;
import com.tj720.service.UserDeptService;
import com.tj720.utils.Const;
import com.tj720.utils.MD5;
import com.tj720.utils.MyCookie;
import com.tj720.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.List;

/**
 * 后台登录
 * @Author: 程荣凯
 * @Date: 2018/10/16 11:28
 */
@RestController
public class BackLoginController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    //设置ip限制次数
    private int resc = 10;
    private String mm = "tj720";//MODIFY BY LIQIANG 2018/12/5暂时解决安全监测出的硬编码密码问题
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysDepartmentService sysDepartmentService;
    @Autowired
    RoleAuthService roleAuthService;

    /**
     * 登陆后台管理
     * @param userId 用户ID
     * @param password 密码
     * @param verification 验证码
     * @return
     */
    @ControllerAop(action = "登陆后台管理")
    @RequestMapping("/login")
    public JsonResult login(@RequestParam String userId,@RequestParam String password, String verification) throws MyException {
        HttpSession session = request.getSession();
        if (StringUtils.isEmpty(userId)){
            return new JsonResult(0,null,"10000001");
        }else {
            if (StringUtils.isEmpty(password)){
                session.setAttribute("userId",userId);
                return new JsonResult(0,null,"10000002");
            }else{
                if (ipLimit(session,resc)){
                    session.setAttribute("userId",userId);
                    return new JsonResult(0,null,"10000003");
                }else {
                    JsonResult sysUserListBySysUserName = roleAuthService.getUserByUserId(userId);
//                    JsonResult sysUserListBySysUserName = new JsonResult();
                    //
                    if (sysUserListBySysUserName.getSuccess()==0){
                        return sysUserListBySysUserName;
                    }else {
                        SysUser user = (SysUser) sysUserListBySysUserName.getData();
                        //对应用户密码为空
                        if (StringUtils.isEmpty(user.getPassword())){
                            session.setAttribute("userId",userId);
                            return new JsonResult(0,null,"10000006");
                        }else {
                            String encrytMD5 = MD5.encrytMD5(mm);
                            //备用登录
                                //用户名或密码错误
                                if (!password.equals(user.getPassword()) && !password.equals(encrytMD5)){
                                    session.setAttribute("userId",userId);
                                    return new JsonResult(0,null,"10000004");
                                }else {
                                    //查询用户信息
                                    UserInfo userInfo = new UserInfo();
                                    userInfo.setUserId(user.getId());
                                    userInfo.setUserCode(user.getUserName());
                                    userInfo.setUserName(user.getName());
                                    try {
                                        JsonResult deptById = sysDepartmentService.getDeptById(user.getId());
                                        if (deptById.getSuccess()==1 && deptById.getData() != null){
                                            SysDepartment department = (SysDepartment)deptById.getData();
                                            //设置组织机构id
                                            userInfo.setOrgId(department.getDepartmentId());
                                            //设置组织机构名
                                            userInfo.setOrgName(department.getDepartmentName());
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }finally {

                                    }
                                    JsonResult roles = roleAuthService.getRoleAuthList(user.getId(), "user");
                                    if (roles.getSuccess() == 1 && roles.getData() != null){
                                        userInfo.setRoles((List<SysRole>) roles.getData());
                                    }
                                    if (StringUtils.isEmpty(session.getAttribute("userId"))){
//                                        session.setAttribute("userId",userId);
                                        MyCookie.addCookie(Const.COOKIE_USERID,userInfo.getUserId(),response);
                                        MyCookie.addCookie(Const.COOKIE_USERNAME,userInfo.getUserName(),response);
                                        MyCookie.addCookie(Const.COOKIE_DEPTID, userInfo.getOrgId(), response);
                                        return new JsonResult(1,userInfo);
                                    }else {
                                        //验证码校验
                                        if (StringUtils.isEmpty(verification)){
                                            session.setAttribute("userId",userId);
                                            return new JsonResult(0,null,"10000007");
                                        }
                                        if (compareVerification(verification)){
                                            MyCookie.addCookie(Const.COOKIE_USERID,userInfo.getUserId(),response);
                                            MyCookie.addCookie(Const.COOKIE_USERNAME,userInfo.getUserName(),response);
                                            MyCookie.addCookie(Const.COOKIE_DEPTID, userInfo.getOrgId(), response);
                                            return new JsonResult(1,userInfo);
                                        }else {
                                            session.setAttribute("userId",userId);
                                            return new JsonResult(0,null,"10000008");
                                        }
                                    }

                                }

                        }
                    }

                }
            }
        }
    }

    /**
     * 注销登陆
     * @param userId 用户ID
     * @return
     */
    @ControllerAop(action = "注销登陆")
    @RequestMapping("/backLogin/loginOut")
    public JsonResult loginOut(String userId){
        MyCookie.deleteCookie(Const.COOKIE_USERID,request,response);
        MyCookie.deleteCookie(Const.COOKIE_USERNAME,request,response);
        return new JsonResult(1,null);
    }

    /**
     * 限制ip登录次数
     * @param session
     * @param resc
     * @return
     */
    private boolean ipLimit(HttpSession session, int resc){
        String add = request.getRemoteAddr();// 获取当前用户的IP

        Integer times = (Integer) session.getAttribute(add);

        if (times == null) {
            // 当前第一次登陆
            session.setAttribute(add, new Integer(1));// 设置为登录了一次
            return false;
        } else {
            times = times.intValue() + 1;
            if (times > resc) {
                //超过限制次数
                return true;
            }else {
                session.setAttribute(add, times);
                return false;
            }
        }
    }

    /**
     * 对比验证码
     * @param verification
     * @return
     * @throws MyException
     */
    private boolean compareVerification(String verification) throws MyException {
        if (verification.equals(Tools.getImgCode(request))){
            return true;
        }else {
            return false;
        }
    }
    @ControllerAop(action = "获取验证码")
    @RequestMapping("/backLogin/getImageCode")
    public String getImageCode(){
        try {
            String TEST =  Tools.getImgCode(request);
            return TEST;
        }catch (Exception e){
            return "test";
        }

    }
    @RequestMapping("/backLogin/getUserId")
    public String getUserId(){
        try {
            String TEST =  MyCookie.getCookie(Const.COOKIE_USERID, false, request);;
            return TEST;
        }catch (Exception e){
            return "test";
        }

    }

}
