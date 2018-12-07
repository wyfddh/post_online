package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.SysUserDeptMapper;
import com.tj720.model.common.system.department.SysUserDept;
import com.tj720.model.common.system.department.SysUserDeptExample;
import com.tj720.service.UserDeptService;
import com.tj720.utils.common.IdUtils;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;


@Service
public class UserDeptServiceImpl implements UserDeptService{

    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;


    @Override
    public JsonResult insertSelective(SysUserDept sysUserDept) {
        if (StringUtils.isEmpty(sysUserDept) ){
            return new JsonResult(0,null,"200101");
        }
        List<SysUserDept> userDeptList = (List<SysUserDept>)getUserDept(sysUserDept.getUserId(), sysUserDept
                .getUserId())
                .getData();
        if ( userDeptList.size()<0){
            return new JsonResult(0,null,"200110");
        }
        try {
            sysUserDept.setId(IdUtils.getIncreaseIdByNanoTime());
            for(SysUserDept userDept : userDeptList){
                sysUserDept.setUserId(userDept.getUserId());
                sysUserDept.setDeptId(userDept.getDeptId());
            }

            int count = sysUserDeptMapper.insertSelective(sysUserDept);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200104");
        }

    }

    @Override
    public JsonResult insertUserDept(String userId, String deptId) {
        SysUserDept sysUserDept= new SysUserDept();
        sysUserDept.setId(IdUtils.getIncreaseIdByNanoTime());
        sysUserDept.setUserId(userId);
        sysUserDept.setDeptId(deptId);
        sysUserDept.setStatus(1);
        sysUserDept.setIsdelete(0);
        sysUserDept.setCreator("测试数据");
        sysUserDept.setCreateTime(new Date());
        int count = sysUserDeptMapper.insert(sysUserDept);
        return new JsonResult(1,null);
    }




    @Override
    public JsonResult deleteUserDept(SysUserDept sysUserDept) {
        if (StringUtils.isEmpty(sysUserDept)){
            return new JsonResult(0,null,"200101");
        }
        JsonResult jsonResult = deleteUserDeptById(sysUserDept.getUserId(),sysUserDept.getDeptId());
        return jsonResult;
    }

    @Override
    public JsonResult deleteUserDeptById(String userId,String deptId) {
        if (StringUtils.isEmpty(userId) ){
            return new JsonResult(0,null,"200101");
        }
        if (StringUtils.isEmpty(deptId) ){
            return new JsonResult(0,null,"200101");
        }
        try {
            SysUserDept sysUserDept = new SysUserDept();
            sysUserDept.setDeptId(deptId);
            sysUserDept.setUserId(userId);
            sysUserDept.setIsdelete(1);
            sysUserDept.setUpdateTime(new Date());
            int count = sysUserDeptMapper.updateByPrimaryKeySelective(sysUserDept);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200106");
        }
    }


    @Override
    public JsonResult deleteUserDeptById(String userId) {
        if (StringUtils.isEmpty(userId) ){
            return new JsonResult(0,null,"200101");
        }
        try {
            SysUserDept sysUserDept = new SysUserDept();
            sysUserDept.setUserId(userId);
            sysUserDept.setIsdelete(1);
            sysUserDept.setUpdater("测试数据");
            sysUserDept.setUpdateTime(new Date());
            int count = sysUserDeptMapper.updateByPrimaryKeySelective(sysUserDept);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200106");
        }
    }

    @Override
    public JsonResult getUserDept(String userId, String deptId) {
        try {
//            SysUserDeptExample example = new SysUserDeptExample();
//            SysUserDeptExample.Criteria criteria = example.createCriteria();
//            criteria.andUserIdEqualTo(userId);
//            criteria.andDeptIdEqualTo(deptId);
//            List<SysUserDept > sysUserDepts  = sysUserDeptMapper.selectByExample(example);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("userId",userId);
            map.put("deptId",deptId);
            List<SysUserDept > sysUserDepts  = sysUserDeptMapper.getByMap(map);

            if (null != sysUserDepts && sysUserDepts.size()>0){
                return new JsonResult(1,sysUserDepts.get(0));
            }else{
                return new JsonResult(1,null);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200109");
        }
    }

    @Override
    public JsonResult getDeptById(String userId){
        try {
            if(StringUtils.isEmpty(userId)){
                return new JsonResult(0,"参数错误");
            }
            SysUserDept sysUserDept = sysUserDeptMapper.getDeptById(userId);
            return new JsonResult(1,sysUserDept);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }
}
