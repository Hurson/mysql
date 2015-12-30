package com.avit.dtmb.channelGroup.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.classic.QueryTranslatorImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.avit.dtmb.channelGroup.bean.DChannelGroup;
import com.avit.dtmb.channelGroup.bean.DChannelGroupRef;
import com.avit.dtmb.channelGroup.bean.DChannelInfoSync;
import com.avit.dtmb.channelGroup.dao.ChannelGroupDao;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.ChannelGroupRef;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;
import com.dvnchina.advertDelivery.utils.StringUtil;
@Repository("ChannelGroupDao")
public class ChannelGroupDaoImpl extends HibernateSQLTemplete implements ChannelGroupDao {

	@Override
	public PageBeanDB queryChanelGroupList(DChannelGroup channelGroupQuery,
			int pageSize, int pageNo) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		 UserLogin user = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		 String accessUserIds = StringUtil.objListToString(user.getAccessUserIds(), ",", "-1");
		 String sql;
	     sql="from DChannelGroup d where 1=1  " ;
	     sql+=" and d.operatorId in (" + accessUserIds + ")";
	     if (channelGroupQuery!=null && channelGroupQuery.getName()!=null && !"".equals(channelGroupQuery.getName()))
	     {
	        		sql=sql+" and d.name='"+channelGroupQuery.getName()+"'";
	     }
	     
	    
	     sql=sql+" order by d.id desc";    
	      //分页查询 
	      List params = null;
	      return getPageList(sql, params, pageNo, pageSize); 
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
		BigDecimal count =new BigDecimal(0);
	//	System.out.println(list.get(0));
		if (list!= null && list.size()> 0 && !list.get(0).toString().equals("0") )
		{
			count =new BigDecimal(list.get(0).toString());
		}
		//BigDecimal count = list == null || list.size() <= 0 ? new BigDecimal(0): (BigDecimal) list.get(0); 
		return count.longValue(); 
		}



	@Override
	public Integer getEntityCountByCode(String code) {
		final StringBuffer sb = new StringBuffer();
		sb.append(" SELECT COUNT(1) FROM d_channel_group WHERE CODE = '" + code + "'");
		List<?> list = this.getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sb.toString());
				return query.list();
			}
		});
	    int count = 0;
	    if(list.size() > 0){
	    	count = ((BigInteger)list.get(0)).intValue();
	    }
		return count;
	}



	@Override
	public boolean updateChannelGroup(DChannelGroup channelGroup) {
		this.getHibernateTemplate().saveOrUpdate(channelGroup);
        return true;
	}



	@Override
	public boolean saveChannelGroup(DChannelGroup channelGroup) {
		this.getHibernateTemplate().saveOrUpdate(channelGroup);
        return true;
	}



	@Override
	public boolean deleteChannelGroup(String ids) {
		ids = ids.replace(" ", "");
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        String hqlUpdate1 = "DELETE from D_CHANNEL_GROUP_REF WHERE GROUP_ID IN "+ids;
        String hqlUpdate2 = "DELETE from D_CHANNEL_GROUP WHERE ID IN "+ids;
        Query query1 = session.createSQLQuery(hqlUpdate1);
        Query query2 = session.createSQLQuery(hqlUpdate2);

        query1.executeUpdate();
        query2.executeUpdate();

        ts.commit();
        session.close();
        return true;
	}



	@Override
	public PageBeanDB queryChannelGroupRefList(DChannelInfoSync channelQuery,
			int pageSize, int pageNo) {
		String sql;
		 sql="select distinct new com.avit.dtmb.channelGroup.bean.DChannelInfoSync(t.channelId,t.channelName,t.channelType,t.serviceId,t.tsId,t.networkId,t.isPlayBack) from DChannelInfoSync t,DChannelGroupRef a where t.serviceId = a.serviceId" ;
		 if (channelQuery!=null && channelQuery.getChannelGroupId()!=null && !"".equals(channelQuery.getChannelGroupId()))
	     {
	        		sql=sql+" and a.groupId="+channelQuery.getChannelGroupId();
	     }
	     if (channelQuery!=null && channelQuery.getChannelName()!=null && !"".equals(channelQuery.getChannelName()))
	     {
	        		//sql=sql+" and t.channelName='"+channel.getChannelName()+"'";
	        		sql=sql+" and t.channelName like '%"+channelQuery.getChannelName().trim()+"%'";
	     }
	     
	    
	     sql=sql+" group by t.serviceId order by t.id desc";    
	     System.out.println(sql);
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNo, pageSize); 
	}



	@Override
	public PageBeanDB selectChannelList(DChannelInfoSync selectChannelQuery,
			int pageSize, int pageNo, String channelGroupType) {
		String sql;
		 sql="select distinct new com.avit.dtmb.channelGroup.bean.DChannelInfoSync(t.channelId,t.channelName,t.channelType,t.serviceId,t.tsId,t.networkId,t.isPlayBack) from DChannelInfoSync t where t.channelType = '"+channelGroupType+"'";
		 if(selectChannelQuery!=null &&selectChannelQuery.getChannelGroupId()!=null){;
		     sql=sql+"and  t.serviceId not in(select distinct r.serviceId from DChannelGroupRef r where r.groupId="+selectChannelQuery.getChannelGroupId()+")";
		 }
	     if (selectChannelQuery!=null && selectChannelQuery.getChannelName()!=null && !"".equals(selectChannelQuery.getChannelName()))
	     {
	         sql=sql+" and t.channelName like '%"+selectChannelQuery.getChannelName().trim()+"%'";
	     }
	     if (selectChannelQuery!=null && selectChannelQuery.getChannelType()!=null && !"".equals(selectChannelQuery.getChannelType()))
	     {
	         sql=sql+" and t.channelType = '"+selectChannelQuery.getChannelType().trim()+"'";
	     }
	    
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNo, pageSize); 
	}



	@Override
	public boolean saveChannelGroupRefList(
			List<DChannelGroupRef> channelGroupRefList) {
		this.getHibernateTemplate().saveOrUpdateAll(channelGroupRefList);
        return true;
	}



	@Override
	public boolean deleteChannelGroupRef(String ids) {
		ids = ids.replace(" ", "");
	        Session session = this.getSessionFactory().openSession();
	        Transaction ts = session.beginTransaction();
	        String hqlUpdate = "DELETE from D_CHANNEL_GROUP_REF WHERE SERVICE_ID IN "+ids;
	        Query query = session.createSQLQuery(hqlUpdate);

	        query.executeUpdate();

	        ts.commit();
	        session.close();
	        return true;
	}



	@Override
	public DChannelGroup getChannelGroupByID(Long channelGroupId) {
		String hql =" from DChannelGroup t " +     
		        "where t.id="+channelGroupId;

		        List list = this.getList(hql,null);
		        DChannelGroup DchannelGroup =null;
		        if (list!=null && list.size()>0)
		        {
		        	DchannelGroup = (DChannelGroup)(list.get(0));
		        }

		        return DchannelGroup;
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
}

