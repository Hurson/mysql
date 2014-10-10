package com.dvnchina.advertDelivery.model;

import java.util.Date;

public class ResourceReal extends CommonObject{
	/**
	 * 资产名称
	 */
	private String    resourceName;
	/**
	 * 资产类型
	 */
	private Integer   resourceType;
	/**
	 * 资产ID
	 */
	private Integer   resourceId;
	/**
	 * 资产描述
	 */
	private String    resourceDesc;
	/**
	 * 所属广告商
	 */
	private Integer   customerId;
	/**
	 * 所属内容分类
	 */
	private Integer   categoryId;
	
	/**
	 * 所属合同号
	 */
	private Integer   contractId;
	
	private String contractNumberStr;
	/**
	 * 有效期开始时间
	 */
	private Date      startTime;
	/**
	 * 有效期结束时间
	 */
	private Date      endTime;
	/**
	 * 广告位ID
	 */
	private Integer   advertPositionId;
	/**
	 * 状态
	 */
	private Character state;
	
	/**
	 * 状态 用于页面展现 ，不入库
	 * 
	 */
	private String stateStr;
	
	/**
	 * 创建时间
	 */
	private Date      createTime;
	/**
	 * 修改时间
	 */
	private Date      modifyTime;
	/**
	 * 操作人员ID
	 */
	private Integer   operationId;
	
	/** 是否默认素材 */
	private Integer isDefault;
	
	/**
	 * 广告位名称 用于页面展示 不入库
	 * 
	 */
	private String advertPositionName;
	
	//创建时间 前
	private Date  createTimeA;
	//创建时间 后
	private Date  createTimeB;
	
	private String metaState;
	
	private String keyWords;
	
	private String fileSize;
	
	
	
	
	public ResourceReal(){}

	public ResourceReal(String resourceName, Integer resourceType,
			Integer resourceId, String resourceDesc, Integer customerId,
			Integer categoryId, Integer contractId, Date startTime,
			Date endTime, Integer advertPositionId,
			String examinationOpintions, Character state, Date createTime,
			Date modifyTime, Integer operationId, Integer isDefault) {
		super();
		this.resourceName = resourceName;
		this.resourceType = resourceType;
		this.resourceId = resourceId;
		this.resourceDesc = resourceDesc;
		this.customerId = customerId;
		this.categoryId = categoryId;
		this.contractId = contractId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.advertPositionId = advertPositionId;
		this.state = state;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.operationId = operationId;
		this.isDefault = isDefault;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getAdvertPositionId() {
		return advertPositionId;
	}

	public void setAdvertPositionId(Integer advertPositionId) {
		this.advertPositionId = advertPositionId;
	}

	public Character getState() {
		return state;
	}

	public void setState(Character state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public Date getCreateTimeA() {
		return createTimeA;
	}

	public void setCreateTimeA(Date createTimeA) {
		this.createTimeA = createTimeA;
	}

	public Date getCreateTimeB() {
		return createTimeB;
	}

	public void setCreateTimeB(Date createTimeB) {
		this.createTimeB = createTimeB;
	}

	public String getMetaState() {
		return metaState;
	}

	public void setMetaState(String metaState) {
		this.metaState = metaState;
	}

	public String getContractNumberStr() {
		return contractNumberStr;
	}

	public void setContractNumberStr(String contractNumberStr) {
		this.contractNumberStr = contractNumberStr;
	}

	public String getAdvertPositionName() {
		return advertPositionName;
	}

	public void setAdvertPositionName(String advertPositionName) {
		this.advertPositionName = advertPositionName;
	}

	public String getStateStr() {
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
}






