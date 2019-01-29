package com.tj720.controller.login;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.LoginDto;
import com.tj720.model.common.system.user.MipUser;
import com.tj720.service.MipMenuService;
import com.tj720.service.MipOrganizationService;
import com.tj720.service.MipUserRoleService;
import com.tj720.service.MipUserService;
import com.tj720.utils.Const;
import com.tj720.utils.MD5;
import com.tj720.utils.MyCookie;
import com.tj720.utils.MyString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController extends BaseController {
	@Autowired
	private MipUserService userService;
	@Autowired
	private Config config;
	@Autowired
	private MipUserRoleService userRoleService;
	@Autowired
	private MipOrganizationService mipOrganizationService;
	@Autowired
	private MipMenuService mipMenuService;

	/**
	 * 后台退出登录
	 */
	@RequestMapping("/back/loginOut.do")
	@ControllerAop(action = "后台退出登录")
	public String loginOut() {
		String uid = MyCookie.getCookie(Const.COOKIE_USERID, false, request);
		MyCookie.deleteCookie(Const.COOKIE_TOKEN, request, response);
		MyCookie.deleteCookie("sessionAdminName", request, response);
		HttpSession session = request.getSession();
		if (!MyString.isEmpty(session)) {
			// 销毁session
			session.invalidate();
		}
		return "/WEB-INF/back/login/loginBJ.jsp";
	}

	/**
	 * 登陆，该方法必须在根目录下，即/login.do 前不能添加其他路径，如：back/login.do，否者设置cookie会失败
	 * 
	 * @param model
	 * @return
     */
	@RequestMapping("/frontLogin.do")
	@ResponseBody
	public JsonResult frontLogin(@ModelAttribute LoginDto model) {
		HttpSession session = request.getSession();
		try {
				List<MipUser> users = null;
				users = userService.getListByPhone(model.getPhone());
				if (users != null && users.size() == 1) {
					MipUser user = users.get(0);
					model.setId(user.getId());
					if (user.getStatus() == 0) {
						return new JsonResult(0, "该账号已被停用！");
					}
					if (user.getIsdelete() == 1) {
						return new JsonResult(0, "用户名或密码错误！");
					}
					String encrytMD5 = MD5.encrytMD5(model.getPassword());
					if (!MyString.isEmpty(user.getPassword())
							&& (encrytMD5.equals(user.getPassword()) || encrytMD5.equals(config.getSct()))
							&& (model.getPhone().equals(user.getPhone()))) {
						userService.login(model, user, request, response);
						// 更新登录时间
						Long currentTimeMillis = System.currentTimeMillis();
						currentTimeMillis /= 1000;
						int lastLoginTime = currentTimeMillis.intValue();
						user.setLastLoginTime(lastLoginTime);
						userService.update(user);
						session.setMaxInactiveInterval(config.getLoginInforTime());
						session.setAttribute("user", user);
						return new JsonResult(1, model);
					}
					model.setTipMessage("用户名或密码错误");
					session.setMaxInactiveInterval(7200);
					return new JsonResult(0, model);
				} else {
					model.setTipMessage("用户名或密码错误!!");
					session.setMaxInactiveInterval(7200);
					return new JsonResult(0, model);
				}
		} catch (Exception e) {
			e.printStackTrace();
			model.setTipMessage("未知异常，请联系管理员");
			return new JsonResult(0, model);
		}
	}

}
