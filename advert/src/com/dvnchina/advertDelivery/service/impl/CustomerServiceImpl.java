package com.dvnchina.advertDelivery.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.constant.CustomerConstant;
import com.dvnchina.advertDelivery.dao.CustomerDao;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.CustomerBackUp;
import com.dvnchina.advertDelivery.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	
	private Logger logger = Logger.getLogger(CustomerServiceImpl.class);
	
	private CustomerDao customerDao;

	
	/**
	 * 通过资源表中客户id得到运行期客户表中的所属合同号
	 * @return
	 */

	@Override
	public List getRunClientCodeByCustomerId(Integer customerId) {
		logger.debug("------getRunClientCodeByCustomerId  start--------");
		return customerDao.getRunClientCodeByCustomerId(customerId);
	}
	
	
	
	/**
	 * 通过资源表中客户id得到客户表中的所属合同号 
	 * @return
	 */
	public List getClientCodeByCustomerId(Integer customerId){
		logger.debug("------getClientCodeByCustomerId  start--------");
		return customerDao.getClientCodeByCustomerId(customerId);
	}
	
	
	/**
	 * 广告商信息没有通过审核
	 * 
	 */
	public int insertNoAuditCustomer(Integer id, String examinationOpintions) {
		
		int count = 0;
		
		if(id != null && !"".equals(id)){
			CustomerBackUp customerBackUp = customerDao.getCustomerBackUpById(id);
			
			customerBackUp.setAuditDate(new Date());
			customerBackUp.setExaminationOpinions(examinationOpintions);
			customerBackUp.setStatus(CustomerConstant.CUSTOMER_AUDIT_NO_PASS);
			
			customerDao.updateCustomerBackUp(customerBackUp);
			
			count = 1;
		}
		
		return count;
	}
	
	
	
	
	/**
	 * 广告商信息通过审核
	 * 
	 */
	public int insertAuditCustomer(Integer id, String examinationOpintions) {
		logger.debug("-----insertAuditCustomer()  start-----");
		int count = 0;
		if(id != null && !"".equals(id)){
			CustomerBackUp customerBackUp = customerDao.getCustomerBackUpById(id);
			
			Customer customer = new Customer();
			customer.setId(id);
			customer.setAdvertisersName(customerBackUp.getAdvertisersName());
			customer.setClientCode(customerBackUp.getClientCode());
			customer.setRemark(customerBackUp.getRemark());
			customer.setConpanyAddress(customerBackUp.getConpanyAddress());
			customer.setConpanySheet(customerBackUp.getConpanySheet());
			customer.setCommunicator(customerBackUp.getCommunicator());
			customer.setTel(customerBackUp.getTel());
			customer.setMobileTel(customerBackUp.getMobileTel());
			customer.setFax(customerBackUp.getTel());
			customer.setContacts(customerBackUp.getContacts());
			customer.setCooperationTime(customerBackUp.getCooperationTime());
			customer.setCreditRating(customerBackUp.getCreditRating());
			customer.setContract(customerBackUp.getContract());
			customer.setBusinessLicence(customerBackUp.getBusinessLicence());
			//甚至状态为审核通过
			customer.setStatus(CustomerConstant.CUSTOMER_AUDIT_PASS);
			customer.setCreateTime(new Date());
			customer.setOperator(customerBackUp.getOperator());
			
			customer.setCustomerLevel(customerBackUp.getCustomerLevel());
			customer.setBusinessArea(customerBackUp.getBusinessArea());
			customer.setRegisterFinancing(customerBackUp.getRegisterFinancing());
			customer.setRegisterAddress(customerBackUp.getRegisterAddress());
			customer.setOperator(customerBackUp.getOperator());
			
			
			//设置广告商维护表的状态为 审核通过
			customerBackUp.setStatus(CustomerConstant.CUSTOMER_AUDIT_PASS);
			customerBackUp.setExaminationOpinions(examinationOpintions);
			customerBackUp.setAuditDate(new Date());
			//审核通过，保存信息到广告商运行期表
			customerDao.insertCustomer(customer);
			//审核通过，更新广告商维护表中的数据
			customerDao.updateCustomerBackUp(customerBackUp);
			count = 1;
		}
		return count;
	}
	
	/**
	 * 通过客户id 查询是绑定了合同
	 * 
	 */
	public List<Contract> getContractByCustomerId(Integer id){
		return customerDao.getContractByCustomerId(id);
	}
	
	/**
	 * 通过客户id 查询是绑定了合同
	 * 
	 */
	public List<ContractBackup> getContractBackupByCustomerId(Integer id){
		return customerDao.getContractBackupByCustomerId(id);
	}
	
	
	/**
	 * 删除客户记录
	 */
	public int deleteCustomerBackUpById(Integer id) {
		int count = 0;
		if(id != null && !"".equals(id)){
			CustomerBackUp customerBackUp = customerDao.getCustomerBackUpById(id);
			customerDao.deleteCustomerBackUp(customerBackUp);
			count = 1;
		}else{
			System.out.println("---ImageMeta---的id 为空----");
		}
		return count;
	}
	
	/**
	 * 查询客户运行期表的所有结果集 
	 */
	public List listCustomerMgr(Customer customer, int x, int y) {
		return customerDao.listCustomerMgr(customer, x, y);
	}
	
	/**
	 * 查询客户维护表的所有结果集 
	 */
	public List listCustomerBackUpMgr(CustomerBackUp customerBackUp, int x, int y,String state,String customerIds) {
		return customerDao.listCustomerBackUpMgr(customerBackUp, x, y,state,customerIds);
	}
	
	/**
	 * 得到运行期表总的记录数
	 */
	public int getCustomerBackUpCount(CustomerBackUp customerBackUp,String state,String customerIds) {
		return customerDao.getCustomerBackUpCount(customerBackUp,state,customerIds);
	}
	
	/**
	 * 添加用户
	 */
	public int insertCustomerBackUp(CustomerBackUp customerBackUp) {
		
		logger.debug("-----saveCustomer  start----");
		int count = 0;
		if(customerBackUp != null && !"".equals(customerBackUp)){
			customerDao.insertCustomerBackUp(customerBackUp);
			count = 1;
		}else{
			logger.debug("----添加用户时，customer为空----");
		}
		return count;
	}
	
	//通过id 的到客户的信息
	public CustomerBackUp getCustomerBackUpById(Integer id) {
		
		logger.debug("-----getCustomerBackUpById  start-----");
		CustomerBackUp customerBackUp = null;
		if(id != null && !"".equals(id)){
			customerBackUp = customerDao.getCustomerBackUpById(id);
		}
		return customerBackUp;
	}


	//修改客户
	public int updateCustomerBackUp(CustomerBackUp customerBackUp) {
		logger.debug("-----updateCustomer  start-----");
		int count = 0;
		if(customerBackUp != null && !"".equals(customerBackUp)){
			customerDao.updateCustomerBackUp(customerBackUp);
			count = 1;
		}
		
		return count;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	@Override
	public List<Customer> getCustomerList(Customer cust) {
		return customerDao.getAllCustomer(cust);
	}

	@Override
	public List<CustomerBackUp> getCustomerBackUpList() {
		return customerDao.getAllCustomerBackUp();
	}



	@Override
	public List<ContractBackup> getContractContractBackByCustomerId(Integer id) {
		return customerDao.getContractBackupByCustomerId(id);
	}
	/**
	 * 校验客户是否可删除
	 * 
	 */
	@Override
	public int checkDelCustomerBackUpById(Integer id)
	{
		return customerDao.checkDelCustomerBackUpById(id);
	}
}
