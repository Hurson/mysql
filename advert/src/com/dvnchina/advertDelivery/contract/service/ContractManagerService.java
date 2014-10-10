package com.dvnchina.advertDelivery.contract.service;

import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.accounts.bean.ContractAccounts;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.contract.bean.AdvertPositionPackage;
import com.dvnchina.advertDelivery.contract.bean.PositionAD;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.MarketingRule;

public interface ContractManagerService {
    
    /**
     * 
     * @description: 删除合同信息
     * @param id
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 下午03:28:30
     */
    public boolean deleteContractByIds(String id) ;
    
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
     * 查询合同列表
     * @param contract
     * @param pageSize
     * @param pageNumber
     * @return
     */
    public PageBeanDB queryContractList(ContractQueryBean contract,Integer pageSize,Integer pageNumber);
    
    /**
     * 查询所有广告商列表
     * @param pageSize
     * @param pageNumber
     * @return
     */
    public PageBeanDB queryCustomerList(Integer pageSize, Integer pageNumber);
    
    /**
     * 
     * @description: 查询广告位包列表
     * @param advertPositionPackageQuery
     * @param pageSize
     * @param pageNumber
     * @return 
     * PageBeanDB
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-4-28 上午11:03:16
     */
    public PageBeanDB queryAdPositionPackageList(AdvertPositionPackage advertPositionPackageQuery, Integer pageSize, Integer pageNumber);
    
    /**
     * 
     * @description: 根据广告位ID获取营销规则
     * @param id
     * @return 
     * MarketingRule
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-8 下午07:15:30
     */
    public MarketingRule getMarketingRuleByPositionId(int id);
    
    /**
     * 
     * @description: 查询客户列表
     * @param customerQuery
     * @param pageSize
     * @param pageNumber
     * @return 
     * PageBeanDB
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-8 上午10:45:36
     */
    public PageBeanDB queryCustomerRealList(Customer customerQuery, String customerids,Integer pageSize, Integer pageNumber);
    
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
    public AdvertPositionPackage getAdPositionPackageByID(int id);
    
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
    public ContractBackup getContractByCode(String code);
    
    /**
     * 
     * @description: 保存合同临时表
     * @param contractBackup
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午09:41:37
     */
    public boolean saveConstractBackup(ContractBackup contractBackup);
    
    /**
     * 
     * @description: 保存合同正式表
     * @param contractBackup
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午09:41:37
     */
    public boolean saveConstract(Contract contract);
    
    /**
     * 
     * @description: 批量保存合同广告位表
     * @param contractADList
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午10:16:28
     */
    public boolean saveConstractADBackupList(List<ContractADBackup> contractADList);
    
    /**
     * 
     * @description: 批量保存合同广告位正式表
     * @param contractADList
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午10:16:28
     */
    public boolean saveConstractADList(List<ContractAD> contractADList,Integer contractId);
    
    /**
     * 
     * @description: 批量保存合同广告位表
     * @param contractADList
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午10:16:28
     */
    public boolean updateConstractADBackupList(List<ContractADBackup> contractADList,Integer contractId);
    
//    public int getMaxIdForContractAD();
//    
//    public int getMaxIdForContractADForReal();
    
    /**
     * 
     * @description: 获取合同信息
     * @param id
     * @return 
     * ContractBackup
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午11:25:11
     */
    public ContractBackup getContractByID(int id);
    
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
    public List<PositionAD> getPositionADByContractID(int contractId);
    
    public List<ContractADBackup> getContractADByContractID(int contractId);
    
    /**
     * 
     * @description: 保存合同临时表
     * @param contractBackup
     * @return 
     * boolean
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-6 上午09:41:37
     */
    public boolean updateConstractBackup(ContractBackup contractBackup);
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
	 * 根据ID删除合同广告位正式表数据
	 * @param contractId
	 * @return
	 */
	public boolean deleteContractADReal(Integer contractId);

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
