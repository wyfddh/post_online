package com.tj720.service.impl;

import java.util.List;

import com.tj720.controller.base.dao.BaseDao;
import com.tj720.controller.base.service.BaseServiceImpl;
import com.tj720.dao.MipRoleMapper;
import com.tj720.model.common.system.role.MipRole;
import com.tj720.service.MipRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MipRoleServiceImpl extends BaseServiceImpl<MipRole> implements MipRoleService {

	@Autowired
	private MipRoleMapper mipRoleMapper;
	
	@Override
	public BaseDao<MipRole> getBaseDao() {
		return mipRoleMapper;
	}
	
	@Override
	public List<MipRole> getList() {
		return mipRoleMapper.getList();
	}

}
