package com.dvnchina.advertDelivery.ploy.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.model.Ploy;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.ploy.bean.PloyAreaChannel;
import com.dvnchina.advertDelivery.ploy.bean.PloyBackup;
import com.dvnchina.advertDelivery.ploy.bean.PloyChannel;
import com.dvnchina.advertDelivery.ploy.bean.PloyTimeCGroup;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatchBk;
import com.dvnchina.advertDelivery.ploy.dao.PloyDao;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;

public class PloyDaoImpl extends BaseDaoImpl implements PloyDao {

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
	public PageBeanDB queryPloyList(PloyBackup ploy,Contract contract,AdvertPosition adPosition,String customerIds, Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		
		 String sql;
	     sql="select distinct new com.dvnchina.advertDelivery.ploy.bean.PloyBackup(" +
        		"t.ployId," +
        		"t.ployName," +
        		"t.contractId," +
        		"t.positionId," +
        		"t.ruleId," +        		
        		"t.operationId," +
        		"t.createTime," +
        		"t.modifyTime," +
        		"t.state," +
        		"t.description) " +
        		"from PloyBackup t, AdvertPosition p,Contract c where 1=1 and t.delflag=0 and t.contractId=c.id and t.positionId= p.id  " ;
	     if (ploy!=null && ploy.getPloyName()!=null && !"".equals(ploy.getPloyName()))
	     {
	        		sql=sql+" and t.ployName like '%"+ploy.getPloyName()+"%'";
	     }
	     if (ploy!=null && ploy.getPloyId()!=null)
	     {
	        		sql=sql+" and t.ployId="+ploy.getPloyId();
	     }	  
	     if (ploy!=null && ploy.getContractId()!=0)
	     {
	        		sql=sql+" and t.contractId="+ploy.getContractId();
	     }	  
	     if (contract!=null && contract.getContractName()!=null && !contract.getContractName().equals(""))
	     {
	        		sql=sql+" and c.contractName like '%"+contract.getContractName()+"%'";
	     }	 
	     if (adPosition!=null && adPosition.getPositionName()!=null && !adPosition.getPositionName().equals(""))
	     {
	        		sql=sql+" and p.positionName like '%"+adPosition.getPositionName()+"%'";
	     }	
	     if (customerIds!=null && !"".equals(customerIds))
	     {
	    	 sql=sql+" and c.customerId in ("+customerIds+") ";
	     }
	     sql=sql+" order by t.ployId desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	public PageBeanDB queryPloyList(PloyBackup ploy,String customerIds,String positionPackageIds,AdvertPosition adPosition,Integer pageSize, Integer pageNumber)
	{
		 String sql;
	     List params = new ArrayList();
	     if (customerIds!=null && !customerIds.equals(""))
			{
	    	 sql="select distinct new com.dvnchina.advertDelivery.ploy.bean.PloyBackup(" +
     		"t.ployId," +
     		"t.channelGroupType," +
     		"t.ployName," +
     		"t.customerId," +
     		"t.positionId," +
     		"t.ruleId," +        		
     		"t.operationId," +
     		"t.createTime," +
     		"t.modifyTime," +
     		"t.state," +
     		"t.description,t.ployNumber,t.defaultstart) " +
     		"from PloyBackup t, AdvertPosition p where 1=1 and t.delflag=0 and t.positionId= p.id ";
	    	// sql=sql+"  and (? between r.startDate and r.endDate) " ;
	    	// params.add(new Date());//当前系统时间
	    	 
	    	 sql=sql+" and t.customerId in (" +customerIds+") ";
			 
			 // "c.marketingRuleId,"+
				// "p.isAsset)"+
			   //  sql=sql+ ")";
			     if (ploy!=null && ploy.getPloyName()!=null && !"".equals(ploy.getPloyName()))
			     {
			        		sql=sql+" and t.ployName like '%"+ploy.getPloyName()+"%'";
			     }
			  if (adPosition!=null && adPosition.getPositionName()!=null && !adPosition.getPositionName().equals(""))
			     {
			        		sql=sql+" and p.positionName like '%"+adPosition.getPositionName()+"%'";
			     }	
			     sql=sql+" order by t.ployId desc";    
			}
			else if (positionPackageIds!=null && !positionPackageIds.equals(""))
			{
//				 sql="select distinct new com.dvnchina.advertDelivery.ploy.bean.PloyBackup(" +
//		     		"t.ployId," +
//					"t.channelGroupType," +
//		     		"t.ployName," +
//		     		"t.customerId," +
//		     		"t.positionId," +
//		     		"t.ruleId," +        		
//		     		"t.operationId," +
//		     		"t.createTime," +
//		     		"t.modifyTime," +
//		     		"t.state," +
//		     		"t.description,t.ployNumber,t.defaultstart) " +
//		     		"from PloyBackup t,AdvertPosition p where t.delflag=0 and t.positionId= p.id and p.positionPackageId in (" +positionPackageIds+") "; 

				 final StringBuffer countSql = new StringBuffer();
				 StringBuffer listSql = new StringBuffer();
				 
				 String sqlForCount = "SELECT COUNT(DISTINCT p.PLOY_ID) FROM t_ploy_backup p, t_advertposition a " 
						 + "WHERE p.POSITION_ID = a.ID AND p.DELFLAG = 0 AND a.POSITION_PACKAGE_ID IN (" + positionPackageIds+ ")";
				 
				 String sqlForList = "SELECT  p.PLOY_ID, p.CHANNEL_GROUP_TYPE, p.PLOY_NAME, p.CUSTOMER_ID, p.POSITION_ID, "
						 + "p.RULE_ID, p.OPERATION_ID, p.CREATE_TIME, p.MODIFY_TIME, " 
						 + "CASE WHEN EXISTS(SELECT * FROM t_order o WHERE p.PLOY_ID = o.PLOY_ID AND o.STATE <> '7') THEN '3' ELSE p.STATE END 'state', "
						 + "p.DESCRIPTION, p.PLOY_NUMBER, p.DEFAULTSTART FROM t_ploy_backup p, t_advertposition a "
						 + "WHERE p.POSITION_ID = a.ID AND p.DELFLAG = 0 AND a.POSITION_PACKAGE_ID IN (" + positionPackageIds+ ") ";
				 
				 countSql.append(sqlForCount);
				 listSql.append(sqlForList);
			     
			     if (ploy!=null && ploy.getPloyName()!=null && !"".equals(ploy.getPloyName())){
			    	 String s= " and p.PLOY_NAME like '%" + ploy.getPloyName() + "%'";
			    	 countSql.append(s);
			    	 listSql.append(s);
			     }
				 if (adPosition!=null && adPosition.getPositionName()!=null && !adPosition.getPositionName().equals("")){
					 String s = " and a.POSITION_NAME like '%" + adPosition.getPositionName() + "%'";
					 countSql.append(s);
			    	 listSql.append(s);
			     }
				 listSql.append(" GROUP BY p.PLOY_ID");
				 listSql.append(" ORDER BY p.PLOY_ID DESC"); 
			     
			     
			     List<?> list = this.getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
					@Override
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						SQLQuery query = session.createSQLQuery(countSql.toString());
						return query.list();
					}
				});
			     
			     int count = 0;
			     if(list.size() > 0){
			    	 count = ((BigInteger)list.get(0)).intValue();
			     }
			     
			     return this.getPageListBySql(count, pageNumber, pageSize, listSql);
			     
			}
			else
			{
				//修改时查询所有广告位
				 sql="select distinct new com.dvnchina.advertDelivery.ploy.bean.PloyBackup(" +
		     		"t.ployId," +
		     		"t.channelGroupType," +
		     		"t.ployName," +
		     		"t.customerId," +
		     		"t.positionId," +
		     		"t.ruleId," +        		
		     		"t.operationId," +
		     		"t.createTime," +
		     		"t.modifyTime," +
		     		"t.state," +
		     		"t.description,t.ployNumber,t.defaultstart) " +
		     		"from PloyBackup t";
			     
			     sql=sql+" order by t.ployId desc "; 
			}
     
	     
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	
	
	private PageBeanDB getPageListBySql(int count, int pageNumber, int pageSize, final StringBuffer sb){
			  
		PageBeanDB pageBean = new PageBeanDB();
		
	    if(count == 0){
			pageBean.setCount(0);
			pageBean.setPageNo(0);
			pageBean.setTotalPage(0);
			pageBean.setPageSize(pageSize);
			pageBean.setDataList(new ArrayList<Object>());
			return pageBean;
		}
		
		pageBean.setPageSize(pageSize);
		pageBean.setCount(count);
		pageBean.setTotalPage(count%pageSize == 0 ? count/pageSize : count/pageSize+1);
		
		//查询时输入页码0
		if (pageNumber==0){
			pageNumber = 1;
		}
		//查询时输入页码大于总页数
		if (pageNumber>pageBean.getTotalPage())
		{
			pageNumber = pageBean.getTotalPage();
		}
		pageBean.setPageNo(pageNumber);
		
		sb.append(" LIMIT ").append((pageNumber - 1) * pageSize).append(", ").append(pageSize);
		List<?> list = this.getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session)throws HibernateException, SQLException {              
				SQLQuery query = session.createSQLQuery(sb.toString());
				return query.list();
			}
		});
		List<PloyBackup> dataList = new ArrayList<PloyBackup>();
		for(Object objArray: list){
			
			Object[] objs = (Object[])objArray;
			
			PloyBackup entity = new PloyBackup();
			
			entity.setPloyId(toInteger(objs[0]));
			entity.setChannelGroupType(toInteger(objs[1]));
			entity.setPloyName((String)objs[2]);
			entity.setCustomerId(toInteger(objs[3]));
			entity.setPositionId(toInteger(objs[4]));
			entity.setRuleId(toInteger(objs[5]));
			entity.setOperationId(toInteger(objs[6]));
			entity.setCreateTime((Date)objs[7]);
			entity.setModifyTime((Date)objs[8]);
			entity.setState((String)objs[9]);
			entity.setDescription((String)objs[10]);
			entity.setPloyNumber(toInteger(objs[11]));
			entity.setDefaultstart((String)objs[12]);
		
			dataList.add(entity);
		}
		
		pageBean.setDataList(dataList);
		  	
		return pageBean;
	}
	
	
    
	  public List queryExitsPloyList(PloyBackup ploy)
	  {
		  String sql;
		     sql="select distinct new com.dvnchina.advertDelivery.ploy.bean.PloyBackup(" +
	        		"t.ployId," +
	        		"t.ployName," +
	        		"t.contractId," +
	        		"t.positionId," +
	        		"t.ruleId," +
	        		"t.startTime," +
	        		"t.endTime," +
	        		"t.operationId," +
	        		"t.createTime," +
	        		"t.modifyTime," +
	        		"t.state," +
	        		"t.description) " +
	        		"from PloyBackup t where delflag=0 and 1=1" ;
		     if (ploy!=null && ploy.getPloyId()!=null)
		     {
		        	sql=sql+" and t.ployId!="+ploy.getPloyId();
		     }
		     if (ploy!=null && ploy.getContractId()!=null)
		     {
		        	sql=sql+" and t.contractId="+ploy.getContractId();
		     }
		     if (ploy!=null && ploy.getPositionId()!=null)
		     {
		        	sql=sql+" and t.positionId="+ploy.getPositionId();
		     }	    
		     sql=sql+" order by t.ployId desc";    
		   
		      //分页查询 
		      List params = null;
		      return this.getList(sql, params); 
	  }
	@Override
	public PageBeanDB queryCheckPloyList(PloyBackup ploy,Contract contract,AdvertPosition adPosition,String customerIds, Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		
		 String sql;
	     sql="select distinct new com.dvnchina.advertDelivery.ploy.bean.PloyBackup(" +
        		"t.ployId," +
        		"t.channelGroupType," +
        		"t.ployName," +       		
        		"t.customerId," +
        		"t.positionId," +
        		"t.ruleId," +      		
        		"t.operationId," +
        		"t.createTime," +
        		"t.modifyTime," +
        		"t.state," +
        		"t.description,t.ployNumber,t.defaultstart) " +
        		"from PloyBackup t ,AdvertPosition p where t.positionId= p.id and t.state<>'1'  and t.delflag=0 " ;
	     if (ploy!=null && ploy.getPloyName()!=null && !"".equals(ploy.getPloyName()))
	     {
	        		sql=sql+" and t.ployName like '%"+ploy.getPloyName()+"%'";
	     }
	     if (adPosition!=null && adPosition.getPositionName()!=null && !adPosition.getPositionName().equals(""))
	     {
	        		sql=sql+" and p.positionName like '%"+adPosition.getPositionName()+"%'";
	     }	
	     sql=sql+" order by t.ployId desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}

	@Override
	public PloyBackup getPloyByPloyID(int ployId) {
		//Session session = this.getSessionFactory().openSession();
		//Transaction ts = session.beginTransaction();
		String hql = "select new com.dvnchina.advertDelivery.ploy.bean.PloyBackup(" +
		"t.ployId," +
		"t.ployName," +
		"t.customerId," +
		"t.positionId," +
		"t.ruleId," +
		"t.operationId," +
		"t.createTime," +
		"t.modifyTime," +
		"t.state," +
		"t.description,t.ployNumber,t.defaultstart) " +
		"from PloyBackup t " +
		
		"where t.ployId="+ployId;
		//Query query = session.createQuery(hql);
		//query.setInteger("ployId", ployId);
		List list = this.getList(hql,null);
		PloyBackup ploy =null;
		if (list!=null && list.size()>0)
		{
			 ploy = (PloyBackup)(list.get(0));
		}
		//	ts.commit();
	///	session.close();
		return ploy;
	}
	@Override
	public List<PloyBackup> getPloyListByPloyID(int ployId) {
		//Session session = this.getSessionFactory().openSession();
		//Transaction ts = session.beginTransaction();
		String hql = "from PloyBackup t " +
		
		"where t.ployId="+ployId;
		List list = this.getList(hql,null);
		return list;
	}
	public PloyTimeCGroup getPloyTimeCGroupByPloyID(int ployId, String channelGroupType)
	{
		String hql = "select distinct " +
		"t.startTime," +
		"t.endTime " +
		"from PloyBackup t " +
		"where t.ployId="+ployId+" order by t.startTime ";
		//Query query = session.createQuery(hql);
		//query.setInteger("ployId", ployId);
		List timelist = this.getList(hql,null);
		List<PloyBackup>   timeList =null;
		List<PloyBackup>   channelgroupList =null;
		PloyTimeCGroup ployTimeCGroup = new PloyTimeCGroup();
		String starttimes="";
		String endtimes="";
		String cgroups="";
		String temp;
		if (timelist!=null && timelist.size()>0)
		{
			timeList = new ArrayList();
			for (int i=0;i<timelist.size();i++)
			{
				Object [] objtemp;
				objtemp = (Object [])timelist.get(i);
				temp=objtemp[0].toString();
				if (!temp.equals("0"))
				{
					PloyBackup tempPloy = new PloyBackup();
					tempPloy.setStartTime(temp);
					tempPloy.setEndTime(objtemp[1].toString());
					timeList.add(tempPloy);				
				}
			}
		}
		
		if("2".equals(channelGroupType)){     //回看频道组
			hql = "select distinct " +
					"t.groupId,t.priority,g.name " +
					"from PloyBackup t, TNpvrChannelGroup g " +
					"where t.groupId=g.id and t.ployId="+ployId+" order by t.groupId ";
		}else{
			hql = "select distinct " +
					"t.groupId,t.priority,g.name " +
					"from PloyBackup t, TChannelGroup g " +
					"where t.groupId=g.id and t.ployId="+ployId+" order by t.groupId ";
		}
		 
			List grouplist = this.getList(hql,null);
			if (grouplist!=null && grouplist.size()>0)
			{
				channelgroupList = new ArrayList();
				for (int i=0;i<grouplist.size();i++)
				{
					Object [] objtemp;
					objtemp = (Object [])grouplist.get(i);
					temp=objtemp[0].toString();
					if (!temp.equals("0"))
					{
						PloyBackup tempPloy = new PloyBackup();
						tempPloy.setGroupId(Integer.valueOf(temp));
						tempPloy.setPriority((Integer)objtemp[1]);
						tempPloy.setGroupName(objtemp[2].toString());
						channelgroupList.add(tempPloy);				
					}
						
				}				
			}
			
			 hql = "select distinct " +
				"t.areaId " +
				"from PloyBackup t " +
				"where t.ployId="+ployId;
				List arealist = this.getList(hql,null);
				String areas="";
				if (arealist!=null && arealist.size()>0)
				{
					for (int i=0;i<arealist.size();i++)
					{
						Long  objtemp;
						objtemp = (Long )arealist.get(i);
						temp=objtemp.toString();
						
						areas=areas+temp;
						if (i<arealist.size()-1)
						{
							areas=areas+",";
						}
					}				
				}
				ployTimeCGroup.setAreas(areas);
			ployTimeCGroup.setTimeList(timeList);
			ployTimeCGroup.setChannelgroupList(channelgroupList);
		return ployTimeCGroup;
	}
	public PageBeanDB queryCustomer(Integer pageSize, Integer pageNumber)
	{
		 String sql;
	     sql="from Customer t where 1=1" ;
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	@Override
	public PageBeanDB queryContract(Contract contract,String customerIds,Integer pageSize, Integer pageNumber)
	{
		// TODO Auto-generated method stub
		 String sql;
	     sql="from Contract t where 1=1" ;
	     if (customerIds!=null && !customerIds.equals(""))
	     {
	        	sql=sql+" and t.customerId in ("+customerIds+") ";
	     }
	     if (contract!=null && contract.getContractName()!=null && !"".equals(contract.getContractName()))
	     {
	        	sql=sql+" and t.contractName like '%"+contract.getContractName()+"%'";
	     }
	     if (contract!=null && contract.getContractCode()!=null && !"".equals(contract.getContractCode()))
	     {
	        	sql=sql+" and t.contractCode like '%"+contract.getContractCode()+"%'";
	     }
	     sql=sql+" order by t.id ";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
		
	}

	@Override
	public PageBeanDB queryAdPosition(String customerIds,Integer contractId,
			AdvertPosition adPosition,Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		 String sql;
	    // sql="from AdvertPosition t where 1=1" ;
		 sql ="select distinct new com.dvnchina.advertDelivery.ploy.bean.ContractAdvertPosition("+
		 "p.id,"+
		 "p.positionName,"+
		 "p.positionCode,"+
		 "p.isAllTime,"+
		 "p.isArea,"+
		 "p.isChannel,"+
		 "p.isFreq,"+
		 "p.isPlayback,"+
		 "p.isCharacteristic,"+
		 "p.isColumn,"+
		 "p.isLookbackProduct,"+
		 "c.contractId,"+
		 "c.marketingRuleId,"+
		 "p.isAsset,p.isFollowAsset)"+
		 " from AdvertPosition p,ContractAD c where p.positionPackageId=c.positionId ";
		// "c.marketingRuleId,"+
		// "p.isAsset)"+
	     if (adPosition!=null && adPosition.getId()!=null)
	     {
	        	sql=sql+" and p.id="+adPosition.getId();
	     }
	     if (adPosition!=null && adPosition.getPositionName()!=null)
	     {
	        	sql=sql+" and p.positionName like '%"+adPosition.getPositionName()+"%'";
	     }
	   /*  
	     sql=sql+ " and p.id in (select a.positionId from ContractAD a where 1=1 ";
	     if (contractId!=null && contractId!=0)
	     {
	    	sql=sql+ " and a.contractId = "+contractId;
	     } */
	     if (contractId!=null && contractId!=0)
	     {
	    	sql=sql+ " and c.contractId = "+contractId;
	     }
	     
	   //  sql=sql+ ")";
	    
	     sql=sql+" order by p.id ";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	 /**
 	 * 查询可选广告位
 	 * @param customerIds 广告商ID
 	 * @param positionPackageIds 广告位类型ID
 	 * @return the page bean
 	 */
	@Override
	public PageBeanDB queryAdPosition(String customerIds,String positionPackageIds ,Integer pageSize, Integer pageNumber)
	{
		 String sql="";
		 List params = new ArrayList();
		    // sql="from AdvertPosition t where 1=1" ;
		if (customerIds!=null && !customerIds.equals(""))
		{
		 sql ="select distinct new com.dvnchina.advertDelivery.ploy.bean.ContractAdvertPosition("+
			 "p.id,"+
			 "p.positionName,"+
			 "p.positionCode,"+
			 "p.isAllTime,"+
			 "p.isArea,"+
			 "p.isChannel,"+
			 "p.isFreq,"+
			 "p.isPlayback,"+
			 "p.isCharacteristic,"+
			 "p.isColumn,"+
			 "p.isLookbackProduct,"+
			// "r.contractId,"+
			// "r.marketingRuleId,"+
			 "p.isAsset,p.isFollowAsset)"+
			 " from AdvertPosition p,ContractAD r,Customer c,Contract t where p.positionPackageId=r.positionId and t.customerId=c.id and r.contractId=t.id ";
		     sql=sql+" and (? <= r.endDate) ";
		    sql=sql+" and c.id in (" +customerIds+") ";
		    Date dtemp =  new Date();
		    dtemp.setHours(0);
		    dtemp.setMinutes(0);
		    dtemp.setSeconds(0);
		    params.add(dtemp);//当前系统时间
		 // "c.marketingRuleId,"+
			// "p.isAsset)"+
		   //  sql=sql+ ")";
		     
		     sql=sql+" order by p.id ";    
		}
		else if (positionPackageIds!=null && !positionPackageIds.equals(""))
		{
			 sql ="select distinct new com.dvnchina.advertDelivery.ploy.bean.ContractAdvertPosition("+
			 "p.id,"+
			 "p.positionName,"+
			 "p.positionCode,"+
			 "p.isAllTime,"+
			 "p.isArea,"+
			 "p.isChannel,"+
			 "p.isFreq,"+
			 "p.isPlayback,"+
			 "p.isCharacteristic,"+
			 "p.isColumn,"+
			 "p.isLookbackProduct,"+
			// "r.contractId,"+
			// "r.marketingRuleId,"+
			 "p.isAsset,p.isFollowAsset)"+
			 " from AdvertPosition p where p.positionPackageId in (" +positionPackageIds+") "; 
		 // "c.marketingRuleId,"+
			// "p.isAsset)"+
		   //  sql=sql+ ")";
		     
		     sql=sql+" order by p.id "; 
		}
		else
		{
			//修改时查询所有广告位
			 sql ="select distinct new com.dvnchina.advertDelivery.ploy.bean.ContractAdvertPosition("+
			 "p.id,"+
			 "p.positionName,"+
			 "p.positionCode,"+
			 "p.isAllTime,"+
			 "p.isArea,"+
			 "p.isChannel,"+
			 "p.isFreq,"+
			 "p.isPlayback,"+
			 "p.isCharacteristic,"+
			 "p.isColumn,"+
			 "p.isLookbackProduct,"+
			// "r.contractId,"+
			// "r.marketingRuleId,"+
			 "p.isAsset,p.isFollowAsset)"+
			 " from AdvertPosition p";
		 // "c.marketingRuleId,"+
			// "p.isAsset)"+
		   //  sql=sql+ ")";
		     
		     sql=sql+" order by p.id "; 
		}
		      //分页查询 
		
		return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	
	
	@Override
	public PageBeanDB queryAdPositionRule(Integer contractId, Integer adId,
			MarketingRule rule,Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stubnew com.dvnchina.advertDelivery.model.MarketingRule(" +0,"+
		//"0)
        //"0,
		 String sql;
	     sql="select distinct new com.dvnchina.advertDelivery.model.MarketingRule(" +
			 "t.startTime,"+
			"t.endTime,"+
			"t.createTime,"+
			"t.state,"+
			"t.marketingRuleId,"+
			"t.marketingRuleName,"+
			"t.positionId)"+
			" from MarketingRule t where 1=1" ;
	     if (rule!=null && rule.getId()!=null)
	     {
	        	sql=sql+" and t.id="+rule.getId();
	     }
	     if (rule!=null && rule.getMarketingRuleName()!=null)
	     {
	        	sql=sql+" and t.marketingRuleName='"+rule.getMarketingRuleName()+"'";
	     }
	     if (adId!=null && adId!=0)
	     {
	    	 sql=sql+ " and t.marketingRuleId in (select ca.marketingRuleId from ContractAD ca where ca.positionId = "+adId+")";
	     }
	     if (contractId!=null && contractId!=0)
	     {
	    	 sql=sql+ " and t.marketingRuleId in (select c.marketingRuleId from ContractAD c where c.contractId = "+contractId+")";
	     }
	     //sql=sql+" order by t.id ";   
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}

	@Override
	public String checkPloy(PloyBackup ploy) {
		// TODO Auto-generated method stub
		
		 String sql;
	     sql="from PloyBackup t where 1=1 and t.delflag=0 " ;
	     if (ploy!=null && ploy.getPloyId()!=null)
	     {
	        	sql=sql+" and t.ployId!="+ploy.getPloyId();
	     }
	     if (ploy!=null && ploy.getPloyName()!=null)
	     {
	        	sql=sql+" and t.ployName='"+ploy.getPloyName()+"'";
	     }
	     List retList=this.getList(sql, null);
	     if (retList!=null && retList.size()>0)
	     {
	    	 return "1";//策略名称重复
	     }
	     /*
	     sql="from PloyBackup t where 1=1 and delflag=0 " ;
	     if (ploy!=null && ploy.getPloyId()!=null)
	     {
	        	sql=sql+" and t.ployId!="+ploy.getPloyId();
	     }
	     if (ploy!=null && ploy.getContractId()!=null)
	     {
	        	sql=sql+" and t.contractId="+ploy.getContractId();
	     }
	     if (ploy!=null && ploy.getPositionId()!=null)
	     {
	        	sql=sql+" and t.positionId="+ploy.getPositionId();
	     }
	     if (ploy!=null && ploy.getStartTime()!=null && ploy.getEndTime()!=null)
	     {
	        	sql=sql+" and (";
	        	sql=sql+" ( t.startTime >='"+ploy.getStartTime()+"' and t.endTime <='"+ploy.getEndTime()+"')";
	        	sql=sql+" or ( t.startTime <='"+ploy.getStartTime()+"' and t.endTime>='"+ploy.getStartTime()+"')";
	        	sql=sql+" or ( t.startTime <='"+ploy.getEndTime()+"' and t.endTime>='"+ploy.getEndTime()+"')";
	        	sql=sql+" ) ";
	     }
	   
	     
	     retList=this.getList(sql, null);
	     if (retList!=null && retList.size()>0)
	     {
	    	 return "2";//策略时间冲突重复
	     }*/
		return "0";
	}

	@Override
	public PageBeanDB queryAreaList(PloyBackup ploy, Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		 String sql;
	     sql="from ReleaseArea " ;                    
	    return  getPageList(sql,null,1,100);
	}
	
	
	@Override
	public PageBeanDB queryCityAreaList(PloyBackup ploy, Integer pageSize, Integer pageNumber) {
		String sql = "from ReleaseArea ra where ra.areaCode != '152000000000'" ;  
	    return  getPageList(sql,null,1,10000);
	}
	
	@Override
	public Object[] getPloyInfo(int contractId, int adsiteId, int ruleId) {
		Session session = this.getSessionFactory().openSession();
		Object[] object = null;
		Transaction ts = session.beginTransaction();
		String hql = "select c.contractName, c.positionName, c.marketingRuleName from ContractAD c where c.contractId = :contractId and c.positionId = :adsiteId and c.marketingRuleId = :ruleId";
		
		
		Query query = session.createQuery(hql);
		query.setInteger("contractId", contractId);
		query.setInteger("adsiteId", adsiteId);
		query.setInteger("ruleId", ruleId);
		List lst = query.list();
		if(lst != null && lst.size() > 0){
			object = (Object[]) lst.get(0);
		}
		ts.commit();
		session.close();
		return object;
	}
	
	/**
	 * 根据用户获取合同信息
	 */
	public List<Contract> getContractByAdMerchantId(Integer usetId) {
		Session session = this.getSessionFactory().openSession();
		String hql = "select new Contract(c.id, c.contractNumber, c.contractName, c.financialInformation) from Contract c, UserCustomer u where u.customerId = c.customerId and u.userId = :userId";
		Query query = session.createQuery(hql);
		query.setInteger("usetId", usetId);
		List<Contract> lstContract = query.list();
		return lstContract;
	}
	
	public List<ContractAD> getAdSiteByContract(int contractId) {
		Session session = this.getSessionFactory().openSession();
		String hql = "select new ContractAD(c.positionId, c.positionName) from ContractAD c where c.contractId = :contractId ";
		Query query = session.createQuery(hql);
		query.setInteger("contractId", contractId);
		List<ContractAD> lstContractAd = query.list();
		return lstContractAd;
	}
	
	public List<ContractAD> getMarketRuleByAdSiteId(int contractId, int adSiteId) {
		Session session = this.getSessionFactory().openSession();
		String hql = "select new ContractAD(c.marketingRuleId, c.marketingRuleName) from ContractAD c where c.contractId = :contractId and adSiteId=:adSiteId";
		Query query = session.createQuery(hql);
		query.setInteger("contractId", contractId);
		query.setInteger("adSiteId", adSiteId);
		List<ContractAD> lstContractAd = query.list();
		return lstContractAd;
	}
	
	public String[] getTimeSegmentsByMarketRule(int ruleId) {
		Session session = this.getSessionFactory().openSession();
		String hql = "select c.START_TIME, c.END_TIME from T_MARKETING_RULE c where c.RULE_ID = :ruleId";
		Query query = session.createSQLQuery(hql);
		query.setInteger("ruleId", ruleId);
		String[] array = (String[]) query.list().get(0);
		return array;
	}
	
	public List<ReleaseArea> getChoiceArea(int contractId, int adSiteId, int ruleId,
			String startTime, String endTime) {
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		//String hql = "select distinct new ReleaseArea(r.id, r.areaCode, r.parentCode, r.areaName) from ContractAD c, MarketingRule m, ReleaseArea r where c.marketingRuleId = m.marketingRuleId and m.locationId = r.id and c.contractId = :contractId and m.marketingRuleId = :ruleId and m.positionId = :positionId and (m.startTime > :endTime or m.endTime < :startTime)";
		String hql = "select distinct new ReleaseArea(r.id, r.areaCode, r.parentCode, r.areaName) from ContractAD c, MarketingRule m, ReleaseArea r where c.marketingRuleId = m.marketingRuleId and m.locationId = r.id and c.contractId = :contractId and m.marketingRuleId = :ruleId and m.positionId = :positionId and (1=1)";
		
		Query query = session.createQuery(hql);
		query.setInteger("contractId", contractId);
		query.setInteger("ruleId", ruleId);
		query.setInteger("positionId", adSiteId);
		//query.setString("endTime", endTime);
		//query.setString("startTime", startTime);
		List<ReleaseArea> list = query.list();
		ts.commit();
		session.close();
		return list;
	}
	
	public List<ChannelInfo> getChoiceChannels(int contractId, int postionId,
			int ruleId, String startTime, String endTime, int areaId) {
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		String hql = "select new ChannelInfo(ci.channelId, ci.channelName, ci.channelCode, ci.serviceId) from ChannelInfo ci, ContractAD c, MarketingRule m where c.marketingRuleId = m.marketingRuleId and m.channelId = ci.channelId and c.contractId = :contractId and m.marketingRuleId = :ruleId and m.positionId = :positionId and (m.startTime > :endTime or m.endTime < :startTime) and m.locationId = :locationId";
		Query query = session.createQuery(hql);
		query.setInteger("contractId", contractId);
		query.setInteger("ruleId", ruleId);
		query.setInteger("positionId", postionId);
		query.setString("endTime", endTime);
		query.setString("startTime", startTime);
		query.setInteger("locationId", areaId);
		List<ChannelInfo> list = query.list();
		ts.commit();
		session.close();
		return list;
	}
	
	public List<Ploy> getChannelListByArea(int contractId, int adSiteId,
			int ruleId, String startTime, String endTime, int areaId) {
		Session session = this.getSessionFactory().openSession();
		String hql = "from Ploy p where p.contractId = :contractId and p.positionId = :positionId and p.ruleId = :ruleId and (p.startTime > :endTime or p.endTime < :startTime) and p.areaId = :areaId";
		Query query = session.createQuery(hql);
		query.setInteger("contractId", contractId);
		query.setInteger("adSiteId", adSiteId);
		query.setInteger("ruleId", ruleId);
		query.setString("endTime", endTime);
		query.setString("startTime", startTime);
		query.setInteger("areaId", areaId);
		List<Ploy> list = query.list();
		session.close();
		return list;
	}

	public List<PloyAreaChannel> getAreaChannelsByPloyID(int ployId) {
		Session session = this.getSessionFactory().openSession();
		String hql = "select distinct new ReleaseArea(r.id, r.areaCode, r.parentCode, r.areaName) from Ploy p, ReleaseArea r where r.id = p.areaId and p.ployId = "+ployId;
		
		
		List<PloyAreaChannel> lstPloyAreaChannel = new ArrayList<PloyAreaChannel>();
		List list =this.getList(hql,null);;
		for (int i = 0, j=list.size(); i < j; i++) {
			PloyAreaChannel ployAreaChannel = new PloyAreaChannel();
			ReleaseArea obj = (ReleaseArea) list.get(i);
			List<PloyChannel> lstChannel = this.getChannelsByAreaId(ployId, obj.getId());
			ployAreaChannel.setAreaCode(obj.getAreaCode());
			ployAreaChannel.setAreaId(obj.getId());
			ployAreaChannel.setAreaName(obj.getAreaName());
			ployAreaChannel.setPatentCode(obj.getParentCode());
			ployAreaChannel.setChannels(lstChannel);
			lstPloyAreaChannel.add(ployAreaChannel);
		}
		return lstPloyAreaChannel;
	}
	
	private List<PloyChannel> getChannelsByAreaId(int ployId, int areaId){
		String hql = " select new ChannelInfo(c.channelId, c.channelName, c.channelCode, c.serviceId) from ChannelInfo c where 1=1 and c.id in (select p.channelId from Ploy p where 1=1 ";
		if (areaId!=0)
		{
			hql = hql + " and p.areaId="+areaId ;
		}
		if (ployId!=0)
		{
			hql = hql + " and p.ployId = "+ployId;
		}
		hql = hql +" )";
		
		List list =null;
		try
		{
		list= this.getList(hql,null);
		}
		catch(Exception e)
		{
			System.out.println("");
		}
		List<PloyChannel> lstPloy = new ArrayList<PloyChannel>();
		for (int i = 0, j=list.size(); i < j; i++) {
			ChannelInfo obj = (ChannelInfo) list.get(i);
			PloyChannel ployChannel = new PloyChannel();
			ployChannel.setChannel_id(obj.getChannelId());
			ployChannel.setChannel_code(obj.getChannelCode());
			ployChannel.setChannel_name(obj.getChannelName());
			ployChannel.setService_id(obj.getServiceId());
			lstPloy.add(ployChannel);
		}
		return lstPloy;
	}
	
	public boolean deletePloyByIds(String ids) {
		/*Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		String hqlUpdate = "DELETE T_PLOY WHERE PLOY_ID IN :ids";
		Query query = session.createSQLQuery(hqlUpdate);
		query.setString("ids", ids);
		query.executeUpdate();
		ts.commit();
		session.close();
		*/
		String sql;
	    //更新维护其表删除标识
		sql="update PloyBackup t set delflag=1 where 1=1  " ;
	       
	     sql=sql+" and t.ployId in (" +ids+")";    
	 	 Query query = this.getSession().createQuery(sql);
	 	query.executeUpdate();
	 	//删除运行期表数据
	 	sql="delete Ploy t where 1=1  " ;
	       
	    sql=sql+" and t.ployId in (" +ids+")";    
	 	query = this.getSession().createQuery(sql);
	 	query.executeUpdate();
		return true;
	}
	
	public boolean savePloy(List<PloyBackup> lstPloy) {
		this.getHibernateTemplate().saveOrUpdateAll(lstPloy);
		return true;
	}
	
	public boolean saveOrUpdate(List<PloyBackup> lstPloy, Integer ployId) {
		//Session session = this.getSession();
		///Transaction ts = session.beginTransaction();
		String hqlUpdate = "delete from PloyBackup where ployId = " + ployId;
		this.executeByHQL(hqlUpdate, null);
		this.savePloy(lstPloy);
		//ts.commit();
		//session.close();
		return true;
	}
	
	public int getMaxId(){
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		//String hqlUpdate = "SELECT MAX(T.ID) FROM T_PLOY T";///
		String hqlQuery = "select tableseq+1 from table_sequence where tableName='T_PLOY_SEQ'";///;
		
		Query query = session.createSQLQuery(hqlQuery);
		BigInteger obj = (BigInteger) query.list().get(0);
		
		String hqlUpdate = "update table_sequence set  tableseq=tableseq+1 where tableName='T_PLOY_SEQ'";///;
		
		query = session.createSQLQuery(hqlUpdate);
		query.executeUpdate();
		ts.commit();
		session.close();
		return obj.intValue();
	}
	public void CheckPloy(PloyBackup ploy)
	{
		String sql;
		if (ploy==null)
		{
			return ;
		}
	     sql="update PloyBackup t set t.auditDate=?,t.auditOption=?,t.state='" +ploy.getState()+"'" ;
	     sql=sql+ " where 1=1 and t.ployId="+ploy.getPloyId(); 
	     //sql=sql+" and t.ployId in (" +ids+")";    
	 	 Query query = this.getSession().createQuery(sql);
	 	 Object auditDate = new Date();
	 	 query.setParameter(0, auditDate);
	 	 query.setParameter(1, ploy.getAuditOption());
	 	//query.e
	 	query.executeUpdate();
	 	if (ploy.getState().equals("1"))
	 	{
	 		//复制维护期表数据至运行期表
	 		String deletesql="delete from t_ploy where ploy_id="+ploy.getPloyId();
	 		String insertsql ="insert into t_ploy(id,ploy_id,ploy_name,contract_id,position_id,rule_id,start_time,end_time,"+
	 		"area_id,channel_id,create_time,modify_time,state,description,userindustrys, userlevels, tvn_number, operator_id,audit_id,audit_option,audit_date,CUSTOMER_ID,CHANNEL_GROUP_ID,CHANNEL_GROUP_TYPE,PRIORITY,PLOY_NUMBER,defaultstart)"+
	 		" select "+
	 		"t.id,t.ploy_id,t.ploy_name,t.contract_id,t.position_id,t.rule_id,t.start_time,t.end_time,t.area_id,t.channel_id"+
	 		",t.create_time,t.modify_time,t.state,t.description,userindustrys, userlevels, tvn_number, t.operator_id,t.audit_id,t.audit_option,t.audit_date,t.CUSTOMER_ID,t.CHANNEL_GROUP_ID, t.CHANNEL_GROUP_TYPE,t.PRIORITY,t.PLOY_NUMBER,t.defaultstart"+
            " from t_ploy_backup t where t.ploy_id="+ploy.getPloyId();
	 		query = this.getSession().createSQLQuery(deletesql);
	 		query.executeUpdate();	 		
	 		query = this.getSession().createSQLQuery(insertsql);
	 		query.executeUpdate();
	 		
	 		deletesql="delete from T_PRECISE_MATCH where ploy_id="+ploy.getPloyId();
	 		insertsql ="insert into T_PRECISE_MATCH(id,MATCH_NAME,Precisetype,PRODUCT_ID,ASSET_NAME,ASSET_KEY,ASSET_SORT_ID"+
	 		",DTV_CHANNEL_ID,PLAYBACK_CHANNEL_ID,LOOKBACK_CATEGORY_ID,USER_AREA,USER_AREA2,USER_AREA3,USERINDUSTRYS,USERLEVELS,TVN_NUMBER,Priority,Ploy_Id,CHANNEL_GROUP_ID,TVN_EXPRESSION)"+
			" select "+
			"t.id,t.MATCH_NAME,t.Precisetype,t.PRODUCT_ID,t.ASSET_NAME,t.ASSET_KEY,t.ASSET_SORT_ID,t.DTV_CHANNEL_ID,t.PLAYBACK_CHANNEL_ID"+
			",t.LOOKBACK_CATEGORY_ID,t.USER_AREA,t.USER_AREA2,t.USER_AREA3,t.USERINDUSTRYS,t.USERLEVELS,t.TVN_NUMBER,t.Priority,t.Ploy_Id,t.CHANNEL_GROUP_ID,t.TVN_EXPRESSION"+
			" from T_PRECISE_MATCH_bk t where 1=1 and t.ploy_id="+ploy.getPloyId();
	 		query = this.getSession().createSQLQuery(deletesql);
	 		query.executeUpdate();
	 		query = this.getSession().createSQLQuery(insertsql);
	 		query.executeUpdate();
	 	}
	}
	
	/**
	 * @description: 首页代办获取待审批的策略的总数
	 * @return 待审批的策略的总数
	 */
	public int getWaitingAuditPloyCount(String ids){
		String sql="select distinct new com.dvnchina.advertDelivery.ploy.bean.PloyBackup(t.ployName ) " +
       		"from PloyBackup t, AdvertPosition p where t.state<>'1' and  t.positionId= p.id  and t.delflag = 0 and p.id in ("+ids+")";
		Long count = getDataTotal(sql, null);
		return count.intValue();
	}
	/**
	 * 校验策略是否可删除
	 * return 1不可删除 0 可删除
	 */
	public int checkDelPloyBackUpById(Integer id)
	{
		List param = null;//new HashMap<String, Object>();
		String hql = "from Order t where 1=1 and t.state!='7' ";// and t.state!='7'  执行完毕
		//过滤可操作的广告商查询
		if(id!= null && id!=0){
			hql +=" and t.ployId="+id;
		}
		hql +=" order by t.ployId";
		int totalCount=0;
		try
		{
			totalCount = this.getTotalCountHQL(hql, param);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		if (totalCount>0)
		{
			return 1;
		}
		else
		{	
			return 0;
		}
	}
	public List<TPreciseMatchBk> queryPreciseList(String ployId)
	{
		String sql;
	     sql=" from TPreciseMatchBk t where 1=1  " ;
	     if (ployId!=null )
	     {
	        		sql=sql+" and t.ployId="+ployId;
	     }
	 
	     sql=sql+" order by t.id desc";    
	     return this.getList(sql, null);
	    	}
	public boolean saveOrUpdate(List<TPreciseMatchBk> preciseMatchs,int ployId)
	{
		
		String sql="delete TPreciseMatchBk t where 1=1  " ;
	       
		sql=sql+" and t.ployId = " +ployId;      
		Query query = this.getSession().createQuery(sql);
	 	query.executeUpdate();
	 	this.getHibernateTemplate().saveOrUpdateAll(preciseMatchs);
		return true;
	   
	}
	public boolean deletePrecise(String ployId)
	{
		String sql;
		Query query ;
		sql="delete TPreciseMatchBk t where 1=1  " ;	       
		 sql=sql+" and t.ployId = " +ployId;      
	 	query = this.getSession().createQuery(sql);
	 	query.executeUpdate();
		//return ;
		return true;
	}
}
