package com.tj720.common.enumeration;

public enum ArticleType {
	DICTIONARY("项目数据字典"),PAGE("站点页面"),ARTICLE("文章");
	private final String name;
	
	ArticleType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
