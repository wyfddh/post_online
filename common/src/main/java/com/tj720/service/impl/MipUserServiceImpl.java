package com.tj720.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tj720.controller.base.dao.BaseDao;
import com.tj720.controller.base.service.BaseServiceImpl;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.MipUserMapper;
import com.tj720.model.common.LoginDto;
import com.tj720.model.common.LoginInfoDto;
import com.tj720.model.common.system.user.MipUser;
import com.tj720.model.common.system.user.MipUserExample;
import com.tj720.service.MipUserService;
import com.tj720.utils.Aes;
import com.tj720.utils.Const;
import com.tj720.utils.MyCookie;
import com.tj720.utils.MyString;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MipUserServiceImpl extends BaseServiceImpl<MipUser> implements MipUserService {
	@Autowired
	private Config config;
	@Autowired
	private MipUserMapper mipUserMapper;

	@Override
	public BaseDao<MipUser> getBaseDao() {
		return mipUserMapper;
	}
	
	@Override
	public void login(LoginDto model, MipUser user, HttpServletRequest request, HttpServletResponse response) {
		String token = Aes.encrypt(user.getId());
		MyCookie.addCookie(Const.COOKIE_TOKEN, token, response);
		MyCookie.addCookie(Const.COOKIE_USERID, user.getId(), response);
		MyCookie.addCookie(Const.COOKIE_USERNAME, user.getName(), response);
		model.setId(user.getId());
		if (!MyString.isEmpty(model.getPhone())) {
			MyCookie.addCookie(Const.COOKIE_PHONE, model.getPhone(), response);
		}
		MyCookie.addCookie("sessionAdminName", user.getNickName()==null?"":user.getNickName(), response);

		MyCookie.deleteCookie(Const.COOKIE_PASSWORD, request, response);
		model.setSessionAdminName(user.getNickName());
		model.setHeadimgurl(user.getAvatarurl());
	}

	@Override
	public List<MipUser> getListByPhone(String phone) {
//		MipUserExample example = new MipUserExample();
//		MipUserExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isBlank(phone)) {
			return new ArrayList<>();
		}else {
//			criteria.andPhoneEqualTo(phone);
//			List<MipUser> selectByExample = mipUserMapper.selectByExample(example);
			List<MipUser> userList = mipUserMapper.getListByPhone(phone);
			return userList;
		}
	}

}
