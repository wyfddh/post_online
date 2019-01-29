package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.SysDepartmentMapper;
import com.tj720.dao.SysRoleAuthMapper;
import com.tj720.dao.SysUserMapper;
import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.system.role.SysRoleAuth;
import com.tj720.model.common.system.role.SysRoleAuthExample;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.service.RoleAuthService;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import java.util.Map;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/8 11:33
 */
@Service
public class RoleAuthServiceImpl implements RoleAuthService{

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysDepartmentMapper sysDepartmentMapper;
//    private static String userId = Tools.getUserId();
    @Autowired
    SysRoleAuthMapper sysRoleAuthMapper;
    @Autowired
    SysUserMapper userMapper;
    private SysRoleAuth setActionInfo(SysRoleAuth sysRoleAuth,String type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        }catch (Exception e){
            e.printStackTrace();
        }
        sysRoleAuth.setUpdater(Tools.getUserId());
        sysRoleAuth.setUpdateTime(date);
        if (type == "0"){
            sysRoleAuth.setCreateTime(date);
            sysRoleAuth.setCreator(Tools.getUserId());
        }
        return sysRoleAuth;
    }
    @Override
    public JsonResult addRoleAuth(SysRoleAuth sysRoleAuth) {
        if (StringUtils.isEmpty(sysRoleAuth) ){
            return new JsonResult(0,null,"200101");
        }
        List<SysRoleAuth> roleAuth = (List<SysRoleAuth>)getRoleAuth(sysRoleAuth.getRoleId(), sysRoleAuth.getPartyId())
                .getData();
        if (null != roleAuth && roleAuth.size()>0){
            return new JsonResult(0,null,"200110");
        }
        try {
            sysRoleAuth.setId(IdUtils.getIncreaseIdByNanoTime());
            sysRoleAuth = setActionInfo(sysRoleAuth,"0");
            sysRoleAuth.setStatus((byte)1);
            int count = sysRoleAuthMapper.insertSelective(sysRoleAuth);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200104");
        }

    }

    @Override
    public JsonResult addRoleAuthSimple(String roleId, String partyId, String partyType) {
        SysRoleAuth sysRoleAuth = new SysRoleAuth();
        sysRoleAuth.setRoleId(roleId);
        sysRoleAuth.setPartyId(partyId);
        sysRoleAuth.setPartyType(partyType);
        JsonResult jsonResult = addRoleAuth(sysRoleAuth);
        return jsonResult;
    }

    @Transactional
    @Override
    public JsonResult batchAddRoleAuth(String roleId, List<HashMap<String, String>> party) {
        for (HashMap<String, String> map : party) {
            SysRoleAuth sysRoleAuth = new SysRoleAuth();
            sysRoleAuth.setRoleId(roleId);
            sysRoleAuth.setPartyId(map.get("partyId"));
            sysRoleAuth.setPartyType(map.get("partyType"));
            int success = addRoleAuth(sysRoleAuth).getSuccess();
            if (success == 0){
                return new JsonResult(0,null,"200104");
            }
        }
        return new JsonResult(1,null);
    }


    @Override
    public JsonResult updateRoleAuth(SysRoleAuth sysRoleAuth) {
        if (StringUtils.isEmpty(sysRoleAuth) ){
            return new JsonResult(0,null,"200101");
        }
        SysRoleAuth roleAuth = sysRoleAuthMapper.selectByPrimaryKey(sysRoleAuth.getId());
        if (null == roleAuth ){
            return new JsonResult(0,null,"200102");
        }
        try {
            sysRoleAuth = setActionInfo(sysRoleAuth,"1");
            int count = sysRoleAuthMapper.updateByPrimaryKeySelective(sysRoleAuth);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200105");
        }
    }

    @Override
    public JsonResult updateRoleAuth(String roleId, String partyId, String partyType) {
        try {
//            SysRoleAuthExample example = new SysRoleAuthExample();
//            SysRoleAuthExample.Criteria criteria = example.createCriteria();
//            criteria.andPartyIdEqualTo(partyId);
//            criteria.andPartyTypeEqualTo(partyType);
//            SysRoleAuth sysRoleAuth = new SysRoleAuth();
//            sysRoleAuth.setRoleId(roleId);
//            sysRoleAuthMapper.updateByExampleSelective(sysRoleAuth,example);


            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("roleId",roleId);
            map.put("partyId",partyId);
            map.put("partyType",partyType);
            sysRoleAuthMapper.updateByMap(map);

            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200105");
        }

    }

    @Override
    @Transactional
    public JsonResult batchUpdateRoleAuth(String roleId, List<HashMap<String, String>> party) {
        //删除原有权限
//        SysRoleAuthExample example = new SysRoleAuthExample();
//        SysRoleAuthExample.Criteria criteria = example.createCriteria();
//        criteria.andRoleIdEqualTo(roleId);
//        sysRoleAuthMapper.deleteByExample(example);
        sysRoleAuthMapper.deleteByRoleId(roleId);
        JsonResult jsonResult = batchAddRoleAuth(roleId, party);
        return jsonResult;
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public JsonResult getUserList() {
        try {
            List<SysUser> allUserInfo = sysUserMapper.getAllUserInfo();
            return new JsonResult(1,allUserInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("");
        }

    }

    /**
     * 查询所有部门
     *
     * @return
     */
    @Override
    public JsonResult getOrgList() {
        try {
            List<SysDepartment> allOrgInfo = sysDepartmentMapper.getAllOrgInfo();
            return new JsonResult(1,allOrgInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("");
        }
    }

    @Override
    public JsonResult deleteRoleAuth(SysRoleAuth sysRoleAuth) {
        if (StringUtils.isEmpty(sysRoleAuth)){
            return new JsonResult(0,null,"200101");
        }
        JsonResult jsonResult = deleteRoleAuthById(sysRoleAuth.getId());
        return jsonResult;
    }

    @Override
    public JsonResult deleteRoleAuthById(String authId) {
        if (StringUtils.isEmpty(authId) ){
            return new JsonResult(0,null,"200101");
        }
        try {
            int count = sysRoleAuthMapper.deleteByPrimaryKey(authId);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200106");
        }
    }

    @Override
    public JsonResult deleteRoleAuthById(String roleId, String partyId, String partyType) {
        try {
//            SysRoleAuthExample example = new SysRoleAuthExample();
//            SysRoleAuthExample.Criteria criteria = example.createCriteria();
//            if (null != roleId){
//                criteria.andRoleIdEqualTo(roleId);
//            }
//            criteria.andPartyIdEqualTo(partyId);
//            criteria.andPartyTypeEqualTo(partyType);
//            sysRoleAuthMapper.deleteByExample(example);

            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("partyId",partyId);
            map.put("partyType",partyType);
            if (null != roleId) {
                map.put("roleId",roleId);
            }
            sysRoleAuthMapper.deleteByMap(map);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200106");
        }

    }

    @Override
    public JsonResult getRoleAuth(String roleId,String partyId) {
        try {
//            SysRoleAuthExample example = new SysRoleAuthExample();
//            SysRoleAuthExample.Criteria criteria = example.createCriteria();
//            criteria.andRoleIdEqualTo(roleId);
//            criteria.andPartyIdEqualTo(partyId);
//            List<SysRoleAuth> sysRoleAuths = sysRoleAuthMapper.selectByExample(example);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("roleId",roleId);
            map.put("partyId",partyId);
            List<SysRoleAuth> sysRoleAuths = sysRoleAuthMapper.getRolesByMap(map);
            if (null != sysRoleAuths && sysRoleAuths.size()>0){
                return new JsonResult(1,sysRoleAuths.get(0));
            }else{
                return new JsonResult(1,null);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200109");
        }
    }

    @Override
    public JsonResult getRoleAuthList(String partyId,String partyType) {
        try {
//            SysRoleAuthExample example = new SysRoleAuthExample();
//            SysRoleAuthExample.Criteria criteria = example.createCriteria();
//            criteria.andPartyIdEqualTo(partyId);
//            criteria.andPartyTypeEqualTo(partyType);
//            List<SysRoleAuth> sysRoleAuths = sysRoleAuthMapper.selectByExample(example);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("partyId",partyId);
            map.put("partyType",partyType);
            List<SysRoleAuth> sysRoleAuths = sysRoleAuthMapper.getRolesByMap(map);
            return new JsonResult(1,sysRoleAuths);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200109");
        }
    }

    @Override
    public Integer countRoleAuthList(String roleId, String partyType) {
        Integer integer = sysRoleAuthMapper.countRoleAuth(roleId, partyType);
        return integer;
    }

    @Override
    public List<SysRoleAuth> getRoleAuthListByCondition(String roleId, String partyType) {
        try {
            List<SysRoleAuth> roleAuthByCondition = sysRoleAuthMapper.getRoleAuthByCondition(roleId, partyType);
            return roleAuthByCondition;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<SysRoleAuth>();
        }
    }

    @Override
    public JsonResult getUserListByRoleCode(String roleCode) {
        try {
            List<SysUser> userList = sysRoleAuthMapper.getUserListByRoleCode(roleCode);
            return new JsonResult(1,userList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200507");
        }

    }

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public JsonResult getUserByUserId(String userId) {
        try {
            SysUser user = sysRoleAuthMapper.getUserByUserId(userId);
            if (!StringUtils.isEmpty(user)){
                if (user.getStatus()==1){
                    return new JsonResult(1,user);
                }else{
                    return new JsonResult(0,null,"20000013");
                }

            }else {
                return new JsonResult(0,null,"20000023");
            }

        }catch (Exception e){
            return new JsonResult(0,null,"10000006");
        }

    }

    /**
     * 根据用户名查用户列表
     *
     * @param userName 用户名
     * @return
     */
    @Override
    public JsonResult getUserListByName(String userName) {
        try {
            List<SysUser> userListByName = sysRoleAuthMapper.getUserListByName(userName);
            return new JsonResult(1,userListByName);
        }catch (Exception e){
            return new JsonResult(0,null,"10000010");
        }

    }

    /**
     * @param Id
     * @return
     */
    @Override
    public JsonResult getUserById(String Id) {
        try {
            SysUser user = userMapper.selectByPrimaryKey(Id);
            return new JsonResult(1,user);
        }catch (Exception e){
            return new JsonResult(0,null,"10000010");
        }
    }
}
