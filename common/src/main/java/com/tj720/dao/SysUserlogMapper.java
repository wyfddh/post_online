package com.tj720.dao;

import com.tj720.model.common.system.userlog.SysUserlog;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface SysUserlogMapper{




    /**
     * 根据id删除部门
     */
    int deleteByPrimaryKey(String  signId);


    /**
     * 根据id批量删除部门
     */
    int batchRemove(String[] ids);

    /**
     * 查询系统日志记录集合
     */
    List<SysUserlog> userlogList(Map<String,Object> map);

    /**
     * 查询系统日志记录数
     */
    int count(Map<String, Object> map);

    /**
     * 查询系统日志记录集合（操作人员）
     */
    List<SysUserlog> getUserOptions();


    /**
     * 添加系统日志
     */
    int insertSelective(String userName, Date loginTime, String  loginIp, String  action);

    /**
     * 添加系统日志
     */
    int insertUserlog(SysUserlog  sysUserlog);

}