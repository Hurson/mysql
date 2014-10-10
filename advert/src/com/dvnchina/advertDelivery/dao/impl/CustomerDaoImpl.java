package com.dvnchina.advertDelivery.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.dao.CustomerDao;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.CustomerBackUp;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;

public class CustomerDaoImpl extends HibernateSQLTemplete implements CustomerDao {
	
	
	/**
	 * 通过资源表中客户id得到运行客户表中的所属合同号 
	 * @return
	 */
	public List getRunClientCodeByCustomerId(Integer customerId){
		
		List<Customer> listCustomer = new ArrayList<Customer>();
		
		StringBuffer sb = new StringBuffer();
		sb.append("from Customer where id = ");
		sb.append(customerId);
		
		listCustomer = this.findByHQL(sb.toString());
		return listCustomer;
	}
	
	
	

	/**
	 * 通过资源表中客户id得到维护客户表中的所属合同号 
	 * @return
	 */
	public List getClientCodeByCustomerId(Integer customerId){
		
		List<CustomerBackUp> listCustomer = new ArrayList<CustomerBackUp>();
		
		StringBuffer sb = new StringBuffer();
		sb.append("from CustomerBackUp where delflag =0 and id = ");
		sb.append(customerId);
		
		listCustomer = this.findByHQL(sb.toString());
		return listCustomer;
	}
	
	
	
	public List<Contract> getContractByCustomerId(Integer id){
		
		List<Contract> list = new ArrayList<Contract>();
		
		StringBuffer sb = new StringBuffer();
			
		sb.append("from Contract where customerId=");
		sb.append(id);
		
		list = this.findByHQL(sb.toString());
		
		return list; 
	}
	
	
	/**
	 * 查询客户运行期结果集
	 */
	public List listCustomerMgr(Customer customer, int x, int y) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		List<Customer> listCustomers = null;
		String hql = "from Customer where 1=1"; 
		listCustomers =(List<Customer>)this.findByHQL(hql, map,x,y);
		return listCustomers;
	}
	
	
	/**
	 * 查询客户维护表所有记录
	 */
	public List listCustomerBackUpMgr(CustomerBackUp customerBackUp, int x, int y,String state,String customerIds) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		List<CustomerBackUp> listCustomers = null;
		String hql = "from CustomerBackUp where delflag =0 and 1=1"; 
		//过滤可操作的广告商查询
		if(customerIds!= null && !"".equals(customerIds)){
			hql +=" AND id in ("+customerIds+") ";
		}
		hql += fillCond(map,customerBackUp,state);
		
	
		listCustomers =(List<CustomerBackUp>)this.findByHQL(hql, map,x,y);
		
		return listCustomers;
	}
	
	
	/* 
	 *查询维护期表所有内容 
	 * 
	 */
	public int getCustomerBackUpCount(CustomerBackUp customerBackUp,String state,String customerIds) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int count = 0;
		
		String hql ="select count(*) from CustomerBackUp where delflag =0 and 1=1";
		if(customerIds!= null && !"".equals(customerIds)){
			hql +=" AND id in ("+customerIds+")";
		}
		hql += this.fillCond(map, customerBackUp ,state);
		//过滤可操作的广告商查询
		
		count = this.getTotalByHQL(hql, map);
		
		return count;
	}
	

	private String fillCond(Map<String, Object> map, CustomerBackUp customerBackUp,String state) {
		
		logger.info("----fillCond 被调用----");
		
		StringBuffer sb = new StringBuffer("");
		
		if(customerBackUp.getAdvertisersName()!= null && !"".equals(customerBackUp.getAdvertisersName())){
			map.put("advertisersName", "%" + customerBackUp.getAdvertisersName()+ "%");
			sb.append(" AND advertisersName like:advertisersName");
			
		}
		
		if(customerBackUp.getCommunicator() != null && !"".equals(customerBackUp.getCommunicator())){
			map.put("communicator","%" + customerBackUp.getCommunicator()+ "%");
			sb.append(" AND communicator like:communicator");
			
		}
		
		if(customerBackUp.getClientCode()!= null && !"".equals(customerBackUp.getClientCode())){
			map.put("clientCode","%" + customerBackUp.getClientCode()+ "%");
			sb.append(" AND clientCode like:clientCode ");
			
		}
		
		if(customerBackUp.getConpanyAddress()!= null && !"".equals(customerBackUp.getConpanyAddress())){
			map.put("conpanyAddress","%" + customerBackUp.getConpanyAddress()+ "%");
			sb.append(" AND conpanyAddress like:conpanyAddress");
			
		}
		
		if(customerBackUp.getCooperationTime()!= null && !"".equals(customerBackUp.getCooperationTime())){
			map.put("cooperationTime","%" + customerBackUp.getCooperationTime() + "%");
			sb.append(" AND cooperationTime like:cooperationTime");
			
		}
		
		if(customerBackUp.getStatus()!= null && !"".equals(customerBackUp.getStatus()) && !"5".equals(customerBackUp.getStatus())){
			map.put("status", customerBackUp.getStatus());
			sb.append(" AND status =:status ");
			
		}
		
		//创建时间 前
		if(customerBackUp.getCreateTimeA()!= null && !"".equals(customerBackUp.getCreateTimeA())){
			map.put("createTimeA","%" + customerBackUp.getCreateTimeA()+ "%");
			sb.append(" AND createTime >=:createTimeA ");
		}
		
		//创建时间 后
		if(customerBackUp.getCreateTimeB()!= null && !"".equals(customerBackUp.getCreateTimeB())){
			map.put("createTimeB","%" + customerBackUp.getCreateTimeB()+ "%");
			sb.append(" AND createTime <=:createTimeB");
		}
		
		//待审核状态
		if(state != null && !"".equals(state)){
			sb.append(" AND status = 0");
		}

		sb.append(" order by createTime desc ");
		
		return sb.toString();
	}

	/*
	 * 删除客户记录
	 */
	public void deleteCustomerBackUp(CustomerBackUp customerBackUp) {
		logger.debug("------deleteCustomerBackUp()-----start----");
		if(customerBackUp != null && !"".equals(customerBackUp)){
			customerBackUp.setDelflag(1);			
			this.getHibernateTemplate().save(customerBackUp);
			//add by rengm 同时删除运行期表数据
			Customer temp=getHibernateTemplate().get(Customer.class, customerBackUp.getId());
			if (temp!=null)
			{
				this.getHibernateTemplate().delete(temp);
			}
		}else{
			System.out.println("--customerBackUp 为空----");
		}
		logger.debug("------deleteCustomerBackUp()-----start----");
	}

	/**
	 * 根据id得到客户记录
	 */
	public CustomerBackUp getCustomerBackUpById(Integer id) {
		logger.debug("----getCustomerBackUpById start-----");
		return getHibernateTemplate().get(CustomerBackUp.class, id);
	}

	/**
	 * 添加广告商维护表中的数据
	 * 
	 */
	public void insertCustomerBackUp(CustomerBackUp customerBackUp) {
		logger.debug("----insertCustomerBackUp start-----");
		customerBackUp.setDelflag(0);
		if (customerBackUp.getCreditRating()==null || customerBackUp.getCreditRating().equals(""))
		{
			customerBackUp.setCreditRating("1");
		}
		this.getHibernateTemplate().save(customerBackUp);
	}

	/**
	 * 添加广告商运行表中的数据
	 * 
	 */
	public void insertCustomer(Customer customer) {
		logger.debug("----insertCustomer start-----");
		this.getHibernateTemplate().saveOrUpdate(customer);
	}

	
	
	
	/**
	 * 更新广告商维护表中客户
	 */         
	public void updateCustomerBackUp(CustomerBackUp customerBackUp) {
		logger.debug("----updateCustomerBackUp start-----");
		
		if(customerBackUp != null && !"".equals(customerBackUp)){
			customerBackUp.setDelflag(0);
			if (customerBackUp.getCreditRating()==null || customerBackUp.getCreditRating().equals(""))
			{
				customerBackUp.setCreditRating("1");
			}
			this.getHibernateTemplate().update(customerBackUp);
		}else{
			System.out.println("----更新客户信息时，customer 为空----");
		}
	}

	
	/**
	 * 更新广告商运行期表中客户
	 */         
	public void updateCustomer(Customer customer) {
		logger.debug("----updateCustomer start-----");
		
		if(customer != null && !"".equals(customer)){
			this.getHibernateTemplate().update(customer);
		}else{
			System.out.println("----更新客户信息时，customer 为空----");
		}
	}
	
	/**
	 * 无过滤查询整个表单结果集
	 */
	public List listCustomerMgr(Customer customer) {
		List<Customer> list = null;
		String hql = "from Customer ";
		list = this.findByHQL(hql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getAllCustomer(Customer cust) {
		List<Customer> list = new ArrayList<Customer>();
		String hql = "from Customer where 1=1 ";
		if(cust != null){
			if(StringUtils.isNotBlank(cust.getAdvertisersName())){
				hql += " and advertisersName like '%"+cust.getAdvertisersName().trim()+"%'";
			}
			if(StringUtils.isNotBlank(cust.getClientCode())){
				hql += " and clientCode like '%"+cust.getClientCode().trim()+"%'";
			}
		}
		list = this.findByHQL(hql);
		return list;
	}


	@Override
	public List<CustomerBackUp> getAllCustomerBackUp() {
		List<CustomerBackUp> list = new ArrayList<CustomerBackUp>();
		String hql = "from CustomerBackUp where delflag =0 order by id desc";
		list = this.findByHQL(hql);
		return list;
	}




	@SuppressWarnings("unchecked")
	@Override
	public List<ContractBackup> getContractBackupByCustomerId(Integer id) {
        List<ContractBackup> list = new ArrayList<ContractBackup>();
	//	StringBuffer sb = new StringBuffer();
	//	sb.append("from ContractBackup where customerId=");
        String sql = "select t.* from T_CONTRACT_BACKUP t where delflag =0 and t.CUSTOMER_ID ="+id;
		//list = this.findByHQL(sb.toString());
		//list = this.getHibernateTemplate().find();
        list = this.getSession().createSQLQuery(sql).addEntity(ContractBackup.class).list();
		return list; 
	}
	/**
	 * 校验客户是否可删除
	 * 
	 */
	@Override
	public int checkDelCustomerBackUpById(Integer id)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		String hql = "select count(*) from ContractBackup t where 1=1 ";// and t.delflag=0  
		//过滤可操作的广告商查询
		if(id!= null &&  id!=0){
			hql +=" AND t.customerId="+id;
		}
		
		int totalCount=0;
		try 
		{
			totalCount= this.getTotalByHQL(hql, map);
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
		//return customerDao.checkDelCustomerBackUpById(id);
	}
}
