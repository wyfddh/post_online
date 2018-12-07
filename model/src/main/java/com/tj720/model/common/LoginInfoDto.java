package com.tj720.model.common;

import com.tj720.model.common.system.user.MipUser;

import java.io.Serializable;


public class LoginInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userName;
	private String trueName;
	private String authStr;// 权限，由用户权限、角色拼接而成
	private String roleId;
	private String id;
	private byte type;
	private String email;
	private String nickName;

	public LoginInfoDto(MipUser user) {
		this.userName = user.getName();
		this.id = user.getId();
	}

	public String getUserName() {
		return userName;
	}

	public String getTrueName() {
		return trueName;
	}

	public String getAuthStr() {
		if (authStr == null)
			return "";
		return authStr;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getId() {
		return id;
	}

	public byte getType() {
		return type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
