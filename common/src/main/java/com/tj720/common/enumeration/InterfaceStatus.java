package com.tj720.common.enumeration;

public enum InterfaceStatus {
	不可用("0"),可用("1");
	private final String name;
	
	InterfaceStatus(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
