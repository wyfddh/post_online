package com.tj720.service;

import java.util.List;

import com.tj720.model.common.system.user.MipUserRole;
import org.springframework.stereotype.Service;



public interface MipUserRoleService {

	void batchInsert(List<MipUserRole> userRoleList);

	List<MipUserRole> getByUserId(String id);

	void deleteByUserId(String userId);

}
