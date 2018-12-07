package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.system.department.SysUserDept;
import com.tj720.model.common.system.role.SysRoleAuth;

import java.util.HashMap;
import java.util.List;



public interface UserDeptService{

    /**
     * 添加用户部门管理信息
     */
    JsonResult insertSelective(SysUserDept sysUserDept);

    /**
     * 添加关联id(userId,deptId)
     */
    JsonResult insertUserDept(String userId, String deptId);

    /**
     * 删除用户部门管理信息
     */
    JsonResult deleteUserDept(SysUserDept sysUserDept);

    /**
     * 删除用户部门管理信息
     */
    JsonResult deleteUserDeptById(String userId);


    /**
     * 删除用户部门管理信息
     */
    JsonResult deleteUserDeptById(String userId,String deptId);

    /**
     * 得到用户部门管理信息,返回数据为集合
     */
    JsonResult getUserDept(String userId, String deptId);

    /**
     * 得到用户部门管理信息,返回数据为对象
     */
    JsonResult getDeptById(String userId);


}
