package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.*;
import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.system.department.SysUserDept;
import com.tj720.model.common.system.role.SysRole;
import com.tj720.model.common.system.role.SysRoleAuth;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.service.OuterInterService;
import com.tj720.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/26 15:39
 */
@Service
public class OuterInterServiceImpl implements OuterInterService{
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysDepartmentMapper sysDepartmentMapper;
    @Autowired
    SysRoleAuthMapper sysRoleAuthMapper;
    @Autowired
    SysUserDeptMapper sysUserDeptMapper;
    /**
     * 获取所有用户信息
     *
     * @return
     */
    @Override
    public JsonResult getAllUserInfo() {
        try {
            List<SysUser> userInfo = sysUserMapper.getAllUserInfo();
            return new JsonResult(1,userInfo);
        }catch (Exception e){
            return new JsonResult("200507");
        }
    }

    /**
     * 获取所有角色信息
     *
     * @return
     */
    @Override
    public JsonResult getAllRoleInfo() {
        try {
            List<SysRole> roleList = sysRoleMapper.getRoleList();
            return new JsonResult(1,roleList);
        }catch (Exception e){
            return new JsonResult("200507");
        }
    }

    /**
     * 获取所有组织机构信息
     *
     * @return
     */
    @Override
    public JsonResult getAllOrgInfo() {
        try {
            List<SysDepartment> departmentList = sysDepartmentMapper.getAllOrgInfo();
            return new JsonResult(1,departmentList);
        }catch (Exception e){
            return new JsonResult("200507");
        }
    }

    /**
     * 获取所有角色权限信息
     *
     * @return
     */
    @Override
    public JsonResult getAllRoleAuthInfo() {
        try {
            List<SysRoleAuth> roleAuthList = sysRoleAuthMapper.getAllRoleAuthInfo();
            return new JsonResult(1,roleAuthList);
        }catch (Exception e){
            return new JsonResult("200507");
        }
    }

    /**
     * 获取所有用户部门关联信息
     *
     * @return
     */
    @Override
    public JsonResult getAllUserDeptInfo() {
        try {
            List<SysUserDept> userDeptInfo = sysUserDeptMapper.getAllUserDeptInfo();
            return new JsonResult(1,userDeptInfo);
        }catch (Exception e){
            return new JsonResult("200507");
        }
    }
}
