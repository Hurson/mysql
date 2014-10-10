package com.dvnchina.advertDelivery.accounts.dao;

import java.util.Date;
import java.util.Map;

import com.dvnchina.advertDelivery.accounts.bean.ContractAccounts;
import com.dvnchina.advertDelivery.bean.PageBeanDB;

public interface ContractAccountsDao {

	PageBeanDB queryContractAccountsList(int pageNo, int pageSize, Map<String, String> object, int contractId);

	ContractAccounts getContractAccountsById(int lid);

	void saveContractAccounts(ContractAccounts contractAccounts);

	void updateContractAccounts(ContractAccounts contractAccounts);

	ContractAccounts getContractsDeadLine(int contractId);

	PageBeanDB queryContract(String contractName, int pageSize,int pageNumber);
	
	/**
	 * @description: 首页代办7天内合同到期欠费的合同数
	 * @return 7天内合同到期欠费的合同数
	 */
	public int getExpireingContractCount(String positionIds, Date today, Date shiftDay);

}
