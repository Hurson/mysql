package com.dvnchina.advertDelivery.meterial.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.classic.QueryTranslatorImpl;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.meterial.bean.MaterialCategory;
import com.dvnchina.advertDelivery.meterial.dao.MeterialManagerDao;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Question;
import com.dvnchina.advertDelivery.model.QuestionReal;
import com.dvnchina.advertDelivery.model.Questionnaire;
import com.dvnchina.advertDelivery.model.QuestionnaireReal;
import com.dvnchina.advertDelivery.model.QuestionnaireTemplate;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;

public class MeterialManagerDaoImpl extends HibernateSQLTemplete implements MeterialManagerDao {

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
	 * 
	 * @description: 查询素材列表 
	 * @param meterialQuery
	 * @param pageSize
	 * @param pageNumber
	 * @return 
	 * PageBeanDB
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-9 上午09:30:40
	 */
	public PageBeanDB queryMeterialList(Resource meterialQuery,Integer pageSize,Integer pageNumber) {
		
		 String sql="select distinct new com.dvnchina.advertDelivery.model.Resource(" +
 		"r.id,r.resourceId,r.resourceName,r.resourceType,r.categoryId,r.startTime,r.endTime,r.state,r.createTime,r.resourceDesc,r.keyWords,r.examinationOpintions," +
 		"a.id,a.positionName,r.isDefault,r.customerId,r.modifyTime,b.advertisersName)" +		
        " from Resource r,AdvertPosition a,Customer b "+
        "where r.advertPositionId=a.id and r.customerId=b.id";
		 if (meterialQuery!=null && meterialQuery.getState()!=null && !"".equals(meterialQuery.getState()))
         {
                    sql=sql+" and r.state='"+meterialQuery.getState()+"'";
         }
		 if (meterialQuery!=null && meterialQuery.getResourceName()!=null && !"".equals(meterialQuery.getResourceName()))
         {
                    sql=sql+" and r.resourceName like '%"+meterialQuery.getResourceName().trim()+"%'";
         }
		 if (meterialQuery!=null && meterialQuery.getAdvertPositionName()!=null && !"".equals(meterialQuery.getAdvertPositionName()))
         {
                    sql=sql+" and a.positionName like '%"+meterialQuery.getAdvertPositionName().trim()+"%'";
         }
//		 if (meterialQuery!=null && meterialQuery.getContractName()!=null && !"".equals(meterialQuery.getContractName()))
//         {
//                    sql=sql+" and c.contractName='"+meterialQuery.getContractName().trim()+"'";
//         }

		 if (meterialQuery!=null && meterialQuery.getPositionIds()!=null && !"".equals(meterialQuery.getPositionIds()))
         {
                    sql=sql+" and a.id in ("+meterialQuery.getPositionIds()+")";
         }
		 if (meterialQuery!=null && meterialQuery.getCustomerId()!=null )
         {
                    sql=sql+" and r.customerId = "+meterialQuery.getCustomerId();
         }
		 if (meterialQuery!=null && meterialQuery.getCustomerName()!=null && !meterialQuery.getCustomerName().equals(""))
         {
                    sql=sql+" and b.advertisersName like '%"+meterialQuery.getCustomerName().trim()+"%'";
         }
	    
	     sql=sql+" order by r.id desc";    
	   
	      //分页查询 
	     PageBeanDB pageResultList = this.getPageList(sql, null, pageNumber, pageSize);
	     //给已使用的素材一个特征值
	     for(Object obj : pageResultList.getDataList()){
	    	 Resource entity = (Resource)obj;
	    	 int id=entity.getId();
	    	 String sql1="SELECT COUNT(1) FROM t_order_mate_rel rel, t_order o WHERE rel.MATE_ID ="+id+"  AND rel.ORDER_ID = o.ID AND o.STATE <> '7'";
	    	 Query query = getSession().createSQLQuery(sql1);
	    	 List a=query.list();	    	 
	    	 if(a.get(0).toString()!="0"){
	    	 	entity.setStateStr("7");
	    	 }
	     }
	     return pageResultList; 
	}
	
	
	
	@Override
	public PageBeanDB queryMeterialList(Resource resource, String accessUserIds, Integer pageSize, Integer pageNumber) {
		 String sql="select distinct new com.dvnchina.advertDelivery.model.Resource(" +
				 "r.id,r.resourceId,r.resourceName,r.resourceType,r.categoryId,r.startTime,r.endTime,r.state,r.createTime,r.resourceDesc,r.keyWords,r.examinationOpintions," +
				 "a.id,a.positionName,r.isDefault,r.customerId,r.modifyTime,b.advertisersName)" +		
				 " from Resource r,AdvertPosition a,Customer b "+
				 "where r.advertPositionId=a.id and r.customerId=b.id";
		 
		 if(null != resource){
			 if(null != resource.getState() && !"".equals(resource.getState())){
				 sql += " and r.state = '" + resource.getState() + "'";
			 }
			 if(StringUtils.isNotBlank(resource.getResourceName())){
				 sql += " and r.resourceName like '%" + resource.getResourceName().trim() + "%'";
			 }
			 if(StringUtils.isNotBlank(resource.getAdvertPositionName())){
				 sql += " and a.positionName like '%" + resource.getAdvertPositionName().trim() + "%'";
			 }
			 if(StringUtils.isNotBlank(resource.getCustomerName())){
				 sql += " and b.advertisersName like '%" + resource.getCustomerName().trim() + "%'";
			 }
		 }

	     sql += " and r.operationId in (" + accessUserIds + ") order by r.id desc";    
	   
	      //分页查询 
	     PageBeanDB pageResultList = this.getPageList(sql, null, pageNumber, pageSize);
	     //给已使用的素材一个特征值
	     for(Object obj : pageResultList.getDataList()){
	    	 Resource entity = (Resource)obj;
	    	 int id=entity.getId();
	    	 String sql1="SELECT COUNT(1) FROM t_order_mate_rel rel, t_order o WHERE rel.MATE_ID ="+id+"  AND rel.ORDER_ID = o.ID AND o.STATE <> '7'";
	    	 Query query = getSession().createSQLQuery(sql1);
	    	 List a=query.list();	    	 
	    	 if(a.get(0).toString()!="0"){
	    	 	entity.setStateStr("7");
	    	 }
	     }
	     return pageResultList; 
	}


	/**
	 * 
	 * @description: 查询问卷模板列表
	 * @param questionnaireTemplate
	 * @param pageSize
	 * @param pageNumber
	 * @return 
	 * PageBeanDB
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-20 上午11:16:23
	 */
	public PageBeanDB queryQuestionTemplateList(QuestionnaireTemplate questionnaireTemplate,Integer pageSize,Integer pageNumber) {
        
        String sql= " from QuestionnaireTemplate t "+
       "where 1=1 ";
        if (questionnaireTemplate!=null && questionnaireTemplate.getTemplatePackageName()!=null && !"".equals(questionnaireTemplate.getTemplatePackageName()))
        {
                   sql=sql+" and t.templatePackageName='"+questionnaireTemplate.getTemplatePackageName().trim()+"'";
        }
       
        sql=sql+" order by t.id desc";    
      
         //分页查询 
         List params = null;
         return this.getPageList(sql, params, pageNumber, pageSize); 
   }
	
	/**
	 * 
	 * @description: 获取素材分类列表
	 * @return 
	 * List<MaterialCategory>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-9 下午03:10:51
	 */
	public List<MaterialCategory> getMaterialCategoryList() {
        Session session = this.getSession();
        String hql = "from MaterialCategory ";     
        
        List<MaterialCategory> materialCategoryList = new ArrayList<MaterialCategory>();
        List list =this.getList(hql,null);;
        for (int i = 0, j=list.size(); i < j; i++) {
            MaterialCategory temp = new MaterialCategory();
            temp = (MaterialCategory) list.get(i);

            materialCategoryList.add(temp);
        }
        return materialCategoryList;
    }
	
	
	/**
	 * 获取广告商列表
	 * @return
	 */
	public List<Customer> getCustomerList() {
        Session session = this.getSession();
        String hql = "from Customer t order by t.id";     
        
        List<Customer> customerList = new ArrayList<Customer>();
        List list =this.getList(hql,null);;
        for (int i = 0, j=list.size(); i < j; i++) {
        	Customer temp = new Customer();
            temp = (Customer) list.get(i);

            customerList.add(temp);
        }
        return customerList;
    }
	
	/**
	 * 
	 * @description: 获取模板列表
	 * @return 
	 * List<QuestionnaireTemplate>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-21 下午02:44:39
	 */
	public List<QuestionnaireTemplate> getQuestionTemplateList() {
        Session session = this.getSession();
        String hql = "from QuestionnaireTemplate ";     
        
        List<QuestionnaireTemplate> templateList = new ArrayList<QuestionnaireTemplate>();
        List list =this.getList(hql,null);;
        for (int i = 0, j=list.size(); i < j; i++) {
            QuestionnaireTemplate temp = new QuestionnaireTemplate();
            temp = (QuestionnaireTemplate) list.get(i);

            templateList.add(temp);
        }
        return templateList;
    }
	
	/**
	 * 
	 * @description: 获取子广告位列表
	 * @param advertPositionQuery
	 * @param pageSize
	 * @param pageNumber
	 * @return 
	 * PageBeanDB
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-9 下午04:00:12
	 */
    public PageBeanDB queryAdPositionList(AdvertPosition advertPositionQuery,Integer pageSize,Integer pageNumber) {
        
        String sql;
        sql= " from AdvertPosition t where 1=1" ;
        if (advertPositionQuery!=null && advertPositionQuery.getPositionName()!=null && !"".equals(advertPositionQuery.getPositionName()))
        {
                   sql=sql+" and t.positionName like '%"+advertPositionQuery.getPositionName()+"%'";
        }
       
        sql=sql+" order by t.id desc";    
        if (advertPositionQuery!=null && advertPositionQuery.getContractId()!=null && advertPositionQuery.getContractId()!=0)
        {
        	sql="select p from AdvertPosition p,ContractAD c where p.positionPackageId=c.positionId ";
			// "c.marketingRuleId,"+
			// "p.isAsset)"+
		     if (advertPositionQuery!=null && advertPositionQuery.getId()!=null)
		     {
		        	sql=sql+" and p.id="+advertPositionQuery.getId();
		     }
		     if (advertPositionQuery!=null && advertPositionQuery.getPositionName()!=null)
		     {
		        	sql=sql+" and p.positionName like '%"+advertPositionQuery.getPositionName()+"%'";
		     }
		     if (advertPositionQuery.getContractId()!=null && advertPositionQuery.getContractId()!=0)
		     {
		    	sql=sql+ " and c.contractId = "+advertPositionQuery.getContractId();
		     }
		     sql=sql+" order by p.id desc";    
        }//分页查询 
         List params = null;
         return this.getPageList(sql, params, pageNumber, pageSize); 
   }
    
    /**
     * 
     * @description: 获取子广告位列表
     * @param advertPositionQuery
     * @param pageSize
     * @param pageNumber
     * @return 
     * PageBeanDB
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-9 下午04:00:12
     */
    public PageBeanDB queryAdPositionList(AdvertPosition advertPositionQuery,String positionPackIds,Integer pageSize,Integer pageNumber) {
        
        String sql;
        sql= " from AdvertPosition t where 1=1" ;          
        if (positionPackIds!=null && !positionPackIds.equals("") )
        {
                    sql=sql+" and t.positionPackageId in ("+positionPackIds+")";
        }
        if (advertPositionQuery!=null && advertPositionQuery.getPositionName()!=null)
        {
               sql=sql+" and t.positionName like '%"+advertPositionQuery.getPositionName()+"%'";
        }
        sql=sql+" order by t.id desc"; 
        //分页查询 
         List params = null;
         return this.getPageList(sql, params, pageNumber, pageSize); 
   }
    
    /**
     * 
     * @description: 根据广告位包ID获取广告位列表
     * @param positionPackIds
     * @return 
     * List<AdvertPosition>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-8-14 上午10:43:10
     */
    public List<AdvertPosition> getAdvertPositionList(String positionPackIds) {
        //Session session = this.getSession();
        String hql = " from AdvertPosition t where 1=1" ;          
        if (positionPackIds!=null && !positionPackIds.equals("") )
        {
            hql=hql+" and t.positionPackageId in ("+positionPackIds+")";
        }
        hql=hql+" order by t.id desc";
        
        List<AdvertPosition> advertPositionList = new ArrayList<AdvertPosition>();
        List list =this.getList(hql,null);;
        for (int i = 0, j=list.size(); i < j; i++) {
            AdvertPosition temp = new AdvertPosition();
            temp = (AdvertPosition) list.get(i);

            advertPositionList.add(temp);
        }
        return advertPositionList;
    }
    
    /**
     * 
     * @description: 根据广告商ID获取广告位包信息
     * @param customerId
     * @return 
     * List<ContractAD>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-8-14 下午04:38:25
     */
    public List<ContractAD> getAdvertPositionPackListByCustomer(Integer customerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(new Date());
               
        String hql = "select distinct new com.dvnchina.advertDelivery.model.ContractAD(t.id,t.contractId,t.positionId,t.startDate,t.endDate) from ContractAD t,Contract a where t.contractId=a.id" ;  
               //hql=hql+" and t.startDate <=str_to_date('"+nowDate+"','%Y-%m-%d') and t.endDate>=str_to_date('"+nowDate+"','%Y-%m-%d') ";
               hql=hql+" and t.endDate>=str_to_date('"+nowDate+"','%Y-%m-%d') ";
        if (customerId!=null )
        {
            hql=hql+" and a.customerId ="+customerId;
        }
        hql=hql+" order by t.id desc";
        
        List<ContractAD> advertPositionList = new ArrayList<ContractAD>();
        List list =this.getList(hql,null);;
        for (int i = 0, j=list.size(); i < j; i++) {
            ContractAD temp = new ContractAD();
            temp = (ContractAD) list.get(i);

            advertPositionList.add(temp);
        }
        return advertPositionList;
    }
	

    /**
     * 
     * @description: 获取合同列表
     * @param contract
     * @param pageSize
     * @param pageNumber
     * @return 
     * PageBeanDB
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-9 下午05:48:28
     */
    public PageBeanDB queryContractList(ContractQueryBean contract,Integer pageSize,Integer pageNumber) {
        
        String sql;
        sql="select distinct new com.dvnchina.advertDelivery.model.Contract(" +
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
       "t.state) " +
       " from Contract t,Customer c where t.customerId = c.id and t.effectiveEndDate>?" ;
        List params = new ArrayList();
        Date nowDate= new Date();
        Calendar c1 =Calendar.getInstance();
        c1.setTime(nowDate);
        c1.add(Calendar.DATE,-1);
        nowDate=c1.getTime();         
        params.add(nowDate);
        if (contract!=null && contract.getContractName()!=null && !"".equals(contract.getContractName()))
        {
                   sql=sql+" and t.contractName like '%"+contract.getContractName().trim()+"%'";
        }
        if (contract!=null && contract.getCustomerName()!=null && !"".equals(contract.getCustomerName()))
        {
                   sql=sql+" and c.advertisersName like '%"+contract.getCustomerName().trim()+"%'";
        }
        if (contract!=null && contract.getEffectiveStartDate()!=null && contract.getEffectiveEndDate()!=null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String effectiveStartDate = sdf.format(contract.getEffectiveStartDate());
            String effectiveEndDate = sdf.format(contract.getEffectiveEndDate());
            //       sql=sql+" and t.effectiveStartDate >= to_date('"+effectiveStartDate+"','yyyy-MM-dd')and  t.effectiveEndDate <=to_date('"+effectiveEndDate+"','yyyy-MM-dd')";
           sql=sql+" and t.effectiveStartDate >= ? and  t.effectiveEndDate <=?";
           //sql=sql+" and t.effectiveStartDate >= str_to_date('"+effectiveStartDate+"','%Y-%m-%d')and  t.effectiveEndDate <=str_to_date('"+effectiveEndDate+"','%Y-%m-%d')";
           params.add(contract.getEffectiveStartDate());
           params.add(contract.getEffectiveEndDate());
        }
        if (contract!=null && contract.getCustomerids()!=null && !"".equals(contract.getCustomerids()))
        {
                   sql=sql+" and c.id in ("+contract.getCustomerids()+")";
        }
       
        sql=sql+" order by t.id desc";    
      
         //分页查询 
         return this.getPageList(sql, params, pageNumber, pageSize); 
   }
    
    /**
     * 
     * @description: 保存Resource
     * @param resource 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-10 下午04:52:10
     */
    public void saveResource(Resource resource) {
        getHibernateTemplate().saveOrUpdate(resource);
    } 
    
    /**
     * 
     * @description: 保存问卷模板
     * @param questionnaireTemplate 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-20 下午05:03:13
     */
    public void saveQuestionTemplate(QuestionnaireTemplate questionnaireTemplate) {
        getHibernateTemplate().saveOrUpdate(questionnaireTemplate);
    }
    
    /**
     * 
     * @description: 保存Resource正式表
     * @param resource 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午04:03:44
     */
    public void saveResourceReal(ResourceReal resource) {
        getHibernateTemplate().saveOrUpdate(resource);
    }
    
    /**
     * 
     * @description: 保存问卷主题
     * @param questionnaire 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-10 下午05:20:26
     */
    public void saveQuestionSubject(Questionnaire questionnaire) {
        getHibernateTemplate().saveOrUpdate(questionnaire);
    }
    
    /**
     * 
     * @description: 保存问卷主题正式表
     * @param questionnaire 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:39:41
     */
    public void saveQuestionSubjectReal(QuestionnaireReal questionnaire) {
        getHibernateTemplate().saveOrUpdate(questionnaire);
    }
    
    
    /**
     * 
     * @description: 保存问题列表
     * @param questionList
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-10 下午05:29:54
     */
    public boolean saveQuestionList(List<Question> questionList) {
        this.getHibernateTemplate().saveOrUpdateAll(questionList);
        return true;
    }
    
    /**
     * 
     * @description: 保存问卷问题正式表列表
     * @param questionList
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:46:56
     */
    public boolean saveQuestionRealList(List<QuestionReal> questionList) {
        this.getHibernateTemplate().saveOrUpdateAll(questionList);
        return true;
    }
    
    /**
     * 
     * @description: 保存文字素材子表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-13 上午11:45:32
     */
    public void saveTextMaterial(MessageMeta material) {
        getHibernateTemplate().saveOrUpdate(material);
    }
    
    /**
     * 
     * @description: 保存文字素材正式表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:02:27
     */
    public void saveTextMaterialReal(MessageReal material) {
        getHibernateTemplate().saveOrUpdate(material);
    }
    
    /**
     * 
     * @description: 保存图片素材
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-14 下午03:06:18
     */
    public void saveImageMaterial(ImageMeta material) {
        getHibernateTemplate().saveOrUpdate(material);
    }  
    
   
	/**
     * 
     * @description: 保存图片素材正式表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午04:22:38
     */
    public void saveImageMaterialReal(ImageReal material) {
        getHibernateTemplate().saveOrUpdate(material);
    }
    
    /**
     * 
     * @description: 保存视频素材
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 上午09:57:01
     */
    public void saveVideoMaterial(VideoMeta material) {
        getHibernateTemplate().saveOrUpdate(material);
    }
    
    /**
     * 
     * @description: 保存视频素材正式表
     * @param material 
     * void
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午04:46:59
     */
    public void saveVideoMaterialReal(VideoReal material) {
        getHibernateTemplate().saveOrUpdate(material);
    }
    
    /**
     * 
     * @description: 获取合同
     * @param id
     * @return 
     * Contract
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-13 下午01:54:12
     */
    public Contract getContractByID(int id) {

        String hql =  " from Contract t where t.id="+id;

        List list = this.getList(hql,null);
        Contract contract =null;
        if (list!=null && list.size()>0)
        {
            contract = (Contract)(list.get(0));
        }

        return contract;
    }
    
    /**
     * 
     * @description: 根据ID获取material
     * @param id
     * @return 
     * Resource
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-14 上午09:51:03
     */
    public Resource getMaterialByID(int id) {

        String hql = "select distinct new com.dvnchina.advertDelivery.model.Resource(" +
        "r.id,r.resourceId,r.resourceName,r.resourceType,r.categoryId,r.startTime,r.endTime,r.state,r.createTime,r.resourceDesc,r.keyWords,r.examinationOpintions," +
        "a.id,a.positionName,r.isDefault,r.customerId,r.operationId,r.modifyTime,b.advertisersName)" +      
        " from Resource r,AdvertPosition a,Customer b "+
        "where r.advertPositionId=a.id and r.customerId=b.id and r.id="+id;

        List list = this.getList(hql,null);
        Resource material =null;
        if (list!=null && list.size()>0)
        {
            material = (Resource)(list.get(0));
        }
       
	    	 
	    	 int mid=material.getId();
	    	 String sql1="SELECT COUNT(1) FROM t_order_mate_rel rel, t_order o WHERE rel.MATE_ID ="+mid+"  AND rel.ORDER_ID = o.ID AND o.STATE <> '7'";
	    	 Query query = getSession().createSQLQuery(sql1);
	    	 List a=query.list();	    	 
	    	 if(a.get(0).toString()!="0"){
	    		 material.setStateStr("7");
	    	 }
	   

        return material;
    }
    
    /**
     * 
     * @description: 根据ID获取文字素材
     * @param id
     * @return 
     * MessageMeta
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-14 上午10:45:51
     */
    public MessageMeta getTextMetaByID(int id) {

        String hql =  " from MessageMeta t where t.id="+id;

        List list = this.getList(hql,null);
        MessageMeta textMeta =null;
        if (list!=null && list.size()>0)
        {
            textMeta = (MessageMeta)(list.get(0));
        }

        return textMeta;
    }
    
    /**
     * 
     * @description: 根据ID获取图片素材
     * @param id
     * @return 
     * ImageMeta
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-14 上午10:47:24
     */
    public ImageMeta getImageMetaByID(int id) {

        String hql =  " from ImageMeta t where t.id="+id;

        List list = this.getList(hql,null);
        ImageMeta imageMeta =null;
        if (list!=null && list.size()>0)
        {
            imageMeta = (ImageMeta)(list.get(0));
        }

        return imageMeta;
    }
	
    /**
     * 
     * @description: 根据ID获取视频素材
     * @param id
     * @return 
     * VideoMeta
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-14 上午10:48:48
     */
    public VideoMeta getVideoMetaByID(int id) {

        String hql =  " from VideoMeta t where t.id="+id;

        List list = this.getList(hql,null);
        VideoMeta videoMeta =null;
        if (list!=null && list.size()>0)
        {
            videoMeta = (VideoMeta)(list.get(0));
        }

        return videoMeta;
    }
    
    /**
     * 
     * @description: 根据ID获取问卷主题
     * @param id
     * @return 
     * Questionnaire
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:13:06
     */
    public Questionnaire getQueMetaByID(int id) {

        String hql =  " from Questionnaire t where t.id="+id;

        List list = this.getList(hql,null);
        Questionnaire questionnaire =null;
        if (list!=null && list.size()>0)
        {
            questionnaire = (Questionnaire)(list.get(0));
        }

        return questionnaire;
    }
    
    /**
     * 
     * @description: 根据ID获取问卷问题列表
     * @param id
     * @return 
     * List<Question>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:17:24
     */
    public List<Question> getQuestionAnswerList(int id) {
        Session session = this.getSession();
        String hql = "from Question t where t.questionnaireId="+id;     
        
        List<Question> questionAnswerList = new ArrayList<Question>();
        List list =this.getList(hql,null);;
        for (int i = 0, j=list.size(); i < j; i++) {
            Question temp = new Question();
            temp = (Question) list.get(i);

            questionAnswerList.add(temp);
        }
        return questionAnswerList;
    }
    
    /**
     * 获取问题列表
     * @param id
     * @return
     */
    public List<Question> getQuestionList(int id) {
        Session session = this.getSession();
        String hql = "select distinct com.dvnchina.advertDelivery.model.Question(t.question) from Question t where t.questionnaireId="+id;     
        
        List<Question> questionAnswerList = new ArrayList<Question>();
        List list =this.getList(hql,null);;
        for (int i = 0, j=list.size(); i < j; i++) {
            Question temp = new Question();
            temp = (Question) list.get(i);

            questionAnswerList.add(temp);
        }
        return questionAnswerList;
    }
    
    /**
     * 根据问题获取答案列表
     * @param question
     * @return
     */
    public List<Question> getAnswerList(String question,Integer id) {
        Session session = this.getSession();
        String hql = "from Question t where t.question='"+question+"' and t.questionnaireId="+id ;     
        
        List<Question> questionAnswerList = new ArrayList<Question>();
        List list =this.getList(hql,null);;
        for (int i = 0, j=list.size(); i < j; i++) {
            Question temp = new Question();
            temp = (Question) list.get(i);

            questionAnswerList.add(temp);
        }
        return questionAnswerList;
    }
    
    /**
	 * 校验素材是否可删除
	 * return 1不可删除 0 可删除
	 */
	public int checkDelMeterial(Integer id)
	{
		int totalCount=0;
		String sql="select a.id,b.mate_id" +		
        " from t_order a,t_order_mate_rel b "+
        "where a.id=b.order_id and a.state!='7' ";
		if(id!= null && id!=0){
			sql +=" and b.mate_id="+id;
		}
		 
		totalCount = this.getTotalCountSQL(sql.toString(), null);
		
		if (totalCount>0)
		{
			return 1;
		}
		else
		{	
			return 0;
		}
	}
	
	public int getTotalCountSQL(String sql, Object[] values) {
        String countSql = "select count(*) from (" + sql + ") tot";
        Query query = getSession().createSQLQuery(countSql);
        
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
            	query.setParameter(i, values[i]);
            }
        }
		List<?> list = query.list();
		if (list.size()>0) {
			try {
				return toInteger(list.get(0)).intValue();
			} catch (Exception ex) {
				ex.printStackTrace();
				return 0;
			}
		} else{
			return 0;
		}
	}
	
	/**
	 * BigDecimal to Integer
	 * @param obj
	 * @return Integer
	 */
	public Integer toInteger(Object obj) {
		Integer value = null;
		if (obj != null) {
			if (obj instanceof BigDecimal) {
				value = ((BigDecimal) obj).intValue();
			} else if (obj instanceof BigInteger) {
				value = ((BigInteger) obj).intValue();
			} else if (obj instanceof Integer) {
				value = ((Integer) obj).intValue();
			}
		}
		return value;
	}
    
    /**
     * 
     * @description: 删除素材 
     * @param ids
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 上午10:53:02
     */
    public boolean deleteMaterialByIds(String ids,String imageIds,String videoIds,String textIds,String questionIds) {
   
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();

        //素材主表
        ids = ids.replace(" ", "");
        String hqlUpdate1 = "DELETE from T_RESOURCE_BACKUP WHERE ID IN "+ids;
        //从物理删除改为逻辑删除
        //String hqlUpdate2 = "DELETE from T_RESOURCE WHERE ID IN "+ids;
        String hqlUpdate2 = "update T_RESOURCE set state='4' WHERE ID IN "+ids;
        Query query1 = session.createSQLQuery(hqlUpdate1);
        Query query2 = session.createSQLQuery(hqlUpdate2);
        query1.executeUpdate();
        query2.executeUpdate();
        //图片
        if(imageIds!=null&&!imageIds.equals("()")){
            imageIds = imageIds.replace(" ", "");
            String imageHqlUpdate1 = "DELETE from T_IMAGE_META_BACKUP WHERE ID IN "+imageIds;
            String imageHqlUpdate2 = "DELETE from T_IMAGE_META WHERE ID IN "+imageIds;
            Query imageQuery1 = session.createSQLQuery(imageHqlUpdate1);
            Query imageQuery2 = session.createSQLQuery(imageHqlUpdate2);
            imageQuery1.executeUpdate();
            imageQuery2.executeUpdate();
        }  
        //视频
        if(videoIds!=null&&!videoIds.equals("()")){
            videoIds = videoIds.replace(" ", "");
            String videoHqlUpdate1 = "DELETE from T_VIDEO_META_BACKUP WHERE ID IN "+videoIds;
            String videoHqlUpdate2 = "DELETE from T_VIDEO_META WHERE ID IN "+videoIds;
            Query videoQuery1 = session.createSQLQuery(videoHqlUpdate1);
            Query videoQuery2 = session.createSQLQuery(videoHqlUpdate2);
            videoQuery1.executeUpdate();
            videoQuery2.executeUpdate();
        }       
        //文字
        if(textIds!=null&&!textIds.equals("()")){
            textIds = textIds.replace(" ", "");
            String textHqlUpdate1 = "DELETE from T_TEXT_META_BACKUP WHERE ID IN "+textIds;
            String textHqlUpdate2 = "DELETE from T_TEXT_META WHERE ID IN "+textIds;
            Query textQuery1 = session.createSQLQuery(textHqlUpdate1);
            Query textQuery2 = session.createSQLQuery(textHqlUpdate2);
            textQuery1.executeUpdate();
            textQuery2.executeUpdate();
        }      
        //问卷
        if(questionIds!=null&&!questionIds.equals("()")){
            questionIds = questionIds.replace(" ", "");
            String questHqlUpdate1 = "DELETE from T_QUESTION_REAL WHERE QUESTIONNAIRE_ID IN "+questionIds;
            String questHqlUpdate2 = "DELETE from T_QUESTION WHERE QUESTIONNAIRE_ID IN "+questionIds;       
            String questHqlUpdate3 = "DELETE from T_QUESTIONNAIRE_REAL WHERE ID IN "+questionIds;
            String questHqlUpdate4 = "DELETE from T_QUESTIONNAIRE WHERE ID IN "+questionIds;
            Query questQuery1 = session.createSQLQuery(questHqlUpdate1);
            Query questQuery2 = session.createSQLQuery(questHqlUpdate2);
            Query questQuery3 = session.createSQLQuery(questHqlUpdate3);
            Query questQuery4 = session.createSQLQuery(questHqlUpdate4);
            questQuery1.executeUpdate();
            questQuery2.executeUpdate();
            questQuery3.executeUpdate();
            questQuery4.executeUpdate();
        }     

        ts.commit();
        session.close();
        return true;
    }
    
    /**
     * 
     * @description: 删除问卷模板
     * @param ids
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-21 上午11:13:26
     */
    public boolean deleteQuestionTemplate(String ids) {
        ids = ids.replace(" ", "");
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();

        String hqlUpdate1 = "DELETE from T_QUESTIONNAIRE_TEMPLATE WHERE ID IN "+ids;       
        Query query1 = session.createSQLQuery(hqlUpdate1);

        query1.executeUpdate();
        ts.commit();
        session.close();
        return true;
    }
    
    /**
     * 
     * @description: 删除文字素材
     * @param ids
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 上午11:17:18
     */
    public boolean deleteTextMeta(String ids) {
        ids = ids.replace(" ", "");
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();

        String hqlUpdate1 = "DELETE from  T_TEXT_META_BACKUP WHERE ID IN "+ids;
        String hqlUpdate2 = "DELETE from T_TEXT_META WHERE ID IN "+ids;
       
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
     * @description: 删除视频素材
     * @param ids
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 上午11:18:26
     */
    public boolean deleteVideoMeta(String ids) {
        ids = ids.replace(" ", "");
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();

        String hqlUpdate1 = "DELETE from T_VIDEO_META_BACKUP WHERE ID IN "+ids;
        String hqlUpdate2 = "DELETE from T_VIDEO_META WHERE ID IN "+ids;
       
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
     * @description: 删除图片素材
     * @param ids
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 上午11:18:26
     */
    public boolean deleteImageMeta(String ids) {
        ids = ids.replace(" ", "");
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();

        String hqlUpdate1 = "DELETE from T_IMAGE_META_BACKUP WHERE ID IN "+ids;
        String hqlUpdate2 = "DELETE from T_IMAGE_META WHERE ID IN "+ids;
       
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
     * @description: 删除问卷素材
     * @param ids
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 上午11:18:26
     */
    public boolean deleteQuestionMeta(String ids) {
        ids = ids.replace(" ", "");
        Session session = this.getSessionFactory().openSession();
        Transaction ts = session.beginTransaction();

        String hqlUpdate1 = "DELETE from T_QUESTION_REAL WHERE QUESTIONNAIRE_ID IN "+ids;
        String hqlUpdate2 = "DELETE from T_QUESTION WHERE QUESTIONNAIRE_ID IN "+ids;       
        String hqlUpdate3 = "DELETE from T_QUESTIONNAIRE_REAL WHERE ID IN "+ids;
        String hqlUpdate4 = "DELETE from T_QUESTIONNAIRE WHERE ID IN "+ids;
       
        Query query1 = session.createSQLQuery(hqlUpdate1);
        Query query2 = session.createSQLQuery(hqlUpdate2);
        Query query3 = session.createSQLQuery(hqlUpdate3);
        Query query4 = session.createSQLQuery(hqlUpdate4);

        query1.executeUpdate();
        query2.executeUpdate();
        query3.executeUpdate();
        query4.executeUpdate();

        ts.commit();
        session.close();
        return true;
    }
    
    
    /**
     * 
     * @description: 获取广告位图片规格
     * @param positionId
     * @return 
     * ImageSpecification
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-17 上午11:54:15
     */
    public ImageSpecification getImageMateSpeci(Integer positionId) {
        String hql =  "select  new com.dvnchina.advertDelivery.position.bean.ImageSpecification(t.id,t.imageWidth,t.imageHeight,t.type,t.fileSize ) from ImageSpecification t,AdvertPosition a where a.imageRuleId=t.id and a.id ="+positionId;

        List list = this.getList(hql,null);
        ImageSpecification imageSpecification =null;
        if (list!=null && list.size()>0)
        {
            imageSpecification = (ImageSpecification)(list.get(0));
        }

        return imageSpecification;
    }
    
    /**
     * 
     * @description: 获取广告位视频规格
     * @param positionId
     * @return 
     * VideoSpecification
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-27 上午11:21:31
     */
    public VideoSpecification getVideoMateSpeci(Integer positionId) {
        String hql =  "select  new com.dvnchina.advertDelivery.position.bean.VideoSpecification(t.id,t.duration,t.fileSize) from VideoSpecification t,AdvertPosition a where a.videoRuleId=t.id and a.id ="+positionId;

        List list = this.getList(hql,null);
        VideoSpecification videoSpecification =null;
        if (list!=null && list.size()>0)
        {
            videoSpecification = (VideoSpecification)(list.get(0));
        }

        return videoSpecification;
    }
    
    /**
     * 
     * @description: 根据ID获取问卷模板
     * @param id
     * @return 
     * QuestionnaireTemplate
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-20 下午05:51:54
     */
    public QuestionnaireTemplate getQuestionTemplateByID(int id) {

        String hql = " from QuestionnaireTemplate t where t.id="+id;

        List list = this.getList(hql,null);
        QuestionnaireTemplate questionnaireTemplate =null;
        if (list!=null && list.size()>0)
        {
            questionnaireTemplate = (QuestionnaireTemplate)(list.get(0));
        }

        return questionnaireTemplate;
    }
    
    /**
     * 
     * @description: 获取问题列表
     * @param id
     * @return 
     * List<Question>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-23 上午10:33:49
     */
    public List<Question> getQuestionList(Integer id) {
        List<Question> questionList =new ArrayList<Question>();;  
        String sql = "select t.question from t_question t where t.questionnaire_id=? group by t.question";
        List resultList = this.getDataBySql(sql, new Object[]{id});

        for (int i=0; i<resultList.size(); i++) {
            String que = (String)resultList.get(i);
            Question temp = new Question();
            temp.setQuestion(que);
            questionList.add(temp);
        }
        
        return questionList;
    }
    
    /**
     * @param sql  sql语句
    * @param values 参数
    * 根据SQL语句获取列表
    */
  public List getDataBySql(String sql, final Object[] values) {
      Query qury = getSession().createSQLQuery(sql);
      if (values != null) {
          for (int i = 0; i < values.length; i++) {
              qury.setParameter(i, values[i]);
          }
      }
      List qList = qury.list();
      return qList;
  }


  	/**
	 * @description: 首页代办获取待审批的资产的总数
	 * @return 待审批的资产的总数
	 */
	public int queryMaterialWaitingAuditCount(String positionIds) {
		String sql="select distinct new com.dvnchina.advertDelivery.model.Resource(" +
 		"r.id,r.resourceId,r.resourceName,r.resourceType,r.categoryId,r.startTime,r.endTime,r.state,r.createTime,r.resourceDesc,r.keyWords,r.examinationOpintions," +
 		"a.id,a.positionName,r.isDefault,r.customerId,r.modifyTime,b.advertisersName)" +		
        " from Resource r,AdvertPosition a,Customer b "+
        "where r.advertPositionId=a.id and r.customerId=b.id and r.state=0 and a.id in ("+positionIds+")";
		 Long count = getDataTotal(sql, null);
		return count.intValue();
	}
	
	/**
	 * 获取子广告位信息
	 * @param id
	 * @return
	 */
	public AdvertPosition getAdvertPosition(Integer id) {

        String hql = "select distinct new com.dvnchina.advertDelivery.position.bean.AdvertPosition(" +
        "a.id,a.positionCode,a.positionPackageId,b.positionPackageType)" +      
        " from AdvertPosition a,PositionPackage b "+
        "where a.positionPackageId=b.id and a.id="+id;

        List list = this.getList(hql,null);
        AdvertPosition advertPosition =null;
        if (list!=null && list.size()>0)
        {
        	advertPosition = (AdvertPosition)(list.get(0));
        }

        return advertPosition;
    }
	
	/**
	 * 效验素材名称是否重复
	 * @param resourceName
	 * @return
	 */
	public int checkMaterialExist(String resourceName)
	{
		List param = null;
		String hql = "from Resource t where 1=1  ";

		if(resourceName!= null && !resourceName.equals("")){
			hql +=" and t.resourceName='"+resourceName+"'";
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
	 * 效验模板名称是否重复
	 * @param templateName
	 * @return
	 */
	public int checkQuestionTemplateExist(String templateName)
	{
		List param = null;
		String hql = "from QuestionnaireTemplate t where 1=1  ";

		if(templateName!= null && !templateName.equals("")){
			hql +=" and t.templatePackageName='"+templateName+"'";
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
	
	
}
