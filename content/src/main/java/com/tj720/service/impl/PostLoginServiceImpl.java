package com.tj720.service.impl;

import com.tj720.common.duanxin.HttpsRequest;
import com.tj720.common.redis.JedisDao;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.MyException;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PostLoginMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.postwebcollect.PostWebCollect;
import com.tj720.model.common.pubuser.PubUser;
import com.tj720.model.common.pubuser.PubUserDto;
import com.tj720.model.common.pubuser.PubUserVo;
import com.tj720.service.AttachmentService;
import com.tj720.service.PostLoginService;
import com.tj720.utils.MD5;
import com.tj720.utils.Tools;
import com.tj720.utils.common.Base64Utils;
import com.tj720.utils.common.IdUtils;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import sun.misc.BASE64Encoder;

/**
 * @author 杜昶
 * @Date: 2018/11/5 12:09
 */
@Service
public class PostLoginServiceImpl implements PostLoginService {

    /**
     * 设置ip限制次数
     */
    private int resc = 9999999;

    @Autowired
    PostLoginMapper postLoginMapper;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private Config config;

    private final String creator = "sysadmin";

    /** 注册短信验证码前缀 */
    private final String regiestCodeKey = "front_regies_mobile_";

    /** 更换手机号短信验证码前缀 */
    private final String changePhoneCodeKey = "front_change_mobile_%s_%s_%s";

    /** 忘记密码短信验证码前缀 */
    //private final String forgetPasswordCodeKey = "front_forget_password_";
    private final String forgetMMCodeKey = "front_forget_password_";
    @Override
    public JsonResult postLogin(String loginName, String password, String verification, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            /**
             * 数据基本校验
             */
            if (StringUtils.isEmpty(loginName)) {
                return new JsonResult(0, null ,"10000001");
            }
            if (StringUtils.isEmpty(password)) {
                return new JsonResult(0, null ,"10000002");
            }
            if (this.ipLimit(request, resc)) {
                return new JsonResult(0, null, "10000003");
            }

            Map<String, String> param = new HashMap<String, String>(2);
            PubUser pubUser = postLoginMapper.getUserByUserNameOrPhone(loginName);
            /**
             * 数据库数据校验
             */
            if (null == pubUser) {
                return new JsonResult(0,null,"10000005");
            }
            Integer isdelete = pubUser.getIsdelete();
            if (null != isdelete && isdelete == 1) {
                return new JsonResult(0,null,"20000013");
            }
            if (StringUtils.isEmpty(pubUser.getPassword())) {
                return new JsonResult(0, null, "10000006");
            }
            if ("0".equals(pubUser.getStatus())) {
                return new JsonResult("20000021");
            }

            /**
             * 密码校验
             */
            //String encrytMD5 = MD5.encrytMD5(password);
            String truePass = pubUser.getPassword();
            //if (encrytMD5.equals(truePass) || password.equals(truePass)) {
            if (password.equals(truePass)){
            //if (pubUser!=null){
                // 密码正确
                // 封装返回前端对象
                PubUserVo returnUser = new PubUserVo();
                returnUser.setId(pubUser.getId());
                returnUser.setUserName(pubUser.getUserName());
                returnUser.setSex(pubUser.getSex());
                returnUser.setBirthday(pubUser.getBirthday());
                returnUser.setProvinceId(pubUser.getProvinceId());
                returnUser.setCityId(pubUser.getCityId());
                returnUser.setJob(pubUser.getJob());
                returnUser.setSingleName(pubUser.getSingleName());
                returnUser.setPhone(pubUser.getPhone());
                String avatarurl = pubUser.getAvatarurl();
                if (!StringUtils.isEmpty(avatarurl)) {
                    Attachment attachment = attachmentService.getAttachmentsById(avatarurl);
                    returnUser.setAvatarLink(attachment == null ? null : attachment.getAttPath());
                }
                if (StringUtils.isEmpty(session.getAttribute("userId"))) {
                    session.removeAttribute("userId");
                    return new JsonResult(1, returnUser);
                } else {
                    // 验证码校验
                    if (StringUtils.isEmpty(verification)){
                        return new JsonResult(0, null, "10000007");
                    }
                    if (this.compareVerification(verification, request)) {
                        session.removeAttribute("userId");
                        return new JsonResult(1, returnUser);
                    } else {
                        return new JsonResult(0,null,"10000008");
                    }
                }
            } else {
                // 密码错误
                session.setAttribute("userId",loginName);
                return new JsonResult(0,null,"10000004");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,null,"10000006");
        }
    }

    @Override
    public JsonResult postLogin(String loginName, String password, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            /**
             * 数据基本校验
             */
            if (StringUtils.isEmpty(loginName)) {
                return new JsonResult(0, null ,"10000001");
            }
            if (StringUtils.isEmpty(password)) {
                return new JsonResult(0, null ,"10000002");
            }
            if (this.ipLimit(request, resc)) {
                return new JsonResult(0, null, "10000003");
            }

            Map<String, String> param = new HashMap<String, String>(2);
            PubUser pubUser = postLoginMapper.getUserByUserNameOrPhone(loginName);
            /**
             * 数据库数据校验
             */
            if (null == pubUser) {
                return new JsonResult(0,null,"10000005");
            }
            Integer isdelete = pubUser.getIsdelete();
            if (null != isdelete && isdelete == 1) {
                return new JsonResult(0,null,"20000013");
            }
            if (StringUtils.isEmpty(pubUser.getPassword())) {
                return new JsonResult(0, null, "10000006");
            }

            /**
             * 密码校验
             */
            String encrytMD5 = MD5.encrytMD5(password);
            String truePass = pubUser.getPassword();
            if (encrytMD5.equals(truePass) || password.equals(truePass)) {
                // 密码正确
                // 封装返回前端对象
                PubUserVo returnUser = new PubUserVo();
                returnUser.setId(pubUser.getId());
                returnUser.setUserName(pubUser.getUserName());
                returnUser.setSex(pubUser.getSex());
                returnUser.setBirthday(pubUser.getBirthday());
                returnUser.setProvinceId(pubUser.getProvinceId());
                returnUser.setCityId(pubUser.getCityId());
                returnUser.setJob(pubUser.getJob());
                returnUser.setSingleName(pubUser.getSingleName());
                returnUser.setPhone(pubUser.getPhone());
                String avatarurl = pubUser.getAvatarurl();
                if (!StringUtils.isEmpty(avatarurl)) {
                    Attachment attachment = attachmentService.getAttachmentsById(avatarurl);
                    returnUser.setAvatarLink(attachment == null ? null : attachment.getAttPath());
                }
                    session.removeAttribute("userId");
                    return new JsonResult(1, returnUser);

            } else {
                // 密码错误
                session.setAttribute("userId",loginName);
                return new JsonResult(0,null,"10000004");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,null,"10000006");
        }
    }

    /**
     * 限制ip登录次数
     * @param request
     * @param resc
     * @return
     */
    private boolean ipLimit(HttpServletRequest request, int resc){
        // 获取当前用户的IP
        String add = request.getRemoteAddr();

        HttpSession session = request.getSession();

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
    private boolean compareVerification(String verification, HttpServletRequest request) throws MyException {
        if (verification.equals(Tools.getImgCode(request))){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public JsonResult sendSecretCode(String phone, HttpServletRequest request) {
        if (phone.length() != 11 && !phone.startsWith("1")) {
            return new JsonResult(0, null, "110000014");
        }
        Integer userExist = this.userExist(phone);
        switch (userExist) {
            case -1:
                return new JsonResult(0, null, "110000001");
            case 0:
                return new JsonResult(0, null, "110000002");
            case 1:
                String verificationCode = this.sendSMS(phone, request);
                if (null != verificationCode) {
                    request.getSession().setAttribute(this.regiestCodeKey + phone, verificationCode);
                    return new JsonResult(1, verificationCode);
                } else {
                    return new JsonResult(0, null, "110000003");
                }
            default:
                return new JsonResult(0, null, "20000015");
        }
    }

    /**
     * 判断手机号在数据库中的状态
     * @param phone
     * @return -1-手机号为空 0-手机号已使用 1-手机号未使用
     */
    private Integer userExist(String phone) {
        if (StringUtils.isEmpty(phone)) {
            // 手机号为空
            return -1;
        }
        phone = phone.trim();
        PubUser pubUser = postLoginMapper.getUserByPhone(phone);
        if (null != pubUser) {
            // 手机号已使用
            return 0;
        }
        // 手机号未使用
        return 1;
    }

    /**
     * 发送短信验证码
     * @param phone
     * @param request
     * @return 验证码
     */
    private String sendSMS(String phone, HttpServletRequest request) {
        String regCode = config.getRegCode();
        String regPasswod = config.getRegPasswod();
        HttpsRequest httpsRequest = new HttpsRequest();
        String verificationCode = httpsRequest.sendSms("POST", phone, regCode, regPasswod, request);
        return verificationCode;
    }

    @Override
    public JsonResult register(PubUserDto pubUserDto, HttpServletRequest request) {
        String phone = pubUserDto.getPhone();
        String userName = pubUserDto.getUserName();
        String loginType = pubUserDto.getLoginType();
        String surePassword = pubUserDto.getSurePassword();

        String verificationCode = (String) request.getSession().getAttribute(this.regiestCodeKey + phone);
        String password = pubUserDto.getPassword();
   /*     if (loginType.equals("app")){
//            userName = phone;
            surePassword = password;
        }*/
        surePassword = password;
        long lastAccessedTime = request.getSession().getLastAccessedTime();
        /**
         * 非空校验
         */
        if (StringUtils.isEmpty(userName)) {
            return this.registerResponse(new JsonResult(0, null, "110000004"), request);
        }
        if (StringUtils.isEmpty(phone)) {
            return this.registerResponse(new JsonResult(0, null, "110000001"), request);
        }
        if (StringUtils.isEmpty(pubUserDto.getVerificationCode())) {
            return this.registerResponse(new JsonResult(0, null, "110000005"), request);
        }
        if (StringUtils.isEmpty(password)) {
            return this.registerResponse(new JsonResult(0, null, "110000006"), request);
        }
      /*  if (!loginType.equals("app")){
            if (StringUtils.isEmpty(surePassword)) {
                return this.registerResponse(new JsonResult(0, null, "110000007"), request);
            }
        }*/
        if (StringUtils.isEmpty(surePassword)) {
            return this.registerResponse(new JsonResult(0, null, "110000007"), request);
        }
        if (StringUtils.isEmpty(verificationCode)) {
            return this.registerResponse(new JsonResult(0, null, "110000009"), request);
        }
        /**
         * 数据正确性校验
         */
        PubUser user = postLoginMapper.getUserByUserName(userName);
        if (null != user) {
            return this.registerResponse(new JsonResult(0, null, "110000008"), request);
        }
        if (phone.length() != 11 && !phone.startsWith("1")) {
            return new JsonResult(0, null, "110000014");
        }
        user = postLoginMapper.getUserByPhone(phone);
        if (null != user) {
            return this.registerResponse(new JsonResult(0, null, "110000002"), request);
        }
        long currentTimeMillis = System.currentTimeMillis();
        long time = currentTimeMillis - lastAccessedTime;
        if (time > (30 * 60000)) {
            return this.registerResponse(new JsonResult(0, null, "110000013"), request);
        }
        if (!verificationCode.equals(pubUserDto.getVerificationCode())) {
            return this.registerResponse(new JsonResult(0, null, "110000010"), request);
        }
        Integer passwordSize = password.length();
      /*  if (passwordSize < 6 || passwordSize > 20) {
            return this.registerResponse(new JsonResult(0, null, "110000011"), request);
        }*/
        if (!password.equals(surePassword)) {
            return this.registerResponse(new JsonResult(0, null, "110000012"), request);
        }
        /*if (!loginType.equals("app")) {
            if (!password.equals(surePassword)) {
                return this.registerResponse(new JsonResult(0, null, "110000012"), request);
            }
        }*/
        /**
         * 保存新注册的用户
         */
        PubUser pubUser = new PubUser();
        pubUser.setId(IdUtils.getIncreaseIdByNanoTime());
        pubUser.setUserName(userName);
        try {
            pubUser.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1991-01-01"));
        } catch (ParseException e) {
            return this.registerResponse(new JsonResult(0, null, "20000003"), request);
        }
        pubUser.setPhone(phone);
        //pubUser.setPassword(MD5.encrytMD5(password));
        //pubUser.setSurePassword(MD5.encrytMD5(surePassword));
        pubUser.setPassword((password));
        pubUser.setSurePassword((surePassword));
        pubUser.setCreator(creator);
        pubUser.setUpdater(creator);
        Integer index = postLoginMapper.insertPubUser(pubUser);
        if (index < 1) {
            return this.registerResponse(new JsonResult(0, null, "20000016"), request);
        } else {
            // 移除session
            request.getSession().removeAttribute(this.regiestCodeKey + phone);
            return new JsonResult(1, null);
        }
    }

    /**
     * 注册返回验证ip限制
     * @param jsonResult
     * @param request
     * @return
     */
    private JsonResult registerResponse(JsonResult jsonResult, HttpServletRequest request) {
        if (this.ipLimit(request, resc)) {
            return new JsonResult(0, null, "10000003");
        } else {
            return jsonResult;
        }
    }

    @Override
    public JsonResult sendChangePhoneCode(String id, String phone, Integer step, HttpServletRequest request) {
        // 基本数据校验
        if (StringUtils.isEmpty(phone)) {
            return new JsonResult(0, null, "110000001");
        }
        if (phone.length() != 11 && !phone.startsWith("1")) {
            return new JsonResult(0, null, "110000014");
        }
        Integer userExist = this.userExist(phone);
        if (null == step) {
            return new JsonResult(0, null, "20000003");
        }
        if (1 == step && userExist == 0) {
            return new JsonResult(0, null, "110000002");
        }
        if (userExist.equals(step) && null != id) {
            // 发送验证码
            String verificationCode = this.sendSMS(phone, request);
            if (!StringUtils.isEmpty(verificationCode)) {
                request.getSession().setAttribute(String.format(this.changePhoneCodeKey, id, phone, step), verificationCode);
                return new JsonResult(1, verificationCode);
            } else {
                return new JsonResult(0, null, "110000003");
            }
        }
        return new JsonResult(0, null, "20000014");
    }

    @Override
    public JsonResult checkChangePhoneCode(String id, String phone, Integer step, String verification, HttpServletRequest request) {
        /**
         * 必要参数校验
         */
        HttpSession session = request.getSession();
        String verificationCode = (String) session.getAttribute(String.format(this.changePhoneCodeKey, id, phone, step));
        if (null == step || null == id) {
            return new JsonResult(0, null, "20000003");
        }
        if (StringUtils.isEmpty(phone)) {
            return new JsonResult(0, null, "110000001");
        }
        if (phone.length() != 11 && !phone.startsWith("1")) {
            return new JsonResult(0, null, "110000014");
        }
        if (StringUtils.isEmpty(verification)) {
            return new JsonResult(0, null, "110000005");
        }
        if (StringUtils.isEmpty(verificationCode)) {
            return new JsonResult(0, null, "110000009");
        }

        /**
         * 数据校验
         */
        long lastAccessedTime = request.getSession().getLastAccessedTime();
        long currentTimeMillis = System.currentTimeMillis();
        long time = currentTimeMillis - lastAccessedTime;
        if (time > (5 * 60000)) {
            return new JsonResult(0, null, "110000013");
        }
        if (!verification.equals(verificationCode)) {
            return new JsonResult(0, null, "110000010");
        }

        /**
         * 业务处理
         * step = 1修改数据库
         */
        if (1 == step) {
            PubUser pubUser = new PubUser();
            pubUser.setId(id);
            pubUser.setPhone(phone);
            pubUser.setUpdater(id);
            Integer index = postLoginMapper.updatePubUserPhone(pubUser);
            if (index < 1) {
                return new JsonResult(0, null, "20000018");
            } else {
                return new JsonResult(1, phone);
            }
        }
        session.removeAttribute(String.format(this.changePhoneCodeKey, id, phone, step));
        return new JsonResult(1);
    }

    @Override
    public JsonResult changePubUserInfo(PubUser pubUser) {
        if (StringUtils.isEmpty(pubUser.getId()) && StringUtils.isEmpty(pubUser.getUserName())) {
            return new JsonResult(0, null, "110000022");
        }
        if (0 < pubUser.getSingleName().length() && pubUser.getSingleName().length() < 10) {
            return new JsonResult(0, null, "110000023");
        }
        pubUser.setUpdater(pubUser.getId());
        Integer index = postLoginMapper.changePubUserInfo(pubUser);
        if (index < 1) {
            return new JsonResult(0, null, "20000007");
        } else {
            return new JsonResult(1, pubUser);
        }
    }



    @Override
    public JsonResult forgetPassword(PubUserDto pubUserDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String codeMsg = (String) session.getAttribute(this.forgetMMCodeKey + pubUserDto.getPhone());
        String verificationCode = pubUserDto.getVerificationCode();
        String password = pubUserDto.getPassword();
        /**
         * 非空校验
         */
        if (StringUtils.isEmpty(pubUserDto.getPhone())) {
            return new JsonResult(0, null, "110000001");
        }
        if (StringUtils.isEmpty(codeMsg)) {
            return new JsonResult(0, null, "110000009");
        }
        if (StringUtils.isEmpty(verificationCode)) {
            return new JsonResult(0, null, "110000005");
        }
        if (StringUtils.isEmpty(password)) {
            return new JsonResult(0, null, "110000006");
        }
        if (StringUtils.isEmpty(pubUserDto.getSurePassword())) {
            return new JsonResult(0, null, "110000007");
        }
        /**
         * 数据校验
         */
        long lastAccessedTime = request.getSession().getLastAccessedTime();
        long currentTimeMillis = System.currentTimeMillis();
        long time = currentTimeMillis - lastAccessedTime;
        if (time > (5 * 60000)) {
            return new JsonResult(0, null, "110000013");
        }
        if (!verificationCode.equals(codeMsg)) {
            return new JsonResult(0, null, "110000010");
        }
        if (!password.equals(pubUserDto.getSurePassword())) {
            return new JsonResult(0, null, "110000012");
        }
        if (6 < password.length() && password.length() > 20) {
            return new JsonResult(0, null, "110000011");
        }
        /**
         * 保存数据
         */
        pubUserDto.setPassword(MD5.encrytMD5(password));
        pubUserDto.setSurePassword(MD5.encrytMD5(pubUserDto.getSurePassword()));
        PubUser userByPhone = postLoginMapper.getUserByPhone(pubUserDto.getPhone());
        pubUserDto.setId(userByPhone.getId());
        pubUserDto.setUpdater(userByPhone.getId());
        pubUserDto.setUpdater(pubUserDto.getId());
        Integer index = postLoginMapper.updatePubUserPassword(pubUserDto);
        if (index > 0) {
            return new JsonResult(1);
        } else {
            return new JsonResult(0, null, "10000011");
        }
    }

    @Override
    public JsonResult sendForgetPasswordCode(String phone, HttpServletRequest request) {
        if (StringUtils.isEmpty(phone)) {
            return new JsonResult(0, null, "110000001");
        }
        if (this.userExist(phone) == 1) {
            return new JsonResult(0, null, "110000010");
        }
        if (phone.length() != 11 && !phone.startsWith("1")) {
            return new JsonResult(0, null, "110000014");
        }
        String verificationCode = this.sendSMS(phone, request);
        if (StringUtils.isEmpty(verificationCode)) {
            return new JsonResult(0, null, "110000003");
        }
        request.getSession().setAttribute(this.forgetMMCodeKey + phone, verificationCode);
        return new  JsonResult(1, verificationCode);
    }

    @Override
    public JsonResult changePassword(PubUserDto pubUserDto) {
        String oldPassword = pubUserDto.getOldPassword();
        String password = pubUserDto.getPassword();
        String surePassword = pubUserDto.getSurePassword();

        if (StringUtils.isEmpty(pubUserDto.getId())) {
            return new JsonResult(0, null, "000021");
        }
        if (StringUtils.isEmpty(oldPassword)) {
            return new JsonResult(0, null, "110000026");
        }
        if (StringUtils.isEmpty(password)) {
            return new JsonResult(0, null, "110000027");
        }
        if (StringUtils.isEmpty(surePassword)) {
            return new JsonResult(0, null, "110000028");
        }

        PubUser pubUser = postLoginMapper.getUserById(pubUserDto.getId());
        String truePasswrod = pubUser.getPassword();
        /*if (!MD5.encrytMD5(oldPassword).equals(truePasswrod) && !oldPassword.equals(truePasswrod)) {
            return new JsonResult(0, null, "110000029");
        }*/
        /*if (!oldPassword.equals(truePasswrod)) {
            return new JsonResult(0, null, "110000029");
        }*/
        if (oldPassword.equals(password)) {
            return new JsonResult(0, null, "110000036");
        }
        if (!password.equals(surePassword)) {
            return new JsonResult(0, null, "110000012");
        }
        if(!oldPassword.equals(pubUser.getPassword())){
            return new JsonResult(0, null, "110000031");
        }
        //pubUserDto.setPassword(MD5.encrytMD5(password));
        //pubUserDto.setSurePassword(MD5.encrytMD5(surePassword));
        if(oldPassword.equals(pubUser.getPassword())){
            pubUserDto.setPassword(password);
        }

        if(! pubUser.getPassword().equals(surePassword)){
            pubUserDto.setSurePassword(surePassword);
        }
        //pubUserDto.setSurePassword(surePassword);
        pubUserDto.setUpdater(pubUser.getId());
        Integer index = postLoginMapper.updatePubUserPassword(pubUserDto);
        if (index < 1) {
            return new JsonResult(0, null, "20000020");
        } else {
            return new JsonResult(1);
        }
    }

    @Override
    public JsonResult getPubUserById(String id) {
        if (StringUtils.isEmpty(id)) {
            return new JsonResult(0, null, "000021");
        }
        String userId = Tools.getUserId();
        if (org.apache.commons.lang3.StringUtils.isBlank(userId)) {
            return new JsonResult("111116");
        }

        PubUser pubUser = postLoginMapper.getUserById(id);
        pubUser.setPassword(null);
        pubUser.setSurePassword(null);
        if (null != pubUser) {
            PubUserVo returnUser = new PubUserVo();
            returnUser.setId(pubUser.getId());
            returnUser.setUserName(pubUser.getUserName());
            returnUser.setSex(pubUser.getSex());
            returnUser.setBirthday(pubUser.getBirthday());
            if (!StringUtils.isEmpty(pubUser.getBirthday())) {
                returnUser.setBirthdayStr(new SimpleDateFormat("yyyy-MM-dd").format(pubUser.getBirthday()));
            }else {
                returnUser.setBirthdayStr("1990-01-01");
            }
            if (!StringUtils.isEmpty(pubUser.getProvinceId())){
                returnUser.setProvinceId(pubUser.getProvinceId());
            }else {
                returnUser.setProvinceId("655");
            }
            if (!StringUtils.isEmpty(pubUser.getCityId())){
                returnUser.setCityId(pubUser.getCityId());
            }else {
                returnUser.setCityId("656");
            }
            returnUser.setJob(pubUser.getJob());
            returnUser.setSingleName(pubUser.getSingleName());
            returnUser.setPhone(pubUser.getPhone());
            String avatarurl = pubUser.getAvatarurl();
            if (!StringUtils.isEmpty(avatarurl)) {
                Attachment attachment = attachmentService.getAttachmentsById(avatarurl);
                returnUser.setAvatarLink(attachment == null ? null : attachment.getAttPath());
            }
            PostWebCollect postWebCollect = new PostWebCollect();
            postWebCollect.setUserId(pubUser.getId());
            postWebCollect.setType("1");
            returnUser.setCollectSpecialCount(postLoginMapper.getCollectCountByTypeAndUserId(postWebCollect));
            postWebCollect.setType("2");
            returnUser.setCollectCollectionCount(postLoginMapper.getCollectCountByTypeAndUserId(postWebCollect));
            //returnUser.setBookManage(postLoginMapper.getBookManage(postWebCollect.getId()));
            returnUser.setBookManage(postLoginMapper.getBookManage(id));  //此处参数是用户id
            //returnUser.setPublicCuratorCount(postLoginMapper.getPublicCurator(postWebCollect.getId()));
            returnUser.setPublicCuratorCount(postLoginMapper.getPublicCurator(id));
            return new JsonResult(1, returnUser);
        } else {
            return new JsonResult(0, null, "20000008");
        }
    }
}
