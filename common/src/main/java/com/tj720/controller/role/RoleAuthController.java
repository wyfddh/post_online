package com.tj720.controller.role;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.system.role.SysRoleAuth;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.service.RoleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/9 17:37
 */

@RestController
@RequestMapping("/RoleAuth")
public class RoleAuthController {
    @Autowired
    RoleAuthService roleAuthService;

    /**
     * 新增角色关联
     * @param sysRoleAuth 角色关联对象
     * @return
     */
    @ControllerAop(action = "新增角色关联")
    @RequestMapping("/addRoleAuth")
    public JsonResult addRoleAuth(SysRoleAuth sysRoleAuth){
        JsonResult result = roleAuthService.addRoleAuth(sysRoleAuth);
        return result;
    }

    /**
     * 修改角色关联
     * @param sysRoleAuth 角色关联对象
     * @return
     */
    @ControllerAop(action = "修改角色关联")
    @RequestMapping("/updateRoleAuth")
    public JsonResult updateRoleAuth(SysRoleAuth sysRoleAuth){
        JsonResult result = roleAuthService.updateRoleAuth(sysRoleAuth);
        return result;
    }

    /**
     * 删除角色关联
     * @param authId
     * @return
     */
    @ControllerAop(action = "删除角色关联")
    @RequestMapping("/deleteRoleAuth")
    public JsonResult deleteRoleAuth(@RequestParam String authId){
        JsonResult result = roleAuthService.deleteRoleAuthById(authId);
        return result;
    }

    /**
     * 查询角色关联的对象
     * @param roleId 角色ID
     * @return
     */
    @ControllerAop(action = "查询角色关联的对象")
    @RequestMapping("/queryRoleAuthList")
    public JsonResult queryRoleAuthList(String roleId){
        List<SysRoleAuth> roleAuthListByCondition = roleAuthService.getRoleAuthListByCondition(roleId, null);
        return new JsonResult(1,roleAuthListByCondition);
    }

    /**
     * 添加角色关联
     * @return
     */
    @ControllerAop(action = "添加角色关联")
    @RequestMapping("/batchAddRoleAuth")
    public JsonResult batchAddRoleAuth(@RequestBody HashMap<String,Object> data){
        String roleId = data.get("roleId").toString();
        List<HashMap<String,String>> party = (List<HashMap<String,String>>)data.get("party");
        JsonResult jsonResult = roleAuthService.batchAddRoleAuth(roleId, party);
        return jsonResult;
    }

    /**
     * 修改角色关联
     * @param data
     * @return
     */
    @ControllerAop(action = "修改角色关联")
    @RequestMapping("/batchUpdateRoleAuth")
    public JsonResult batchUpdateRoleAuth(@RequestBody HashMap<String,Object> data){
        String roleId = data.get("roleId").toString();
        List<HashMap<String,String>> party = (List<HashMap<String,String>>)data.get("party");
        JsonResult jsonResult = roleAuthService.batchUpdateRoleAuth(roleId, party);
        return jsonResult;
    }
    @ControllerAop(action = "根据用户id查询用户信息")
    @RequestMapping("/getUserByUserId")
    public JsonResult getUserByUserId(@RequestParam String userId){
        JsonResult userByUserId = roleAuthService.getUserByUserId(userId);
        return userByUserId;
    }
    @ControllerAop(action = "根据角色编码查询用户列表")
    @RequestMapping("/getUserListByRoleCode")
    public JsonResult getUserListByRoleCode(@RequestParam String roleCode){
        JsonResult users = roleAuthService.getUserListByRoleCode(roleCode);
        return users;
    }
    @ControllerAop(action = "查询用户列表")
    @RequestMapping("/getAllUserList")
    public JsonResult getAllUserList(){
        JsonResult users = roleAuthService.getUserList();
        return users;
    }
    @ControllerAop(action = "查询部门列表")
    @RequestMapping("/getAllOrgList")
    public JsonResult getAllOrgList(){
        JsonResult users = roleAuthService.getOrgList();
        return users;
    }
    @ControllerAop(action = "根据id查询用户")
    @RequestMapping("getUserById")
    public JsonResult getUserById(@RequestParam String id){
        JsonResult jsonResult = roleAuthService.getUserById(id);
        return jsonResult;
    }

}
