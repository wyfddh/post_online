package com.tj720.controller.appInterface;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.MyException;
import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.pubuser.LoginDto;
import com.tj720.model.common.pubuser.PubUser;
import com.tj720.model.common.pubuser.PubUserDto;
import com.tj720.service.ICacheService;
import com.tj720.service.PostLoginService;
import com.tj720.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * TODO:三方登陆
 * @author 杜昶
 * @Date: 2018/11/5 11:31
 */
@RestController
public class AppPostLoginController extends BaseController {

    @Autowired
    private ICacheService cacheService;

    @Autowired
    private PostLoginService postLoginService;

    @Autowired
    private Config config;



    private int resc = 10;

    /**
     * 登陆前台页面
     * @return
     */
    @RequestMapping("/appPostLogin")
    @ResponseBody
    public JsonResult postLogin(@RequestBody LoginDto loginDto) {
        HttpSession session = request.getSession();
        try {
            if (ipLimit(session,resc)){
                session.setAttribute("userId",loginDto.getLoginName());
                return new JsonResult(0,null,"10000003");
            }
            JsonResult jsonResult = postLoginService.postLogin(loginDto.getLoginName(), loginDto.getPassword(), request);
            if (jsonResult.getSuccess() == 1) {
                PubUser pubUser = (PubUser) jsonResult.getData();
                String token = Aes.encrypt(pubUser.getId());
                MyCookie.addCookie(Const.COOKIE_TOKEN, token, response);
                MyCookie.addCookie(Const.COOKIE_USERID, pubUser.getId(), response);
                MyCookie.addCookie(Const.COOKIE_USERNAME, pubUser.getUserName(), response);
                if (!MyString.isEmpty(pubUser.getPhone())) {
                    MyCookie.addCookie(Const.COOKIE_PHONE, pubUser.getPhone(), response);
                }
                MyCookie.addCookie("sessionUserName", pubUser.getNickName()==null?"":pubUser.getNickName(), response);
                MyCookie.deleteCookie(Const.COOKIE_INFO, request, response);
            }
            return jsonResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000014");
    }

    /**
     * 注册页面发送验证码
     * @param phone
     * @return
     */
    @RequestMapping("/app/sendSecretCode")
    @ResponseBody
    public JsonResult sendSecretCode(@RequestParam String phone) {
        try {
            return postLoginService.sendSecretCode(phone, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000015");
    }

    /**
     * 前台用户注册
     * @param pubUserDto
     * @return
     */
    @RequestMapping("/app/register")
    @ResponseBody
    public JsonResult register(@RequestBody PubUserDto pubUserDto) {
        try {
            return postLoginService.register(pubUserDto, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000016");
    }

    /**
     * 修改用户手机号发送验证码
     * @param id 用户id
     * @param phone 手机号
     * @param step 步骤 0-验证初始手机号 1-验证新手机号
     * @return
     */
    @RequestMapping("/app/sendChangePhoneCode")
    @ResponseBody
    public JsonResult sendChangePhoneCode(@RequestParam String id, String phone, Integer step) {
        try {
            return postLoginService.sendChangePhoneCode(id, phone, step, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000014");
    }

    /**
     * 验证输入的验证码
     * @param id 用户id
     * @param phone 手机号
     * @param step 步骤 0-验证初识手机号 1-验证新手机号
     * @param verification 验证码
     * @return
     */
    @RequestMapping("/app/checkChangePhoneCode")
    @ResponseBody
    public JsonResult checkChangePhoneCode(@RequestParam String id, String phone, Integer step, String verification) {
        try {
            return postLoginService.checkChangePhoneCode(id, phone, step, verification, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000018");
    }

    /**
     * 修改用户信息
     * @param pubUser
     * @return
     */
    @RequestMapping("/app/changePubUserInfo")
    @ResponseBody
    public JsonResult changePubUserInfo(@RequestBody PubUser pubUser) {
        try {
            return postLoginService.changePubUserInfo(pubUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000007");
    }

    @RequestMapping("/app/getPubUserInfo")
    @ResponseBody
    public JsonResult getPubUserInfo(@RequestParam String id){
        try {
            return postLoginService.getPubUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }


    /**
     * 忘记密码
     * @param pubUserDto
     * @return
     */
    @RequestMapping("/app/forgetPassword")
    public JsonResult forgetPassword(@RequestBody PubUserDto pubUserDto) {
        try {
            return postLoginService.forgetPassword(pubUserDto, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000004");
    }

    /**
     * 忘记密码发送短信
     * @param phone
     * @return
     */
    @RequestMapping("/app/sendForgetPasswordCode")
    public JsonResult sendForgetPasswordCode(@RequestParam String phone) {
        try {
            return postLoginService.sendForgetPasswordCode(phone, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000019");
    }

    /**
     * 修改密码
     * @param pubUserDto
     * @return
     */
    @RequestMapping("/app/changePassword")
    public JsonResult changePassword(@RequestBody PubUserDto pubUserDto) {
        try {
            return postLoginService.changePassword(pubUserDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000020");
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @RequestMapping("/app/getPubUserById")
    public JsonResult getPubUserById(@RequestParam String id) {
        try {
            return postLoginService.getPubUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000008");
    }

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
    @RequestMapping("/app/checkVerification")
    public JsonResult checkVerification(@RequestParam String phone,@RequestParam String verification){
        boolean result = false;
        try {
            result = compareVerification(phone,verification);
        }catch (Exception e){
            result = false;
        }
        return new JsonResult(1,result);
    }

    /**
     * 对比验证码
     * @param verification
     * @return
     * @throws MyException
     */
    private boolean compareVerification(String phone,String verification) throws MyException {
        if (verification.equals(cacheService.getStr(phone))){
            return true;
        }else {
            return false;
        }
    }
}
