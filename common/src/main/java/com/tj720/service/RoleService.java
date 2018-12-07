package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.system.role.MipRole;
import com.tj720.model.common.system.role.SysRole;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.utils.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 角色管理
 * @Author: 程荣凯
 * @Date: 2018/9/30 14:32
 */
@Service
public interface RoleService {
    /**
     * 添加角色
     * @param role 角色
     * @return
     */
    public JsonResult addRole(SysRole role);


    /**
     * 修改角色
     * @param role 角色
     * @return
     */
    public JsonResult updateRole(SysRole role);

    /**
     * 删除角色
     * @param role 角色
     * @return
     */
    public JsonResult deleteRole(SysRole role);

    /**
     * 删除角色
     * @param roleId 角色ID
     * @return
     */
    public JsonResult deleteRoleById(String roleId);

    /**
     * 获取角色信息
     * @param roleId 角色编码
     * @return
     */
    public JsonResult getRole(String roleId);

    /**
     * 获取角色列表
     * @param roleCode 角色编码
     * @param roleName 角色名称
     * @return
     */
    public JsonResult getRoleList(String roleCode,String roleName, Page page);

    /**
     * 根据角色编码查询角色列表
     * @param roleCode 角色编码
     * @return
     */
    public JsonResult getRoleListByRoleCode(String roleCode);

    /**
     * 查询角色列表
     * @return
     */
    public JsonResult getRoleList();


}
