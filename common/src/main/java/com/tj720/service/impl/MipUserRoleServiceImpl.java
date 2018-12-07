package com.tj720.service.impl;

import java.util.List;

import com.tj720.controller.base.dao.BaseDao;
import com.tj720.controller.base.service.BaseServiceImpl;
import com.tj720.dao.MipUserRoleMapper;
import com.tj720.model.common.system.user.MipUserRole;
import com.tj720.service.MipUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MipUserRoleServiceImpl extends BaseServiceImpl<MipUserRole> implements MipUserRoleService {
	
	@Autowired
	private MipUserRoleMapper mipUserRoleMapper;
	
	@Override
	public BaseDao<MipUserRole> getBaseDao() {
		return mipUserRoleMapper;
	}
	
	@Override
	public void batchInsert(List<MipUserRole> userRoleList) {

		mipUserRoleMapper.batchInsert(userRoleList);
		
	}

	@Override
	public List<MipUserRole> getByUserId(String id) {
	
		return mipUserRoleMapper.getByUserId(id);
	}

	@Override
	public void deleteByUserId(String userId) {
		
		mipUserRoleMapper.deleteByUserId(userId);
	}

}
