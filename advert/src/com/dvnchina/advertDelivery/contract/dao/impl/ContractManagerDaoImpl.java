package com.dvnchina.advertDelivery.contract.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.classic.QueryTranslatorImpl;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.dvnchina.advertDelivery.accounts.bean.ContractAccounts;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.contract.bean.AdvertPositionPackage;
import com.dvnchina.advertDelivery.contract.bean.ContractBackUpArea;
import com.dvnchina.advertDelivery.contract.bean.PositionAD;
import com.dvnchina.advertDelivery.contract.dao.ContractManagerDao;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.Location;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;

public class ContractManagerDaoImpl extends HibernateSQLTemplete implements ContractManagerDao {

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
		System.out.println(list.get(0));
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
		pageBean.setTotalPage((rowcount-1)/pageSize+1);
		
		if(rowcount == 0) {
			pageNumber = 1;
			pageBean.setTotalPage(1);
		}
		
		pageBean.setPageNo(pageNumber);
		List list = this.getListForPage(hql, params, pageNumber, pageSize);
		pageBean.setDataList(list);
		
		return pageBean;
	}
	
	/**
	 * 查询合同列表
	 * @param contract
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB queryContractList(ContractQueryBean contract,Integer pageSize,Integer pageNumber) {
		
		 String sql;
		 sql="select distinct new com.dvnchina.advertDelivery.model.ContractBackup(" +
 		"t.id," +
 		"t.contractName," +
 		"t.contractNumber," +
 		"t.contractCode," +
 		"t.customerId," +
 		"c.advertisersName," +
 		"t.submitUnits," +
 		"t.financialInformation," +
 		"t.approvalCode," +
 		"t.effectiveStartDate," +
 		"t.effectiveEndDate," +
 		"t.status,t.examinationOpinions) " +
        " from ContractBackup t,Customer c where t.customerId = c.id" ;
	     if (contract!=null && contract.getContractName()!=null && !"".equals(contract.getContractName()))
	     {
	        		sql=sql+" and t.contractName='"+contract.getContractName()+"'";
	     }
	     if (contract!=null && contract.getCustomerName()!=null && !"".equals(contract.getCustomerName()))
         {
                    sql=sql+" and c.advertisersName='"+contract.getCustomerName()+"'";
         }
	     if (contract!=null && contract.getEffectiveStartDate()!=null && contract.getEffectiveEndDate()!=null)
         {
	         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             String effectiveStartDate = sdf.format(contract.getEffectiveStartDate());
             String effectiveEndDate = sdf.format(contract.getEffectiveEndDate());
                    sql=sql+" and t.effectiveStartDate >= str_to_date('"+effectiveStartDate+"','%Y-%m-%d')and  t.effectiveEndDate <=str_to_date('"+effectiveEndDate+"','%Y-%m-%d')";
         }
	     if (contract!=null && contract.getCustomerids()!=null && !"".equals(contract.getCustomerids()))
         {
                    sql=sql+" and c.id in ("+contract.getCustomerids()+")";
         }
	     if (contract!=null && contract.getCustomerId()!=null && !"".equals(contract.getCustomerId()))
         {
                    sql=sql+" and c.id = "+contract.getCustomerId();
         }
	     if (contract!=null && contract.getStatus()!=null )
         {
                    sql=sql+" and t.status="+contract.getStatus();
         }
	    
	     sql=sql+" order by t.id desc";    
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
	}
	
	/**
	 * 
	 * @description: 查询广告位列表
	 * @param advertPositionPackageQuery
	 * @param pageSize
	 * @param pageNumber
	 * @return 
	 * PageBeanDB
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-4-28 上午11:06:56
	 */
	public PageBeanDB queryAdPositionPackageList(AdvertPositionPackage advertPositionPackageQuery,Integer pageSize,Integer pageNumber) {
        
        String sql;
        sql= " from AdvertPositionPackage t where 1=1" ;
        if (advertPositionPackageQuery!=null && advertPositionPackageQuery.getName()!=null && !"".equals(advertPositionPackageQuery.getName()))
        {
                   sql=sql+" and t.name='"+advertPositionPackageQuery.getName()+"'";
        }
       
        sql=sql+" order by t.id desc";    
      
         //分页查询 
         List params = null;
         //pageSize=50;
         return this.getPageList(sql, params, pageNumber, pageSize); 
   }
   
	public PageBeanDB queryAreaList(Integer pageNumber, Integer pageSize){
		
        String sql= "from Location order by id desc";
        
        return this.getPageList(sql, null, pageNumber, pageSize); 
	}
	
	/**
	 * 
	 * @description: 查询客户列表
	 * @param customerQuery
	 * @param customerids
	 * @param pageSize
	 * @param pageNumber
	 * @return 
	 * PageBeanDB
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-8 上午10:51:08
	 */
    public PageBeanDB queryCustomerRealList(Customer customerQuery,String customerids,Integer pageSize,Integer pageNumber) {
        
        String sql;
        sql= " from Customer t where t.id != 0" ;
        if (customerids!=null && !"".equals(customerids))
        {
                   sql=sql+" and t.id in ("+customerids+")";
        }
        if (customerQuery!=null && customerQuery.getAdvertisersName()!=null && !"".equals(customerQuery.getAdvertisersName()))
        {
                   sql=sql+" and t.advertisersName='"+customerQuery.getAdvertisersName()+"'";
        }
       
        sql=sql+" order by t.id desc";    
      
         //分页查询 
         List params = null;
         return this.getPageList(sql, params, pageNumber, pageSize); 
   }
	
	/**
	 * 
	 * @description: 根据ID获取广告位包 
	 * @param id
	 * @return 
	 * AdvertPositionPackage
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 下午02:52:19
	 */
	public AdvertPositionPackage getAdPositionPackageByID(int id) {

        String hql =" from AdvertPositionPackage t " +     
        "where  t.id="+id;

        List list = this.getList(hql,null);
        AdvertPositionPackage advertPositionPackage =null;
        if (list!=null && list.size()>0)
        {
            advertPositionPackage = (AdvertPositionPackage)(list.get(0));
        }

        return advertPositionPackage;
    }
	
	/**
	 * 
	 * @description: 根据广告位ID获取营销规则
	 * @param id
	 * @return 
	 * MarketingRule
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-8 下午07:17:41
	 */
	public MarketingRule getMarketingRuleByPositionId(int id) {

        String hql =" from MarketingRule t " +     
        "where  t.positionId="+id;

        List list = this.getList(hql,null);
        MarketingRule marketingRule =null;
        if (list!=null && list.size()>0)
        {
            marketingRule = (MarketingRule)(list.get(0));
        }

        return marketingRule;
    }


	/**
	 * 查询所有广告商列表
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB queryCustomerList(Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		 String sql;
	     sql="from Customer t order by t.id" ; 
	   
	      //分页查询 
	      List params = null;
	      return this.getPageList(sql, params, pageNumber, pageSize); 
		
	}

	public void addContractAreaBinging(List<String> areaCodeList, Integer contractId){
		for(String areaCode : areaCodeList){
			ContractBackUpArea ca =new ContractBackUpArea();
			ca.setContractId(contractId);
			ca.setAreaCode(areaCode);
			this.getHibernateTemplate().save(ca);
		}
	}
	
	public void deleteContractAreaBinging(final Integer contractId){
		final String sql = "delete from ContractBackUpArea ca where ca.contractId= ?";
		this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				Query query = session.createQuery(sql);
				query.setParameter(0, contractId);
			   return  query.executeUpdate();
				}
		});
	}
	/**
	 * 
	 * @description: 保存合同临时表
	 * @param contractBackup
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 上午09:44:04
	 */
	public boolean saveConstractBackup(ContractBackup contractBackup) {
        this.getHibernateTemplate().saveOrUpdate(contractBackup);
        return true;
    }
	
	/**
     * 
     * @description: 保存合同正式表
     * @param contractBackup
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午09:44:04
     */
    public boolean saveConstract(Contract contract) {
        this.getHibernateTemplate().saveOrUpdate(contract);
        return true;
    }
	
	/**
	 * 
	 * @description: 修改合同临时表
	 * @param contractBackup
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 下午06:04:50
	 */
	public boolean updateConstractBackup(ContractBackup contractBackup) {
        this.getHibernateTemplate().saveOrUpdate(contractBackup);
        return true;
    }
	
	/**
	 * 
	 * @description: 批量保存合同广告位表
	 * @param contractADList
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 上午10:17:47
	 */
	public boolean saveConstractADBackupList(List<ContractADBackup> contractADList) {
        this.getHibernateTemplate().saveOrUpdateAll(contractADList);
        return true;
    }
	
	/**
     * 
     * @description: 批量保存合同广告位正式表
     * @param contractADList
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午10:17:47
     */
    public boolean saveConstractADList(List<ContractAD> contractADList) {
        this.getHibernateTemplate().saveOrUpdateAll(contractADList);
        return true;
    }
	
	/**
	 * (non-Javadoc)
	 * <p>Title: deleteContractAD</p> 
	 * <p>Description:根据合同ID删除合同广告位表 </p> 
	 * @param contractId
	 * @return 
	 * @see com.dvnchina.advertDelivery.contract.dao.ContractManagerDao#deleteContractAD(java.lang.Integer)
	 */
	public boolean deleteContractAD(Integer contractId) {
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        String hqlUpdate = "DELETE from T_CONTRACT_AD_BACKUP WHERE CONTRACT_ID = :id";
        Query query = session.createSQLQuery(hqlUpdate);
        query.setInteger("id", contractId);
        query.executeUpdate();
        ts.commit();
        session.close();
        return true;
    }
	
	/**
	 * 根据ID删除合同广告位正式表数据
	 * @param contractId
	 * @return
	 */
	public boolean deleteContractADReal(Integer contractId) {
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        String hqlUpdate = "DELETE from T_CONTRACT_AD WHERE CONTRACT_ID = :id";
        Query query = session.createSQLQuery(hqlUpdate);
        query.setInteger("id", contractId);
        query.executeUpdate();
        ts.commit();
        session.close();
        return true;
    }
	

	
	public int getMaxId(){
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		String hqlUpdate = "SELECT MAX(T.ID) FROM T_CONTRACT_BACKUP T";
		Query query = session.createSQLQuery(hqlUpdate);
		BigDecimal obj = (BigDecimal) query.list().get(0);
		ts.commit();
		session.close();
		return obj.intValue();
	}
	
	public int getMaxIdForReal(){
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        String hqlUpdate = "SELECT MAX(T.ID) FROM T_CONTRACT T";
        Query query = session.createSQLQuery(hqlUpdate);
        BigDecimal obj = (BigDecimal) query.list().get(0);
        ts.commit();
        session.close();
        return obj.intValue();
    }
	
	public int getMaxIdForContractAD(){
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        String hqlUpdate = "SELECT MAX(T.ID) FROM T_CONTRACT_AD_BACKUP T";
        Query query = session.createSQLQuery(hqlUpdate);
        BigDecimal obj = (BigDecimal) query.list().get(0);
        ts.commit();
        session.close();
        return obj.intValue();
    }
	
	public int getMaxIdForContractADForReal(){
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        String hqlUpdate = "SELECT MAX(T.ID) FROM T_CONTRACT_AD T";
        Query query = session.createSQLQuery(hqlUpdate);
        BigDecimal obj = (BigDecimal) query.list().get(0);
        ts.commit();
        session.close();
        return obj.intValue();
    }
	
	/**
	 * 
	 * @description: 根据ID获取合同信息
	 * @param id
	 * @return 
	 * ContractBackup
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 上午11:29:00
	 */
	public ContractBackup getContractByID(int id) {

        String hql = "select distinct new com.dvnchina.advertDelivery.model.ContractBackup(" +
        "t.id," +
        "t.contractName," +
        "t.contractNumber," +
        "t.contractCode," +
        "t.customerId," +
        "t.submitUnits," +
        "t.financialInformation," +
        "t.approvalCode," +
        "t.effectiveStartDate," +
        "t.effectiveEndDate," +
        "t.status," +
        "t.approvalStartDate," +
        "t.approvalEndDate," +
        "a.advertisersName, " +
        "t.contractDesc,t.examinationOpinions,t.createTime,t.operatorId) " +
        
        " from ContractBackup t, CustomerBackUp a " +     
        "where t.customerId =a.id and t.id="+id;

        List list = this.getList(hql,null);
        ContractBackup contractBackup =null;
        if (list!=null && list.size()>0)
        {
            contractBackup = (ContractBackup)(list.get(0));
        }
        
        getContractBackupArea(contractBackup);

        return contractBackup;
    }
	
	private void getContractBackupArea(ContractBackup contract){
		String hql ="select la from Location la,ContractBackUpArea ca where la.areaCode = ca.areaCode and ca.contractId = ?";
		
		List<Location> laList = this.getHibernateTemplate().find(hql,contract.getId());
		String areaCodes = "";
		String areaNames = "";
		for(Location la : laList){
			areaCodes += ","+la.getAreaCode();
			areaNames += ","+ la.getAreaName();
		}
		contract.setAreaCodes(areaCodes.equals("")?areaCodes:areaCodes.substring(1));
		contract.setAreaNames(areaNames.equals("")?areaNames:areaNames.substring(1));
	}
	/**
	 * 
	 * @description: 根据编码查询合同信息
	 * @param id
	 * @return 
	 * ContractBackup
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 下午05:31:12
	 */
	public ContractBackup getContractByCode(String code) {

        String hql = " from ContractBackup t " +     
        "where t.contractNumber='"+code+"'";

        List list = this.getList(hql,null);
        ContractBackup contractBackup =null;
        if (list!=null && list.size()>0)
        {
            contractBackup = (ContractBackup)(list.get(0));
        }

        return contractBackup;
    }
	
	/**
	 * 
	 * @description: 根据合同ID获取合同广告位信息
	 * @param contractId
	 * @return 
	 * List<PositionAD>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 上午11:46:42
	 */
	public List<PositionAD> getPositionADByContractID(int contractId) {
        Session session = this.getSession();
        String hql = "select distinct new com.dvnchina.advertDelivery.contract.bean.PositionAD(t.positionId,a.packageType, a.videoType,a.deliveryMode,t.positionName,t.startDate,t.endDate) from ContractADBackup t,AdvertPositionPackage a where t.positionId=a.id and t.contractId  = "+contractId;
        
        
        List<PositionAD> positionADList = new ArrayList<PositionAD>();
        List list =this.getList(hql,null);;
        for (int i = 0, j=list.size(); i < j; i++) {
            PositionAD positionAD = new PositionAD();
            positionAD = (PositionAD) list.get(i);

            positionADList.add(positionAD);
        }
        return positionADList;
    }
	
	public List<ContractADBackup> getContractADByContractID(int contractId) {
        Session session = this.getSession();
        String hql = " from ContractADBackup t where  t.contractId  = "+contractId;
             
        List<ContractADBackup> contractADList = new ArrayList<ContractADBackup>();
        contractADList =this.getList(hql,null);;
        
        return contractADList;
    }
	
	/**
	 * 校验合同是否可删除
	 * return 1不可删除 0 可删除
	 */
	public int checkDelContractById(Integer id)
	{
		List param = null;//new HashMap<String, Object>();
		String hql = "from Order t where 1=1 and t.state!='7' ";// and t.state!='7'  执行完毕

		if(id!= null && id!=0){
			hql +=" and t.contractId="+id;
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
	
	/**根据合同号效验合同是否存在
	 * 
	 * @param id
	 * @return
	 */
	public int checkContractExist(String contractCode)
	{
		List param = null;
		String hql = "from ContractBackup t where 1=1  ";

		if(contractCode!= null && !contractCode.equals("")){
			hql +=" and t.contractNumber='"+contractCode+"'";
		}
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
	
	
	/**
	 * 
	 * @description: 删除合同信息
	 * @param ids
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 下午03:29:43
	 */
	public boolean deleteContractByIds(String ids) {
	    ids = ids.replace(" ", "");
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
//        String hqlUpdate1 = "DELETE T_CONTRACT_BACKUP WHERE ID IN :ids";
//        String hqlUpdate2 = "DELETE T_CONTRACT_AD_BACKUP WHERE CONTRACT_ID IN :ids";
        String hqlUpdate1 = "DELETE from T_CONTRACT_BACKUP WHERE ID IN "+ids;
        String hqlUpdate2 = "DELETE from T_CONTRACT_AD_BACKUP WHERE CONTRACT_ID IN "+ids;
        String hqlUpdate3 = "DELETE from T_CONTRACT WHERE ID IN "+ids;
        String hqlUpdate4 = "DELETE from T_CONTRACT_AD WHERE CONTRACT_ID IN "+ids;
        Query query1 = session.createSQLQuery(hqlUpdate1);
        Query query2 = session.createSQLQuery(hqlUpdate2);
        Query query3 = session.createSQLQuery(hqlUpdate3);
        Query query4 = session.createSQLQuery(hqlUpdate4);
        //query1.setString("ids", ids);
        query1.executeUpdate();
        //query2.setString("ids", ids);
        query2.executeUpdate();
        query3.executeUpdate();
        query4.executeUpdate();
        ts.commit();
        session.close();
        return true;
    }
	 /**
     * Checks if is 校验合同是否已审核过.
     *
     * @param contractId the contract id
     * @return true, if is audit
     */
    public boolean isAudit(int contractId)
    {
    	 
        String sql;
        sql= " from Contract t where 1=1" ;
        if (contractId!=0 )
        {
           sql=sql+" and t.id="+contractId;
        }      
         //分页查询 
         List params = null;
         //pageSize=50;
         List retList=this.getList(sql, params);
         if (retList!=null && retList.size()>0)
         {
        	 return true;
         }
    	return false;
    }
    
    /**
     * 校验广告位是否已销售占用.
     *
     * @param contractId the contract id
     * @param positionId the position id
     * @param startDate the start date
     * @param endDate the end date
     * @return true, if successful
     */
    public boolean checkPostitionUsed(int contractId,int positionId,Date startDate,Date endDate)
    {
    	 String sql;
         sql= " from ContractADBackup t where 1=1" ;
         if (contractId!=0 )
         {
            sql=sql+" and t.contractId!="+contractId;
         } 
         if (positionId!=0 )
         {
            sql=sql+" and t.positionId="+positionId;
         } 
         List params = new ArrayList();
         sql=sql+" and (1!=1 ";
         if (startDate!=null && endDate!=null)
	     {
	        	sql=sql+" or ( t.startDate >=? and t.endDate <=?)";
	        	params.add(startDate);
	        	params.add(endDate);
	      }
         if (startDate!=null)
	     {
        	sql=sql+" or ( t.startDate <=? and t.endDate>=?)";
        	params.add(startDate);
          	params.add(startDate);
	     }
         if (endDate!=null)
	     {
        	sql=sql+" or ( t.startDate <=? and t.endDate>=?)";
        	params.add(endDate);
         	params.add(endDate);
	     }
     	
         sql=sql+" ) ";
          //分页查询 
         
          //pageSize=50;
          List retList=this.getList(sql, params);
          if (retList!=null && retList.size()>0)
          {
         	 return true;
          }
          return false;
    }


	@Override
	public float getAccountsAmount(int contractId) {
		Double amount = 0d;
		String hql = "select sum(a.moneyAmount)  from ContractAccounts a where a.contractId = :contractId";
		Query query = getSession().createQuery(hql);
		query.setInteger("contractId", contractId);
		if(query.list() != null && query.list().size() > 0){
			amount = (Double)query.list().get(0);
			;
		}
		if(amount == null){
			return 0;
		}else{
			return 	amount.floatValue();
		}
	}
	
	/**
	 * 
	 * @description: 获取合同台帐流水
	 * @param contractId
	 * @return 
	 * List<ContractAccounts>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-8-16 下午02:26:39
	 */
	public List<ContractAccounts> getContractAccountsList(Integer contractId) {
	    String hql = "from ContractAccounts c where contractId= "+contractId;
        hql += " order by c.accountsId desc";
        
        List<ContractAccounts> contractAccountsList = new ArrayList<ContractAccounts>();
        List list =this.getList(hql,null);;
        for (int i = 0, j=list.size(); i < j; i++) {
            ContractAccounts temp = new ContractAccounts();
            temp = (ContractAccounts) list.get(i);

            contractAccountsList.add(temp);
        }
        return contractAccountsList;
    }
}
