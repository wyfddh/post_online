package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.SysFunctionMapper;
import com.tj720.dao.SysResAuthMapper;
import com.tj720.dao.SysUserMapper;
import com.tj720.model.common.system.menu.*;
import com.tj720.model.common.system.role.SysRoleAuth;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.service.FunctionService;
import com.tj720.service.ResAuthService;
import com.tj720.service.RoleAuthService;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/10 16:52
 */
@Service
public class ResAuthServiceImpl implements ResAuthService{
    //    private static String userId = Tools.getUserId();
    private static String userId = "sysadmin";
    @Autowired
    SysResAuthMapper sysResAuthMapper;
    @Autowired
    SysFunctionMapper sysFunctionMapper;
    @Autowired
    RoleAuthService roleAuthService;
    @Autowired
    FunctionService functionService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    private Config config;

    private SysResAuth setActionInfo(SysResAuth sysResAuth, String type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        }catch (Exception e){
            e.printStackTrace();
        }
        sysResAuth.setUpdater(userId);
        sysResAuth.setUpdateTime(date);
        if (type == "0"){
            sysResAuth.setCreateTime(date);
            sysResAuth.setCreator(userId);
        }
        return sysResAuth;
    }
    @Override
    public JsonResult addResAuth(SysResAuth sysResAuth) {
        if (StringUtils.isEmpty(sysResAuth) ){
            return new JsonResult(0,null,"200301");
        }
        List<SysResAuth> roleAuth = (List<SysResAuth>)getResAuth(sysResAuth.getRoleId(), sysResAuth.getFunctionId())
                .getData();
        if (null != roleAuth && roleAuth.size()>0){
            return new JsonResult(0,null,"200310");
        }
        try {
            sysResAuth.setId(IdUtils.getIncreaseIdByNanoTime());
            sysResAuth = setActionInfo(sysResAuth,"0");
            sysResAuth.setStatus((byte)1);
            int count = sysResAuthMapper.insertSelective(sysResAuth);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200304");
        }
    }

    @Override
    public JsonResult batchAddResAuth(String roleId, List<HashMap<String, String>> res) {
        for (HashMap<String, String> map : res) {
            SysResAuth sysResAuth = new SysResAuth();
            sysResAuth.setRoleId(roleId);
            sysResAuth.setFunctionId(map.get("functionId"));
            sysResAuth.setFunctionType(map.get("functionType"));
            int success = addResAuth(sysResAuth).getSuccess();
            if (success == 0){
                return new JsonResult(0,null,"200304");
            }
        }
        return new JsonResult(1,null);
    }

    @Override
    public JsonResult updateResAuth(SysResAuth sysResAuth) {
        if (StringUtils.isEmpty(sysResAuth) ){
            return new JsonResult(0,null,"200301");
        }
        try {
            sysResAuth = setActionInfo(sysResAuth,"1");
            int count = sysResAuthMapper.updateByPrimaryKeySelective(sysResAuth);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200305");
        }
    }

    @Override
    public JsonResult batchUpdateResAuth(String roleId, List<HashMap<String, String>> res) {
        //删除原有权限
//        SysResAuthExample example = new SysResAuthExample();
//        SysResAuthExample.Criteria criteria = example.createCriteria();
//        criteria.andRoleIdEqualTo(roleId);
//        sysResAuthMapper.deleteByExample(example);
        sysResAuthMapper.deleteByRoleId(roleId);
        JsonResult jsonResult = batchAddResAuth(roleId, res);
        return jsonResult;
    }

    @Override
    public JsonResult deleteResAuth(String resAuthId) {
        if (StringUtils.isEmpty(resAuthId) ){
            return new JsonResult(0,null,"200301");
        }
        try {
            int count = sysResAuthMapper.deleteByPrimaryKey(resAuthId);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200306");
        }
    }

    @Override
    public JsonResult queryResAuthList(String userId) {
        try {
            JsonResult roleAuthList = roleAuthService.getRoleAuthList(userId, "user");
            List<SysFunction> result = new ArrayList<SysFunction>();
            if (roleAuthList.getSuccess()==1 && roleAuthList.getData() != null){
                List<SysRoleAuth> roleAuths = (List<SysRoleAuth>)roleAuthList.getData();
                for (SysRoleAuth roleAuth : roleAuths) {
                    List<SysFunction> sysResAuths = sysResAuthMapper.queryFunctionList(roleAuth.getRoleId());
                    if (!StringUtils.isEmpty(sysResAuths)){
                        result.addAll(sysResAuths);
                    }
                }
            }
            return new JsonResult(1,result);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200309");
        }

    }

    @Override
    public JsonResult queryResListTreeByRole(String roleId,String functionId,String type) {
        try {
//            SysResAuthExample example = new SysResAuthExample();
//            SysResAuthExample.Criteria criteria = example.createCriteria();
//            criteria.andRoleIdEqualTo(roleId);
//            List<SysResAuth> sysResAuths = sysResAuthMapper.selectByExample(example);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("roleId",roleId);
            List<SysResAuth> sysResAuths = sysResAuthMapper.selectByMap(map);

            List<SysFunctionTreeDto> list = new ArrayList<SysFunctionTreeDto>();
            if (null == functionId){
                functionId = "-1";
            }
            if (null == type){
                type = "0";
            }
            List<SysFunctionTreeDto> childrenTree = getChildrenTree(list, sysResAuths, functionId, type);
            return new JsonResult(1,childrenTree);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200309");
        }

    }


    public JsonResult getResAuth(String roleId,String functionId) {
        try {
//            SysResAuthExample example = new SysResAuthExample();
//            SysResAuthExample.Criteria criteria = example.createCriteria();
//            criteria.andRoleIdEqualTo(roleId);
//            criteria.andFunctionIdEqualTo(functionId);
//            List<SysResAuth> sysResAuths = sysResAuthMapper.selectByExample(example);

            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("roleId",roleId);
            map.put("functionId",functionId);
            List<SysResAuth> sysResAuths = sysResAuthMapper.selectByMap(map);
            if (null != sysResAuths && sysResAuths.size()>0){
                return new JsonResult(1,sysResAuths);
            }else{
                return new JsonResult(1,null);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200309");
        }
    }

    @Override
    public List<SysFunctionTreeDto> getChildrenTree(List<SysFunctionTreeDto> functionList,List<SysResAuth> sysResAuths
            ,String functionId,String type){
            List<SysFunction> sysFunctionList = sysFunctionMapper.selectByParentId(functionId, type);
            if (null == sysFunctionList || sysFunctionList.size() == 0){
                return functionList;
            }else {
                HashSet<String> set = new HashSet<String>();
                if (null != sysResAuths && sysResAuths.size() >0){
                    //判断是否被选中
                    for (SysResAuth sysResAuth : sysResAuths) {
                            set.add(sysResAuth.getFunctionId());
                    }
                }
                for (SysFunction sysFunction : sysFunctionList) {
                    SysFunctionTreeDto sysFunctionTreeDto = new SysFunctionTreeDto();
                    sysFunctionTreeDto.setKey(sysFunction.getId());
                    sysFunctionTreeDto.setLabel(sysFunction.getFunctionname());
                    sysFunctionTreeDto.setSpread(false);
                    sysFunctionTreeDto.setChecked(false);
                    if (set.contains(sysFunction.getId())){
                        sysFunctionTreeDto.setChecked(true);
                    }
                    List<SysFunction> sysFunctions = sysFunctionMapper.selectByParentId(sysFunction.getId(), type);
                    if (null != sysFunctions && sysFunctions.size()>0){
                        List<SysFunctionTreeDto> childrenList = new ArrayList<SysFunctionTreeDto>();
                        sysFunctionTreeDto.setChildren(getChildrenTree(childrenList,sysResAuths,sysFunction.getId(),type));
                        sysFunctionTreeDto.setChecked(false);
                        functionList.add(sysFunctionTreeDto);
                    }else {
                        functionList.add(sysFunctionTreeDto);
                        continue;
                    }
                }
                return functionList;
            }

    }

    /**
     * 查询子功能
     *
     * @param pId  父功能id
     * @param type 功能类型
     * @return
     */
    @Override
    public List<SysFunctionMenuDto> queryMenuListByPid(List<SysFunctionMenuDto> functionList,HashSet<String> resAuths,String pId, String type) {
        if (null == resAuths && resAuths.size()==0){
            return null;
        }
        List<SysFunction> sysFunctionList = sysFunctionMapper.selectByParentId(pId, type);
        if (null == sysFunctionList || sysFunctionList.size() == 0){
            return functionList;
        }else {
            for (SysFunction sysFunction : sysFunctionList) {
                if (!resAuths.contains(sysFunction.getId())){
                    continue;
                }
                SysFunctionMenuDto sysFunctionMenuDto = new SysFunctionMenuDto();
                sysFunctionMenuDto.setKey(sysFunction.getId());
                sysFunctionMenuDto.setName(sysFunction.getFunctionname());
                sysFunctionMenuDto.setUrl(sysFunction.getFunctionurl());
                sysFunctionMenuDto.setIcon(sysFunction.getIconremark());
                sysFunctionMenuDto.setExt1(sysFunction.getExt1());
                sysFunctionMenuDto.setExt2(sysFunction.getExt2());
                sysFunctionMenuDto.setType("1");
                List<SysFunction> sysFunctions = sysFunctionMapper.selectByParentId(sysFunction.getId(), type);
                if (null != sysFunctions && sysFunctions.size()>0){
                    sysFunctionMenuDto.setType("0");
                    List<SysFunctionMenuDto> childrenList = new ArrayList<SysFunctionMenuDto>();
                    sysFunctionMenuDto.setList(queryMenuListByPid(childrenList,resAuths,sysFunction.getId(),type));
                    functionList.add(sysFunctionMenuDto);
                }else {
                    functionList.add(sysFunctionMenuDto);
                    continue;
                }
            }
            return functionList;
        }
    }

    /**
     * 查询子菜单（不查询子集）
     * @param pId 父id
     * @param type 类型
     * @return
     */
    @Override
    public List<SysFunction> selectByParentId(String pId,String type){
        List<SysFunction> functionList = sysFunctionMapper.selectByParentId(pId, type);
        return functionList;
    }

    /**
     * 查询子菜单（不查询子集）
     *
     * @param pId  父id
     * @param type 类型
     * @return
     */
    @Override
    public List<SysFunction> selectByParentIdPlus(String pId, String type,String userId) {
        List<SysFunction> result = new ArrayList<SysFunction>();
        List<SysFunction> functionList = selectByParentId(pId,type);
        JsonResult roleAuthList = queryResAuthList(userId);
        if (null == roleAuthList || roleAuthList.getSuccess() == 0 || roleAuthList.getData() == null){
            return null;
        }
        SysUser user = sysUserMapper.selectByPrimaryKey(Tools.getUserId());
//        SysFunction collect = new SysFunction();
//        collect.setId("-1");
//        collect.setFunctionname("藏品");
//        collect.setFunctionurl(config.getInterfaceCollectPath() + "/loginController.do?login&u_=" + user.getUserName() + "&p_=" + user.getPassword());
//        result.add(collect);
        List<SysFunction> functions = (List<SysFunction>)roleAuthList.getData();
        for (SysFunction function : functionList) {
            for (SysFunction sysFunction : functions) {
                if (sysFunction.getId().equals(function.getId())){
                    if (sysFunction.getFunctionname().equals("藏品")){
                        function.setId("-1");
                        function.setFunctionurl(config.getInterfaceCollectPath() + "/loginController.do?login&u_=" + user.getUserName() + "&p_=" + user.getPassword());
                    }
                    result.add(function);
                    continue;
                }
            }
        }
        return result;
    }

    /**
     * 查询菜单
     *
     * @param userId
     * @param type
     * @return
     */
    @Override
    public JsonResult getFunctionByUser(String userId, String type) {
        try {
            List<SysFunction> functionByUser = sysResAuthMapper.getFunctionByUser(userId, type);
            return new JsonResult(1,functionByUser);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200309");
        }
    }
}
