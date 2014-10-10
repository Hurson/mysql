package com.dvnchina.advertDelivery.service;

import java.util.List;

import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.CustomerBackUp;

public interface CustomerService {
	
	/**
	 * 通过资源表中客户id得到运行期客户表中的所属合同号 
	 * @return
	 */
	public List getRunClientCodeByCustomerId(Integer customerId);
	
	
	
	/**
	 * 通过资源表中客户id得到维护客户表中的所属合同号 
	 * @return
	 */
	public List getClientCodeByCustomerId(Integer customerId);
	
	
	/**
	 * 广告商信息没有审核通过
	 * 
	 */
	public int insertNoAuditCustomer(Integer id,String examinationOpintions);
	
	
	
	/**
	 * 广告商信息审核通过
	 * 
	 */
	public int insertAuditCustomer(Integer id,String examinationOpintions);
	
	
	
	/**
	 * 
	 * 通过客户id，得到合同记录
	 * 
	 */
	
	public List<Contract> getContractByCustomerId(Integer id);
	
	/**
	 * 
	 * 通过客户id，得到合同记录
	 * 
	 */
	
	public List<ContractBackup> getContractContractBackByCustomerId(Integer id);
	
	
	
	/**
	 * 查询运行期表的所有记录
	 * 
	 */
	public List listCustomerMgr(Customer customer,int x,int y);
	
	/**
	 * 查询客户维护表的所有记录
	 * 
	 */
	public List listCustomerBackUpMgr(CustomerBackUp customerBackUp,int x,int y,String state,String customerIds);
	
	
	
	//查询记录总的条数
    //public int getCustomerCount(Customer customer,String state);
	
	
	
	/**
	 * 得到分页总数
	 * 
	 */
	public int getCustomerBackUpCount(CustomerBackUp customerBackUp,String state,String customerIds);
	
	/**
	 * 删除客户
	 * 
	 */
	public int deleteCustomerBackUpById(Integer id);
	
	/**
	 * 校验客户是否可删除
	 * return 1不可删除 0 可删除
	 */
	public int checkDelCustomerBackUpById(Integer id);
	
	/**
	 * 添加客户
	 * 
	 */
	public int insertCustomerBackUp(CustomerBackUp customerBackUp);
	
	/**
	 *修改客户重定向
	 * 
	 * 	 
	 */
	public CustomerBackUp getCustomerBackUpById(Integer id);
	
	/**
	 * 修改客户
	 * 
	 */
	public int updateCustomerBackUp(CustomerBackUp customerBackUp);
	
	/**
	 * 获取所有客户维护表的信息
	 * @return
	 */
	public List<CustomerBackUp> getCustomerBackUpList();
	
	/**
	 * 获取所有的客户信息列表
	 * @return
	 */
	public List<Customer> getCustomerList(Customer cust);
	
}
