package com.dvnchina.advertDelivery.bean.contract;

import java.io.Serializable;

public class ContractContractADRelation implements Serializable {

	private static final long serialVersionUID = -2127676815752928342L;
	/**
	 * 合同主键
	 */
	private Integer contractId;
	/**
	 * 关系表主键
	 */
	private Integer adId;
	/**
	 * 规则id
	 */
	private Integer ruleId;

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

}
