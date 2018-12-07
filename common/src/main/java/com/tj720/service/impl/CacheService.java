package com.tj720.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.tj720.dao.ICacheDao;
import com.tj720.dao.SysUserMapper;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.utils.Const;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tj720.service.ICacheService;
import com.tj720.controller.springbeans.Config ;

import org.springframework.util.StringUtils;

@Service
@Repository(value = "cacheService")
public class CacheService implements ICacheService {
	@Autowired
	private Config config;
	
	@Resource(name="memoryCacheDao")
	private ICacheDao memoryCacheDao;
	@Resource(name="redisCacheDao")
	private ICacheDao redisCacheDao;
	@Autowired
	private SysUserMapper userDao;
	
	
	
	
	private ICacheDao getDao(){
		if( config.getRedisIp().trim().equals("") ){
			return memoryCacheDao;
		}else{
			return redisCacheDao;
		}
	}
	
	@Override
	public Object getObj(String key){
		return getDao().getObj(key);
	}
	
	@Override
	public Object setObj(String key, Object value, int expireTime){
		return getDao().setObj(key, value, expireTime);
	}
	
	@Override
	public boolean delObj(String key){
		return getDao().delObj(key);
	}

	@Override
	public String getModuleName(String moduleId) {
		return null;
	}

	@Override
	public boolean delObj(String key,String field){
		return getDao().delObj(key,field);
	}


	@Override
	public boolean setStr(String key, String value, int expireTime) {
		return getDao().setStr(key, value, expireTime);
	}

	@Override
	public String getStr(String key) {
		return getDao().getStr(key);
	}

	@Override
	public void delStr(String key) {
		getDao().delStr(key);
	}

	@Override
	public boolean flushDB() {
		return getDao().flushDB();
	}

	@Override
	public Object setObj(String key, String field, Object value, int expireTime) {
		return getDao().setObj(key, field, value, expireTime);
	}

	@Override
	public Object getObj(String key, String field) {
		return getDao().getObj(key, field);
	}
	
	@Override
	@Transactional
	public SysUser getUser(String userId){
		if(StringUtils.isEmpty(userId)){
			return new SysUser();
		}
		
		Object obj = getDao().getObj(Const.CACHE_USER_MODEL + userId);
		if(obj == null){
			SysUser user = userDao.selectByPrimaryKey(userId);
			if(user == null){
				user = new SysUser();
			}
			getDao().setObj(Const.CACHE_USER_MODEL + userId, user, config.getCacheTime());
			return user;
		}
		return (SysUser) obj;
	}
}
