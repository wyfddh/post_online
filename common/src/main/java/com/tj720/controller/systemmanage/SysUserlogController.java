
package com.tj720.controller.systemmanage;


import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.system.userlog.SysUserlog;
import com.tj720.service.SysUserlogService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * @ClassName: SysUserlogController
 * @Description: 系统日志控制层
 * @Author: xiaoao
 * @Date: 2018/10/10
 * @Version: 1.0
 **/

@Controller
@RequestMapping("/syslog")
public class SysUserlogController{

      @Autowired
      private SysUserlogService sysUserlogService;



    /**
     * 删除系统日志信息
     * @param signId
     * @return
     * @throws Exception
     */
    @ControllerAop(action="删除系统日志信息")
    @RequestMapping("/deleteSyslog")
    @ResponseBody
    public JsonResult deleteDepart(String signId) throws Exception{
        return sysUserlogService.deleteByPrimaryKey(signId);
    }


    /**
     * 批量删除系统日志信息
     * @param ids
     * @return
     * @throws Exception
     */
    @ControllerAop(action="批量删除系统日志信息")
    @RequestMapping("/batchRemove")
    @ResponseBody
    public JsonResult batchRemove(String[] ids) throws Exception{
         return  sysUserlogService.batchRemove(ids);
    }



    /**
     * @Description 查询日志列表
     * @param   //分页参数
     * @return
     */
    @ControllerAop(action="查询日志列表")
    @RequestMapping("/getSyslogList")
    @ResponseBody
    public JSONObject getDepartList(String username,String logintime,@RequestParam(defaultValue = "1") Integer
            currentPage, @RequestParam(defaultValue =
            "10") Integer size) throws Exception{
        return  sysUserlogService.userlogList(username,logintime,currentPage,size);
    }





   /**
     * 获取系统日志集合（操作人员）
     * @return
     * @throws  Exception
     */
    @ControllerAop(action="操作人员")
    @RequestMapping("/getUserOptions")
    @ResponseBody
    public JSONObject getDeptOptions() throws Exception{
        return  sysUserlogService.getUserOptions();
    }



    /**
     * 添加系统日志
     * @return
     * @throws  Exception
     */
    @ControllerAop(action="添加系统日志")
    @RequestMapping("/insertSysUserlog")
    @ResponseBody
    public JsonResult insertSelective(String username, Date logintime, String  loginIp, String  action){
        return  sysUserlogService.insertSelective(username,logintime,loginIp,action);
    }


    /**
     * 添加系统日志(对象)
     * @return
     * @throws  Exception
     */
    @ControllerAop(action="添加整个系统日志")
    @RequestMapping("/insertSysUserlogs")
    @ResponseBody
    public JsonResult insertUserlog(SysUserlog sysUserlog){
        return  sysUserlogService.insertUserlog(sysUserlog);
    }




}

