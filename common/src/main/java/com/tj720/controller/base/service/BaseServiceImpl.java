package com.tj720.controller.base.service;


import com.tj720.controller.base.dao.BaseDao;

/**
 * 基础实现类
 * @author: caiming
 * @date: 2018年9月2日
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
	/** 
     * 由业务类实现 
     * @return 
     */  
    public abstract BaseDao<T> getBaseDao();
      
	@Override
	public T get(String id) {
		return getBaseDao().selectByPrimaryKey(id);
	}

	@Override
	public int create(T model) {
		return getBaseDao().insert(model);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return getBaseDao().deleteByPrimaryKey(id);
	}
	
	public int update(T model) {
		return getBaseDao().updateByPrimaryKey(model);
	}

}
