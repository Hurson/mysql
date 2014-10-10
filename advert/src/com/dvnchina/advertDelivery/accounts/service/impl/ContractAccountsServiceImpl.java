package com.dvnchina.advertDelivery.accounts.service.impl;

import java.util.Date;
import java.util.Map;

import com.dvnchina.advertDelivery.accounts.bean.ContractAccounts;
import com.dvnchina.advertDelivery.accounts.dao.ContractAccountsDao;
import com.dvnchina.advertDelivery.accounts.service.ContractAccountsService;
import com.dvnchina.advertDelivery.bean.PageBeanDB;

public class ContractAccountsServiceImpl implements ContractAccountsService {

	private ContractAccountsDao contractAccountsDao;
	

	@Override
	public PageBeanDB queryContractAccountsList(int pageNo, int pageSize,
			Map<String, String> object, int contractId) {
		PageBeanDB page = contractAccountsDao.queryContractAccountsList(pageNo, pageSize, object, contractId);
		return page;
	}

	@Override
	public ContractAccounts getContractAccountsById(int lid) {
		ContractAccounts accounts = contractAccountsDao.getContractAccountsById(lid);
		return accounts;
	}

	@SuppressWarnings("finally")
	@Override
	public boolean saveContractAccounts(ContractAccounts contractAccounts) {
		boolean success = false;
		try {
			contractAccountsDao.saveContractAccounts(contractAccounts);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		} finally {
			return success;
		}
		
		
	}

	@SuppressWarnings("finally")
	@Override
	public boolean updateContractAccounts(ContractAccounts contractAccounts) {
		boolean success = false;
		try {
			contractAccountsDao.updateContractAccounts(contractAccounts);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		} finally {
			return success;
		}
	}
	
	@Override
	public Date getContractsDeadLine(int contractId) {
		Date deadLine = null;
		try {
			ContractAccounts contractAccounts = contractAccountsDao.getContractsDeadLine(contractId);
			if(contractAccounts == null){
				return null;
			}
			deadLine = contractAccounts.getPayVallidityPeriodEnd();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return deadLine;
	}
	
	@Override
	public PageBeanDB queryContract( String contractName,int pageNumber,int pageSize) {
		// TODO Auto-generated method stub
		return contractAccountsDao.queryContract( contractName,  pageNumber, pageSize);
	}
	
	public ContractAccountsDao getContractAccountsDao() {
		return contractAccountsDao;
	}

	public void setContractAccountsDao(ContractAccountsDao contractAccountsDao) {
		this.contractAccountsDao = contractAccountsDao;
	}

	/**
	 * @description: 首页代办7天内合同到期欠费的合同数
	 * @return 7天内合同到期欠费的合同数
	 */
	public int getExpireingContractCount(String contractIds, Date today, Date shiftDay){
		return contractAccountsDao.getExpireingContractCount(contractIds, today, shiftDay);
	}

}
