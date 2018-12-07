package com.tj720.controller.role;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.system.role.SysRole;
import com.tj720.service.CookieService;
import com.tj720.service.RoleService;
import com.tj720.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/10 13:57
 */
@RestController
@RequestMapping("/Role")
public class RoleController {
    @Autowired
    RoleService roleService;


    /**
     * 添加角色
     * @param sysRole 角色
     * @return
     */
    @ControllerAop(action = "添加角色")
    @RequestMapping("/addRole")
    public JsonResult addRole(SysRole sysRole){
        JsonResult jsonResult = roleService.addRole(sysRole);
        return jsonResult;
    }

    /**
     * 修改角色
     * @param sysRole 角色
     * @return
     */
    @ControllerAop(action = "修改角色")
    @RequestMapping("/updateRole")
    public JsonResult updateRole(SysRole sysRole){
        JsonResult jsonResult = roleService.updateRole(sysRole);
        return jsonResult;
    }

    /**
     * 删除角色
     * @param roleId 角色id
     * @return
     */
    @ControllerAop(action = "删除角色")
    @RequestMapping("/deleteRole")
    public JsonResult deleteRole(@RequestParam String roleId){
        JsonResult jsonResult = roleService.deleteRoleById(roleId);
        return jsonResult;
    }

    /**
     * 查询角色列表
     * @param roleCode 角色编码
     * @param roleName 角色名称
     * @param page 当前页数
     * @param limit 每页数据量
     * @return
     */
    @ControllerAop(action = "查询角色列表")
    @RequestMapping("/queryRoleList")
    public LayUiTableJson queryRoleList(String roleCode, String roleName
            , @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult jsonResult = roleService.getRoleList(roleCode,roleName,pageInfo);
        LayUiTableJson layUiTableJson = new LayUiTableJson(0,null,pageInfo.getAllRow(),(List)jsonResult.getData());
        return layUiTableJson;
    }
    @ControllerAop(action = "根据角色id查询角色列表")
    @RequestMapping("/queryRoleById")
    public JsonResult queryRoleById(String roleId){
        JsonResult jsonResult = roleService.getRole(roleId);
        return jsonResult;
    }
    /**
     * 修改角色状态
     * @param roleId 角色id
     * @param status 状态
     * @return
     */
    @ControllerAop(action = "修改角色状态")
    @RequestMapping("/changeStatus")
    public JsonResult changeStatus(@RequestParam String roleId,@RequestParam int status){
        SysRole sysRole = new SysRole();
        sysRole.setId(roleId);
        sysRole.setStatus((byte)status);
        JsonResult jsonResult = roleService.updateRole(sysRole);
        return jsonResult;
    }
    /**
     * 停用角色
     * @param roleId 角色id
     * @return
     */
    @ControllerAop(action = "停用角色")
    @RequestMapping("/suspendRole")
    public JsonResult suspendRole(@RequestParam String roleId){
        SysRole sysRole = new SysRole();
        sysRole.setId(roleId);
        sysRole.setStatus((byte)0);
        JsonResult jsonResult = roleService.updateRole(sysRole);
        return jsonResult;
    }

    /**
     * 启用角色
     * @param roleId 角色id
     * @return
     */
    @ControllerAop(action = "启用角色")
    @RequestMapping("/resumeRole")
    public JsonResult resumeRole(@RequestParam String roleId){
        SysRole sysRole = new SysRole();
        sysRole.setId(roleId);
        sysRole.setStatus((byte)1);
        JsonResult jsonResult = roleService.updateRole(sysRole);
        return jsonResult;
    }

}
