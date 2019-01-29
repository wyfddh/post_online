package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.SysRoleAuthMapper;
import com.tj720.dao.SysRoleMapper;
import com.tj720.model.common.system.role.*;
import com.tj720.service.RoleService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/8 11:32
 */
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysRoleAuthMapper sysRoleAuthMapper;
    private SysRole setActionInfo(SysRole sysRole, String type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        }catch (Exception e){
            e.printStackTrace();
        }
        sysRole.setUpdater(Tools.getUserId());
        sysRole.setUpdateTime(date);
        if (type == "0"){
            sysRole.setCreateTime(date);
            sysRole.setCreator(Tools.getUserId());
        }
        return sysRole;
    }
    @Override
    public JsonResult addRole(SysRole role) {
        //检验该角色是否存在(对角色编码做不重复校验)
        List<SysRole> sysRoles = new ArrayList<SysRole>();
        //检测角色编码是否为空
        if (!StringUtils.isEmpty(role.getRoleCode()) ){
            sysRoles = (List<SysRole>) getRoleListByRoleCode(role.getRoleCode()).getData();
        }else {
            return new JsonResult(0,null,"200007");
        }
        //若角色已存在
        if (null != sysRoles && sysRoles.size()>0){
            return new JsonResult(0,null,"200001");
        }else {
           try {
               //设置主键
               role.setId(IdUtils.getIncreaseIdByNanoTime());
               role = setActionInfo(role,"0");
               role.setStatus((byte)1);
               //设置默认排序
               if (null == role.getSort()){
                   role.setSort("1");
               }
               int count = sysRoleMapper.insertSelective(role);

               if (count>0){
                   return new JsonResult(1,null);
               }
               else {
                   return new JsonResult(0,null,"200004");
               }
           }catch (Exception e){
                e.printStackTrace();
                return new JsonResult(0,null,"200004");
           }
        }
    }

    @Override
    public JsonResult updateRole(SysRole role) {
        //检验该角色是否存在(对角色编码做不重复校验)
        SysRole sysRoles = null;
        //检测角色编码是否为空
        if (!StringUtils.isEmpty(role.getId()) ){
            sysRoles = sysRoleMapper.selectByPrimaryKey(role.getId());
        }else {
            return new JsonResult(0,null,"200007");
        }
        //若角色不存在
        if (null == sysRoles){
            return new JsonResult(0,null,"200002");
        }else {
            try {
                role = setActionInfo(role,"0");
                int count = sysRoleMapper.updateByPrimaryKeySelective(role);
                if (count>0){
                    return new JsonResult(1,null);
                }
                else {
                    return new JsonResult(0,null,"200005");
                }
            }catch (Exception e){
                e.printStackTrace();
                return new JsonResult(0,null,"200005");
            }
        }
    }

    @Override
    public JsonResult deleteRole(SysRole role) {
        if(StringUtils.isEmpty(role)){
            return new JsonResult(0,null,"200007");
        }
        JsonResult jsonResult = deleteRoleById(role.getId());
        return jsonResult;
    }

    @Override
    public JsonResult deleteRoleById(String roleId) {
        if(StringUtils.isEmpty(roleId)){
            return new JsonResult(0,null,"200007");
        }
        try {
            int count = sysRoleMapper.deleteByPrimaryKey(roleId);
            if (count >0){
                return new JsonResult(1,null);
            }
            else{
                return new JsonResult(0,null,"200006");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200006");
        }
    }

    @Override
    public JsonResult getRole(String roleId) {
        if(StringUtils.isEmpty(roleId)){
            return new JsonResult(0,null,"200008");
        }
        try {
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
            return new JsonResult(1,sysRole);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200009");
        }

    }

    @Override
    public JsonResult getRoleList(String roleCode,String roleName, Page page) {
        try {
            Integer count = sysRoleMapper.countByCondition(roleCode, roleName);
            page.setAllRow(count);
            List<SysRole> sysRoles = sysRoleMapper.selectByCondition(roleCode,roleName,page.getStart(),page.getSize());
            List<SysRoleDto> sysRoleDtos = new ArrayList<SysRoleDto>();
            for (SysRole sysRole : sysRoles) {
                Integer userNumber = sysRoleAuthMapper.countRoleAuth(sysRole.getId(), "user");
                SysRoleDto sysRoleDto = new SysRoleDto(sysRole,userNumber);
                sysRoleDtos.add(sysRoleDto);
            }
            return new JsonResult(1,sysRoleDtos);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200009");
        }
    }

    @Override
    public JsonResult getRoleListByRoleCode(String roleCode) {
        if (StringUtils.isEmpty(roleCode)){
            return new JsonResult(0,null,"200007");
        }
        try {
//            SysRoleExample example = new SysRoleExample();
//            SysRoleExample.Criteria criteria = example.createCriteria();
//            criteria.andRoleCodeEqualTo(roleCode);
//            List<SysRole> sysRoles = sysRoleMapper.selectByExample(example);
            List<SysRole> sysRoles = sysRoleMapper.getRoleListByRoleCode(roleCode);
            return new JsonResult(1,sysRoles);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200009");
        }

    }

    /**
     * 查询角色列表
     *
     * @return
     */
    @Override
    public JsonResult getRoleList() {
        try {
            List<SysRole> roleList = sysRoleMapper.getRoleList();
            return new JsonResult(1,roleList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200009");
        }
    }




}
