package com.tj720.controller.systemmanage;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.model.common.system.user.SysUserDto;
import com.tj720.service.RoleService;
import com.tj720.service.SysDepartmentService;
import com.tj720.service.SysUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/9/18.
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserManagerController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysDepartmentService sysDepartmentService;

    @Autowired
    RoleService roleService;

    /**
     * 添加用户
     * @param sysUserDto 用户
     * @return
     */
    @ControllerAop(action= "添加用户")
    @RequestMapping("/addSysUser")
    public JsonResult addSysUser(@RequestBody SysUserDto sysUserDto){

        return sysUserService.addSysUser(sysUserDto);
    }

    /**
     * 修改用户
     * @param sysUserDto 用户
     * @return
     */
    @ControllerAop(action= "修改用户")
    @RequestMapping("/updateSysUser")
    public JsonResult updateSysUser(@RequestBody SysUserDto sysUserDto){

        return sysUserService.updateSysUser(sysUserDto);
    }

    /**
     * 控制用户状态
     * @param sysUser 用户，前台需传入一个状态标识
     * @return
     */
    @ControllerAop(action= "修改用户状态")
    @RequestMapping("/updateSysUserType")
    public JsonResult updateSysUserType(@RequestBody SysUser sysUser){
        return sysUserService.updateSysUserType(sysUser);
    }

    /**
     * 用户修改密码
     * @param sysUser 用户，前台需一个对象
     * @return
     */
    @RequestMapping("/updateSysUserPossword")
    public JsonResult updateSysUserPossword(@RequestBody SysUser sysUser){
        return sysUserService.updateSysUserPossword(sysUser);
    }



    /**
     * 修改密码   (OK)
     * @param
     * @return
     */
    @ControllerAop(action= "修改用户密码")
    @RequestMapping("/changePassword")
    public JsonResult changePassword(@RequestBody SysUserDto sysUserDto){
        try {
            return  sysUserService.changePassword(sysUserDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000020");
    }



    /**
     * 删除用户 假删 此方法不用
     * @param sysUserDto 用户
     * @return
     */
    @ControllerAop(action= "删除用户")
    @RequestMapping("/deleteSysUser")
    public JsonResult deleteSysUser(SysUserDto sysUserDto){
        return sysUserService.deleteSysUser(sysUserDto);
    }

    /**
     * 删除用户 假删
     * @param id 用户
     * @return
     */
    @ControllerAop(action= "删除单个用户")
    @RequestMapping("/deleteSysUserById")
    public JsonResult deleteSysUserById(@RequestParam("id") String id){
        return sysUserService.deleteSysUserById(id);
    }


    /**
     * 查询用户列表 layui url只能用get方法
     * @param
     * @return
     */
    @ControllerAop(action= "删除用户")
    @RequestMapping("/查询用户列表")
    public JSONObject getSysUserList(String name, String departmentName, String orderBy,
                                     @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size){

        return sysUserService.getSysUserList(name, departmentName, orderBy, currentPage, size);
    }


    /**
     * 查询单个用户
     * @param id
     * @return
     */
    @ControllerAop(action="查询单个用户")
    @RequestMapping("/getSysUserById")
    public JsonResult getSysUserById(@RequestParam("id") String id){

        return sysUserService.getSysUserById(id);
    }


    /**
     * 查询所有部门名称
     * @param
     * @return
     */
    @ControllerAop(action="查询所有部门名称")
    @RequestMapping("/getDeptName")
    public JsonResult getDeptName() throws Exception {
        JSONObject jsonObject = sysDepartmentService.getDeptOptions();
        return new JsonResult(1, jsonObject);
    }

    /**
     * 查询所有部门名称
     * @param
     * @return
     */
    @ControllerAop(action="查询所有角色名称")
    @RequestMapping("/getRoleName")
    public JsonResult getRoleName(){
        return roleService.getRoleList();
    }


}
