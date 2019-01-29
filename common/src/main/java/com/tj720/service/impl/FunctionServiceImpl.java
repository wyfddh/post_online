package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.SysFunctionMapper;
import com.tj720.dao.SysResAuthMapper;
import com.tj720.model.common.system.menu.MenuTreeDto;
import com.tj720.model.common.system.menu.SysFunction;
import com.tj720.model.common.system.menu.SysFunctionExample;
import com.tj720.model.common.system.menu.SysResAuthExample;
import com.tj720.service.FunctionService;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/10 15:57
 */
@Service
public class FunctionServiceImpl implements FunctionService{
    @Autowired
    SysFunctionMapper sysFunctionMapper;
    @Autowired
    SysResAuthMapper sysResAuthMapper;
    @Autowired

    private static String userId = "sysadmin";
    private SysFunction setAction(SysFunction sysFunction,String type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        }catch (Exception e){
            e.printStackTrace();
        }
        sysFunction.setUpdater(userId);
        sysFunction.setUpdateTime(date);
        if (type == "0"){
            sysFunction.setCreateTime(date);
            sysFunction.setCreator(userId);
        }
        return sysFunction;
    }

    private List<SysFunction> getFunctionByName(String functionName){
//        SysFunctionExample example = new SysFunctionExample();
//        SysFunctionExample.Criteria criteria = example.createCriteria();
//        criteria.andFunctionnameEqualTo(functionName);
//        List<SysFunction> sysFunctions = sysFunctionMapper.selectByExample(example);
        List<SysFunction> sysFunctions = sysFunctionMapper.selectByFunctionName(functionName);


        return sysFunctions;
    }

    @Override
    public JsonResult addFunction(SysFunction sysFunction) {
        if (StringUtils.isEmpty(sysFunction) || StringUtils.isEmpty(sysFunction.getFunctionname())){
            return new JsonResult(0,null,"200207");
        }
        List<SysFunction> functionByName = getFunctionByName(sysFunction.getFunctionname());
        //判断名称是否已存在
        if (functionByName != null && functionByName.size()>0){
            return new JsonResult(0,null,"200201");
        }
        try {
            sysFunction.setId(IdUtils.getIncreaseIdByNanoTime());
            sysFunction = setAction(sysFunction,"0");
            //默认为启用
            sysFunction.setStatus((byte)1);
            int count = sysFunctionMapper.insertSelective(sysFunction);
            if (count>0){
                return new JsonResult(1,null);
            }else {
                return new JsonResult(0,null,"200204");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200204");
        }
    }

    @Override
    public JsonResult updateFunction(SysFunction sysFunction) {
        if (StringUtils.isEmpty(sysFunction) || StringUtils.isEmpty(sysFunction.getFunctionname())){
            return new JsonResult(0,null,"200207");
        }
        try {
            sysFunction = setAction(sysFunction,"1");
            int count = sysFunctionMapper.updateByPrimaryKeySelective(sysFunction);
            if (count>0){
                return new JsonResult(1,null);
            }else {
                return new JsonResult(0,null,"200205");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200205");
        }
    }

    @Override
    @Transactional
    public JsonResult deleteFunctionById(String functionId) {
        if (StringUtils.isEmpty(functionId)){
            return new JsonResult(0,null,"200208");
        }
        try {
            //先删除关联权限
            sysResAuthMapper.deleteByFunctionId(functionId);
            int count = sysFunctionMapper.deleteByPrimaryKey(functionId);
            if (count>0){
                return new JsonResult(1,null);
            }else {
                return new JsonResult(0,null,"200206");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200206");
        }
    }

    @Override
    public JsonResult queryFunctionList(String type,String functionName) {
        try {
            List<MenuTreeDto> sysFunctions = null;
            sysFunctions = sysFunctionMapper.selectByCondition(type,functionName);
            return new JsonResult(1,sysFunctions);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200209");
        }

    }

    @Override
    public JsonResult queryFunctionById(String functionId) {
        try {
            SysFunction sysFunction = sysFunctionMapper.selectFunctionById(functionId);
            return new JsonResult(1,sysFunction);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200209");
        }

    }

    /**
     * 根据id查询子列表
     *
     * @param functionId 功能id
     * @param type       子功能类型
     * @return
     */
    @Override
    public JsonResult queryChildrenListById(String functionId, String type) {
        try {
            List<SysFunction> sysFunctionList = sysFunctionMapper.selectByParentId(functionId, type);
            return new JsonResult(1,sysFunctionList);
        }catch (Exception e){
            return new JsonResult(0,null,"200209");
        }
    }
}
