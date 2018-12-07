package com.tj720.service;


import com.tj720.controller.base.service.BaseService;
import com.tj720.model.common.SysLog;

public interface SysLogService extends BaseService<SysLog> {

	SysLog get(Integer id) ;

	int create(SysLog model) ;

	int deleteByPrimaryKey(Integer id);
}
