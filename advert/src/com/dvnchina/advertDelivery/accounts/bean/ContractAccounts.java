package com.dvnchina.advertDelivery.accounts.bean;

import java.util.Date;

/**
 * TContractAccounts entity. @author MyEclipse Persistence Tools
 */

public class ContractAccounts implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer accountsId;
	private String accountsCode;
	private Integer contractId;
	private String contractName;
	private Float moneyAmount;
	private Date payDay;
	private Integer paySort;
	private Date payVallidityPeriodBegin;
	private Date payVallidityPeriodEnd;

	// Constructors

	/** default constructor */
	public ContractAccounts() {
	}

	/** minimal constructor */
	public ContractAccounts(Integer accountsId) {
		this.accountsId = accountsId;
	}

	/** full constructor */
	public ContractAccounts(Integer accountsId, String accountsCode, Integer contractId,
			String contractName, Float moneyAmount, Date payDay, Integer paySort,
			Date payVallidityPeriodBegin, Date payVallidityPeriodEnd) {
		this.accountsId = accountsId;
		this.contractId = contractId;
		this.contractName = contractName;
		this.moneyAmount = moneyAmount;
		this.payDay = payDay;
		this.paySort = paySort;
		this.payVallidityPeriodBegin = payVallidityPeriodBegin;
		this.payVallidityPeriodEnd = payVallidityPeriodEnd;
	}

	// Property accessors
	public Integer getAccountsId() {
		return this.accountsId;
	}

	public void setAccountsId(Integer accountsId) {
		this.accountsId = accountsId;
	}

	public String getAccountsCode() {
		return accountsCode;
	}

	public void setAccountsCode(String accountsCode) {
		this.accountsCode = accountsCode;
	}
	
	public Integer getContractId() {
		return this.contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Float getMoneyAmount() {
		return this.moneyAmount;
	}

	public void setMoneyAmount(Float moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	public Date getPayDay() {
		return this.payDay;
	}

	public void setPayDay(Date payDay) {
		this.payDay = payDay;
	}

	public Integer getPaySort() {
		return this.paySort;
	}

	public void setPaySort(Integer paySort) {
		this.paySort = paySort;
	}

	public Date getPayVallidityPeriodBegin() {
		return this.payVallidityPeriodBegin;
	}

	public void setPayVallidityPeriodBegin(Date payVallidityPeriodBegin) {
		this.payVallidityPeriodBegin = payVallidityPeriodBegin;
	}

	public Date getPayVallidityPeriodEnd() {
		return this.payVallidityPeriodEnd;
	}

	public void setPayVallidityPeriodEnd(Date payVallidityPeriodEnd) {
		this.payVallidityPeriodEnd = payVallidityPeriodEnd;
	}

}