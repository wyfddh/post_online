package com.tj720.model.common.system.user;

import com.tj720.model.common.system.role.SysRole;

import java.util.List;

/**
 * 用户全局信息
 * @Author: 程荣凯
 * @Date: 2018/10/16 15:05
 */
public class UserInfo {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户登录名
     */
    private String userCode;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 组织机构id
     */
    private String orgId;
    /**
     * 组织机构名
     */
    private String orgName;
    /**
     * 角色列表
     */
    private List<SysRole> roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
