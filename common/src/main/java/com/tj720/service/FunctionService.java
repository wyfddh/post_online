package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.system.menu.SysFunction;
import org.springframework.stereotype.Service;

/**
 * 功能服务
 * @Author: 程荣凯
 * @Date: 2018/10/10 15:50
 */
@Service
public interface FunctionService {
    /**
     * 添加功能(菜单/按钮/其他)
     * @param sysFunction 功能对象
     * @return
     */
    public JsonResult addFunction(SysFunction sysFunction);

    /**
     * 修改功能
     * @param sysFunction 功能对象
     * @return
     */
    public JsonResult updateFunction(SysFunction sysFunction);

    /**
     * 删除功能对象
     * @param functionId 功能对象id
     * @return
     */
    public JsonResult deleteFunctionById(String functionId);

    /**
     * 查询功能列表
     * @param type 功能类型
     * @return
     */
    public JsonResult queryFunctionList(String type,String functionName);

    /**
     * 根据ID查询菜单
     * @param functionId
     * @return
     */
    public JsonResult queryFunctionById(String functionId);

    /**
     * 根据id查询子列表
     * @param functionId 功能id
     * @param type 子功能类型
     * @return
     */
    public JsonResult queryChildrenListById(String functionId,String type);


}
