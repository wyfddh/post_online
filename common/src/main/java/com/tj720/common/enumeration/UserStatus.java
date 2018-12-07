package com.tj720.common.enumeration;

public enum UserStatus {
	不可用("0"),邮箱未验证("1"),邮箱验证成功("2");
	private final String name;
	
	UserStatus(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
