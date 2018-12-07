package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import org.springframework.stereotype.Service;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/26 15:37
 */
@Service
public interface OuterInterService {
    /**
     * 获取所有用户信息
     * @return
     */
    public JsonResult getAllUserInfo();

    /**
     * 获取所有角色信息
     * @return
     */
    public JsonResult getAllRoleInfo();

    /**
     * 获取所有组织机构信息
     * @return
     */
    public JsonResult getAllOrgInfo();

    /**
     * 获取所有角色权限信息
     * @return
     */
    public JsonResult getAllRoleAuthInfo();

    /**
     * 获取所有用户部门关联信息
     * @return
     */
    public JsonResult getAllUserDeptInfo();
}
