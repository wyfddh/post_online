package com.tj720.service;

import com.tj720.controller.base.service.BaseService;
import com.tj720.model.common.LoginDto;
import com.tj720.model.common.system.user.MipUser;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface MipUserService extends BaseService<MipUser> {

	void login(LoginDto model, MipUser user, HttpServletRequest request, HttpServletResponse response);
	
	int update(MipUser model);
	
	/**
	 * 根据手机号查找用户
	 * @param phone
	 * @return
	 */
	List<MipUser> getListByPhone(String phone);
}
