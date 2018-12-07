package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.SysUserlogMapper;
import com.tj720.model.common.system.userlog.SysUserlog;
import com.tj720.service.SysUserlogService;
import com.tj720.utils.Page;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SysUserlogServiceImpl
 * @Description:  系统日志业务实现层
 * @Author: xa
 * @Date: 2018/10/10
 * @Version: 1.0
 **/
@Service
public class SysUserlogServiceImpl implements SysUserlogService {


    @Autowired
    private SysUserlogMapper  sysUserlogMapper;

    @Override
    public JsonResult deleteByPrimaryKey(String signId) throws Exception{
        try {
            if(StringUtils.isBlank(signId)){
                return new JsonResult(0,"参数错误");
            }
            int count = sysUserlogMapper.deleteByPrimaryKey(signId);
            if (count>0){
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
    public JsonResult batchRemove(String[] ids) throws Exception{
        try {
            if(ids.length<0){
                return new JsonResult(0,"参数错误");
            }
                int count = sysUserlogMapper.batchRemove(ids);
                if (count>0){
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
    public JSONObject userlogList(String userName, String loginTime, Integer currentPage, Integer size) throws
            Exception{
        JSONObject jsonObject = new JSONObject();
        try {
            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);

            Map<String,Object> map = new HashMap<String,Object>();
            if (StringUtils.isNotBlank(userName)) {
                map.put("username",userName);
            }
            if (StringUtils.isNotBlank(loginTime)){
                map.put("logintime",loginTime);
            }


            //符合条件总数
            int count= sysUserlogMapper.count(map);
            page.setAllRow(count);
            map.put("start",page.getStart());
            map.put("end",page.getSize());
            //查询分页数据
            List<SysUserlog> sysUserlogs = sysUserlogMapper.userlogList(map);
            String jsonString = JSON.toJSONString(sysUserlogs);
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
    public JSONObject getUserOptions() throws Exception{
        JSONObject jsonObject = new JSONObject();
        try {
            List<SysUserlog> userOptions = sysUserlogMapper.getUserOptions();
            String jsonString = JSON.toJSONString(userOptions);
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

    @Override
    public JsonResult insertSelective(String userName, Date loginTime, String  loginIp, String  action){
        try{
            userName = "admin";
            loginTime = new Date();
            int i = sysUserlogMapper.insertSelective(userName,loginTime,loginIp,action);
            return  new JsonResult(i,"增加系统日志信息成功");
        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult insertUserlog(SysUserlog sysUserlog){
        try{
            sysUserlog.setSignId(IdUtils.getIncreaseIdByNanoTime());
            int i = sysUserlogMapper.insertUserlog(sysUserlog);
            return  new JsonResult(i,"增加系统日志信息成功");
        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }


}
