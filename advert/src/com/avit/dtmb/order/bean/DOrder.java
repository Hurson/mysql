package com.avit.dtmb.order.bean;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.Customer;

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
    private DAdPosition dposition;
    private DPloy dploy;
    private Customer customer;
    private Contract contract;
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
    
    @ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "POSITION_CODE", referencedColumnName ="POSITION_CODE")
    @NotFound(action=NotFoundAction.IGNORE)
    public DAdPosition getDposition() {
        return this.dposition;
    }
    
    public void setDposition(DAdPosition dposition) {
        this.dposition = dposition;
    }
    
    @ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "PLOY_ID", referencedColumnName = "ID")
    @NotFound(action=NotFoundAction.IGNORE)
    public DPloy getDploy() {
        return this.dploy;
    }
    
    public void setDploy(DPloy dploy) {
        this.dploy = dploy;
    }
    
    @ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    @NotFound(action=NotFoundAction.IGNORE)
    public Customer getCustomer() {
		return customer;
	}
    
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID")
    @NotFound(action=NotFoundAction.IGNORE)
    public Contract getContract() {
        return this.contract;
    }
    
    public void setContract(Contract contract) {
        this.contract = contract;
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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID=").append(id);
		sb.append(", 订单编号:").append(orderCode);
		sb.append(dposition == null || StringUtils.isBlank(dposition.getPositionName())?"":",广告位名称: ").append(dposition.getPositionName());
		sb.append(dploy == null || StringUtils.isBlank(dploy.getPloyName())?"":",策略名称: " + dploy.getPloyName());
		sb.append(customer == null || StringUtils.isBlank(customer.getAdvertisersName())?"":",运营商：" + customer.getAdvertisersName());
		return  sb.toString();
	}
	

}
