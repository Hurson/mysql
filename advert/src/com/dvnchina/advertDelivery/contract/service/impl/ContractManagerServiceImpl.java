package com.dvnchina.advertDelivery.contract.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.accounts.bean.ContractAccounts;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.contract.bean.AdvertPositionPackage;
import com.dvnchina.advertDelivery.contract.bean.PositionAD;
import com.dvnchina.advertDelivery.contract.dao.ContractManagerDao;
import com.dvnchina.advertDelivery.contract.service.ContractManagerService;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.MarketingRule;

/**
 * 合同维护相关操作
 * @author lester
 *
 */
public class ContractManagerServiceImpl implements ContractManagerService{
	private ContractManagerDao contractManagerDao;
	
	
	public ContractManagerDao getContractManagerDao() {
		return contractManagerDao;
	}


	public void setContractManagerDao(ContractManagerDao contractManagerDao) {
		this.contractManagerDao = contractManagerDao;
	}
	
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
	public boolean deleteContractByIds(String id) {
        boolean flag = contractManagerDao.deleteContractByIds("("+id+")");
        return flag;
    }
	
	/**
	 * 校验合同是否可删除
	 * return 1不可删除 0 可删除
	 */
	public int checkDelContractById(Integer id)
	{
		return contractManagerDao.checkDelContractById(id);
	}
	
	/**根据合同号效验合同是否存在
	 * 
	 * @param id
	 * @return
	 */
	public int checkContractExist(String contractCode){
		return contractManagerDao.checkContractExist(contractCode);
	}

    /**
     * 查询合同列表
     * @param contract
     * @param pageSize
     * @param pageNumber
     * @return
     */
	public PageBeanDB queryContractList(ContractQueryBean contract,Integer pageSize,Integer pageNumber) {
		return contractManagerDao.queryContractList(contract,pageSize, pageNumber);
	}
	
	/**
	 * 查询所有广告商列表
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB queryCustomerList(Integer pageSize, Integer pageNumber){
		return contractManagerDao.queryCustomerList(pageSize, pageNumber);
	}
	
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
    public PageBeanDB queryAdPositionPackageList(AdvertPositionPackage advertPositionPackageQuery, Integer pageSize, Integer pageNumber){
        return contractManagerDao.queryAdPositionPackageList(advertPositionPackageQuery,pageSize, pageNumber);
    }
    
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
    public PageBeanDB queryCustomerRealList(Customer customerQuery,String customerids, Integer pageSize, Integer pageNumber){
        return contractManagerDao.queryCustomerRealList(customerQuery,customerids,pageSize, pageNumber);
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
    public AdvertPositionPackage getAdPositionPackageByID(int id){
        return contractManagerDao.getAdPositionPackageByID(id);
    }
    
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
    public MarketingRule getMarketingRuleByPositionId(int id){
        return contractManagerDao.getMarketingRuleByPositionId(id);
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
    public ContractBackup getContractByCode(String code){
        return contractManagerDao.getContractByCode(code);
    }
    
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
    public boolean saveConstractBackup(ContractBackup contractBackup) {
        boolean flag = false;
        try {
//            int maxId = contractManagerDao.getMaxId();
//            contractBackup.setId(maxId+1);
            flag = contractManagerDao.saveConstractBackup(contractBackup);
            
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
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
    public boolean saveConstract(Contract contract) {
        boolean flag = false;
        try {
            flag = contractManagerDao.saveConstract(contract);
            
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
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
    public boolean updateConstractBackup(ContractBackup contractBackup) {
        boolean flag = false;
        try {
            flag = contractManagerDao.updateConstractBackup(contractBackup);
            
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
//    public int getMaxIdForContractAD(){
//        return contractManagerDao.getMaxIdForContractAD();
//    }
//    
//    public int getMaxIdForContractADForReal(){
//        return contractManagerDao.getMaxIdForContractADForReal();
//    }
    
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
    public boolean saveConstractADBackupList(List<ContractADBackup> contractADList) {
        boolean flag = false;
        try {
            flag = contractManagerDao.saveConstractADBackupList(contractADList);           
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
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
    public boolean saveConstractADList(List<ContractAD> contractADList,Integer contractId) {
    	//contractManagerDao.deleteContractADReal(contractId);
        boolean flag = false;
        try {
            flag = contractManagerDao.saveConstractADList(contractADList);           
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
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
    public boolean updateConstractADBackupList(List<ContractADBackup> contractADList,Integer contractId) {
        
        contractManagerDao.deleteContractAD(contractId);
        boolean flag = false;
        try {
            flag = contractManagerDao.saveConstractADBackupList(contractADList);           
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
    /**
	 * 根据ID删除合同广告位正式表数据
	 * @param contractId
	 * @return
	 */
	public boolean deleteContractADReal(Integer contractId){
		return contractManagerDao.deleteContractADReal(contractId);
	}
    
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
    public ContractBackup getContractByID(int id) {
        ContractBackup contractBackup = contractManagerDao.getContractByID(id);
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
    public List<PositionAD> getPositionADByContractID(int contractId){
        return contractManagerDao.getPositionADByContractID(contractId);
    }
    
    public List<ContractADBackup> getContractADByContractID(int contractId){
        return contractManagerDao.getContractADByContractID(contractId);
    }
    /**
     * Checks if is 校验合同是否已审核过.
     *
     * @param contractId the contract id
     * @return true, if is audit
     */
    public boolean isAudit(int contractId)
    {
    	return contractManagerDao.isAudit(contractId);
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
    	return contractManagerDao.checkPostitionUsed(contractId, positionId, startDate, endDate);
    }


    /**
	 * 根据合同ID获取合同总计付款金额
	 * @param contractId
	 * @return 总计付款金额
	 */
	public float getAccountsAmount(int contractId) {
		return contractManagerDao.getAccountsAmount(contractId);
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
    public List<ContractAccounts> getContractAccountsList(Integer contractId){
        return contractManagerDao.getContractAccountsList(contractId);
    }
	
}
