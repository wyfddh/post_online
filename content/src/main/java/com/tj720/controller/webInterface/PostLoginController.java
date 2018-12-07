package com.tj720.controller.webInterface;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.pubuser.PubUser;
import com.tj720.model.common.pubuser.PubUserDto;
import com.tj720.service.PostLoginService;
import com.tj720.utils.Aes;
import com.tj720.utils.Const;
import com.tj720.utils.MyCookie;
import com.tj720.utils.MyString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * TODO:三方登陆
 * @author 杜昶
 * @Date: 2018/11/5 11:31
 */
@RestController
public class PostLoginController extends BaseController {

    @Autowired
    private PostLoginService postLoginService;

    @Autowired
    private Config config;

    /**
     * 登陆前台页面
     * @param loginName
     * @param password
     * @param verification
     * @return
     */
    @RequestMapping("/postLogin")
    @ResponseBody
    public JsonResult postLogin(String loginName, String password, String verification, Integer autoLogin) {
        HttpSession session = request.getSession();
        try {
            JsonResult jsonResult = postLoginService.postLogin(loginName, password, verification, request);
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
                MyCookie.deleteCookie(Const.COOKIE_PASSWORD, request, response);
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
    @RequestMapping("/sendSecretCode")
    @ResponseBody
    public JsonResult sendSecretCode(String phone) {
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
    @RequestMapping("/front/register")
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
    @RequestMapping("/front/sendChangePhoneCode")
    @ResponseBody
    public JsonResult sendChangePhoneCode(String id, String phone, Integer step) {
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
    @RequestMapping("/front/checkChangePhoneCode")
    @ResponseBody
    public JsonResult checkChangePhoneCode(String id, String phone, Integer step, String verification) {
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
    @RequestMapping("/front/changePubUserInfo")
    @ResponseBody
    public JsonResult changePubUserInfo(@RequestBody PubUser pubUser) {
        try {
            return postLoginService.changePubUserInfo(pubUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000007");
    }

    /**
     * 忘记密码
     * @param pubUserDto
     * @return
     */
    @RequestMapping("/front/forgetPassword")
    public JsonResult forgetPassword(@RequestBody PubUserDto pubUserDto) {
        try {
            return postLoginService.forgetPassword(pubUserDto, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "10000011");
    }

    /**
     * 忘记密码发送短信
     * @param phone
     * @return
     */
    @RequestMapping("/front/sendForgetPasswordCode")
    public JsonResult sendForgetPasswordCode(String phone) {
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
    @RequestMapping("/front/changePassword")
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
    @RequestMapping("/front/getPubUserById")
    public JsonResult getPubUserById(String id) {
        try {
            return postLoginService.getPubUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000008");
    }
}
