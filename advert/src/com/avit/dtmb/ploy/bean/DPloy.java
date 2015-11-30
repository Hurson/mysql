package com.avit.dtmb.ploy.bean;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DPloy entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "d_ploy", catalog = "ads_x")
public class DPloy implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7645317591800693088L;
	
	private Integer id;
	private String ployName;
	private String positionCode;
	private Integer customerId;
	private Timestamp createTime;
	private Timestamp modifyTime;
	private String status;
	private String deleteFlag;
	private Timestamp auditTime;
	private String auditAdvice;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PLOY_NAME")
	public String getPloyName() {
		return this.ployName;
	}

	public void setPloyName(String ployName) {
		this.ployName = ployName;
	}

	@Column(name = "POSITION_CODE")
	public String getPositionCode() {
		return this.positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	@Column(name = "CUSTOMER_ID")
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CREATE_TIME", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "MODIFY_TIME", length = 19)
	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "AUDIT_TIME", length = 19)
	public Timestamp getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "AUDIT_ADVICE")
	public String getAuditAdvice() {
		return this.auditAdvice;
	}

	public void setAuditAdvice(String auditAdvice) {
		this.auditAdvice = auditAdvice;
	}


}
