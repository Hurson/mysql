package com.avit.dtmb.order.bean;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * DOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "d_order")
public class DOrder  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4312667623943216215L;
	private Integer id;
    private String orderCode;
    private String positionCode;
    private Integer ployId;
    private Integer customerId;
    private Integer contractId;
    private Date startDate;
    private Date endDate;
    private Date createTime;
    private Date modifyTime;
    private String state;
    private String auditAdvice;
    private Date auditTime;
    private Integer operatorId;
    private String description;
    private String isDefault;


	/** default constructor */
	public DOrder() {
	}
	
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="ORDER_CODE")
    public String getOrderCode() {
        return this.orderCode;
    }
    
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    
    @Column(name="POSITION_CODE")
    public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	@Column(name="PLOY_ID")
	public Integer getPloyId() {
		return ployId;
	}

	public void setPloyId(Integer ployId) {
		this.ployId = ployId;
	}
	@Column(name="CUSTOMER_ID")
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	@Column(name="CONTRACT_ID")
	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	@Column(name="START_DATE", length=10)
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    @Column(name="END_DATE", length=10)
    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @Column(name="CREATE_TIME", length=19)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="MODIFY_TIME", length=19)
    public Date getModifyTime() {
        return this.modifyTime;
    }
    
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    
    @Column(name="STATE", length=2)
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    @Column(name="AUDIT_ADVICE")
	public String getAuditAdvice() {
		return auditAdvice;
	}

	public void setAuditAdvice(String auditAdvice) {
		this.auditAdvice = auditAdvice;
	}
	 @Column(name="AUDIT_TIME")
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	@Column(name="OPERATOR_ID")
	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="IS_DEFAULT")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	

}
