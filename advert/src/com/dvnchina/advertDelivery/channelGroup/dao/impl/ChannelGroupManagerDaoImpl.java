package com.dvnchina.advertDelivery.channelGroup.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.channelGroup.bean.ChannelGroupRef;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.channelGroup.dao.ChannelGroupManagerDao;
import com.dvnchina.advertDelivery.contract.bean.AdvertPositionPackage;
import com.dvnchina.advertDelivery.contract.bean.PositionAD;
import com.dvnchina.advertDelivery.contract.dao.ContractManagerDao;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.UserLogin;

import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.model.Ploy;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.ploy.bean.PloyAreaChannel;
import com.dvnchina.advertDelivery.ploy.bean.PloyChannel;
import com.dvnchina.advertDelivery.ploy.dao.PloyDao;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;
import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;

public class ChannelGroupManagerDaoImpl extends HibernateSQLTemplete implements ChannelGroupManagerDao {

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
	public PageBeanDB queryChanelGroupList(TChannelGroup channelGroup,Integer pageSize,Integer pageNumber) {
		
		 HttpSession session = ServletActionContext.getRequest().getSession();
		 UserLogin user = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		 String accessUserIds = StringUtil.objListToString(user.getAccessUserIds(), ",", "-1");
		 String sql;
	     sql="from TChannelGroup t where 1=1  " ;
	     sql+=" and t.operatorId in (" + accessUserIds + ")";
	     if (channelGroup!=null && channelGroup.getName()!=null && !"".equals(channelGroup.getName()))
	     {
	        		sql=sql+" and t.name='"+channelGroup.getName()+"'";
	     }
	     
	    
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	
	
	/**
	 * 查询频道列表
	 * @param channel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB queryChannelGroupRefList(ChannelInfo channel,Integer pageSize,Integer pageNumber) {
		
		 String sql;
		 sql="select distinct new com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo(t.channelId,t.channelName,t.channelCode,t.channelType,t.serviceId,t.tsId,t.networkId,t.isPlayBack) from ChannelInfo t,ChannelGroupRef a where t.channelId = a.channelId" ;
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
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	

	/**
	 * 查询频道选择列表
	 * @param channel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB selectChannelList(ChannelInfo channel,Integer pageSize,Integer pageNumber) {
		
		 String sql;
		 sql="select distinct new com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo(t.channelId,t.channelName,t.channelCode,t.channelType,t.serviceId,t.tsId,t.networkId,t.isPlayBack) from ChannelInfo t where 1=1 " ;
		 //sql=sql+"where t.channelId not in(select distinct r.channelId from ChannelGroupRef r)";
		 if(channel!=null && channel.getChannelGroupId()!=null){
		     sql=sql+"and  t.channelId not in(select distinct r.channelId from ChannelGroupRef r where r.channelGroupId="+channel.getChannelGroupId()+")";
		 }

	     if (channel!=null && channel.getChannelName()!=null && !"".equals(channel.getChannelName()))
	     {
	         sql=sql+" and t.channelName like '%"+channel.getChannelName().trim()+"%'";
	     }
	     if (channel!=null && channel.getChannelType()!=null && !"".equals(channel.getChannelType()))
	     {
	         sql=sql+" and t.channelType = '"+channel.getChannelType().trim()+"'";
	     }
	    
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
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
	public boolean saveChannelGroup(TChannelGroup channelGroup) {
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
	public boolean updateChannelGroup(TChannelGroup channelGroup) {
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
	public boolean saveChannelGroupRefList(List<ChannelGroupRef> channelGroupRefList) {
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
        String hqlUpdate = "DELETE from T_CHANNEL_GROUP_REF WHERE CHANNEL_ID IN "+ids;
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
        String hqlUpdate1 = "DELETE from T_CHANNEL_GROUP_REF WHERE GROUP_ID IN "+ids;
        String hqlUpdate2 = "DELETE from T_CHANNEL_GROUP WHERE ID IN "+ids;
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
	public TChannelGroup getChannelGroupByID(int id) {

        String hql =" from TChannelGroup t " +     
        "where t.id="+id;

        List list = this.getList(hql,null);
        TChannelGroup channelGroup =null;
        if (list!=null && list.size()>0)
        {
        	channelGroup = (TChannelGroup)(list.get(0));
        }

        return channelGroup;
    }


	@Override
	public Integer getEntityCountByCode(String code) {
		final StringBuffer sb = new StringBuffer();
		sb.append(" SELECT COUNT(1) FROM t_channel_group WHERE CODE = '" + code + "'");
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
