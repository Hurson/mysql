package com.dvnchina.advertDelivery.ploy.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.classic.QueryTranslatorImpl;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.AssetInfo;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.model.Ploy;
import com.dvnchina.advertDelivery.model.ProductInfo;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.TNpvrChannelGroup;
import com.dvnchina.advertDelivery.ploy.bean.LocationCode;
import com.dvnchina.advertDelivery.ploy.bean.TAssetinfo;
import com.dvnchina.advertDelivery.ploy.bean.TCategoryinfo;
import com.dvnchina.advertDelivery.ploy.bean.TChannelinfoNpvr;
import com.dvnchina.advertDelivery.ploy.bean.TLoopbackCategory;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatch;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatchBk;
import com.dvnchina.advertDelivery.ploy.bean.TProductinfo;
import com.dvnchina.advertDelivery.ploy.dao.PreciseDao;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;
import com.dvnchina.advertDelivery.sysconfig.bean.UserRank;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;
import com.dvnchina.advertDelivery.utils.StringUtil;

public class PreciseDaoImpl extends BaseDaoImpl implements PreciseDao {
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
	public PageBeanDB queryPreciseList(TPreciseMatchBk preciseMatch,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		String sql;
	     sql=" from TPreciseMatchBk t where 1=1 and t.delflag=0  " ;
	     if (preciseMatch!=null && preciseMatch.getMatchName()!=null && !"".equals(preciseMatch.getMatchName()))
	     {
	        		sql=sql+" and t.matchName like '%"+preciseMatch.getMatchName()+"%'";
	     }
	     if (preciseMatch!=null && preciseMatch.getPloyId()!=null)
	     {
	        		sql=sql+" and t.ployId="+preciseMatch.getPloyId();
	     }
	 
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}

	@Override
	public TPreciseMatchBk getPreciseMatchByID(Long id) {
		// TODO Auto-generated method stub
		//Object obj= this.getSessionFactory().openSession().get(TPreciseMatch.class,id);
		String sql;
	     sql=" from TPreciseMatchBk t where 1=1 " ;
	    
	    // if (id!=null && id>0)
	     {
	        		sql=sql+" and t.id="+id;
	     }    
	   
	      //分页查询 
	      List params = null;
	     List tempList =this.getListForPage(sql, params, 1, 1);
	     if (tempList!=null && tempList.size()>0)
	     {
	    	 return (TPreciseMatchBk)tempList.get(0);
	     }	
		return null;
	}

	//查询所有广告位
	@Override
	public List queryAdPosition() {
		// TODO Auto-generated method stub
		 String sql;
		    // sql="from AdvertPosition t where 1=1" ;
			 sql ="select distinct new com.dvnchina.advertDelivery.ploy.bean.ContractAdvertPosition("+
			 "p.id,"+
			 "p.positionName,"+
			 "p.isAllTime,"+
			 "p.isArea,"+
			 "p.isChannel,"+
			 "p.isFreq,"+
			 "p.isPlayback,"+
			 "p.isCharacteristic,"+
			 "p.isColumn,"+
			 "p.isLookbackProduct,"+
			 "p.isAsset,"+
			 "p.isAsset,"+
			 "p.isAsset)"+
			 " from AdvertPosition p where 1=1";
			// "c.marketingRuleId,"+
			// "p.isAsset)"+
		 
		     
		   //  sql=sql+ ")";
		    
		     sql=sql+" order by p.id ";    
		   
		      //分页查询 
		      List params = null;		     
		      return this.getListForPage(sql, params, 1, 100); 
	}

	@Override
	public PageBeanDB queryAreaList(String ployId, Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	 /**
 	 * 获取广告位详情
 	 * 广告位ID.
 	 *
 	 * @param id the id
 	 * @return the precise match by ploy id
 	 */
	public AdvertPosition getAdvertPositionByID(Long id)
	{
		String sql;
	     sql=" from AdvertPosition t where 1=1 " ;
	    
	    // if (id!=null && id>0)
	     {
	        		sql=sql+" and t.id="+id;
	     }    
	   
	      //分页查询 
	      List params = null;
	     List tempList =this.getListForPage(sql, params, 1, 1);
	     if (tempList!=null && tempList.size()>0)
	     {
	    	 return (AdvertPosition)tempList.get(0);
	     }	
		return null;
	}
	@Override
	public PageBeanDB queryChannelList(String ployId, ChannelInfo channel,String ids,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		//for (int i=0;i<50;i++)
		{
		String sql;
	    sql="select distinct new com.dvnchina.advertDelivery.model.ChannelInfo(t.channelName, t.channelCode, t.serviceId)  " +
		//  sql="select distinct new com.dvnchina.advertDelivery.model.ChannelInfo(t.channelName, t.channelCode, t.serviceId)  " +
	   		  " from ChannelInfo t where 1=1  " ;
	     if (channel!=null && channel.getChannelName()!=null && !"".equals(channel.getChannelName()))
	     {
	        		sql=sql+" and t.channelName like '%"+channel.getChannelName()+"%'";
	     }
	     if (channel!=null && channel.getChannelCode()!=null && !"".equals(channel.getChannelCode()))
	     {
	        		sql=sql+" and t.channelCode like '%"+channel.getChannelCode()+"%'";
	     }
	     if (channel!=null && channel.getServiceId()!=null && !"".equals(channel.getServiceId()))
	     {
	        		sql=sql+" and t.serviceId ="+channel.getServiceId();
	     }
	     if (channel!=null && channel.getChannelType()!=null && !"".equals(channel.getChannelType()))
	     {
	        		sql=sql+" and t.channelType ='"+channel.getChannelType()+"'";
	     }
	     if (ids!=null && !"".equals(ids))
	     {
	        		sql=sql+" and t.serviceId in("+ids+")";
	     }
	     
	  //   sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	     return  this.getPageList(sql, params, pageNumber, pageSize); 
		}
		// null;
	}

	@Override
	public PageBeanDB queryNpvrChannelList(String ployId, TChannelinfoNpvr channel,String ids,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		String sql;
	     sql="select distinct new com.dvnchina.advertDelivery.ploy.bean.TChannelinfoNpvr(t.channelName, t.channelCode, t.serviceId)  " +
     		"from TChannelinfoNpvr t where 1=1  " ;
	     if (channel!=null && channel.getChannelName()!=null && !"".equals(channel.getChannelName()))
	     {
	        		sql=sql+" and t.channelName like '%"+channel.getChannelName()+"%'";
	     }
	     if (channel!=null && channel.getChannelCode()!=null && !"".equals(channel.getChannelCode()))
	     {
	        		sql=sql+" and t.channelCode like '%"+channel.getChannelCode()+"%'";
	     }
	     if (channel!=null && channel.getServiceId()!=null && !"".equals(channel.getServiceId()))
	     {
	        		sql=sql+" and t.serviceId ='"+channel.getServiceId()+"'";
	     }
	     if (ids!=null && !"".equals(ids))
	     {
	        		sql=sql+" and t.serviceId in("+ids+")";
	     }
	    // sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}

	@Override
	public PageBeanDB queryColumnList(String ployId, TLoopbackCategory category,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		String sql;
	     sql="from TLoopbackCategory t where 1=1  " ;
	     if (category!=null && category.getCategoryName()!=null && !"".equals(category.getCategoryName()))
	     {
	        		sql=sql+" and t.categoryName like '%"+category.getCategoryName()+"%'";
	     }	 
	     if (category!=null && category.getCategoryType()!=null && !"".equals(category.getCategoryType()))
	     {
	        		sql=sql+" and t.categoryType ='"+category.getCategoryType()+"'";
	     }	
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}

	@Override
	public PageBeanDB queryProductList(String ployId, TProductinfo product,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		 String sql;
	     sql="from TProductinfo t where 1=1  " ;
	     if (product!=null && product.getProductName()!=null && !"".equals(product.getProductName()))
	     {
	        		sql=sql+" and t.productName like '%"+product.getProductName()+"%'";
	     }	  
	     //只查询回看产品
	     if (product!=null && product.getType()!=null && !"".equals(product.getType()))
	     {
	        		sql=sql+" and t.type = '"+product.getType()+"'";
	     }	 
	    
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}

	@Override
	public PageBeanDB queryAssetCategoryList(String ployId, TCategoryinfo category, Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		 String sql;
	     sql="from TCategoryinfo t where 1=1  " ;
	     if (category!=null && category.getCategoryName()!=null && !"".equals(category.getCategoryName()))
	     {
	        		sql=sql+" and t.categoryName like '%"+category.getCategoryName()+"%'";
	     }	
	     if (category!=null && category.getType()!=null && !"".equals(category.getType()))
	     {
	        		sql=sql+" and t.type = '"+category.getType()+"'";
	     }	
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}

	@Override
	public PageBeanDB queryAssetList(String assetName, TAssetinfo asset,String ids,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		 String sql;
	     sql="from TAssetinfo t where 1=1  " ;
	     if (asset!=null && asset.getAssetName()!=null && !"".equals(asset.getAssetName()))
	     {
	        		sql=sql+" and t.assetName like '%"+asset.getAssetName()+"%'";
	     }	  
	     //只查询回看产品
	     if (ids!=null && !"".equals(ids))
	     {
	        		sql=sql+" and t.id in( "+ids+")";
	     }	     
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	/**
 	 * 查询可选区域
 	 * 
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	public PageBeanDB queryreleaseAreaList(ReleaseArea releassArea,String ids,Integer pageSize, Integer pageNumber)
	{
		 String sql;
	     sql="from ReleaseArea t where 1=1  " ;
	     if (releassArea!=null && releassArea.getAreaName()!=null && !"".equals(releassArea.getAreaName()))
	     {
	        		sql=sql+" and t.areaName like '%"+releassArea.getAreaName()+"%'";
	     }	  
	     if (ids!=null && !"".equals(ids))
	     {
	        		sql=sql+" and t.id in( "+ids+")";
	     }	     
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	
	/**
 	 * 查询可选行业
 	 * 
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	public PageBeanDB queryuserIndustryList(UserIndustryCategory userIndustry,String ids,Integer pageSize, Integer pageNumber)
	{
		 String sql;
	     sql="from UserIndustryCategory t where 1=1  " ;
	     if (userIndustry!=null && userIndustry.getUserIndustryCategoryValue()!=null && !"".equals(userIndustry.getUserIndustryCategoryValue()))
	     {
	        		sql=sql+" and t.userIndustryCategoryValue like '%"+userIndustry.getUserIndustryCategoryValue()+"%'";
	     }	  
	     //只查询回看产品
	     if (ids!=null && !"".equals(ids))
	     {
	        		sql=sql+" and t.id in( "+ids+")";
	     }	     
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	
	/**
 	 * 查询可选用户类型
 	 * 
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	public PageBeanDB queryuserRankList(UserRank userRank,String ids,Integer pageSize, Integer pageNumber)
	{
		 String sql;
	     sql="from UserRank t where 1=1  " ;
	     if (userRank!=null && userRank.getUserRankName()!=null && !"".equals(userRank.getUserRankName()))
	     {
	        		sql=sql+" and t.userRankName like '%"+userRank.getUserRankName()+"%'";
	     }	  
	     //只查询回看产品
	     if (ids!=null && !"".equals(ids))
	     {
	        		sql=sql+" and t.id in( "+ids+")";
	     }	     
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	/**
 	 * 查询可选用户类型
 	 * 
 	 * 
 	 * @param channelGroup 
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	public PageBeanDB queryChannelGroupList(TChannelGroup channelGroup,String ids,Integer pageSize, Integer pageNumber)
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		UserLogin user = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		String accessUserIds = StringUtil.objListToString(user.getAccessUserIds(), ",", "-1");
		 String sql;
	     sql="from TChannelGroup t where 1=1  " ;
	     sql+=" and t.operatorId in (" + accessUserIds + ")";
	     if (channelGroup!=null && channelGroup.getName()!=null && !"".equals(channelGroup.getName()))
	     {
	        		sql=sql+" and t.name like '%"+channelGroup.getName()+"%'";
	     }	  
	     //只查询回看产品
	     if (ids!=null && !"".equals(ids))
	     {
	        		sql=sql+" and t.id in( "+ids+")";
	     }	     
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	
	
	@Override
	public PageBeanDB queryNpvrChannelGroupList(TNpvrChannelGroup channelGroup, String ids, Integer pageSize, Integer pageNumber) {
		 String  sql="from TNpvrChannelGroup t where 1=1  " ;
	     if (channelGroup!=null && channelGroup.getName()!=null && !"".equals(channelGroup.getName())){
	        		sql=sql+" and t.name like '%"+channelGroup.getName()+"%'";
	     }	      
	     sql=sql+" order by t.id desc";    
	     return this.getPageList(sql, null, pageNumber, pageSize); 
	}
	public PageBeanDB queryLocationCodeList(LocationCode location,Integer pageSize, Integer pageNumber)
	{
		 String sql;
	     sql="from LocationCode t where 1=1 and t.locationtype in ('02','03','04','05') " ;
	     if (location!=null && location.getLocationname()!=null && !"".equals(location.getLocationname()))
	     {
	        		sql=sql+" and t.locationname like '%"+location.getLocationname()+"%'";
	     }	
	    // sql=sql+" order by t.locationcode desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	
	
	
	@Override
	public boolean saveOrUpdate(TPreciseMatchBk preciseMatch) {
		// TODO Auto-generated method stub
		preciseMatch.setDelflag(0);
		this.getHibernateTemplate().saveOrUpdate(preciseMatch);
		String sql;
	    sql="update PloyBackup t set t.delflag=0 , t.state='0' where 1=1  " ;
	       
	    sql=sql+" and t.ployId = " +preciseMatch.getPloyId();    
	 	this.executeByHQL(sql, null);
	    //Query query = this.getSession().createQuery(sql);
	 	//query.executeUpdate();
		//this.getSessionFactory().openSession().saveOrUpdate(preciseMatch);
		return true;
	}
	public boolean deletePrecise(String dataids)
	{
		String sql;
		sql="update TPreciseMatchBk t set t.delflag=1 where 1=1  " ;
	       
	    sql=sql+" and t.id in (" +dataids+")";    
	 	Query query = this.getSession().createQuery(sql);
	 	query.executeUpdate();
		
		
		sql="delete TPreciseMatch t where 1=1  " ;
	       
	    sql=sql+" and t.id in (" +dataids+")";    
	 	query = this.getSession().createQuery(sql);
	 	query.executeUpdate();
		//return ;
		return true;
	}
	public boolean checkName(String name,Long id)
	{
		String sql;
	     sql="from TPreciseMatchBk t where 1=1 and t.delflag=0 " ;
	     if (id!=null)
	     {
	    	 sql=sql+" and t.id !="+id;    
	     }
	     if (name!=null)
	     {
	    	 sql=sql+" and t.matchName ='"+name+"'";    
	     }
		List temp =this.getList(sql, null);
		if (temp!=null && temp.size()>0)
		{
			return true;
		}
		return false;
	}
}
