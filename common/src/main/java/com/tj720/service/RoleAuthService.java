package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.system.role.SysRoleAuth;
import com.tj720.model.common.system.user.SysUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 角色授权
 * @Author: 程荣凯
 * @Date: 2018/9/30 14:44
 */
@Service
public interface RoleAuthService {
    /**
     * 新增角色关联
     * @param sysRoleAuth 角色关联对象
     * @return
     */
    public JsonResult addRoleAuth(SysRoleAuth sysRoleAuth);

    /**
     * 新增角色关联
     * @param roleId 角色ID
     * @param partyId 关联对象id
     * @param partyType 关联对象类型(user:用户,org:部门)
     * @return
     */
    public JsonResult addRoleAuthSimple(String roleId,String partyId,String partyType);

    /**
     * 批量添加角色关联
     * @param roleId 角色id
     * @param party 关联对象列表
     * @return
     */
    public JsonResult batchAddRoleAuth(String roleId,List<HashMap<String,String>> party);

    /**
     * 修改角色关联对象
     * @param sysRoleAuth 角色关联对象
     * @return
     */
    public JsonResult updateRoleAuth(SysRoleAuth sysRoleAuth);

    /**
     * 根据关联对象修改角色
     * @param roleId 角色类型
     * @param partyId 关联对象ID
     * @param partyType 关联对象类型
     * @return
     */
    public JsonResult updateRoleAuth(String roleId,String partyId,String partyType);

    /**
     * 批量修改角色关联对象
     * @param roleId 角色id
     * @param party 关联对象列表
     * @return
     */
    public JsonResult batchUpdateRoleAuth(String roleId,List<HashMap<String,String>> party);
    /**
     * 删除角色关联对象
     * @param sysRoleAuth
     * @return
     */

    public JsonResult deleteRoleAuth(SysRoleAuth sysRoleAuth);

    /**
     * 根据id删除角色关联对象
     * @param authId id
     * @return
     */
    public JsonResult deleteRoleAuthById(String authId);

    /**
     * 根据条件删除对应关联信息
     * @param roleId 角色ID
     * @param partyId 关联对象ID
     * @param partyType 关联对象类型
     * @return
     */
    public JsonResult deleteRoleAuthById(String roleId,String partyId,String partyType);
    /**
     * 根据角色id和关联id查询角色关联对象
     * @param roleId 角色id
     * @param partyId 关联id
     * @return
     */
    public JsonResult getRoleAuth(String roleId,String partyId);

    /**
     * 根据条件查询角色关联对象列表
     * @param partyId 关联id
     * @param partyType 关联类型
     * @return
     */
    public JsonResult getRoleAuthList(String partyId,String partyType);

    /**
     * 统计角色的关联对象数
     * @param roleId 角色id
     * @param partyType 对象类型
     * @return
     */
    public Integer countRoleAuthList(String roleId,String partyType);

    /**
     * 查询角色关联对象数
     * @param roleId 角色id
     * @param partyType 对象类型
     * @return
     */
    public List<SysRoleAuth> getRoleAuthListByCondition(String roleId,String partyType);

    /**
     * 根据用户id查询用户信息
     * @param userId 用户ID
     * @return
     */
    public JsonResult getUserByUserId(String userId);

    /**
     *
     * @param Id
     * @return
     */
    public JsonResult getUserById(String Id);

    /**
     * 根据用户名查用户列表
     * @param userName 用户名
     * @return
     */
    public JsonResult getUserListByName(String userName);

    /**
     * 根据角色查询人员
     * @param roleCode
     * @return
     */

    public JsonResult getUserListByRoleCode(String roleCode);

    /**
     * 查询所有用户
     * @return
     */
    public JsonResult getUserList();

    /**
     * 查询所有部门
     * @return
     */
    public JsonResult getOrgList();
}
