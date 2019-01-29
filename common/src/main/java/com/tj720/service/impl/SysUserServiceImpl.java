package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.SysUserDeptMapper;
import com.tj720.dao.SysUserMapper;
import com.tj720.model.common.pubuser.PubUser;
import com.tj720.model.common.system.department.SysUserDept;
import com.tj720.model.common.system.department.SysUserDeptExample;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.model.common.system.user.SysUserDto;
import com.tj720.model.common.system.user.SysUserExample;
import com.tj720.service.RoleAuthService;
import com.tj720.service.SysUserService;
import com.tj720.service.UserDeptService;
import com.tj720.utils.MD5;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import com.tj720.utils.common.Utils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: 余超
 * @Date: 2018/10/8 11:31
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysUserDeptMapper sysUserDeptMapper;

    @Autowired
    RoleAuthService roleAuthService;

    @Autowired
    UserDeptService userDeptService;


    private SysUser setTimeInfo(SysUser sysUser, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sysUser.setUpdateTime(date);
        sysUser.setUpdater(Tools.getUserId());
        // 0代表 没有创建的用户，录入创建人和创建时间
        if ("0".equals(type)) {
            sysUser.setCreateTime(date);
            sysUser.setCreator(Tools.getUserId());
        }
        return sysUser;
    }

    @Override
    @Transactional
    public JsonResult addSysUser(SysUserDto sysUserDto) {
        List<SysUser> user = null;
        //检测该用户是否存在，（用户名）
        String userName = sysUserDto.getSysUser().getUserName();
        if (StringUtils.isEmpty(userName)) {
//            SysUserExample example = new SysUserExample();
//            SysUserExample.Criteria criteria = example.createCriteria();
//            criteria.andUserNameEqualTo(userName);
//            criteria.andIsdeleteEqualTo(0);
//            user = sysUserMapper.selectByExample(example);
//            HashMap<String,Object> map = new HashMap<String,Object>();
//            map.put("userName",userName);
////            map.put("isdelete",0);
//            user = sysUserMapper.getByMap(map);
            return new JsonResult("20000022");

        }else{
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("userName",userName);
            user = sysUserMapper.getByMap(map);
        }

        if (user != null && user.size()>0) {
            return new JsonResult(0, null, "20000002");
        } else {
            try {
                // 设置主键
                String id = IdUtils.getIncreaseIdByNanoTime();
                SysUser sysUser = sysUserDto.getSysUser();
                sysUser.setId(id);
                sysUser = setTimeInfo(sysUser, "0");
                // 加密密码
                sysUser.setPassword(sysUserDto.getSysUser().getPassword());
                sysUser.setStatus(1);
                sysUser.setIsdelete(0);
                //插入用户表数据
                int count = sysUserMapper.insertSelective(sysUser);
                // 插入用户角色关联表数据
                JsonResult URjsonResult = roleAuthService.addRoleAuthSimple(sysUserDto.getSysRoleAuth().getRoleId(),
                        id, "user");
                // 插入用户部门关联数据
                JsonResult UDjsonResult = userDeptService.insertUserDept(id, sysUserDto
                        .getSysUserDept().getDeptId());
                if (count > 0 && URjsonResult.getSuccess() == 1 && UDjsonResult.getSuccess() == 1) {
                    return new JsonResult(1, null);
                } else {
                    return new JsonResult(0, null, "20000006");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new JsonResult(0, null, "20000003");
            }
        }
    }

    // 此时编辑用户时，需不需要校验密码
    @Override
    @Transactional
    public JsonResult updateSysUser(SysUserDto sysUserDto) {

        int userNum = 1;
        //检测该用户是否存在，（用户名）
        String userId = sysUserDto.getSysUser().getId();
        if (StringUtils.isEmpty(userId)) {
            return new JsonResult(0, null, "20000007");
        }
        else {
//            SysUserExample example = new SysUserExample();
//            SysUserExample.Criteria criteria = example.createCriteria();
//            criteria.andIdEqualTo(userId);
//            criteria.andIsdeleteEqualTo(0);
//            userNum = sysUserMapper.countByExample(example);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("userId",userId);
            userNum = sysUserMapper.countByMap(map);
        }
        if (userNum <= 0) {
            return new JsonResult(0, null, "20000008");
        } else {
            try {
                SysUser sysUser = sysUserDto.getSysUser();
                sysUser = setTimeInfo(sysUser, "1");
                sysUser.setStatus(1);
                // 加密密码
                sysUser.setPassword(sysUserDto.getSysUser().getPassword());
                int count = sysUserMapper.updateByPrimaryKeySelective(sysUser);
                JsonResult URjsonResult = roleAuthService.updateRoleAuth(sysUserDto.getSysRoleAuth().getRoleId(), sysUserDto
                        .getSysUser().getId(), "user");
                // 调用部门角色的接口
                SysUserDept sysUserDept = sysUserDto.getSysUserDept();
                sysUserDept.setUserId(sysUserDto.getSysUser().getId());
                sysUserDept.setDeptId(sysUserDto.getSysUserDept().getDeptId());
                //SysUserDeptExample example = new SysUserDeptExample();
                //SysUserDeptExample.Criteria criteria = example.createCriteria();
                //criteria.andUserIdEqualTo(sysUserDto.getSysUser().getId());
                //int dept = sysUserDeptMapper.updateByExample(sysUserDept, example);
                JsonResult UDjsonResult = updateUserDept(sysUserDept);
                if (count > 0 && URjsonResult.getSuccess() == 1 && UDjsonResult.getSuccess() == 1) {
                //if (count > 0 && URjsonResult.getSuccess() == 1 && dept >= 1) {
                    return new JsonResult(1, null);

                } else {
                    return new JsonResult(0, null, "20000009");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new JsonResult(0, null, "20000003");
            }
        }
    }

    public  JsonResult updateUserDept(SysUserDept sysUserDept) {
//        SysUserDeptExample example = new SysUserDeptExample();
//        SysUserDeptExample.Criteria criteria = example.createCriteria();
//        criteria.andUserIdEqualTo(sysUserDept.getUserId());
//        SysUserDept s = new SysUserDept();
//        s.setDeptId(sysUserDept.getDeptId());
//        int count = sysUserDeptMapper.updateByExampleSelective(s, example);
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("userId",sysUserDept.getUserId());
        map.put("deptId",sysUserDept.getDeptId());
        int count = sysUserDeptMapper.updateByMap(map);
        if (count > 0) {
            return new JsonResult(1, null);
        } else {
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult updateSysUserType(SysUser sysUser) {
        try {
            if (StringUtils.isEmpty(sysUser.getId())) {
                return new JsonResult(0, null, "20000001");
            } else {
                int count = sysUserMapper.updateByPrimaryKeySelective(sysUser);
                if (count > 0) {
                    return new JsonResult(1, null);
                } else {
                    return new JsonResult(0, null, "20000007");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult updateSysUserPossword(SysUser sysUser) {

        return null;
    }

    @Override
    @Transactional
    public JsonResult deleteSysUser(SysUserDto sysUserDto) {
        try {
            if (StringUtils.isEmpty(sysUserDto.getSysUser().getId())) {
                return new JsonResult(0, null, "20000008");
            }
            // 是否删除关联表的数据 -> 删除
            JsonResult UJsonResult = deleteSysUserById(sysUserDto.getSysUser().getId());
            JsonResult URJsonResult = roleAuthService.deleteRoleAuthById(sysUserDto.getSysRoleAuth().getRoleId(), sysUserDto.getSysUser().getId(),
                    "user");
            JsonResult UDJsonResult = userDeptService.deleteUserDeptById(sysUserDto.getSysUser().getId(),
                    sysUserDto.getSysUserDept().getDeptId());
            if (UJsonResult.getSuccess() == 1 && URJsonResult.getSuccess() == 1 && UDJsonResult.getSuccess() == 1) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, null, "20000012");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    @Transactional
    public JsonResult deleteSysUserById(String sysUserId) {
        if (StringUtils.isEmpty(sysUserId)) {
            return new JsonResult(0, null, "20000008");
        }
        try {


            // 假删除 控制isdelete 字段 1 为删除  0为存在
//            int count = sysUserMapper.updateByPrimaryKeySelective(sysUser);

            int count = sysUserMapper.deleteByPrimaryKey(sysUserId);
            int URcount = roleAuthService.deleteRoleAuthById(null, sysUserId, "user").getSuccess();
            int UDcount = userDeptService.deleteUserDeptById(sysUserId).getSuccess();

            if (count > 0 && URcount == 1 && UDcount == 1) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, null, "20000012");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult getSysUserById(String id) {
        if (StringUtils.isEmpty(id)) {
            return new JsonResult(0, null, "20000008");
        } else {
            try {
                // getSysUserById 使用的join on 无需角色，部门接口
                List<SysUserDto> sysUserDto = sysUserMapper.getSysUserById(id);

                return new JsonResult(1, sysUserDto.get(0));
            } catch (Exception e) {
                e.printStackTrace();
                return new JsonResult(0, null, "20000003");
            }
        }
    }

    @Override
    public JSONObject getSysUserList(String name, String departmentName, String orderBy, Integer currentPage, Integer size) {
        JSONObject jsonObject = new JSONObject();
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            Page page = new Page();
            //Object [] objects = {"size", "currentPage", "name", "departmentName", "orderBy"};
            //HashMap<String, Object> map = DatasMapUtils.getUseMapData(condition, objects);
            page.setSize(size);
            page.setCurrentPage(currentPage);
            if (StringUtils.isNotBlank(name)) {
                map.put("name", name);
            }
            if (StringUtils.isNotBlank(departmentName)) {
                map.put("departmentName", departmentName);
            }
            if (StringUtils.isNotBlank(orderBy)) {
                map.put("orderBy", orderBy);
            }
            Integer count = sysUserMapper.selectSysUserCount(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            List<SysUserDto> listSysUser = sysUserMapper.getSysUserList(map);
            String jsonString = JSON.toJSONString(listSysUser);
            jsonObject.put("code", 0);
            jsonObject.put("success", 1);

            jsonObject.put("msg", "");
            jsonObject.put("count", page.getAllRow());
            jsonObject.put("data", jsonString);
        } catch (NumberFormatException e) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("count", 0);
            jsonObject.put("data", null);
        }
        return jsonObject;
    }

    @Override
    public JsonResult getSysUserListBySysUserName(String sysUserName) {
        try {
//            SysUserExample exampleList = new SysUserExample();
//            SysUserExample.Criteria criteriaList = exampleList.createCriteria();
//            criteriaList.andUserNameEqualTo(sysUserName);
//            criteriaList.andIsdeleteEqualTo(0);
//            List<SysUser> sysUserList = sysUserMapper.selectByExample(exampleList);

            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("userName",sysUserName);
            List<SysUser> sysUserList = sysUserMapper.getByMap(map);

            int size = sysUserList.size();
            if (size == 1){
                SysUser sysUser = sysUserList.get(0);
                Integer status = sysUser.getStatus();
                if (status == 0) {
                    return new JsonResult(0, null, "20000013");
                } else {
                    return new JsonResult(1, sysUser);
                }
            } else if (size < 1){
                return new JsonResult(0, null, "20000005");
            } else {
                return new JsonResult(0, null, "20000002");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }

    }

    @Override
    public JsonResult getUserById(String deptId) throws Exception{
        try {
            if(StringUtils.isBlank(deptId)){
                return new JsonResult(0,"参数错误");
            }
            SysUserDto sysUserDto = sysUserMapper.getUserById(deptId);
            return new JsonResult(1,sysUserDto);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getUserByDepartmentName(String name) {
        JsonResult jsonResult = null;
        try {
            List<SysUser> userList = sysUserMapper.getUserByDepartmentName(name);
            jsonResult = new JsonResult(1,userList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }
        return jsonResult;
    }

    @Override
    public JsonResult getUserByRoleCode(String code) {
        JsonResult jsonResult = null;
        try {
            List<SysUser> userList = sysUserMapper.getUserByRoleCode(code);
            jsonResult = new JsonResult(1,userList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }

        return jsonResult;
    }

    @Override
    public JsonResult changePassword(SysUserDto sysUserDto){
        String oldPassword = sysUserDto.getOldPassword();
        String password = sysUserDto.getPassword();
        String surePassword = sysUserDto.getSurePassword();

        if (StringUtils.isEmpty(sysUserDto.getId())) {
            return new JsonResult(0, null, "000021");
        }
        if (StringUtils.isEmpty(oldPassword)) {
            return new JsonResult(0, null, "110000026");
        }
        if (StringUtils.isEmpty(password)) {
            return new JsonResult(0, null, "110000027");
        }
        if (StringUtils.isEmpty(surePassword)) {
            return new JsonResult(0, null, "110000028");
        }


        List<SysUserDto> sysUsers = sysUserMapper.getSysUserById(sysUserDto.getId());


        String truePasswrod = sysUsers.get(0).getPassword();
        if (!MD5.encrytMD5(oldPassword).equals(truePasswrod) && !oldPassword.equals(truePasswrod)) {
            return new JsonResult(0, null, "110000029");
        }
        if (!password.equals(surePassword)) {
            return new JsonResult(0, null, "110000012");
        }
        sysUserDto = sysUsers.get(0);
        //sysUserDto.setPassword(MD5.encrytMD5(password)); 前台加密，后台不加密
        //sysUserDto.setSurePassword(MD5.encrytMD5(surePassword));
        sysUserDto.setPassword((password));
        sysUserDto.setSurePassword((surePassword));
        sysUserDto.setUpdater(sysUserDto.getId());



        int count = sysUserMapper.updateUserPassword(sysUserDto);
        if (count< 1) {
            return new JsonResult(0, null, "110000024");
        } else {
            return new JsonResult(1);
        }
    }


}
