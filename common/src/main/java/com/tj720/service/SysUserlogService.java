package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.system.userlog.SysUserlog;
import net.sf.json.JSONObject;
import java.util.Date;


public interface SysUserlogService{


    JsonResult deleteByPrimaryKey(String  signId) throws Exception;

    JsonResult batchRemove(String[] ids) throws Exception;

    JSONObject userlogList(String username, String logintime,Integer currentPage, Integer size) throws Exception;

    JSONObject getUserOptions() throws Exception;

    JsonResult insertSelective(String userName, Date loginTime, String  loginIp, String  action);

    JsonResult insertUserlog(SysUserlog sysUserlog);



}
