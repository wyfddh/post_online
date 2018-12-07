package com.tj720.service;

import java.util.List;
import com.tj720.model.common.system.user.SysUser;

public interface ICacheService{

	boolean setStr(String key, String value, int expireTime);
	String getStr(String key);
	boolean delObj(String key);

//	Setting getSetting(String key);

//	List<Setting> getSetting();

	String getModuleName(String moduleId);

//	Module getModule(String moduleId);

	boolean delObj(String key, String field);

	Object getObj(String key);

	Object setObj(String key, Object value, int expireTime);
	Object setObj(String key, String field, Object value, int expireTime);
	void delStr(String string);
	
	boolean flushDB();
	Object getObj(String string, String string2);
//	Project getProject(String projectId);
	SysUser getUser(String userId);
}
