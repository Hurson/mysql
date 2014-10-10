package com.dvnchina.advertDelivery.meterial.dao.impl;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.classic.QueryTranslatorImpl;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.meterial.dao.MeterialOperatorDao;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;

public class MeterialOperatorDaoImpl   extends HibernateSQLTemplete implements MeterialOperatorDao{
  
	/**
	 * 查询素材分页列表
	 * @param pageNo
	 * @param pageSize
	 * @param queryCondition
	 * @return
	 */
	public PageBeanDB queryMeterialOperatorList(int pageNo, int pageSize, Resource queryCondition) {
		String sql="select distinct new com.dvnchina.advertDelivery.model.Resource(" +
	 		"r.id,r.resourceId,r.resourceName,r.resourceType,r.categoryId,r.startTime,r.endTime,r.state,r.createTime,r.resourceDesc,r.keyWords,r.examinationOpintions, " +
	 		"c.id,c.contractName,a.id,a.positionName) " +		
	        "from Resource r,Contract c,AdvertPosition a "+
	        "where r.advertPositionId=a.id and r.contractId=c.id " +
	        "and (r.state = '2' or r.state = '3') ";
	
		sql = this.addQueryCondition(sql, queryCondition);
		 sql=sql+" order by r.id desc";    
		 //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNo, pageSize); 
	}

	

	/**
	 * 根据素材的ID获取素材对象
	 * @param lid
	 * @return
	 */
	public Resource getMeterialOperatorById(int lid) {
		return null;
	}

	/**
	 * 修改素材运行表的状态
	 * @param state
	 * @return
	 */
	public boolean modifyRunningResourceState(char state, String ids) {
		boolean flag = false;
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		String hqlUpdate = "UPDATE T_RESOURCE H SET H.STATE = :state WHERE H.ID IN "+ids;
		Query query = session.createSQLQuery(hqlUpdate);
		query.setCharacter("state", state);
		int num = query.executeUpdate();
		if(num == 1){
			flag = true;
		}
		ts.commit();
		session.close();
		return flag;
		
	}

	/**
	 * 填写审核意见并修改审核后的状态
	 * @param resource
	 */
	public boolean writeVerifyOpinion(Resource resource, String ids) {
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		String hqlUpdate = "UPDATE T_RESOURCE_BACKUP H SET H.STATE = :state " +
				", H.EXAMINATION_OPINIONS = :opinions " +
				", H.AUDIT_DATE = :auditDate " +
				", H.AUDIT_TAFF = :auditTaff " +
				"WHERE H.ID IN "+ids;
		Query query = session.createSQLQuery(hqlUpdate);
		query.setCharacter("state", resource.getState());
		query.setString("opinions", resource.getExaminationOpintions());
		query.setDate("auditDate", resource.getAuditDate());
		query.setString("auditTaff", resource.getAuditTaff());
		query.executeUpdate();
		
		ts.commit();
		session.close();
		return true;
		
	}

	/**
	 * 查看指定的素材是否被绑定了订单
	 */
	public int hasBindedOrder(String ids) {
		
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		String hqlUpdate = "SELECT COUNT(*) FROM T_ORDER_MATE_REL T WHERE T.MATE_ID IN " + ids;
		Query query = session.createSQLQuery(hqlUpdate);
		BigInteger obj =  (BigInteger)query.list().get(0);
		ts.commit();
		session.close();
		return obj.intValue();
	}
	
	/**
	 * 批量删除下线的素材
	 * @param ids
	 * @return
	 */
	public boolean deleteMeterialOffline(String ids) {
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		String hqlUpdate = "DELETE from T_RESOURCE_BACKUP WHERE ID IN" +ids;
		Query query = session.createSQLQuery(hqlUpdate);
		query.executeUpdate();
		
		String hqlUpdate2 = "DELETE from T_RESOURCE WHERE ID IN" +ids;
		Query query2 = session.createSQLQuery(hqlUpdate2);
		query2.executeUpdate();
		
		ts.commit();
		session.close();
		return true;
		
	}
	
	/**
	 * 添加查询条件
	 * @param hql
	 * @param queryCondition
	 * @return hql
	 */
	private String addQueryCondition(String hql, Resource queryCondition) {
		// 如果没有查询条件直接返回
		if(queryCondition == null){
			return hql;
		}
		// 添加台账订单号查询条件
		String resourceName = queryCondition.getResourceName();
		if(resourceName != null && !resourceName.isEmpty()){
			hql += " and r.resourceName like '%"+resourceName+"%'";
		}
		return hql;
	}
	
	public Long getDataTotal(String hql, final List params) { 

		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql, 
		Collections.EMPTY_MAP, (SessionFactoryImplementor) this 
		.getSessionFactory()); 
		try
		{
		queryTranslator.compile(Collections.EMPTY_MAP, false); 
		}
		catch(Exception e)
		{
			System.out.println();
		}
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
		BigDecimal count =new BigDecimal(0);
		if (list!= null && list.size()> 0 && !list.get(0).toString().equals("0") )
		{
			count =new BigDecimal(list.get(0).toString());
		}
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
	
	public PageBeanDB getPageList(final String hql, final List params, int pageNumber, final int pageSize) {
		int rowcount = getTotalCountHQL(hql, params);
		
		PageBeanDB pageBean = new PageBeanDB();
		if (pageNumber==0)
		{
			pageNumber =1;
		}		
		pageBean.setPageSize(pageSize);
		pageBean.setCount(rowcount);
		pageBean.setTotalPage(rowcount/pageSize+1);
		
		if(rowcount == 0) {
			pageNumber = 1;
			pageBean.setTotalPage(1);
		}
		
		pageBean.setPageNo(pageNumber);
		List list = this.getListForPage(hql, params, pageNumber, pageSize);
		pageBean.setDataList(list);
		
		return pageBean;
	}




}
