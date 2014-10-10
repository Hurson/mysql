package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.CustomerBackUp;

public interface CustomerDao {
	
	
	/**
	 * 通过资源表中客户id得到运行客户表中的所属合同号 
	 * @return
	 */
	public List getRunClientCodeByCustomerId(Integer customerId);
	
	
	/**
	 * 通过资源表中客户id得到维护客户表中的所属合同号 
	 * @return
	 */
	public List getClientCodeByCustomerId(Integer customerId);
	
	
	/**
	 * 通过客户id，查看客户身上是否绑定了合同
	 * 
	 */
	public List<Contract> getContractByCustomerId(Integer id);
	/**
	 * 通过客户id，查看客户身上是否绑定了合同
	 * 
	 */
	public List<ContractBackup> getContractBackupByCustomerId(Integer id);
	
	
	/**
	 * 查询运行期表结果集
	 * @return
	 */
	public List listCustomerMgr(Customer customer,int x,int y);
	
	/**
	 * 查询维护期表结果集
	 * @return
	 */
	public List listCustomerBackUpMgr(CustomerBackUp customerBackup,int x,int y,String state,String customerIds);
	
	
	/**
	 * 查询维护期表中所有记录数
	 * @return
	 */
	public int getCustomerBackUpCount(CustomerBackUp customerBackUp,String state,String customerIds);
	
	/**
	 * 得到客户
	 * @return
	 */
	public CustomerBackUp getCustomerBackUpById(Integer id);
	
	/**
	 * 删除客户
	 * @return
	 */
	public void deleteCustomerBackUp(CustomerBackUp customerBackUp); 
	
	/**
	 * 添加广告商维护表中客户
	 * @return
	 */
	public void insertCustomerBackUp(CustomerBackUp customerBackUp);
	
	/**
	 * 添加广告商运行期表中的客户
	 * @return
	 */
	public void insertCustomer(Customer customer);
	
	/**
	 * 更新广告商维护表的客户
	 * @return
	 */
	public void updateCustomerBackUp(CustomerBackUp customerBackUp);
	
	
	/**
	 *更新广告商维运行期表中的客户信息 
	 */
	public void updateCustomer(Customer customer);
	
	/**
	 * 获取所有客户维护表信息
	 * @return
	 */
	public List<CustomerBackUp> getAllCustomerBackUp();
	
	/**
	 * 获取所有客户信息
	 * @return
	 */
	public List<Customer> getAllCustomer(Customer cust);
	
	/**
	 * 校验客户是否可删除
	 * 
	 */
	public int checkDelCustomerBackUpById(Integer id);
}
