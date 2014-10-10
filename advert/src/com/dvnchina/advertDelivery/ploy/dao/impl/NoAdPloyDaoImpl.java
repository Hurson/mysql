package com.dvnchina.advertDelivery.ploy.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.classic.QueryTranslatorImpl;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.ploy.bean.TNoAdPloy;
import com.dvnchina.advertDelivery.ploy.dao.NoAdPloyDao;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;

public class NoAdPloyDaoImpl extends HibernateSQLTemplete implements NoAdPloyDao {

	public Long getDataTotal(String hql, final List params) { 

		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql, 
		Collections.EMPTY_MAP, (SessionFactoryImplementor) this 
		.getSessionFactory()); 
		queryTranslator.compile(Collections.EMPTY_MAP, false); 
		String tempSQL = queryTranslator.getSQLString(); 
		String countSQL = "select count(*) from (" + tempSQL + ") tmp_count_t"; 
		Query query = this.getSession().createSQLQuery(countSQL); 
		if (params!=null)
		{
			for (int i = 0; i < params.size(); i++) { 
			query.setParameter(i, params.get(i)); 
			} 
		}
		List list = query.list(); 
		BigInteger count =new BigInteger("0");
		if (list!= null && list.size()> 0 && !list.get(0).toString().equals("0") )
		{
			count =(BigInteger)list.get(0);
		}
		//BigDecimal count = list == null || list.size() <= 0 ? new BigDecimal(0): (BigDecimal) list.get(0); 
		return count.longValue(); 
		} 
	public int getTotalCountHQL(final String hql, final List params) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				int sql_from = hql.indexOf("from");
				if (hql.contains("distinct"))
				{
					return getDataTotal(hql,params).intValue();
				}
				String countStr = "select count(*) " + hql.substring(sql_from);
				
				Query query = session.createQuery(countStr);
				if (params != null){
					for (int i = 0; i < params.size(); i++){
						query.setParameter(i, params.get(i));
					}
				}
				Integer aa ;
			
				List list = query.list();
				if (!list.isEmpty() && list.size()>0) {
					return ((Long)list.get(0)).intValue();
					
				} else{
					return 0;
				}
			}
		});
	}
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
	
	public List getList(final String hql, final List params ) {
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				
				Query query = session.createQuery(hql);
				if (params != null){
					for (int i = 0; i < params.size(); i++){
						query.setParameter(i, params.get(i));
					}
				}
				
					return  query.list();
				}
		});
	}
	public PageBeanDB getPageList(final String hql, final List params, int pageNumber, final int pageSize) {
		int rowcount = getTotalCountHQL(hql, params);
		
		PageBeanDB pageBean = new PageBeanDB();
		if (pageNumber==0)
		{
			pageNumber =1;
		}		
		pageBean.setPageSize(pageSize);
		pageBean.setCount(rowcount);
		pageBean.setTotalPage((rowcount%pageSize==0)?rowcount/pageSize:rowcount/pageSize+1);
		
		if(rowcount == 0) {
			pageNumber = 0;
			pageBean.setTotalPage(0);
		}
		if (pageNumber>pageBean.getTotalPage())
		{
			pageNumber=1;
		}
		pageBean.setPageNo(pageNumber);
		List list = this.getListForPage(hql, params, pageNumber, pageSize);
		pageBean.setDataList(list);
		
		return pageBean;
	}
	
	@Override
	public PageBeanDB queryNoAdPloyList(TNoAdPloy ploy, Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		 String sql;
	     sql="from TNoAdPloy t where 1=1  " ;
	     if (ploy!=null && ploy.getPloyname()!=null && !"".equals(ploy.getPloyname()))
	     {
	        		sql=sql+" and t.ployname like '%"+ploy.getPloyname()+"%'";
	     }
	     if (ploy!=null && ploy.getPositionid()!=null)
	     {
	        		sql=sql+" and t.positionid="+ploy.getPositionid();
	     }	  
	     if (ploy!=null && ploy.getTvn()!=null && !ploy.getTvn().equals(""))
	     {
	        		sql=sql+" and t.tvn like '%"+ploy.getTvn()+"%'";
	     }	  
	   
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}

	@Override
	public TNoAdPloy getNoAdPloyByID(Long ployId) {
		// TODO Auto-generated method stub
		Object obj=this.getSession().get(TNoAdPloy.class,ployId);
		if (obj!=null)
		{
			return (TNoAdPloy)obj;
		}
		return null;
	}

	@Override
	public PageBeanDB queryAdPosition(AdvertPosition adPosition,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		 String sql;
		    // sql="from AdvertPosition t where 1=1" ;
			 sql ="from AdvertPosition p where 1=1 and p.deliveryMode=1";
			// "c.marketingRuleId,"+
			// "p.isAsset)"+
		 
		     
		   //  sql=sql+ ")";
		    
		     sql=sql+" order by p.id ";    
		   
		      //分页查询 
		      List params = null;		     
		      return this.getPageList(sql, params, 1, 100); 
	}

	@Override
	public String checkNoAdPloy(TNoAdPloy ploy) {
		// TODO Auto-generated method stub
		String sql;
	     sql="from TNoAdPloy t where 1=1 " ;
	     if (ploy!=null && ploy.getId()!=null )
	     {
	    	sql=sql+" and t.id !="+ploy.getId();    
	     }
	     if (ploy!=null && ploy.getPloyname()!=null )
	     {
	    	sql=sql+" and t.ployname ='"+ploy.getPloyname()+"'";    
	     }
		List temp =this.getList(sql, null);
		if (temp!=null && temp.size()>0)
		{
			return "1";
		}
		return "0";
	}

	@Override
	public boolean saveOrUpdate(TNoAdPloy ploy) {
		
		// TODO Auto-generated method stub
		this.getSession().saveOrUpdate(ploy);
		return true;
	}
	public boolean deleteNoAdPloy(String dataIds)
	{
		String sql;
	     sql="delete TNoAdPloy t where 1=1  " ;
	       
	     sql=sql+" and t.id in (" +dataIds+")";    
	 	Query query = this.getSession().createQuery(sql);
	 	query.executeUpdate();
		//return ;
		return true;
	}
}
