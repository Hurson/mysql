package com.dvnchina.advertDelivery.model;

import java.util.Date;

import com.dvnchina.advertDelivery.model.Customer;

public class Contract implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 合同主键
	 */
	private Integer id;
	/**
	 * 合同编号 255 
	 */
	private String contractNumber ;
	/**
	 * 合同代码 255
	 */
	private String contractCode;
	/**
	 * 广告商ID 外健
	 */
	private Integer customerId;
	/**
	 * 合同名称 255
	 */
	private String contractName;
	/**
	 * 合同有效开始时间
	 */
	private Date   effectiveStartDate;
	/**
	 * 合同有效结束时间
	 */
	private Date   effectiveEndDate;
	/**
	 * 合同状态 255
	 */
	private Integer state;
	/**
	 * 送审单位
	 */
	private String submitUnits;
	/**
	 * 合同金额
	 */
	private String financialInformation;
	
	private String productName;
	
	private String relevantInformation;
	/**
	 * 审批文号  255
	 */
	private String approvalCode;
	/**
	 * 审批文号有效期的开始时间
	 */
	private Date   approvalStartDate;
	/**
	 * 审批文号有效期的结束时间
	 */
	private Date   approvalEndDate;
	/**
	 * 创建时间
	 */
	private Date   createTime;
	/**
	 * 操作人ID
	 */
	private Integer operatorId;
	private Customer customer;
	
	private String contractDesc;
	
	
	
	public String getContractDesc() {
        return contractDesc;
    }

    public void setContractDesc(String contractDesc) {
        this.contractDesc = contractDesc;
    }

    public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Contract(){}
	
	public Contract(Integer id){
		this.id = id;
	}

	public Contract(Integer id, String contractNumber, String contractCode,
			String contractName, Date effectiveStartDate,
			Date effectiveEndDate, Integer state, String submitUnits,
			String financialInformation, String productName,
			String relevantInformation, String approvalCode,
			Date approvalStartDate, Date approvalEndDate, Date createTime,
			Integer operatorId) {
		super();
		this.id = id;
		this.contractNumber = contractNumber;
		this.contractCode = contractCode;
		this.contractName = contractName;
		this.effectiveStartDate = effectiveStartDate;
		this.effectiveEndDate = effectiveEndDate;
		this.state = state;
		this.submitUnits = submitUnits;
		this.financialInformation = financialInformation;
		this.productName = productName;
		this.relevantInformation = relevantInformation;
		this.approvalCode = approvalCode;
		this.approvalStartDate = approvalStartDate;
		this.approvalEndDate = approvalEndDate;
		this.createTime = createTime;
		this.operatorId = operatorId;
	}
	

	public Contract(Integer id,String contractName,
            String contractNumber
            ,String contractCode,
            Integer customerId,String submitUnits,
            String financialInformation,String approvalCode,
            Date effectiveStartDate,Date effectiveEndDate,Integer status)
    {   
    
        this.setId(id);
        this.setContractName(contractName);
        this.setContractNumber(contractNumber);
        this.setContractCode(contractCode);
        this.setCustomerId(customerId);
        this.setSubmitUnits(submitUnits);
        this.setFinancialInformation(financialInformation);
        this.setApprovalCode(approvalCode);
        this.setEffectiveStartDate(effectiveStartDate);
        this.setEffectiveEndDate(effectiveEndDate);
        this.setState(status);
    
    }
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRelevantInformation() {
		return relevantInformation;
	}

	public void setRelevantInformation(String relevantInformation) {
		this.relevantInformation = relevantInformation;
	}

	public String getApprovalCode() {
		return approvalCode;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	public Date getApprovalStartDate() {
		return approvalStartDate;
	}

	public void setApprovalStartDate(Date approvalStartDate) {
		this.approvalStartDate = approvalStartDate;
	}

	public Date getApprovalEndDate() {
		return approvalEndDate;
	}

	public void setApprovalEndDate(Date approvalEndDate) {
		this.approvalEndDate = approvalEndDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	
}

