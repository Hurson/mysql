package com.dvnchina.advertDelivery.model;

import java.util.Date;

/**
 * 测试测试测试 订单用合同广告位
 */
public class ContractAD implements java.io.Serializable{
	/**
	 * 主键 --> id
	 */
	private Integer id;
	/**
	 * 合同id --> CONTRACT_ID
	 */
	private Integer contractId;
	/**
	 * 广告位id --> ad_id
	 */
	private Integer positionId;
	
	/**
	 * 投放开始日期 --> VALID_START
	 */
	private Date startDate;
	/**
	 * 投放截止日期 --> VALID_END
	 */
	private Date endDate;
	/**
	 * 营销规则id rule_id --> RULE_ID
	 */
	private Integer marketingRuleId;
	/**
	 * 合同代码 --> CONTRACT_CODE
	 */
	private String contractCode;
	/**
	 * 合同名称 --> CONTRACT_NAME
	 */
	private String contractName;
	/**
	 * 广告位名称 --> AD_NAME
	 */
	private String positionName;
	/**
	 * 广告位类型id --> AD_TYPE
	 */
	private Integer positionTypeId;
	/**
	 * 广告位类型名称
	 */
	private String positionTypeName;
	/**
	 * 合同所绑定广告位信息
	 */
	private AdvertPosition position;
	/**
	 * 营销规则name -> RULE_NAME
	 */
	private String marketingRuleName;
	/**
	 * 合同有效开始时间 date EFFECTIVE_START_DATE
	 */
	private Date effectiveStartDate;
	/**
	 * 合同有效结束时间 date EFFECTIVE_END_DATE
	 */
	private Date effectiveEndDate;
	
	/**
	 * 合同有效开始时间
	 */
	private String startDateStr;
	/**
	 * 合同有效结束时间
	 */
	private String endDateStr;
	
	/**
	 * 广告商id
	 */
	private Integer customerId;
	
	/**
	 * 规则的开始/结束时间
	 */
	private String startTime;
	
	public ContractAD(){}
	
	public ContractAD(Integer id,
	                  Integer contractId,
	                  Integer positionId,
	                  Date startDate,
	                  Date endDate){
	    this.id=id;
	    this.contractId=contractId;
	    this.positionId=positionId;
	    this.startDate=startDate;
	    this.endDate=endDate;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	private String endTime;
	/**
	 * 广告商名称
	 */
	private String customerName;
	
	private Integer ruleId;
	
	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	private String ruleName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Integer getPositionTypeId() {
		return positionTypeId;
	}

	public void setPositionTypeId(Integer positionTypeId) {
		this.positionTypeId = positionTypeId;
	}

	public String getPositionTypeName() {
		return positionTypeName;
	}

	public void setPositionTypeName(String positionTypeName) {
		this.positionTypeName = positionTypeName;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getMarketingRuleId() {
		return marketingRuleId;
	}

	public void setMarketingRuleId(Integer marketingRuleId) {
		this.marketingRuleId = marketingRuleId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public AdvertPosition getPosition() {
		return position;
	}

	public void setPosition(AdvertPosition position) {
		this.position = position;
	}

	public String getMarketingRuleName() {
		return marketingRuleName;
	}

	public void setMarketingRuleName(String marketingRuleName) {
		this.marketingRuleName = marketingRuleName;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

}
