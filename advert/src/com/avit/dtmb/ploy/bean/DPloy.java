package com.avit.dtmb.ploy.bean;
// default package

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.model.Customer;
import com.google.gson.Gson;

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
	private DAdPosition dposition;
	private Customer customer;
	private Date createTime;
	private Date modifyTime;
	private String status;
	private String deleteFlag;
	private Date auditTime;
	private String auditAdvice;
    private List<DPloyDetail> ployDetailList;
    private Integer operatorId;
    private String ployDetailJson;
    
    private String positionName;
    private String customerName;

    public DPloy(){}
    
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
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "POSITION_CODE", referencedColumnName ="POSITION_CODE")
    @NotFound(action=NotFoundAction.IGNORE)
	public DAdPosition getDposition() {
		return dposition;
	}

	public void setDposition(DAdPosition dposition) {
		this.dposition = dposition;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName ="id")
    @NotFound(action=NotFoundAction.IGNORE)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "AUDIT_ADVICE")
	public String getAuditAdvice() {
		return this.auditAdvice;
	}

	public void setAuditAdvice(String auditAdvice) {
		this.auditAdvice = auditAdvice;
	}
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="PLOY_ID")
	public List<DPloyDetail> getPloyDetailList() {
		return ployDetailList;
	}

	public void setPloyDetailList(List<DPloyDetail> ployDetailList) {
		this.ployDetailList = ployDetailList;
	}
	
	@Column(name = "OPERATOR_ID")
	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	@Transient
	public String getPloyDetailJson() {
		Gson gson = new Gson();
		ployDetailJson = gson.toJson(ployDetailList);
		return ployDetailJson;
	}

	public void setPloyDetailJson(String ployDetailJson) {
		this.ployDetailJson = ployDetailJson;
	}

	@Transient
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Transient
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sb.append("策略ID：").append(id);
		sb.append(",策略名称： ").append(ployName);
		sb.append(dposition == null || StringUtils.isBlank(dposition.getPositionName())?"":",广告位:" + dposition.getPositionName());
		sb.append(createTime == null ? "":", 创建时间： " + sdf.format(createTime));
		sb.append(modifyTime == null ? "":", 修改时间： " + sdf.format(modifyTime));
		sb.append(auditTime == null ? "":", 审核时间： " + sdf.format(auditTime));
		sb.append(StringUtils.isBlank(auditAdvice)?"":", 审核意见：" + auditAdvice);
		return sb.toString();
	}
	
	
}
