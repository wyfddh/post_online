package com.tj720.service;


import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.system.department.SysDepartment;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDepartmentService{

	JsonResult getChildrenCount(String departmentId) throws Exception;

	JsonResult deleteByPrimaryKey(String departmentId) throws  Exception;

	JSONObject getDepartList(Integer currentPage, Integer size,String orderBy) throws Exception;

	JSONObject getDeptOptions() throws Exception;

	JsonResult insertSelective(SysDepartment record) throws  Exception;

	JsonResult selectByPrimaryKey(String departmentId) throws Exception;

	JsonResult getDeptById(String userId) throws Exception;

	JsonResult  updateDeptStatus(@Param("status")  String status,@Param("departmentId") String departmentId) throws
			Exception;

	JsonResult getAllDeptList() throws Exception;
	//JsonResult updateByPrimaryKeySelective(SysDepartment record) throws Exception;
	//boolean  countByNameAndParentId(@Param("parentId") String parentId, @Param("departmentName") String
			//departmentName,@Param("departmentId") String  departmentId);

}
