package com.dvnchina.advertDelivery.accounts.service;

import java.util.Date;
import java.util.Map;

import com.dvnchina.advertDelivery.accounts.bean.ContractAccounts;
import com.dvnchina.advertDelivery.bean.PageBeanDB;

public interface ContractAccountsService {

	public PageBeanDB queryContractAccountsList(int pageNo, int pageSize, Map<String, String> object, int contractId);

	public ContractAccounts getContractAccountsById(int lid);

	public boolean saveContractAccounts(ContractAccounts contractAccounts);

	public boolean updateContractAccounts(ContractAccounts contractAccounts);

	public Date getContractsDeadLine(int contractId);
	
	
	public PageBeanDB queryContract(String contractName,int pageSize, int pageNumber);

	/**
	 * @description: 首页代办7天内合同到期欠费的合同数
	 * @return 7天内合同到期欠费的合同数
	 */
	public int getExpireingContractCount(String positionIds, Date today, Date shiftDay);

}
