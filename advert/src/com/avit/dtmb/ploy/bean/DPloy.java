package com.avit.dtmb.ploy.bean;
// default package

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * DPloy entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "d_ploy")
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
    private List<DPloyDetail> ployDetailList;
   
    private String customerName;
    private String positionName;

    public DPloy(){}
    
	public DPloy(DPloy ploy, String customerName, String positionName) {
		super();
		this.id = ploy.id;
		this.ployName = ploy.ployName;
		this.positionCode = ploy.positionCode;
		this.customerId = ploy.customerId;
		this.createTime = ploy.createTime;
		this.modifyTime = ploy.modifyTime;
		this.status = ploy.status;
		this.deleteFlag = ploy.deleteFlag;
		this.auditTime = ploy.auditTime;
		this.auditAdvice = ploy.auditAdvice;
		this.ployDetailList = ploy.ployDetailList;
		this.customerName = customerName;
		this.positionName = positionName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="PLOY_ID")
	public List<DPloyDetail> getPloyDetailList() {
		return ployDetailList;
	}

	public void setPloyDetailList(List<DPloyDetail> ployDetailList) {
		this.ployDetailList = ployDetailList;
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
	
	
	

}
