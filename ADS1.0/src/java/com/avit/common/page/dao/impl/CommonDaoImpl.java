/**
 * Copyright (c) AVIT LTD (2012). All Rights Reserved.
 * Welcome to <a href="www.avit.com.cn">www.avit.com.cn</a>
 */
package com.avit.common.page.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.avit.common.page.PageBean;
import com.avit.common.page.dao.CommonDao;

/**
 * @Description:
 * @author lizhiwei
 * @Date: 2012-5-2
 * @Version: 1.0
 */

public class CommonDaoImpl extends BaseDaoImpl implements CommonDao {

	/* (non-Javadoc)
	 * @see cn.com.avit.mmsp.siag.commons.CommonDao#getTotalCountHQL(java.lang.String, java.util.List)
	 */
	public int getTotalCountHQL(final String hql, final List params) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				int sql_from = hql.indexOf("from");
				String countStr = "select count(*) " + hql.substring(sql_from);

				Query query = session.createQuery(countStr);
				if (params != null){
					for (int i = 0; i < params.size(); i++){
						query.setParameter(i, params.get(i));
					}
				}
				List list = query.list();
				if (!list.isEmpty() && list.size()>0) {
					return ((Long)list.get(0)).intValue();
					
				} else{
					return 0;
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see cn.com.avit.mmsp.siag.commons.CommonDao#getListForPage(java.lang.String, java.util.List, int, int)
	 */
	public List getListForPage(final String hql, final List params, final int pageNumber, final int pageSize) {
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				
				Query query = session.createQuery(hql);
				if (params != null){
					for (int i = 0; i < params.size(); i++){
						query.setParameter(i, params.get(i));
					}
				}
				if(pageNumber == -1 || pageSize == -1){
					return query.list();
				}else{
					int start = (pageNumber-1) * pageSize;
					query.setFirstResult(start);
					query.setMaxResults(pageSize);
					return  query.list();
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see cn.com.avit.mmsp.siag.commons.CommonDao#getPageList(java.lang.String, java.util.List, int, int)
	 */
	public PageBean getPageList(final String hql, final List params, int pageNumber, final int pageSize) {
		int rowcount = getTotalCountHQL(hql, params);
		
		PageBean pageBean = new PageBean();
		pageBean.setPagesize(pageSize);
		pageBean.setRowcount(rowcount);
		
		if(pageNumber > pageBean.getPagecount()){
			pageNumber = pageBean.getPagecount();
		}
		
		if(rowcount == 0) {
			pageNumber = 1;
			pageBean.setPagecount(1);
		}
		
		pageBean.setPageno(pageNumber);
		List list = this.getListForPage(hql, params, pageNumber, pageSize);
		pageBean.setListpage(list);
		
		return pageBean;
	}
	
	public List getListForAll(final String hql, final  List params) {
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				Query query = session.createQuery(hql);
				if (params != null){
					for (int i = 0; i < params.size(); i++){
						query.setParameter(i, params.get(i));
					}
				}
				return query.list();
			}
		});
	}
	
	public List getListForAllWithList(final String hql, final  List params, final List<String> listParam) {
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				Query query = session.createQuery(hql);
				if (params != null){
					for (int i = 0; i < params.size(); i++){
						query.setParameter(i, params.get(i));
					}
				}
				query.setParameterList("adsCodes", listParam);
				return query.list();
			}
		});
	}
	
	

	public void delete(final String hql) {
		this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				Query query = session.createQuery(hql);
				
				return query.executeUpdate();
			}
		});
	}
	
	public void delete(Object o) {
		this.getHibernateTemplate().delete(o);
	}
	public List getObjectListForAll(final String sql, final  List params,final Class entity) {
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				Query query = session.createSQLQuery(sql);
				if (params != null){
					for (int i = 0; i < params.size(); i++){
						query.setParameter(i, params.get(i));
					}
				}
				return query.setResultTransformer(Transformers.aliasToBean(entity)).list();
				//return query.list();
			}
		});
	}

	
}
