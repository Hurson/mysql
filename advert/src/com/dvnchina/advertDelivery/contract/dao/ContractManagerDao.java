package com.dvnchina.advertDelivery.contract.dao;

import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.accounts.bean.ContractAccounts;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.contract.bean.AdvertPositionPackage;
import com.dvnchina.advertDelivery.contract.bean.PositionAD;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.model.Ploy;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.ploy.bean.PloyAreaChannel;
import com.dvnchina.advertDelivery.utils.page.PageBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface ContractManagerDao.
 */
public interface ContractManagerDao {
    
    /**
     * Delete contract ad.
     *
     * @param contractId the contract id
     * @return true, if successful
     * @description: 根据合同ID删除合同广告位表
     * boolean
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-7 上午11:04:00
     */
    public boolean deleteContractAD(Integer contractId);
    
    /**
	 * 根据ID删除合同广告位正式表数据
	 * @param contractId
	 * @return
	 */
	public boolean deleteContractADReal(Integer contractId);
	
	/**
	 * 查询合同列表.
	 *
	 * @param contract the contract
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the page bean db
	 */
	public PageBeanDB queryContractList(ContractQueryBean contract,Integer pageSize,Integer pageNumber);
	
	/**
	 * 查询所有广告商列表.
	 *
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the page bean db
	 */
	public PageBeanDB queryCustomerList(Integer pageSize, Integer pageNumber);
	
	/**
	 * Query ad position package list.
	 *
	 * @param advertPositionPackageQuery the advert position package query
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the page bean db
	 * @description: 查询广告位列表
	 * PageBeanDB
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-4-28 上午11:06:56
	 */
    public PageBeanDB queryAdPositionPackageList(AdvertPositionPackage advertPositionPackageQuery,Integer pageSize,Integer pageNumber);
	
    
    /**
     * Query customer real list.
     *
     * @param customerQuery the customer query
     * @param customerids the customerids
     * @param pageSize the page size
     * @param pageNumber the page number
     * @return the page bean db
     * @description: 查询客户列表
     * PageBeanDB
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-8 上午10:51:08
     */
    public PageBeanDB queryCustomerRealList(Customer customerQuery,String customerids,Integer pageSize,Integer pageNumber);
    
    /**
     * Gets the ad position package by id.
     *
     * @param id the id
     * @return the ad position package by id
     * @description: 根据ID获取广告位包
     * AdvertPositionPackage
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 下午02:52:19
     */
    public AdvertPositionPackage getAdPositionPackageByID(int id);
    
    /**
     * Gets the marketing rule by position id.
     *
     * @param id the id
     * @return the marketing rule by position id
     * @description: 根据广告位ID获取营销规则
     * MarketingRule
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-8 下午07:17:41
     */
    public MarketingRule getMarketingRuleByPositionId(int id);
    
    /**
     * Gets the max id.
     *
     * @return the max id
     */
    public int getMaxId();
    
    /**
     * Gets the max id for real.
     *
     * @return the max id for real
     */
    public int getMaxIdForReal();
    
    /**
     * Gets the max id for contract ad.
     *
     * @return the max id for contract ad
     */
    public int getMaxIdForContractAD();
    
    /**
     * Gets the max id for contract ad for real.
     *
     * @return the max id for contract ad for real
     */
    public int getMaxIdForContractADForReal();
    
    /**
     * Save constract backup.
     *
     * @param contractBackup the contract backup
     * @return true, if successful
     * @description: 保存合同临时表
     * boolean
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午09:44:04
     */
    public boolean saveConstractBackup(ContractBackup contractBackup);
    
    /**
     * Save constract.
     *
     * @param contract the contract
     * @return true, if successful
     * @description: 保存合同正式表
     * boolean
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午09:44:04
     */
    public boolean saveConstract(Contract contract);
    
    /**
     * Update constract backup.
     *
     * @param contractBackup the contract backup
     * @return true, if successful
     * @description: 修改合同临时表
     * boolean
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 下午06:04:50
     */
    public boolean updateConstractBackup(ContractBackup contractBackup);
    
    /**
     * Save constract ad backup list.
     *
     * @param contractADList the contract ad list
     * @return true, if successful
     * @description: 批量保存合同广告位表
     * boolean
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午10:17:47
     */
    public boolean saveConstractADBackupList(List<ContractADBackup> contractADList);
    
    /**
     * Save constract ad list.
     *
     * @param contractADList the contract ad list
     * @return true, if successful
     * @description: 批量保存合同广告位正式表
     * boolean
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午10:17:47
     */
    public boolean saveConstractADList(List<ContractAD> contractADList);
    
    /**
     * Gets the contract by id.
     *
     * @param id the id
     * @return the contract by id
     * @description: 根据ID获取合同信息
     * ContractBackup
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午11:29:00
     */
    public ContractBackup getContractByID(int id);
    
    /**
     * Gets the contract by code.
     *
     * @param code the code
     * @return the contract by code
     * @description: 根据编码查询合同信息
     * ContractBackup
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 下午05:31:12
     */
    public ContractBackup getContractByCode(String code);
    
    /**
     * Gets the position ad by contract id.
     *
     * @param contractId the contract id
     * @return the position ad by contract id
     * @description: 根据合同ID获取合同广告位信息
     * List<PositionAD>
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午11:46:42
     */
    public List<PositionAD> getPositionADByContractID(int contractId);
    
    /**
     * Gets the contract ad by contract id.
     *
     * @param contractId the contract id
     * @return the contract ad by contract id
     */
    public List<ContractADBackup> getContractADByContractID(int contractId);
    
    /**
     * Delete contract by ids.
     *
     * @param ids the ids
     * @return true, if successful
     * @description: 删除合同信息
     * boolean
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 下午03:29:43
     */
    public boolean deleteContractByIds(String ids);
    
    /**
	 * 校验合同是否可删除
	 * return 1不可删除 0 可删除
	 */
	public int checkDelContractById(Integer id);
	
	/**根据合同号效验合同是否存在
	 * 
	 * @param id
	 * @return
	 */
	public int checkContractExist(String contractCode);
    
    /**
     * Checks if is 校验合同是否已审核过.
     *
     * @param contractId the contract id
     * @return true, if is audit
     */
    public boolean isAudit(int contractId);
    
    /**
     * 校验广告位是否已销售占用.
     *
     * @param contractId the contract id
     * @param positionId the position id
     * @param startDate the start date
     * @param endDate the end date
     * @return true, if successful
     */
    public boolean checkPostitionUsed(int contractId,int positionId,Date startDate,Date endDate);

    /**
	 * 根据合同ID获取合同总计付款金额
	 * @param contractId
	 * @return 总计付款金额
	 */
	public float getAccountsAmount(int contractId);
	
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
    public List<ContractAccounts> getContractAccountsList(Integer contractId);
}
