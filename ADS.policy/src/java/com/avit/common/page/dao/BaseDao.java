package com.avit.common.page.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * @author Weimmy
 * 
 * @date:2011-7-19
 * @version :1.0
 * 
 */
public interface BaseDao {
	public Object save(Object o);

	public Object merge(Object entity);

	public Object load(final Class entity, final Serializable id);

	public Object get(final Class entity, final Serializable id);

	public List findAll(final Class entity);

	public List findByNamedQuery(final String namedQuery);

	public List findByNamedQuery(final String query, final Object parameter);

	public List findByNamedQuery(final String query, final Object[] parameters);

	public List findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value);

	public List find(final String query);

	public List find(final String query, final Object parameter);

	public Collection saveAll(Collection entities);

	public void deleteAll(Collection entities);
	
	//-------------------------add weimmy 2011-7-21----------------------------
	/**
	 * 通过名称获取SQL语句，并执行
	 * @param namedQuery
	 * @param values
	 * @return
	 */
	public int executeByNamedQuery(final String namedQuery, final Object[] values);
	/**
	 * 传入HQL语句，并执行
	 * @param hql
	 * @param values
	 * @return
	 */
	public int executeByHQL(final String hql, final Object[] values);
	/**
	 * 不分页，全部查询。
	 * @param hql
	 * @param values
	 * @return
	 */
	public List getListForAll(final String hql, final Object[] values);
	/**
	 * 
	 * @param hql  hql语句
	 * @param values 参数
	 * @param page  当前页码
	 * @param pageSize 每页大小
	 * @return
	 */
	public List getListForPage(final String hql, final Object[] values,final int page, final int pageSize);
	/**
	  * @param hql  hql语句
	 * @param values 参数
	 * 获取总页码数
	 */
	public int getTotalCountHQL(final String hql, final Object[] values) ;
}
