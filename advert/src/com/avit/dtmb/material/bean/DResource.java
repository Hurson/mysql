package com.avit.dtmb.material.bean;
// default package

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * DResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "d_resource")
public class DResource implements		java.io.Serializable {

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
	private Date createTime;
	private Date modifyTime;
	private Date auditTime;
	private Integer categoryId;
	private String isDefault;
	private String positionCode;
	private String customerName;
	private String positionName;
	private String advertisersName;
	
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
	public DResource(){
		
	}
	@Id
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
	public String getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(String isDefault) {
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
	public String getAdvertisersName() {
		return advertisersName;
	}
	public void setAdvertisersName(String advertisersName) {
		this.advertisersName = advertisersName;
	}
	

}
