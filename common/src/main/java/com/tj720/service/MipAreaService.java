package com.tj720.service;

import java.util.List;
import java.util.Map;


import com.tj720.model.common.area.MipArea;

public interface MipAreaService{

	Map<String, String> getCityName();

	/**
	 * 根据父id查询下面所有数据
	 * @param pid
	 * @return
	 */
	List<MipArea> getCityListByPid(Integer pid);

	List<MipArea> getAreaJson();
}
