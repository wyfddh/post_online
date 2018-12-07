package com.tj720.service;

import java.util.List;

import com.tj720.controller.base.service.BaseService;
import com.tj720.model.common.system.role.MipRole;
import org.springframework.stereotype.Service;


@Service
public interface MipRoleService extends BaseService<MipRole> {

	List<MipRole> getList();

}
