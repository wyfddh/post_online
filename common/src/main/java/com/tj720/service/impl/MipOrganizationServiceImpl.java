package com.tj720.service.impl;

import com.tj720.controller.base.dao.BaseDao;
import com.tj720.controller.base.service.BaseServiceImpl;
import com.tj720.dao.MipOrganizationMapper;
import com.tj720.model.common.system.org.MipOrganization;
import com.tj720.service.MipOrganizationService;
import com.tj720.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MipOrganizationServiceImpl extends BaseServiceImpl<MipOrganization> implements MipOrganizationService {

	@Autowired
	private MipOrganizationMapper mipOrganizationMapper;

	@Override
	public BaseDao<MipOrganization> getBaseDao() {
		return mipOrganizationMapper;
	}

	public List<MipOrganization> getPageList(String key, String orgTypeId, String platformId, Page page) {
		int count = mipOrganizationMapper.countPageList(key,orgTypeId,platformId);
		page.setAllRow(count);
		return mipOrganizationMapper.getPageList(key,orgTypeId,platformId,page.getStart(),page.getSize());
	}

	@Override
	public int countOrgList(Map<String, Object> map) {
		int count = mipOrganizationMapper.countOrgList(map);
		return count;
	}

	@Override
	public List<MipOrganization> getOrgInfoList(Map<String, Object> map) {
		return mipOrganizationMapper.getOrgInfoList(map);
	}
}
