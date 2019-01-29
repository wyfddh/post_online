package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.system.menu.SysFunction;
import com.tj720.model.common.system.menu.SysFunctionMenuDto;
import com.tj720.model.common.system.menu.SysFunctionTreeDto;
import com.tj720.model.common.system.menu.SysResAuth;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 功能权限服务
 * @Author: 程荣凯
 * @Date: 2018/10/10 16:37
 */
@Service
public interface ResAuthService {
    /**
     * 添加功能权限
     * @param sysResAuth 功能权限
     * @return
     */
    public JsonResult addResAuth(SysResAuth sysResAuth);

    /**
     * 批量添加功能权限
     * @param roleId 角色ID
     * @param res 资源列表
     * @return
     */
    public JsonResult batchAddResAuth(String roleId, List<HashMap<String,String>> res);
    /**
     * 修改功能权限
     * @param sysResAuth 功能权限
     * @return
     */
    public JsonResult updateResAuth(SysResAuth sysResAuth);

    /**
     * 批量修改功能权限
     * @param roleId 角色ID
     * @param res 资源列表
     * @return
     */
    public JsonResult batchUpdateResAuth(String roleId, List<HashMap<String,String>> res);
    /**
     * 删除功能权限
     * @param resAuthId 功能权限
     * @return
     */
    public JsonResult deleteResAuth(String resAuthId);

    /**
     * 查询功能权限列表
     * @param userId 用户ID
     * @return
     */
    public JsonResult queryResAuthList(String userId);

    /**
     * 查询功能列表
     * @param roleId 角色ID
     * @return
     */
    public JsonResult queryResListTreeByRole(String roleId,String functionId,String type);

    /**
     * 查询子功能树
     * @param functionList 功能列表
     * @param sysResAuths 权限列表
     * @param functionId 功能列表
     * @param type 功能类型
     * @return
     */
    public List<SysFunctionTreeDto> getChildrenTree(List<SysFunctionTreeDto> functionList, List<SysResAuth> sysResAuths
            , String functionId, String type);

    /**
     * 查询子功能
     * @param pId 父功能id
     * @param type 功能类型
     * @return
     */
    public List<SysFunctionMenuDto> queryMenuListByPid(List<SysFunctionMenuDto> functionList, HashSet<String> resAuths, String pId, String type);

    /**
     * 查询子菜单（不查询子集）
     * @param pId 父id
     * @param type 类型
     * @return
     */
    public List<SysFunction> selectByParentId(String pId, String type);
    /**
     * 查询子菜单（不查询子集）
     * @param pId 父id
     * @param type 类型
     * @return
     */
    public List<SysFunction> selectByParentIdPlus(String pId, String type,String currentUserId);

    /**
     * 查询菜单
     * @param userId
     * @param type
     * @return
     */
    public JsonResult getFunctionByUser(String userId,String type,String currentId);

    /**
     * 查询菜单
     * @param userId
     * @param type
     * @return
     */
    public JsonResult getFunctionTreeByUser(String userId,String type,String currentId);

    public JsonResult getFunctionTreeByRole(String roleId,String type,String currentId);

    /**
     * 查询数据权限规则
     * @param userId 用户id
     * @param module 所属模块
     * @return
     */
    public JsonResult getDataAuthRule(String userId,String module);
}
