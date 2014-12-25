package com.dvnchina.advertDelivery.dao;

import java.util.Collection;
import java.util.List;



/**
 * 封装用于实体对象操作的接口.
 */
public interface BaseDao {
	/**
	 * 将给定的对象保存到数据库中.
	 * 
	 * @param obj
	 *            要保存的对象
	 * @return 保存成功后的 id
	 */
	public Integer save(Object obj);

	public void saveOrUpdate(Object obj);
	/**
	 * 修改指定的对象
	 * 
	 * @param obj
	 *            修改对象
	 */
	public void update(Object obj);

	/**
	 * 从数据库删除给定的对象.
	 * 
	 * @param obj
	 *            要删除的对象
	 */
	public void delete(Object obj);
	
	/**
	 * 保存集合对象到数据库中
	 * @param entities
	 * @return
	 */
	public Collection saveAll(Collection entities);

	/**
	 * 删除集合对象
	 * @param entities
	 */
	public void deleteAll(Collection entities);
	
	/**
	 * 删除持久化对象，依据主键
	 * @param hql
	 * @param id  主键
	 */
	public void deleteObjectById(String hql ,Integer id);
	
	/**
	 *  删除持久化对象，依据主键
	 * @param objName  对象名
	 * @param id  主键
	 */
	public void deleteObj(String objName ,Integer id);
	
	/**
	 * 执行HQL语句，获取总记录数
	 * @param hql HQL语句
	 * @param values 参数
	 * @return int 总记录数
	 */
	public int getTotalCountHQL(final String hql, final Object[] values) ;
	
	public int getTotalCountSQL(String sql, Object[] values);
	
	public Integer toInteger(Object obj);
	
	public Long toLong(Object obj);
	
	/**
	 * 从数据库查询指定开始位置和记录数的列表.
	 * 
	 * @param hql
	 *            给定 hql 语句,示例: from Users
	 * @param begin
	 *            开始位置
	 * @param pageSize
	 *            记录数
	 * @return
	 */
	public List listByPage(final String hql, final int begin, final int pageSize);
	
	/**
	 * 从数据库查询记录列表.
	 * 
	 * @param hql
	 *            给定 hql 语句,示例: from Users
	 * @return
	 */
	public List list(String hql, Object... params);

	/**
	 * 按给定的 hql 语句和参数返回对象.
	 * 
	 * @param hql
	 *            指定的 hql 语句
	 * @param params
	 *            替换 ？ 的参数
	 * @return 符合条件的对象
	 */
	public Object get(String hql, Object... params);

	/**
	 * 根据给定类的 class 属性和对象的 id 查找对象.
	 * 
	 * @param c
	 *            类的 class 属性
	 * @param id
	 *            要查找的对象的 id
	 * @return 查找的对象，如果不存在，则返回 null
	 */
	public Object get(Class c, Integer id);
	
	/**
	 * 从数据库查询总记录数
	 * 
	 * @param hql
	 *            给定 hql 语句,示例: from Users
	 * 
	 * @return
	 * 
	 */
	public int getCount(String hql);
	
	/**
	 * 执行HQL语句，获取分页记录
	 * @param hql HQL语句
	 * @param values 参数
	 * @param page 当前页码
	 * @param pageSize 每页大小
	 * @return List<?>
	 */
	public List<?> getListForPage(final String hql, final Object[] values,final int page, final int pageSize);
	
	 /**
	  * @param sql  sql语句
	 * @param values 参数
	 * 根据SQL语句获取列表
	 */
  public List getDataBySql(String sql, final Object[] values);
  
  public List<?> getListBySql(final String sql, final Object[] values, final int page, final int pageSize);
  
  public int executeByHQL(final String hql, final Object[] values);
  
  public int executeBySQL(final String sql, final Object[] values);
  
  /**
   * 不分页，全部查询。
   * @param hql
   * @param values
   * @return
   */
  public List getListForAll(final String hql, final Object[] values);
}
