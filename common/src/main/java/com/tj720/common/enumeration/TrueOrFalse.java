package com.tj720.common.enumeration;

public enum TrueOrFalse {
	TRUE("true"),FALSE("false");
	private final String name;
	
	TrueOrFalse(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
