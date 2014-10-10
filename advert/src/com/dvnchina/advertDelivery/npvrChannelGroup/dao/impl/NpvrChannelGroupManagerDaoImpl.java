package com.dvnchina.advertDelivery.npvrChannelGroup.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.classic.QueryTranslatorImpl;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.NpvrChannelGroupRef;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.NpvrChannelInfo;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.TNpvrChannelGroup;
import com.dvnchina.advertDelivery.npvrChannelGroup.dao.NpvrChannelGroupManagerDao;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;

public class NpvrChannelGroupManagerDaoImpl extends HibernateSQLTemplete implements NpvrChannelGroupManagerDao {

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
	
	/**
	 * 查询频道组列表
	 * @param channelGroup
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB queryChanelGroupList(TNpvrChannelGroup channelGroup,Integer pageSize,Integer pageNumber) {
		
		 String sql;
		 sql=" from TNpvrChannelGroup t where 1 = 1" ;
	     if (channelGroup!=null && channelGroup.getName()!=null && !"".equals(channelGroup.getName()))
	     {
	        		sql=sql+" and t.name='"+channelGroup.getName()+"'";
	     }
	    
	     sql=sql+" order by t.id desc";    
	   
	     return this.getPageList(sql, null, pageNumber, pageSize); 
	}
	
	
	/**
	 * 查询频道列表
	 * @param channel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB queryChannelGroupRefList(NpvrChannelInfo channel,Integer pageSize,Integer pageNumber) {
		
		 String sql;
		 sql="select distinct new NpvrChannelInfo(t.channelId,t.channelName,t.channelCode,t.channelType,t.serviceId,t.tsId,t.networkId,t.isPlayBack) from NpvrChannelInfo t,NpvrChannelGroupRef a where t.channelId = a.channelId" ;
		 if (channel!=null && channel.getChannelGroupId()!=null && !"".equals(channel.getChannelGroupId()))
	     {
	        		sql=sql+" and a.channelGroupId="+channel.getChannelGroupId();
	     }
	     if (channel!=null && channel.getChannelName()!=null && !"".equals(channel.getChannelName()))
	     {
	        		//sql=sql+" and t.channelName='"+channel.getChannelName()+"'";
	        		sql=sql+" and t.channelName like '%"+channel.getChannelName().trim()+"%'";
	     }
	     
	    
	     sql=sql+" order by t.id desc";    
	   
	     return this.getPageList(sql, null, pageNumber, pageSize); 
	}
	

	/**
	 * 查询频道选择列表
	 * @param channel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB selectChannelList(NpvrChannelInfo channel,Integer pageSize,Integer pageNumber) {
		
		 String sql;
		 sql="select distinct new NpvrChannelInfo(t.channelId,t.channelName,t.channelCode,t.channelType,t.serviceId,t.tsId,t.networkId,t.isPlayBack) from NpvrChannelInfo t where 1=1 " ;
		 
		 if(channel!=null && channel.getChannelGroupId()!=null){
		     sql=sql+"and  t.channelId not in(select distinct r.channelId from NpvrChannelGroupRef r where r.channelGroupId="+channel.getChannelGroupId()+")";
		 }

	     if (channel!=null && channel.getChannelName()!=null && !"".equals(channel.getChannelName()))
	     {
	         sql=sql+" and t.channelName like '%"+channel.getChannelName().trim()+"%'";
	     }
	    
	     sql=sql+" order by t.id desc";    
	     
	     return this.getPageList(sql, null, pageNumber, pageSize); 
	}
	





	
	/**
	 * 
	 * @description: 保存频道组表
	 * @param channelGroup
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 上午09:44:04
	 */
	public boolean saveChannelGroup(TNpvrChannelGroup channelGroup) {
        this.getHibernateTemplate().saveOrUpdate(channelGroup);
        return true;
    }
	
	
	
	/**
	 * 
	 * @description: 修改频道组表
	 * @param channelGroup
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 下午06:04:50
	 */
	public boolean updateChannelGroup(TNpvrChannelGroup channelGroup) {
        this.getHibernateTemplate().saveOrUpdate(channelGroup);
        return true;
    }
	
	/**
	 * 
	 * @description: 批量保存频道组频道关联表
	 * @param contractADList
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 上午10:17:47
	 */
	public boolean saveChannelGroupRefList(List<NpvrChannelGroupRef> channelGroupRefList) {
        this.getHibernateTemplate().saveOrUpdateAll(channelGroupRefList);
        return true;
    }
	
	/**
	 * 删除频道组频道关联表
	 * @param ids
	 * @return
	 */
	public boolean deleteChannelGroupRef(String ids) {
	    ids = ids.replace(" ", "");
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        String hqlUpdate = "DELETE from T_CHANNEL_GROUP_REF_NPVR WHERE CHANNEL_ID IN "+ids;
        Query query = session.createSQLQuery(hqlUpdate);

        query.executeUpdate();

        ts.commit();
        session.close();
        return true;
    }
	
	/**
	 * 删除频道组
	 * @param ids
	 * @return
	 */
	public boolean deleteChannelGroup(String ids) {
	    ids = ids.replace(" ", "");
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        String hqlUpdate1 = "DELETE from T_CHANNEL_GROUP_REF_NPVR WHERE GROUP_ID IN "+ids;
        String hqlUpdate2 = "DELETE from T_CHANNEL_GROUP_NPVR WHERE ID IN "+ids;
        Query query1 = session.createSQLQuery(hqlUpdate1);
        Query query2 = session.createSQLQuery(hqlUpdate2);

        query1.executeUpdate();
        query2.executeUpdate();

        ts.commit();
        session.close();
        return true;
    }
	

	
	/**
	 * 
	 * @description: 根据ID获取频道组信息
	 * @param id
	 * @return 
	 * TChannelGroup
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 上午11:29:00
	 */
	public TNpvrChannelGroup getChannelGroupByID(int id) {

        String hql =" from TNpvrChannelGroup t " +     
        "where t.id="+id;

        List list = this.getList(hql,null);
        TNpvrChannelGroup channelGroup =null;
        if (list!=null && list.size()>0)
        {
        	channelGroup = (TNpvrChannelGroup)(list.get(0));
        }

        return channelGroup;
    }

	@Override
	public Integer getEntityCountByCode(String code) {
		final StringBuffer sb = new StringBuffer();
		sb.append(" SELECT COUNT(1) FROM t_channel_group_npvr WHERE CODE = '" + code + "'");
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
	
	
}
