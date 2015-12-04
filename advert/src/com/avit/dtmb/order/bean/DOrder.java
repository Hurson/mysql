package com.avit.dtmb.order.bean;

import java.sql.Date;

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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.model.Contract;

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
    private Contract contract;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Date modifyTime;
    private String state;


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
    
    @ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "POSITION_CODE", referencedColumnName ="POSITION_CODE", insertable = false, updatable = false)
    @NotFound(action=NotFoundAction.IGNORE)
    public DAdPosition getDposition() {
        return this.dposition;
    }
    
    public void setDposition(DAdPosition dposition) {
        this.dposition = dposition;
    }
    
    @ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "PLOY_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @NotFound(action=NotFoundAction.IGNORE)
    public DPloy getDploy() {
        return this.dploy;
    }
    
    public void setDploy(DPloy dploy) {
        this.dploy = dploy;
    }
    @ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @NotFound(action=NotFoundAction.IGNORE)
    public Contract getContract() {
        return this.contract;
    }
    
    public void setContract(Contract contract) {
        this.contract = contract;
    }
    
    @Column(name="START_TIME", length=19)
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    @Column(name="END_TIME", length=19)
    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
	

}
