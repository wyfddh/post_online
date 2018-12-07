package com.tj720.controller.base.dao;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import org.springframework.orm.hibernate4.HibernateTemplate;


public interface IBaseDao<T> {
	T save(T t);
	void delete(T t);
	T get(String id);
	List<T> findByExample(T t);
	List<T> loadAll(T t);
	void update(T t);
	List<T> findByMap(Map<String, Object> map,
                      Page pageBean, String order);
	int getCount(Map<String, Object> map, String conditions);
	List<?> queryByHql(String hql, Map<String, Object> map);
	int update(String hql, Map<String, Object> map);
	HibernateTemplate gethibernateTemplate();
	List<T> findByMap(String construct, Map<String, Object> map, Page pageBean, String order);
	List<T> queryByHql(String hql, Map<String, Object> map, Page pageBean);
	void insert(Object model);
	/** <pre>findByObj(这里用一句话描述这个方法的作用)   
	 * 创建人：Caomq caomqvip@sina.com
	 * 创建时间：2017年2月7日 下午2:22:36    
	 * 修改人：Caomq caomqvip@sina.com
	 * 修改时间：2017年2月7日 下午2:22:36    
	 * 修改备注： 
	 * @param map
	 * @param obj
	 * @param pageBean
	 * @param order
	 * @param hqlCondition
	 * @return</pre>    
	 */
	List<?> findByObj(Map<String, Object> map, Object obj, Page pageBean, String order, String hqlCondition);
	/** <pre>findByHql(这里用一句话描述这个方法的作用)   
	 * 创建人：Caomq caomqvip@sina.com
	 * 创建时间：2017年2月7日 下午2:23:28    
	 * 修改人：Caomq caomqvip@sina.com
	 * 修改时间：2017年2月7日 下午2:23:28    
	 * 修改备注： 
	 * @param hql
	 * @param map
	 * @param page
	 * @param modelName 
	 * @return</pre>    
	 */
    List<?> findByHql(String hql, Map<String, Object> map, Page page, String modelName);
	/** <pre>getCount(这里用一句话描述这个方法的作用)   
	 * 创建人：Caomq caomqvip@sina.com
	 * 创建时间：2017年2月7日 下午2:27:02    
	 * 修改人：Caomq caomqvip@sina.com
	 * 修改时间：2017年2月7日 下午2:27:02    
	 * 修改备注： 
	 * @param map
	 * @return</pre>    
	 */
	int getModelCount(Map<String, Object> map, String seach, String modelName);
	

	/**
	 * 查询限定条数
	 * @param hql
	 * @param 
	 * @return
	 */
    List<?> findLimitByHql(String hql, int limit);
	/**
	 * 查询获取第一条记录
	 * @param hql
	 * @return
	 */
    T getByHql(String hql);
	
	void deleteBySql(String sql);
	/** <pre>queryByDto(这里用一句话描述这个方法的作用)   
	 * 创建人：Caomq caomqvip@sina.com
	 * 创建时间：2017年3月24日 下午5:26:17    
	 * 修改人：Caomq caomqvip@sina.com
	 * 修改时间：2017年3月24日 下午5:26:17    
	 * 修改备注： 
	 * @param hql
	 * @param map
	 * @param page
	 * @return</pre>    
	 */
    List<?> queryByDto(String hql, Map<String, Object> map, Page page);
	/** <pre>queryByDto2(这里用一句话描述这个方法的作用)   
	 * 创建人：Caomq caomqvip@sina.com
	 * 创建时间：2017年3月27日 下午2:34:15    
	 * 修改人：Caomq caomqvip@sina.com
	 * 修改时间：2017年3月27日 下午2:34:15    
	 * 修改备注： 
	 * @param hql
	 * @param map
	 * @param page
	 * @return</pre>    
	 */
    List<?> queryByDto2(String hql, Map<String, Object> map, Page page);
	int deleteByHql(String hql);
	Object getDtoByHql(String hql);
	List<?> queryDtoByHql(String hql, Map<String, Object> map, Page pageBean);
	int getNewCount(Map<String, Object> map, String conditions);
	
}
