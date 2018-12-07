package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.SysDepartmentMapper;
import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.system.menu.MenuTreeDto;
import com.tj720.service.SysDepartmentService;
import com.tj720.utils.Page;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SysDepartmentServiceImpl
 * @Description:  部门业务实现
 * @Author: xiaoao
 * @Date: 2018/10/9 0009
 * @Version: 1.0
 **/
@Service
public class SysDepartmentServiceImpl implements SysDepartmentService{

    @Autowired
    private SysDepartmentMapper  sysDepartmentMapper;

    @Override
    public JsonResult getChildrenCount(String departmentId) throws Exception {
        try {
            if(StringUtils.isBlank(departmentId)){
                return new JsonResult(0,"参数错误");
            }
            int childCount = sysDepartmentMapper.getChildCount(departmentId);
            return new JsonResult(childCount);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult deleteByPrimaryKey(String departmentId) throws Exception{
        if(sysDepartmentMapper.getChildCount(departmentId)>0){
            return new JsonResult(0,"此部门下面还有子部门,请确定删除所有的子部门后再进行此操作");
        }
        try {
            if(StringUtils.isBlank(departmentId)){
                return new JsonResult(0,"参数错误");
            }
            int count = sysDepartmentMapper.deleteByPrimaryKey(departmentId);
            if (count >0){
                return new JsonResult(1,null);
            }
            else{
                return new JsonResult(0,null,"200006");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JSONObject getDepartList(Integer currentPage, Integer size,String orderBy) throws Exception{
        JSONObject jsonObject = new JSONObject();
        try {
            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);

            Map<String,Object> map = new HashMap<String,Object>();
            //符合条件总数
            Integer count = sysDepartmentMapper.count(map);
            page.setAllRow(count);
            map.put("start",page.getStart());
            map.put("end",page.getSize());
            map.put("orderBy",orderBy);
            //查询分页数据
            List<SysDepartment> departList = sysDepartmentMapper.departmentList(map);
            String jsonString = JSON.toJSONString(departList);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("count", page.getAllRow());
            jsonObject.put("data", jsonString);
        }catch (Exception e){
            jsonObject.put("code", 1);
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("count", 0);
            jsonObject.put("data", null);
        }
        return jsonObject;
    }

    @Override
    public JSONObject getDeptOptions() throws Exception{
        JSONObject jsonObject = new JSONObject();
        try {
            List<SysDepartment> deptOptions = sysDepartmentMapper.getDeptOptions();
            String jsonString = JSON.toJSONString(deptOptions);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("data", jsonString);
        }catch (Exception e){
            jsonObject.put("code", 1);
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("data", null);
        }
        return jsonObject;
    }

    private boolean checkExist(String parentId, String departmentName, String deptId) {
        boolean  flag  = false;
        int i = sysDepartmentMapper.countByNameAndParentId(parentId, departmentName, deptId);
        if(i > 0){
            flag = true;
        }
        return  flag;
    }



    public JsonResult gotoDeptEdit(String department_id, String parentId){
        if(department_id !=null && department_id != "" ){
            SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(department_id);
            return new JsonResult(0,sysDepartment);
        }else{
            if(parentId!=null){
                SysDepartment parentDept = sysDepartmentMapper.selectByPrimaryKey(parentId);
                SysDepartment dept = new SysDepartment();
                dept.setParentId(parentDept.getDepartmentId());
                dept.setDepartmentName(parentDept.getDepartmentName());
                return new JsonResult(0,dept);
            }
        }

        return new JsonResult(1,null);
    }



    @Override
    public JsonResult  insertSelective(SysDepartment record){
        try {
            if (record == null){
                return new JsonResult(0,"参数有误");
            }

            //部门重复值校验
            if(StringUtils.isNotEmpty(record.getDepartmentId())){
               /* if(!checkExist( record.getDepartmentName(),record.getDepartmentId(),record.getParentId())){
                    return  new JsonResult(1,"同一层级下存在相同名称的部门");
                }*/
                SysDepartment department = sysDepartmentMapper.selectByPrimaryKey(record.getDepartmentId());
                if (department == null){
                    return new JsonResult(0,"部门不存在，无法修改");
                }
                boolean flag = checkExist( record.getParentId(),record.getDepartmentName(), record.getDepartmentId());
                //if (!department.getDepartmentName().equals(record.getDepartmentName())){
                    if(flag == true){
                        return  new JsonResult(1,"同一层级下存在相同名称的部门");
                    }
                //}
                //修改上级部门
                if (!record.getParentId().equals(department.getParentId())){
                    if(null == record.getParentId()||record.getParentId()==""||record.getParentId().equals("-1")){
                        //一级部门只允许存在一个
                        int count = sysDepartmentMapper.getLevel1Org();
                        if(count >0){
                            return new JsonResult(0,"一级部门只允许存在一个");
                        }
                        else {
                            record.setParentId("-1");//不选择默认为一级部门
                            record.setDepartmentLevel("1");//不选择级别默认为一级部门
                        }
                    }
                    else{
                        SysDepartment department1 = sysDepartmentMapper.selectByPrimaryKey(record.getParentId());
                        if (null != department1){
                            if(StringUtils.isNotBlank(department1.getDepartmentLevel())){
                                Integer level =  Integer.valueOf(department1.getDepartmentLevel())+1;
                                record.setDepartmentLevel(String.valueOf(level));
                            }
                        }else {
                            return new JsonResult(0,"上级部门不存在");
                        }
                    }
                }
                int i = sysDepartmentMapper.updateByPrimaryKeySelective(record);
                return new JsonResult(1,"修改部门信息成功");
            }else{
                boolean flag = checkExist(record.getParentId(),record.getDepartmentName(), record.getDepartmentId());
                if(flag == true){
                    return  new JsonResult(1,"同一层级下存在相同名称的部门");
                }
                record.setDepartmentId(IdUtils.getIncreaseIdByNanoTime());
                if(null == record.getParentId()||record.getParentId()==""||record.getParentId().equals("-1")){
                    //一级部门只允许存在一个
                    int count = sysDepartmentMapper.getLevel1Org();
                    if(count >0){
                        return new JsonResult(0,"一级部门只允许存在一个");
                    }
                    else {
                        record.setParentId("-1");//不选择默认为一级部门
                        record.setDepartmentLevel("1");//不选择级别默认为一级部门
                    }
                }
                else{
                    SysDepartment department1 = sysDepartmentMapper.selectByPrimaryKey(record.getParentId());
                    if (null != department1){
                        if(StringUtils.isNotBlank(department1.getDepartmentLevel())){
                            Integer level =  Integer.valueOf(department1.getDepartmentLevel())+1;
                            record.setDepartmentLevel(String.valueOf(level));
                        }
                    }else {
                        return new JsonResult(0,"上级部门不存在");
                    }
                }
//                int childCount = sysDepartmentMapper.getChildCount(record.getDepartmentId());
//                if(childCount>0){
//                    record.setDepartmentLevel(record.getDepartmentLevel()+1);
//                }
                record.setIsdelete(0);
                record.setStatus("1");
                int i = sysDepartmentMapper.insertSelective(record);
                return  new JsonResult(i,"增加部门信息成功");
            }

        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
        //return new JsonResult(1,record);
    }

    @Override
    public JsonResult selectByPrimaryKey(String departmentId) throws Exception{
        try {
            if(StringUtils.isBlank(departmentId)){
                return new JsonResult(0,"参数错误");
            }
            SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(departmentId);
            return new JsonResult(1,sysDepartment);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getDeptById(String userId) throws Exception{
        try {
            if(StringUtils.isBlank(userId)){
                return new JsonResult(0,"参数错误");
            }
            SysDepartment deptByid = sysDepartmentMapper.getDeptById(userId);
            return new JsonResult(1,deptByid);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult updateDeptStatus(String status,String departmentId) throws Exception{
        try {
            if(StringUtils.isBlank(status)){
                return new JsonResult(0,"参数错误");
            }
            int i = sysDepartmentMapper.updateDeptStatus(status,departmentId);
            return new JsonResult(i,"修改状态成功");
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getAllDeptList() throws Exception{
        try {
            List<MenuTreeDto> sysDepts = null;
            sysDepts = sysDepartmentMapper.getAllDeptList();
            return new JsonResult(1,sysDepts);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200209");
        }
    }


}
