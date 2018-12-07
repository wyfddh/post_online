package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.pubuser.PubUser;
import com.tj720.model.common.pubuser.PubUserDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 前台用户登录service
 * @author 杜昶
 * @Date: 2018/11/5 12:08
 */
@Service
public interface PostLoginService {

    /**
     * 前台页面根据用户名/手机号登录
     * @param loginName 用户名/手机号
     * @param password 密码
     * @param verification 验证码
     * @param request HttpServletRequest
     * @return
     */
    JsonResult postLogin(String loginName, String password, String verification, HttpServletRequest request);
    /**
     * app页面根据用户名/手机号登录
     * @param loginName 用户名/手机号
     * @param password 密码
     * @param request HttpServletRequest
     * @return
     */
    JsonResult postLogin(String loginName, String password, HttpServletRequest request);

    /**
     * 注册发送短信验证码
     * @param phone 手机号
     * @param request HttpServletRequest
     * @return
     */
    JsonResult sendSecretCode(String phone, HttpServletRequest request);

    /**
     * 前台用户注册
     * @param pubUserDto 用户注册信息
     * @param request HttpServletRequest
     * @return
     */
    JsonResult register(PubUserDto pubUserDto, HttpServletRequest request);

    /**
     * 修改用户手机号发送验证码
     * @param id 用户id
     * @param phone 手机号
     * @param step 步骤 0-验证初始手机号 1-验证新手机号
     * @param request HttpServletRequest
     * @return
     */
    JsonResult sendChangePhoneCode(String id, String phone, Integer step, HttpServletRequest request);

    /**
     * 验证输入的验证码
     * @param id 用户id
     * @param phone 手机号
     * @param verification 验证码
     * @param step 步骤 0-验证初识手机号 1-验证新手机号
     * @return
     */
    JsonResult checkChangePhoneCode(String id, String phone, Integer step, String verification, HttpServletRequest request);

    /**
     * 修改前台用户信息
     * @param pubUser
     * @return
     */
    JsonResult changePubUserInfo(PubUser pubUser);

    /**
     * 忘记密码
     * @param pubUserDto
     * @param request HttpServletRequest
     * @return
     */
    JsonResult forgetPassword(PubUserDto pubUserDto, HttpServletRequest request);

    /**
     * 忘记密码发送短信
     * @param phone 手机号
     * @param request HttpServletRequest
     * @return
     */
    JsonResult sendForgetPasswordCode(String phone, HttpServletRequest request);

    /**
     * 修改密码
     * @param pubUserDto
     * @return
     */
    JsonResult changePassword(PubUserDto pubUserDto);

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    JsonResult getPubUserById(String id);
}
