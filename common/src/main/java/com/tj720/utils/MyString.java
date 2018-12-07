package com.tj720.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class MyString {
	public static boolean isEquals(String tagValue,String value)
	{
		if(isEmpty(tagValue) || isEmpty(value))
			return false;
		else return tagValue.equals(value);
	}
	/**
	 * 判断对象是否为空
	 * Object = null
	 * String = "","null","undefined"
	 * List size=0
	 * @param object
	 * @return
	 */
	public static boolean isEmpty(Object object)
	{
		if(object instanceof String){
            return object == null || object.toString().trim().equals("") || object.toString().trim().equalsIgnoreCase("null") || object.toString().equals("undefined");
		}else if(object instanceof List<?>){
            return object == null || ((List<?>) object).size() == 0;
		}else return object == null;
    }
	
	// 从request中获取值
	public static String getValueFromRequest(HttpServletRequest request, String name){
		return getValueFromRequest(request, name, "");
	}
	
	public static String getValueFromRequest(HttpServletRequest request, String name, String defValue){
		if( isEmpty(request.getParameter(name)) ){
			return defValue;
		}else{
			return request.getParameter(name);
		}
	}
}
