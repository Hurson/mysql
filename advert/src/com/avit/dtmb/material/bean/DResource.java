package com.avit.dtmb.material.bean;
// default package


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * DResource entity. @author MyEclipse Persistence Tools
 */
/**
 * @author hudongyu
 *
 */
@Entity
@Table(name = "d_resource")
public class DResource  implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7910208655310594692L;
	private Integer id;
	private String resourceName;
	private Integer resourceType;
	private Integer resourceId;
	private String description;
	private Integer customerId;
	private String status;
	private String statusStr;
	private Date createTime;
	private Date modifyTime;
	private Date auditTime;
	private Integer categoryId;
	private Integer isDefault;
	private String positionCode;
	private String positionName;
	private String advertisersName;
	private String keyWords;
	private Integer contractId;
	private String backgroundPath;
	
	/**
	 * 审核意见 
	 */
	private String    examinationOpintions;
	/**
	 * 操作人员ID
	 */
	private Integer   operationId;
	
	
	public DResource(Integer id,String resourceName,Integer resourceType,Integer customerId,Integer categoryId,String positionCode,
			String status,Date createTime,String positionName,String  advertisersName){
		this.id=id;
		this.resourceName=resourceName;
		this.resourceType=resourceType;
		this.customerId=customerId;
		this.categoryId=categoryId;
		this.positionCode=positionCode;
		this.status=status;
		this.createTime=createTime;
		this.positionName=positionName;
		this.advertisersName=advertisersName;
		
		
	}
	public DResource(DResource resource, String positionName, String advertisersName){
		this.id = resource.id;
		this.positionCode = resource.getPositionCode();
		this.resourceId = resource.resourceId;
		this.advertisersName = advertisersName;
		this.categoryId = resource.categoryId;
		this.createTime = resource.createTime;
		this.customerId = resource.customerId;
		this.isDefault = resource.isDefault;
		this.modifyTime = resource.modifyTime;
		this.operationId = resource.operationId;
		this.positionName = positionName;
		this.resourceName = resource.resourceName;
		this.resourceType = resource.resourceType;
		this.status = resource.status;
		this.examinationOpintions = resource.examinationOpintions;
		this.keyWords = resource.keyWords;
		this.contractId = resource.contractId;
		this.description = resource.description;
	}
	public DResource(){
		
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "RESOURCE_NAME")
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "RESOURCE_TYPE")
	public Integer getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name = "RESOURCE_ID")
	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "CUSTOMER_ID")
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "MODIFY_TIME", length = 19)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "AUDIT_TIME", length = 19)
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "CATEGORY_ID")
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "IS_DEFAULT", length = 1)
	public Integer getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	@Column(name = "POSITION_CODE",length = 19)
	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Transient
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	@Transient
	public String getBackgroundPath() {
		return backgroundPath;
	}
	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}
	@Transient
	public String getAdvertisersName() {
		return advertisersName;
	}
	public void setAdvertisersName(String advertisersName) {
		this.advertisersName = advertisersName;
	}
	
	@Column(name = "OPERATION_ID",length = 10)
	public Integer getOperationId() {
		return operationId;
	}
	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}
	
	@Column(name = "EXAMINATION_OPINIONS")
	public String getExaminationOpintions() {
		return examinationOpintions;
	}
	public void setExaminationOpintions(String examinationOpintions) {
		this.examinationOpintions = examinationOpintions;
	}
	@Column(name = "KEY_WORDS")
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	@Column(name = "CONTRACT_ID",length = 10)
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	@Transient
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	
}
