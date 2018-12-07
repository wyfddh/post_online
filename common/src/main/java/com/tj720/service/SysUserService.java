package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.pubuser.PubUserDto;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.model.common.system.user.SysUserDto;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Created by MyPC on 2018/10/9.
 */
@Service
public interface SysUserService {

    /**
     * 添加用户
     * @param sysUserDto 用户
     * @return
     */
    public JsonResult addSysUser(SysUserDto sysUserDto);


    /**
     * 修改用户
     * @param sysUserDto 用户
     * @return
     */
    public JsonResult updateSysUser(SysUserDto sysUserDto);

    /**
     * 控制用户状态
     * @param sysUser 用户
     * @return
     */
    public JsonResult updateSysUserType(SysUser sysUser);

    /**
     * 修改用户密码(无用)
     * @param sysUser 用户
     * @return
     */
    public JsonResult updateSysUserPossword(SysUser sysUser);


    /**
     * 删除用户
     * @param sysUserDto 用户
     * @return
     */
    public JsonResult deleteSysUser(SysUserDto sysUserDto);

    /**
     * 删除用户
     * @param sysUserId 用户ID
     * @return
     */
    public JsonResult deleteSysUserById(String sysUserId);

    /**
     * 获取用户信息
     * @param sysUserId 单个用户信息
     * @return
     */
    public JsonResult getSysUserById(String sysUserId);

    /**
     * 获取用户列表
     * @param name, departmentName, orderBy, currentPage, size
     * @return
     */
    public JSONObject getSysUserList(String name, String departmentName, String orderBy, Integer currentPage, Integer size);

    /**
     * 根据用户名查询用户列表
     * @param sysUserName 用户名
     * @return
     */
    public JsonResult getSysUserListBySysUserName(String sysUserName);


    /**
     * 根据deptId查询用户记录
     * @param deptId 部门id
     * @return
     */
    JsonResult getUserById(String deptId) throws Exception;

    /**
     * @author wyf
     * @description   根据部门名获取所属用户列表
     * @date  2018/10/25 16:49
     * @param  name  部门名
     * @return com.tj720.controller.framework.JsonResult
     */
    JsonResult getUserByDepartmentName(String name);

    /**
     * @author wyf
     * @description   根据角色code获取所属用户列表
     * @date  2018/10/25 16:50
     * @param   code  角色code
     * @return com.tj720.controller.framework.JsonResult
     */
    JsonResult getUserByRoleCode(String code);

    /**
     * 修改密码
     * @return
     */
    JsonResult changePassword(SysUserDto sysUserDto);


}
