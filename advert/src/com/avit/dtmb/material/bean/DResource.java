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

import com.dvnchina.advertDelivery.model.CommonObject;

/**
 * DResource entity. @author MyEclipse Persistence Tools
 */
/**
 * @author hudongyu
 *
 */
@Entity
@Table(name = "d_resource", catalog = "ads_x")
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
	private char status;
	private Date createTime;
	private Date modifyTime;
	private Date auditTime;
	private Integer categoryId;
	private Integer isDefault;
	private String positionCode;
	private String customerName;
	private String positionName;
	private String advertisersName;
	private Integer positionId;
	private String keyWords;
	private Integer contractId;
	private String backgroundPath;
	/**
	 * 有效期开始时间
	 */
	private Date      startTime;
	/**
	 * 有效期结束时间
	 */
	private Date      endTime;
	/**
	 * 审核意见 
	 */
	private String    examinationOpintions;
	/**
	 * 广告位ID
	 */
	private Integer   advertPositionId;
	/**
	 * 操作人员ID
	 */
	private Integer   operationId;
	
	
	public DResource(Integer id,String resourceName,Integer resourceType,Integer customerId,Integer categoryId,String positionCode,
			char status,Date createTime,String positionName,String  advertisersName){
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
	public DResource(Integer id,Integer resourceId,String resourceName,Integer resourceType,Integer categoryId,char status,
			Date createTime,Integer advertPositionId,String positionName,Integer isDefault,Integer customerId,
			Integer operationId,Date modifyTime,String advertisersName,Date endTime,Date startTime,String examinationOpintions,String keyWords,Integer contractId,String description
			,String backgroundPath){
		this.id = id;
		this.resourceId = resourceId;
		this.advertisersName = advertisersName;
		this.advertPositionId = advertPositionId;
		this.categoryId = categoryId;
		this.createTime = createTime;
		this.customerId = customerId;
		this.isDefault = isDefault;
		this.modifyTime = modifyTime;
		this.operationId = operationId;
		this.positionName = positionName;
		this.resourceName = resourceName;
		this.resourceType = resourceType;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
		this.examinationOpintions = examinationOpintions;
		this.keyWords = keyWords;
		this.contractId = contractId;
		this.description = description;
		this.backgroundPath = backgroundPath;
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
	public char getStatus() {
		return this.status;
	}

	public void setStatus(char c) {
		this.status = c;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	@Transient
	public Integer getAdvertPositionId() {
		return advertPositionId;
	}
	public void setAdvertPositionId(Integer advertPositionId) {
		this.advertPositionId = advertPositionId;
	}
	@Transient
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	@Column(name = "OPERATION_ID",length = 10)
	public Integer getOperationId() {
		return operationId;
	}
	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}
	@Column(name = "START_TIME",length = 19)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Column(name = "END_TIME",length = 19)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name = "EXAMINATION_OPINIONS",length = 19)
	public String getExaminationOpintions() {
		return examinationOpintions;
	}
	public void setExaminationOpintions(String examinationOpintions) {
		this.examinationOpintions = examinationOpintions;
	}
	@Column(name = "KEY_WORDS",length = 19)
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
	
	
}
