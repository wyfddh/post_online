package com.tj720.controller.menu;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.system.menu.MenuTreeDto;
import com.tj720.model.common.system.menu.SysFunction;
import com.tj720.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/10 17:33
 */
@RestController
@RequestMapping("/Function")
public class FunctionController {
    @Autowired
    FunctionService functionService;

    /**
     * 添加功能
     * @param sysFunction 功能
     * @return
     */
    @ControllerAop(action = "添加功能")
    @RequestMapping("/addFunction")
    public JsonResult addFunction(SysFunction sysFunction){
        JsonResult jsonResult = functionService.addFunction(sysFunction);
        return jsonResult;
    }

    /**
     * 修改功能
     * @param sysFunction 功能
     * @return
     */
    @ControllerAop(action = "修改功能")
    @RequestMapping("/updateFunction")
    public JsonResult updateFunction(SysFunction sysFunction){
        JsonResult jsonResult = functionService.updateFunction(sysFunction);
        return jsonResult;
    }

    /**
     * 删除功能
     * @param functionId 功能ID
     * @return
     */
    @ControllerAop(action = "删除功能")
    @RequestMapping("/deleteFunction")
    public JsonResult deleteFunction(@RequestParam String functionId){
        JsonResult jsonResult = functionService.deleteFunctionById(functionId);
        return jsonResult;
    }

    /**
     * 查询功能列表
     * @param type 功能类型
     * @return
     */
    @ControllerAop(action = "查询功能列表")
    @RequestMapping("/queryFunctionList")
    public LayUiTableJson queryFunctionList(String type,String functionName){
        JsonResult jsonResult = functionService.queryFunctionList(type,functionName);
        if (jsonResult.getSuccess() == 1){
            List<MenuTreeDto> list = (List<MenuTreeDto>)jsonResult.getData();
            return new LayUiTableJson(0,"查询成功",list.size(),list);
        }else {
            return new LayUiTableJson(1,"查询失败",0,null);
        }
    }

    /**
     * 查询功能列表
     * @param type
     * @param functionName
     * @return
     */
    @ControllerAop(action = "查询功能列表")
    @RequestMapping("/queryFunctionListTree")
    public LayUiTableJson queryFunctionListTree(String type,String functionName){
        JsonResult jsonResult = functionService.queryFunctionList(type,functionName);
        List<MenuTreeDto> list = (List<MenuTreeDto>)jsonResult.getData();
        List<HashMap> data = new ArrayList<HashMap>();
        for (MenuTreeDto menuTreeDto : list) {
            HashMap map = new HashMap();
            map.put("id",menuTreeDto.getId());
            map.put("name",menuTreeDto.getFunctionname());
            map.put("pId",menuTreeDto.getPid());
            map.put("open",null);
            map.put("chkDisabled","false");
            data.add(map);
        }

        return new LayUiTableJson(0,null,data.size(),data);

    }
    @ControllerAop(action = "根据id查询功能")
    @RequestMapping("/queryFunctionById")
    public JsonResult queryFunctionById(@RequestParam String functionId){
        JsonResult jsonResult = functionService.queryFunctionById(functionId);
        return jsonResult;
    }
}
