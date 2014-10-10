package com.dvnchina.advertDelivery.utils;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dvnchina.advertDelivery.model.UserCustomer;

public class HibernateSQLTemplete  extends HibernateDaoSupport {
	
	
	//页面上，查询结果集
	public List list(final String hql,final Map map, final int begin, final int pageSize) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
		  public Object doInHibernate(Session sess)throws HibernateException, SQLException {
			//	Query q = sess.createQuery(hql);
			    Query q = getQuery2(hql,map,sess);
				q.setFirstResult(begin);
				q.setMaxResults(pageSize);
				return q.list();
			}
		});
	}
	
	private Query getQuery2(String queryString, Map map,Session sess) {
		//Session sess = null;
		Query query = null;
		//保证前后使用同一个session
		query = sess.createQuery(queryString);
		for (Iterator<Entry> it = map.entrySet().iterator(); it.hasNext();) {
			Entry entry = it.next();
			query.setParameter(entry.getKey().toString(), entry.getValue());
		}
		
		return query;
	}
	
	
	
	/**
	 * 获取查询条件
	 */
	public List findByHQL(String queryString, Map map,int begin,int pageSize) {
		List<Object> list = null;
		Query query = getQuery(queryString, map);
		query.setFirstResult(begin-1);
		query.setMaxResults(pageSize);
		
		System.out.println(begin);
		System.out.println(pageSize);
		
		list = query.list();
		return list;
	}
	
	/**
	 * 无过滤获取查询条件
	 */
	public List findByHQL(String queryString) {
		Query query = getSession().createQuery(queryString);
		return query.list();
	}
	
	
	public List<Integer> findByHQL2(String queryString) {
		List<Integer> cIds = this.getSession().createSQLQuery(queryString).addEntity(UserCustomer.class).list();;
		return cIds;
	}
	

	/**
	 * 分页获取查询条件
	 * @param queryString
	 * @param map
	 * @param begin
	 * @param pageSize
	 * @return
	 */
	public List findByHQLByPage(String queryString, Map map,int begin,int pageSize) {
		Query query = getQuery(queryString, map);
		query.setFirstResult(begin);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
	
	
	/**
	 * 获取query
	 */
	//可以在这里添加一个参数，用于分页查询
	private Query getQuery(String queryString, Map map) {
		//Session sess = null;
		Query query = null;
		//保证前后使用同一个session
		query = getSession().createQuery(queryString);
		for (Iterator<Entry> it = map.entrySet().iterator(); it.hasNext();) {
			Entry entry = it.next();
			query.setParameter(entry.getKey().toString(), entry.getValue());
		}
		
		return query;
	}

	/**
	 * 查询记录总数HQL
	 * 
	 */
	public int getTotalByHQL(String queryString, Map map) {
		int total = 0;
		Query query = getQuery(queryString, map);
		String s= query.uniqueResult().toString();
		total = Integer.parseInt(s);
		return total;
	}


}






