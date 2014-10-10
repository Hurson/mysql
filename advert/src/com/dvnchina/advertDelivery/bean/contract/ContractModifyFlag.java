package com.dvnchina.advertDelivery.bean.contract;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 用于修改用户修改标记
 * @author lester
 *
 */
public class ContractModifyFlag implements Serializable{
	
	private static final long serialVersionUID = 5232362487489235363L;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String id;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String contractNumber;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String contractCode;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String customerId;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String customerName;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String contractName;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String submitUnits;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String financialInformation;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String approvalCode;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String metarialPath;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String effectiveStartDate;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String effectiveEndDate;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String approvalStartDate;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String approvalEndDate;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String effectiveStartDateShow;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String effectiveEndDateShow;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String approvalStartDateShow;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String approvalEndDateShow;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String otherContent;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String status;
	/**
	 * 默认状态为 false 未修改 true 修改
	 */
	private String contractDesc;
	
	private List<PositionModifyFlag> bindingPosition ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getSubmitUnits() {
		return submitUnits;
	}

	public void setSubmitUnits(String submitUnits) {
		this.submitUnits = submitUnits;
	}

	public String getFinancialInformation() {
		return financialInformation;
	}

	public void setFinancialInformation(String financialInformation) {
		this.financialInformation = financialInformation;
	}

	public String getApprovalCode() {
		return approvalCode;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	public String getMetarialPath() {
		return metarialPath;
	}

	public void setMetarialPath(String metarialPath) {
		this.metarialPath = metarialPath;
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

	public String getApprovalStartDate() {
		return approvalStartDate;
	}

	public void setApprovalStartDate(String approvalStartDate) {
		this.approvalStartDate = approvalStartDate;
	}

	public String getApprovalEndDate() {
		return approvalEndDate;
	}

	public void setApprovalEndDate(String approvalEndDate) {
		this.approvalEndDate = approvalEndDate;
	}

	public String getEffectiveStartDateShow() {
		return effectiveStartDateShow;
	}

	public void setEffectiveStartDateShow(String effectiveStartDateShow) {
		this.effectiveStartDateShow = effectiveStartDateShow;
	}

	public String getEffectiveEndDateShow() {
		return effectiveEndDateShow;
	}

	public void setEffectiveEndDateShow(String effectiveEndDateShow) {
		this.effectiveEndDateShow = effectiveEndDateShow;
	}

	public String getApprovalStartDateShow() {
		return approvalStartDateShow;
	}

	public void setApprovalStartDateShow(String approvalStartDateShow) {
		this.approvalStartDateShow = approvalStartDateShow;
	}

	public String getApprovalEndDateShow() {
		return approvalEndDateShow;
	}

	public void setApprovalEndDateShow(String approvalEndDateShow) {
		this.approvalEndDateShow = approvalEndDateShow;
	}

	public String getOtherContent() {
		return otherContent;
	}

	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContractDesc() {
		return contractDesc;
	}

	public void setContractDesc(String contractDesc) {
		this.contractDesc = contractDesc;
	}
	
	public List<PositionModifyFlag> getBindingPosition() {
		return bindingPosition;
	}

	public void setBindingPosition(List<PositionModifyFlag> bindingPosition) {
		this.bindingPosition = bindingPosition;
	}

	public static void main(String[] args) {
		String text = "{\"id\":\"false\",\"contractNumber\":\"false\",\"contractCode\":\"false\",\"customerId\":\"false\",\"customerName\":\"false\",\"contractName\":\"false\",\"submitUnits\":\"false\",\"financialInformation\":\"false\",\"approvalCode\":\"false\",\"metarialPath\":\"false\",\"effectiveStartDate\":\"false\",\"effectiveEndDate\":\"false\",\"approvalStartDate\":\"false\",\"approvalEndDate\":\"false\",\"effectiveStartDateShow\":\"false\",\"effectiveEndDateShow\":\"false\",\"approvalStartDateShow\":\"false\",\"approvalEndDateShow\":\"false\",\"otherContent\":\"false\",\"status\":\"false\",\"contractDesc\":\"false\",\"bindingPosition\":[{\"id\":\"1\",\"validStartDate\":\"1364129407000\",\"validEndDate\":\"1364129407000\",\"dbFlag\":0,\"flag\":0,\"tabIdList\":[44,43],\"marketRules\":[{\"id\":\"7\",\"dbFlag\":0,\"flag\":0},{\"id\":\"6\",\"dbFlag\":0,\"flag\":0}],\"positionIndexFlag\":\"1364141013184_1\"},{\"id\":\"99\",\"validStartDate\":\"1364129403000\",\"validEndDate\":\"1364129405000\",\"dbFlag\":0,\"flag\":2,\"tabIdList\":[42,41],\"marketRules\":[{\"id\":\"11\",\"dbFlag\":0,\"flag\":0},{\"id\":\"12\",\"dbFlag\":0,\"flag\":0}],\"positionIndexFlag\":\"1364141013185_99\"}]}";
		ContractModifyFlag flag = JSON.toJavaObject(JSON.parseObject(text), ContractModifyFlag.class);
	}
}
