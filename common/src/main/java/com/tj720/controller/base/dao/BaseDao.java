package com.tj720.controller.base.dao;
/**
 * 
 * @author: caiming
 * @date: 2018年9月2日
 */
public interface BaseDao<T> {
	T selectByPrimaryKey(String id);  
    int insert(T model);  
    int deleteByPrimaryKey(String id);
    int updateByPrimaryKey(T model);
}
