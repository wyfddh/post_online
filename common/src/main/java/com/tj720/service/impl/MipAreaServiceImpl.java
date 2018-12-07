package com.tj720.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tj720.model.common.area.MipArea;
import com.tj720.model.common.area.MipAreaExample;
import com.tj720.model.common.area.MipAreaExample.Criteria;
import com.tj720.service.MipAreaService;

import com.tj720.utils.MyString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tj720.dao.MipAreaMapper;

@Service("MipAreaServiceImpl")
public class MipAreaServiceImpl implements MipAreaService{
	
	@Autowired
	MipAreaMapper mipAreaMapper;

	@Override
	public Map<String, String> getCityName() {
		Map<String, String> map = new HashMap<String, String>();
//		MipAreaExample example = new MipAreaExample();
//		Criteria criteria = example.createCriteria();
//		List<MipArea> areaList = mipAreaMapper.selectByExample(example);
		List<MipArea> areaList = mipAreaMapper.getAreaJson();
		if(!MyString.isEmpty(areaList)){
			for(MipArea list : areaList){
				map.put(list.getId().toString(), list.getName());
			}
		}
		return map;
	}

	@Override
	public List<MipArea> getCityListByPid(Integer pid) {
//		MipAreaExample example = new MipAreaExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andPidEqualTo(pid);
//		List<MipArea> areaList = mipAreaMapper.selectByExample(example);
		List<MipArea> areaList = mipAreaMapper.getCityListByPid(pid);

		return areaList;
	}

	@Override
	public List<MipArea> getAreaJson() {
		
		return mipAreaMapper.getAreaJson();
	}

}
