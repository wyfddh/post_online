package com.tj720.controller.outerInterface;

import com.tj720.controller.framework.JsonResult;
import com.tj720.service.OuterInterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 外部系统调用接口
 * @Author: 程荣凯
 * @Date: 2018/10/26 15:33
 */
@RequestMapping("/OuterInterface")
@RestController
public class OuterInterfaceController {
    @Autowired
    OuterInterService outerInterService;
    /**
     * 获取所有用户信息
     * @return
     */
    @RequestMapping("/getAllUserInfo")
    public JsonResult getAllUserInfo(){
        return outerInterService.getAllUserInfo();
    }

    /**
     * 获取所有角色信息
     * @return
     */
    @RequestMapping("/getAllRoleInfo")
    public JsonResult getAllRoleInfo(){
        return outerInterService.getAllRoleInfo();
    }

    /**
     * 获取所有组织机构信息
     * @return
     */
    @RequestMapping("/getAllOrgInfo")
    public JsonResult getAllOrgInfo(){
        return outerInterService.getAllOrgInfo();
    }

    /**
     * 获取所有角色权限信息
     * @return
     */
    @RequestMapping("/getAllRoleAuthInfo")
    public JsonResult getAllRoleAuthInfo(){
        return outerInterService.getAllRoleAuthInfo();
    }

    /**
     * 获取所有用户部门关联信息
     * @return
     */
    @RequestMapping("/getAllUserDeptInfo")
    public JsonResult getAllUserDeptInfo(){
        return outerInterService.getAllUserDeptInfo();
    }
}
