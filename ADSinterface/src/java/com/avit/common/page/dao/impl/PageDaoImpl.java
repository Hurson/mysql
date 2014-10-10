package com.avit.common.page.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;

import com.avit.common.page.PageBean;
import com.avit.common.page.dao.PageDao;

@SuppressWarnings("unchecked")
public class PageDaoImpl extends BaseDaoImpl implements PageDao {

	public static Log log = LogFactory.getLog(PageDaoImpl.class);

	public PageBean getListForPage(Class myclass, PageBean pagebean)
			throws Exception {
		String hsql = "from " + myclass.getName();
		return getListForPage(hsql, pagebean);

	}

	public PageBean getListForPage(Class myclass, String[] parasneeded,
			PageBean pagebean) throws Exception {
		StringBuffer hsql = new StringBuffer(80);
		hsql.append("select ");
		for (int n = 0; n < parasneeded.length; n++) {
			if (n == 0) {
				hsql.append(parasneeded[0]);
			} else {
				hsql.append(",").append(parasneeded[n]);
			}
		}
		hsql.append(" from ").append(myclass.getName());
		return getListForPage(hsql.toString(), pagebean);

	}

	// 通用分页处理
	public PageBean getListForPage(String hsql, PageBean pagebean)
			throws Exception {

		return getListForPage(hsql, new Object[] {}, pagebean);
	}

	/**
	 * @param hsql
	 * @param sqlParas
	 * @param parasTypes
	 * @param pagebean
	 * @return
	 * @throws Exception
	 */
	public PageBean getListForPage(String hsql, Object[] sqlParas,
			Type[] parasTypes, PageBean pagebean) throws Exception {
		List pagelist = null;
		Session session = getSession();
		pagebean.setRowcount(getTotalCount(session, hsql, sqlParas, parasTypes));
		if (pagebean.isUsepage()) { // 是否分页
			Query query = null;
			query = session.createQuery(hsql);
			setparasforquery(query, sqlParas, parasTypes);

			int pagesize = pagebean.getPagesize();
			int start = (pagebean.getPageno() - 1) * pagebean.getPagesize();

			query.setFirstResult(start);
			query.setMaxResults(pagesize);
			pagelist = query.list();
		} else {
			pagelist = session.createQuery(hsql).list();
		}
		pagebean.setListpage(pagelist);
		return pagebean;
	}

	public PageBean getListForPage(String hsql, Object[] sqlParas,
			PageBean pagebean) throws Exception {
		return this.getListForPage(hsql, sqlParas, null, pagebean);
	}

	private int getTotalCountSQL(Session session, String hql,
			Object[] sqlParas, Type[] parasTypes) {
		Long amount = new Long(0);
		int sql_from = hql.indexOf("from");
		int sql_orderby = hql.indexOf("order by");
		String countStr = null;
		if (sql_orderby > 0){
			countStr = "select count(*) "
					+ hql.substring(sql_from, sql_orderby);
		}else{
			countStr = "select count(*) " + hql.substring(sql_from);
		}
		Query query = session.createSQLQuery(countStr
				.replaceAll(" fetch ", " "));
		setparasforquery(query, sqlParas, parasTypes);

		List list = query.list();
		if (!list.isEmpty() && list.size() > 0) {
			try {
				return ((Long) list.get(0)).intValue();
			} catch (Exception ex) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	private Query setparasforquery(Query query, Object[] sqlParas, Type[] types) {
		for (int n = 0; n < sqlParas.length; n++) {
			if (types != null) {
				query.setParameter(n, sqlParas[n], types[n]);
			} else {
				query.setParameter(n, sqlParas[n]);
			}
		}
		return query;
	}

	/**
	 * @param session
	 * @param hql
	 * @param sqlParas
	 * @param parasTypes
	 * @return
	 */
	private int getTotalCount(Session session, String hql, Object[] sqlParas,
			Type[] parasTypes) {
		Long amount = new Long(0);
		int sql_from = hql.indexOf("from");
		int sql_orderby = hql.indexOf("order by");// 为了改进
		String countStr = null;
		if (sql_orderby > 0) {
			countStr = "select count(*) "
					+ hql.substring(sql_from, sql_orderby);
		} else {
			countStr = "select count(*) " + hql.substring(sql_from);
		}
		Query query = session.createQuery(countStr.replaceAll(" fetch ", " "));
		setparasforquery(query, sqlParas, parasTypes);

		List list = query.list();
		if (!list.isEmpty()) {
			amount = (Long) list.get(0);
		} else {
			return 0;
		}
		return amount.intValue();
	}

}
