package com.tj720.controller.menu;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.system.menu.SysFunction;
import com.tj720.model.common.system.menu.SysFunctionMenuDto;
import com.tj720.model.common.system.menu.SysFunctionTreeDto;
import com.tj720.model.common.system.menu.SysResAuth;
import com.tj720.service.ResAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/10 17:43
 */
@RestController
@RequestMapping("/ResAuth")
public class ResAuthController {
    @Autowired
    ResAuthService resAuthService;

    /**
     * 添加功能权限
     * @param sysResAuth 功能权限
     * @return
     */
    @ControllerAop(action = "添加功能权限")
    @RequestMapping("/addResAuth")
    public JsonResult addResAuth(SysResAuth sysResAuth){
        JsonResult jsonResult = resAuthService.addResAuth(sysResAuth);
        return jsonResult;
    }

    /**
     * 批量添加功能权限
     * @param data 数据
     * @return
     */
    @ControllerAop(action = "批量添加功能权限")
    @RequestMapping("/batchAddResAuth")
    public JsonResult batchAddResAuth(@RequestBody HashMap<String,Object> data){
        String roleId = data.get("roleId").toString();
        List<HashMap<String,String>> res = (List<HashMap<String,String>>)data.get("res");
        JsonResult jsonResult = resAuthService.batchAddResAuth(roleId,res);
        return jsonResult;
    }

    /**
     * 修改功能权限
     * @param sysResAuth 功能权限
     * @return
     */
    @ControllerAop(action = "修改功能权限")
    @RequestMapping("/updateResAuth")
    public JsonResult updateResAuth(SysResAuth sysResAuth){
        JsonResult jsonResult = resAuthService.updateResAuth(sysResAuth);
        return jsonResult;
    }

    /**
     * 批量修改功能权限
     * @param data 数据
     * @return
     */
    @ControllerAop(action = "批量修改功能权限")
    @RequestMapping("/batchUpdateResAuth")
    public JsonResult batchUpdateResAuth(@RequestBody HashMap<String,Object> data){
        String roleId = data.get("roleId").toString();
        List<HashMap<String,String>> res = (List<HashMap<String,String>>)data.get("res");
        JsonResult jsonResult = resAuthService.batchUpdateResAuth(roleId,res);
        return jsonResult;
    }

    /**
     * 删除功能权限
     * @param resAuthId 权限id
     * @return
     */
    @ControllerAop(action = "删除功能权限")
    @RequestMapping("/deleteResAuth")
    public JsonResult deleteResAuth(@RequestParam String resAuthId){
        JsonResult jsonResult = resAuthService.deleteResAuth(resAuthId);
        return jsonResult;
    }

    /**
     * 查询功能权限列表
     * @param userId 用户ID
     * @return
     */
    @ControllerAop(action = "查询功能权限列表")
    @RequestMapping("/queryResAuthList")
    public JsonResult queryResAuthList(String userId){
        JsonResult jsonResult = resAuthService.queryResAuthList(userId);
        return jsonResult;
    }

    /**
     * 根据角色查询功能权限(菜单)
     * @param roleId 角色id
     * @return
     */
    @ControllerAop(action = "根据角色查询功能权限(菜单)")
    @RequestMapping("/queryResListTreeByRole")
    public JsonResult queryResListTreeByRole(@RequestParam String roleId,String functionId,String type){
        JsonResult jsonResult = resAuthService.queryResListTreeByRole(roleId,functionId,type);
        return jsonResult;
    }

    /**
     * 根据功能id和子功能类型查询下级功能列表
     * @param functionId
     * @param type
     * @return
     */
    @ControllerAop(action = "根据功能id和子功能类型查询下级功能列表")
    @RequestMapping("/queryResChildren")
    public JsonResult queryResChildren(String functionId,String type){
        List<SysFunctionTreeDto> list = new ArrayList<SysFunctionTreeDto>();
        list = resAuthService.getChildrenTree(list,null,functionId,type);
        return new JsonResult(1,list);
    }

    /**
     * 根据父id查询子功能列表
      * @param pId
     * @param type
     * @param queryType 查询类型
     * @return
     */
    @ControllerAop(action = "根据父id查询子功能列表")
    @RequestMapping("/queryMenuListByPid")
    public JsonResult queryMenuListByPid(String pId,@RequestParam(defaultValue = "0") String type
            ,@RequestParam(defaultValue = "0") String queryType,@RequestParam String currentUserId){
        try {
            if ("1".equals(queryType)){
                List<SysFunction> list = resAuthService.selectByParentIdPlus(pId,type,currentUserId);
                return new JsonResult(1,list);
            }else {
                List<SysFunction> list = resAuthService.selectByParentId(pId,type);
                return new JsonResult(1,list);
            }
        }catch (Exception e){
            return new JsonResult(0,null,"000001");
        }

    }
    /**
     * 根据父id查询子功能列表
     * @param pId
     * @param type
     * @return
     */
    @ControllerAop(action = "根据父id查询子功能列表")
    @RequestMapping("/queryMenuListAndChildrenByPid")
    public JsonResult queryMenuListAndChildrenByPid(String userId,String pId,@RequestParam(defaultValue = "0") String type){
        List<SysFunctionMenuDto> list = new ArrayList<SysFunctionMenuDto>();
        try {
            JsonResult roleAuthList = queryResAuthList(userId);
            if (null == roleAuthList || roleAuthList.getSuccess() == 0 || roleAuthList.getData() == null){
                return null;
            }
            List<SysFunction> functions = (List<SysFunction>)roleAuthList.getData();
            HashSet<String> keyMap = new HashSet<String>();
            for (int i=0;i<functions.size();i++){
                keyMap.add(functions.get(i).getId());
            }
            list = resAuthService.queryMenuListByPid(list,keyMap,pId,type);
            return new JsonResult(1,list);
        }catch (Exception e){
            return new JsonResult(0,null,"000001");
        }

    }

    @ControllerAop(action = "根据用户id查询功能列表")
    @RequestMapping("/getFunctionByUser")
    public JsonResult getFunctionByUser(@RequestParam String userId,@RequestParam(defaultValue = "1") String type,String currentId){
        JsonResult jsonResult = resAuthService.getFunctionByUser(userId,type,currentId);
        return jsonResult;
    }

    @ControllerAop(action = "根据用户id查询功能列表")
    @RequestMapping("/getFunctionTreeByUser")
    public JsonResult getFunctionTreeByUser(@RequestParam String userId,@RequestParam(defaultValue = "1") String type,String currentId){
        JsonResult jsonResult = resAuthService.getFunctionTreeByUser(userId,type,currentId);
        return jsonResult;
    }

    @RequestMapping("/getFunctionTreeByRole")
    public JsonResult getFunctionTreeByRole(@RequestParam String roleId,@RequestParam(defaultValue = "1") String type,String currentId){
        JsonResult jsonResult = resAuthService.getFunctionTreeByRole(roleId,type,currentId);
        return jsonResult;
    }
}
