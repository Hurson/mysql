package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

/**
 * 广告位占用状态信息
 * @author lester
 *
 */
public class PositionOccupyStatesInfo implements Serializable {

	private static final long serialVersionUID = -7764782172296798976L;
	/**
	 * 合同-广告位运行期表(t_contract_ad) 主键
	 */
	private Integer contractAdId;
	/**
	 * 合同-广告位运行期表(t_contract_ad)中外键contract_id，指向合同表(t_contract)中的主键
	 */
	private Integer contractId;
	/**
	 * 合同-广告位运行期表(t_contract_ad)中广告位id ad_id
	 */
	private Integer contractAdPositionId;
	/**
	 * 合同-广告位运行期表(t_contract_ad)中 投放开始日期 valid_start
	 */
	private String validStartDate;
	/**
	 * 合同-广告位运行期表(t_contract_ad)中 投放结束日期 valid_end
	 */
	private String validEndDate;
	/**
	 * 合同-广告位运行期表(t_contract_ad)中 营销规则ID rule_id
	 */
	private Integer contractAdRuleId;
	/**
	 * 合同表(t_contract)中 广告商id custom_id
	 */
	private Integer customerId;
	/**
	 * 合同表(t_contract)中 合同名称 contract_name 
	 */
	private String contractName;
	/**
	 * 合同表(t_contract)中合同有效期的开始时间 Effective_Start_Date
	 */
	private String effectiveStartDate;
	/**
	 * 合同表(t_contract)中合同有效期的结束时间 Effective_End_Date
	 */
	private String effectiveEndDate;
	/**
	 * 待审核0，审核通过 1 ，审核不通过 2,下线3
	 * 合同表(t_contract)中 合同状态 status
	 */
	private Integer contractStatus;
	/**
	 * 合同表(t_contract)中 合同编号 contract_number
	 */
	private String contractNumber;
	/**
	 * 合同表(t_contract)中 合同代码 contract_code
	 */
	private String contractCode;
	/**
	 * 广告位占用类型 1 已销售 2 待销售 3 其他[表单收集时使用]
	 */
	private Integer positionOccupyStatesType;
	
	/**
	 * 营销规则表(t_marketing_rule)中的 开始时间段 start_time
	 */
	private String startTime;
	/**
	 * 营销规则表(t_marketing_rule)中的结束时间段 end_time
	 */
	private String endTime;
	/**
	 * 营销规则表(t_marketing_rule)中 营销规则状态 state
	 */
	private Integer marketingRuleStates;
	/**
	 * 营销规则表中(t_marketing_rule)的 投放区域id location_id
	 */
	private Integer releaseAreaId;
	/**
	 * 营销规则表(t_marketing_rule)中 频道ID channel_id
	 */
	private Integer channelId;
	/**
	 * 营销规则表中(t_contract_ad) 主键 id
	 */
	private Integer ruleIdP;
	/**
	 * 营销规则表(t_contract_ad)中 营销ID rule_id
	 */
	private Integer ruleId;
	/**
	 * 营销规则表(t_contract_ad)中 广告位id ad_id
	 */
	private Integer positionId;
	/**
	 * 广告位表(t_advertposition)中 position_name
	 */
	private String positionName;
	/**
	 * 营销规则表(t_contract_ad)中 的营销规则名称
	 */
	private String ruleName;
	
	/**
	 * 投放区域信息表(t_release_area)中 投放区域编码 area_code
	 */
	private String releaseAreaCode;
	/**
	 * 投放区域信息表(t_release_area)中 投放区域名称 area_name
	 */
	private String areaName;
	/**
	 * 投放区域信息表(t_release_area)中 父级区域编码 parent_code
	 */
	private String parentCode;
	/**
	 * 广告商运行期表t_customer中 广告商名称 ADVERTISERS_NAME
	 */
	private String advertisersName;
	
	public Integer getChannelId() {
		return channelId;
	}


	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}


	public Integer getPositionId() {
		return positionId;
	}


	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}


	public Integer getRuleIdP() {
		return ruleIdP;
	}


	public void setRuleIdP(Integer ruleIdP) {
		this.ruleIdP = ruleIdP;
	}


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


	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public String getContractNumber() {
		return contractNumber;
	}


	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}


	public Integer getPositionOccupyStatesType() {
		return positionOccupyStatesType;
	}


	public void setPositionOccupyStatesType(Integer positionOccupyStatesType) {
		this.positionOccupyStatesType = positionOccupyStatesType;
	}


	public Integer getMarketingRuleStates() {
		return marketingRuleStates;
	}


	public void setMarketingRuleStates(Integer marketingRuleStates) {
		this.marketingRuleStates = marketingRuleStates;
	}


	public Integer getReleaseAreaId() {
		return releaseAreaId;
	}


	public void setReleaseAreaId(Integer releaseAreaId) {
		this.releaseAreaId = releaseAreaId;
	}


	public String getReleaseAreaCode() {
		return releaseAreaCode;
	}


	public void setReleaseAreaCode(String releaseAreaCode) {
		this.releaseAreaCode = releaseAreaCode;
	}


	public String getAreaName() {
		return areaName;
	}


	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


	public String getParentCode() {
		return parentCode;
	}


	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}


	public Integer getContractAdId() {
		return contractAdId;
	}


	public void setContractAdId(Integer contractAdId) {
		this.contractAdId = contractAdId;
	}


	public Integer getContractId() {
		return contractId;
	}


	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}


	public Integer getContractAdPositionId() {
		return contractAdPositionId;
	}


	public void setContractAdPositionId(Integer contractAdPositionId) {
		this.contractAdPositionId = contractAdPositionId;
	}


	public String getValidStartDate() {
		return validStartDate;
	}


	public void setValidStartDate(String validStartDate) {
		this.validStartDate = validStartDate;
	}


	public String getValidEndDate() {
		return validEndDate;
	}


	public void setValidEndDate(String validEndDate) {
		this.validEndDate = validEndDate;
	}


	public Integer getContractAdRuleId() {
		return contractAdRuleId;
	}


	public void setContractAdRuleId(Integer contractAdRuleId) {
		this.contractAdRuleId = contractAdRuleId;
	}


	public String getContractName() {
		return contractName;
	}


	public void setContractName(String contractName) {
		this.contractName = contractName;
	}


	public String getEffectiveStartDate() {
		return effectiveStartDate;
	}


	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}


	public String getEffectiveEndDate() {
		return effectiveEndDate;
	}


	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}


	public Integer getContractStatus() {
		return contractStatus;
	}


	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}


	public String getContractCode() {
		return contractCode;
	}


	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}


	public String getAdvertisersName() {
		return advertisersName;
	}


	public void setAdvertisersName(String advertisersName) {
		this.advertisersName = advertisersName;
	}


	public String getPositionName() {
		return positionName;
	}


	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

}
