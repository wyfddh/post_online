package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.model.common.dict.SysDict;
import com.tj720.service.SysDictSevice;
import com.tj720.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyf
 * @date 2018/10/16 10:41
 **/
@RestController
@RequestMapping("/sysDict")
public class SysDictController {

    @Autowired
    private SysDictSevice sysDictSevice;

    /**
     * @author wyf
     * @description  从字典表取下拉框数据
     * @date  2018/10/16 10:57
     * @param arr 请求的参数数组
     * @return com.tj720.controller.framework.JsonResult
     */
    @RequestMapping("getSelectDataByArea")
    public JsonResult getSelectDataByArea(@RequestParam(value = "arr[]") String[] arr) {

        if (arr == null || arr.length <= 0) {
            return new JsonResult(0);
        }
        Map<String, Object> map = sysDictSevice.getDictListByArr(arr);

        return new JsonResult(1, map);
    }

    @RequestMapping("/getSelectDataByKey")
    public JsonResult getSelectDataByKey(@RequestParam String key){
        try {
            List<SysDict> dictListByKey = sysDictSevice.getDictListByKey(key);
            return new JsonResult(1,dictListByKey);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"查询失败");
        }
    }

    @RequestMapping("/getSysDictList")
    public LayUiTableJson getSysDictList(String dictCode, String dictName, String dictType,@RequestParam(defaultValue = "0")
            int page,@RequestParam(defaultValue = "10") int limit){
        try {
            Page pageInfo =  new Page();
            pageInfo.setCurrentPage(page);
            pageInfo.setSize(limit);
            List<SysDict> sysDictList = sysDictSevice.getSysDictList(dictCode, dictName, dictType,pageInfo);
            return new LayUiTableJson(0,null,pageInfo.getAllRow(),sysDictList);
        }catch (Exception e){
            e.printStackTrace();
            return new LayUiTableJson(1,"查询失败",0,null);
        }

    }


    @RequestMapping("/getSysDictListAll")
    public JsonResult getSysDictListAll(){
        try {
            List<SysDict> sysDictList = sysDictSevice.getSysDictListAll();
            return new JsonResult(1,sysDictList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"查询失败");
        }

    }

    @RequestMapping("/addSysDict")
    public JsonResult addSysDict(SysDict sysDict){
        try {
            int result = sysDictSevice.addSysDict(sysDict);
            return new JsonResult(1,result);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"新增失败");
        }

    }

    @RequestMapping("/updateSysDict")
    public JsonResult updateSysDict(SysDict sysDict){
        try {
            int result = sysDictSevice.updateSysDict(sysDict);
            return new JsonResult(1,result);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"修改失败");
        }

    }

    @RequestMapping("/deleteSysDict")
    public JsonResult deleteSysDict(String dictId){
        try {
            int result = sysDictSevice.deleteSysDict(dictId);
            return new JsonResult(1,result);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"删除失败");
        }

    }
    @RequestMapping("/getDictById")
    public JsonResult getDictById(@RequestParam String dictId){
        try {
            SysDict dict = sysDictSevice.getDictById(dictId);
            return new JsonResult(1,dict);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"查询失败");
        }
    }

    @RequestMapping("/getDictByDictCode")
    public JsonResult getDictByDictCode(@RequestParam String dictCode){
        try {
            SysDict dict = sysDictSevice.getDictByDictCode(dictCode);
            return new JsonResult(1,dict);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"查询失败");
        }
    }

    /**
     * 查询功能列表
     * @param type
     * @param functionName
     * @return
     */
    @RequestMapping("/queryDictListTree")
    public LayUiTableJson queryDictListTree(String type,String functionName){
        List<SysDict> list = sysDictSevice.getSysDictListAll();
        List<HashMap> data = new ArrayList<HashMap>();
        for (SysDict sysDict : list) {
            HashMap map = new HashMap();
            map.put("id",sysDict.getId());
            map.put("name",sysDict.getDictName());
            map.put("pId",sysDict.getPid());
            map.put("open",null);
            map.put("chkDisabled","false");
            data.add(map);
        }
        return new LayUiTableJson(0,null,data.size(),data);

    }


}
