package com.tj720.service.impl;

import com.tj720.controller.base.dao.BaseDao;
import com.tj720.controller.base.service.BaseServiceImpl;
import com.tj720.dao.SysLogMapper;
import com.tj720.model.common.SysLog;
import com.tj720.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLog> implements SysLogService {

	@Autowired
	private SysLogMapper sysLogMapper;

	public BaseDao<SysLog> getBaseDao() {
		return sysLogMapper;
	}
	
	@Override
	public SysLog get(Integer id) {
		return sysLogMapper.selectByPrimaryKey(id);
	}

	@Override
	public int create(SysLog model) {
		return sysLogMapper.insert(model);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return sysLogMapper.deleteByPrimaryKey(id);
	}
	
}
