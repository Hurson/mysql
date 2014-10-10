package com.avit.common.page.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.avit.common.page.dao.BaseDao;

/**
 * @author Weimmy
 * 
 * @date:2011-7-19
 * @version :1.0
 * 
 */
@SuppressWarnings("all")
@Repository("baseDao")
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {
    
    @Resource(name = "sessionFactory")
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
    
	public Object load(final Class entity, final Serializable id) {
		return getHibernateTemplate().load(entity, id);
	}

	public Object get(final Class entity, final Serializable id) {
		return getHibernateTemplate().get(entity, id);
	}

	public void mergeAll(Collection entity) {
		// TODO Auto-generated method stub
		Assert.notEmpty(entity);
		Iterator iter = entity.iterator();
		while (iter.hasNext()) {
			this.merge(iter.next());
		}
	}

	public List findAll(final Class entity) {
		return getHibernateTemplate().find("from " + entity.getName());
	}

	public List findByNamedQuery(final String namedQuery) {
		return getHibernateTemplate().findByNamedQuery(namedQuery);
	}

	public List findByNamedQuery(final String query, final Object parameter) {
		return getHibernateTemplate().findByNamedQuery(query, parameter);
	}

	public List findByNamedQuery(final String query, final Object[] parameters) {
		return getHibernateTemplate().findByNamedQuery(query, parameters);
	}

	public List find(final String query) {
		return getHibernateTemplate().find(query);
	}

	public List find(final String query, final Object parameter) {
		return getHibernateTemplate().find(query, parameter);
	}

	public List findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramName, value);
	}

	public int executeByNamedQuery(final String namedQuery,
			final String[] params, final Object[] values) {
		return (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					protected void applyNamedParameterToQuery(
							Query queryObject, String paramName, Object value)
							throws HibernateException {
						if (value instanceof Collection) {
							queryObject.setParameterList(paramName,
									(Collection) value);
						} else if (value instanceof Object[]) {
							queryObject.setParameterList(paramName,
									(Object[]) value);
						} else {
							queryObject.setParameter(paramName, value);
						}
					}

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						// TODO Auto-generated method stub
						Query query = session.getNamedQuery(namedQuery);

						if (values != null) {
							for (int i = 0; i < values.length; i++) {
								applyNamedParameterToQuery(query, params[i],
										values[i]);
							}
						}

						return query.executeUpdate();
					}
				});
	}

	/**
	 */
	public Object save(Object o) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().saveOrUpdate(o);
		return o;
	}

	public Object merge(Object entity) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().merge(entity);
	}

	public Collection saveAll(Collection entities) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().saveOrUpdateAll(entities);
		return entities;
	}

	public void deleteAll(Collection entities) {
		this.getHibernateTemplate().deleteAll(entities);
	}


	
	/**
	 * 通过名称获取SQL语句，并执行
	 * @param namedQuery
	 * @param values
	 * @return
	 */
	public int executeByNamedQuery(final String namedQuery, final Object[] values){
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				Query query = session.getNamedQuery(namedQuery);

				if (values != null){
					for (int i = 0; i < values.length; i++){
						query.setParameter(i, values[i]);
					}
				}
				return query.executeUpdate();
			}
		});
	}
	/**
	 * 传入HQL语句，并执行
	 * @param hql
	 * @param values
	 * @return
	 */
	public int executeByHQL(final String hql, final Object[] values){
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				Query query = session.createQuery(hql);

				if (values != null){
					for (int i = 0; i < values.length; i++){
						query.setParameter(i, values[i]);
					}
				}
				return query.executeUpdate();
			}
		});
	}
	
	/**
	 * 不分页，全部查询。
	 * @param hql
	 * @param values
	 * @return
	 */
	public List getListForAll(final String hql, final Object[] values){
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				//Query query = session.getNamedQuery(namedQuery);
				Query query = session.createQuery(hql);
				if (values != null){
					for (int i = 0; i < values.length; i++){
						query.setParameter(i, values[i]);
					}
				}
				return query.list();
			}
		});
		
	}
	
	/**
	 * 
	 * @param hql  hql语句
	 * @param values 参数
	 * @param page  当前页码
	 * @param pageSize 每页大小
	 * @return
	 */
	public List getListForPage(final String hql, final Object[] values,final int page, final int pageSize){
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				//Query query = session.getNamedQuery(namedQuery);
				Query query = session.createQuery(hql);
				if (values != null){
					for (int i = 0; i < values.length; i++){
						query.setParameter(i, values[i]);
					}
				}
				if(page == -1 || pageSize == -1){//如果page或pageSize是-1，表示不分页查询
					return query.list();
				}else{
					int start = (page-1) * pageSize;
					query.setFirstResult(start);
					query.setMaxResults(pageSize);
					return  query.list();
				}
			}
		});
	}
	
/*	private <T>List<T> getListForPage(final String hql, final Object[] values,final int page, final int pageSize){
		return (List<T>) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				//Query query = session.getNamedQuery(namedQuery);
				Query query = session.createQuery(hql);
				if (values != null){
					for (int i = 0; i < values.length; i++){
						query.setParameter(i, values[i]);
					}
				}
				int start = (page-1) * pageSize;
				query.setFirstResult(start);
				query.setMaxResults(pageSize);
				return (List<T>) query.list();
			}
		});
	}*/

	/**
	  * @param hql  hql语句
	 * @param values 参数
	 * 获取总页码数
	 */
	public int getTotalCountHQL(final String hql, final Object[] values) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				int sql_from = hql.indexOf("from");
				String countStr = "select count(*) " + hql.substring(sql_from);

				Query query = session.createQuery(countStr);
				if (values != null){
					for (int i = 0; i < values.length; i++){
						if (values[i] instanceof Date) {
		                    // TODO 难道这是bug 使用setParameter不行？？
		                    query.setDate(i, (Date) values[i]);
		                } else {
		                    query.setParameter(i, values[i]);
		                }

					}
				}
				List list = query.list();
				if (!list.isEmpty() && list.size()>0) {
					try {
						return ((Long)list.get(0)).intValue();
					} catch (Exception ex) {
						ex.printStackTrace();
						return 0;
					}
				} else{
					return 0;
				}
			}
		});
	}
	
//	public int getTotalCountSQL(String sql, Object[] values) {
//        String countSql = "select count(*) from (" + sql + ")";
//        Query query = getSession().createSQLQuery(countSql);
//        
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//            	query.setParameter(i, values[i]);
//            }
//        }
//		List<?> list = query.list();
//		if (list.size()>0) {
//			try {
//				return ((BigDecimal)list.get(0)).intValue();
//			} catch (Exception ex) {
//				ex.printStackTrace();
//				return 0;
//			}
//		} else{
//			return 0;
//		}
//	}
	   public int getTotalCountSQL(final String sql, final Object[] params) {
			return (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException{
					Query query = session.createSQLQuery(sql);
					if (params != null) {
			            for (int i = 0; i < params.length; i++) {
			            	query.setParameter(i, params[i]);
			            }
			        }
					List<?> list = query.list();
					if (list.size()>0) {
						try {
							return ((BigDecimal)list.get(0)).intValue();
						} catch (Exception ex) {
							ex.printStackTrace();
							return 0;
						}
					} else{
						return 0;
					}
				}
			});
		}
//	public List<?> getListBySql(String sql, Object[] values, int page, int pageSize) {
//        Query query = getSession().createSQLQuery(sql);
//        
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//            	query.setParameter(i, values[i]);
//            }
//        }
//
//        if (page > -1 && pageSize > -1) {
//        	query.setFirstResult((page - 1) * pageSize);
//        	query.setMaxResults(pageSize);
//        }
//        
//		return query.list();
//	}
	public List<?> getListBySql(final String sql, final Object[] values, final int page, final int pageSize) {
			return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException{
					//Query query = session.getNamedQuery(namedQuery);
					Query query = session.createSQLQuery(sql);
					if (values != null) {
			            for (int i = 0; i < values.length; i++) {
			            	query.setParameter(i, values[i]);
			            }
			        }

			        if (page > -1 && pageSize > -1) {
			        	query.setFirstResult((page - 1) * pageSize);
			        	query.setMaxResults(pageSize);
			        }
			        
					return query.list();
				}
			});
			
		}
	    
    /**
	  * @param sql  sql语句
	 * @param values 参数
	 * 获取菜单表的所有记录 add by hemeijin 20110728
	 */
//    public List getDataBySql(String sql, final Object[] values) {
//        Query qury = getSession().createSQLQuery(sql);
//        if (values != null) {
//            for (int i = 0; i < values.length; i++) {
//                qury.setParameter(i, values[i]);
//            }
//        }
//        List qList = qury.list();
//        return qList;
//    }
    public List getDataBySql(final String sql, final Object[] values){
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				//Query query = session.getNamedQuery(namedQuery);
				Query query = session.createSQLQuery(sql);
				if (values != null){
					for (int i = 0; i < values.length; i++){
						query.setParameter(i, values[i]);
					}
				}
				return query.list();
			}
		});
		
	}
    
    
//    public void deleteBySql(final String deletesql,final Object[] params) {
//		Query query = getSession().createSQLQuery(deletesql);
//		if (params != null) {
//	            for (int i = 0; i < params.length; i++) {
//	            	query.setParameter(i, params[i]);
//	            }
//	        }
//		query.executeUpdate();
//	}
    public int deleteBySql(final String deletesql, final Object[] params) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				Query query = session.createSQLQuery(deletesql);
				if (params != null) {
		            for (int i = 0; i < params.length; i++) {
		            	query.setParameter(i, params[i]);
		            }
		        }
				return query.executeUpdate();
			}
		});
	}
    /**
	 * BigDecimal to Integer
	 * @param obj
	 * @return Integer
	 */
	public Integer toInteger(Object obj) {
		Integer value = null;
		if (obj != null && obj instanceof BigDecimal) {
			value = ((BigDecimal) obj).intValue();
		}
		return value;
	}
	/**
	 * BigDecimal to Long
	 * @param obj
	 * @return Long
	 */
	public Long toLong(Object obj) {
		Long value = null;
		if (obj != null && obj instanceof BigDecimal) {
			value = ((BigDecimal) obj).longValue();
		}
		return value;
	}
	
	
}
