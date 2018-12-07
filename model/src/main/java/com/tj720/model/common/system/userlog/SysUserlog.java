package com.tj720.model.common.system.userlog;

import java.io.Serializable;
import java.util.Date;

public class SysUserlog implements Serializable{


    private static final long serialVersionUID = 1162444582807531389L;

    private String signId;       //系统日志id

    private String userName;     //用户账号

    private Date loginTime;     //操作时间

    private String loginIp;     //登录IP地址

    private String action;      //操作记录

    private String status;     //登录状态,默认状态是0  0成功 1失败

    private String browser;     //浏览器类型

    private String os;         //操作系统

    private String msg;        //提示消息

    private String  requestParam;        //请求参数

    private String url;     //请求url

    private String other2;    //备用字段2

    private String other3;    //备用字段3

    public SysUserlog(){

    }

    public SysUserlog(String userName, Date loginTime, String loginIp, String action) {
        this.userName = userName;
        this.loginTime = loginTime;
        this.loginIp = loginIp;
        this.action = action;
    }



    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOther2() {
        return other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2;
    }

    public String getOther3() {
        return other3;
    }

    public void setOther3(String other3) {
        this.other3 = other3;
    }
}