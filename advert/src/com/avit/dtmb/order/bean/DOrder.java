package com.avit.dtmb.order.bean;

import java.sql.Timestamp;

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
@Table(name = "d_order", catalog = "ads_x")
public class DOrder  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4312667623943216215L;
	private Integer id;
    private Integer orderCode;
    private String positionCode;
    private Integer ployId;
    private Integer contractId;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp createTime;
    private Timestamp modifyTime;
    private String state;

	/** default constructor */
	public DOrder() {
	}
	public DOrder(Integer orderCode, String positionCode, Integer ployId, Integer contractId, Timestamp startTime, Timestamp endTime, Timestamp createTime, Timestamp modifyTime, String state) {
        this.orderCode = orderCode;
        this.positionCode = positionCode;
        this.ployId = ployId;
        this.contractId = contractId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.state = state;
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

    public Integer getOrderCode() {
        return this.orderCode;
    }
    
    public void setOrderCode(Integer orderCode) {
        this.orderCode = orderCode;
    }
    
    @Column(name="POSITION_CODE")

    public String getPositionCode() {
        return this.positionCode;
    }
    
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }
    
    @Column(name="PLOY_ID")

    public Integer getPloyId() {
        return this.ployId;
    }
    
    public void setPloyId(Integer ployId) {
        this.ployId = ployId;
    }
    
    @Column(name="CONTRACT_ID")

    public Integer getContractId() {
        return this.contractId;
    }
    
    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }
    
    @Column(name="START_TIME", length=19)

    public Timestamp getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    
    @Column(name="END_TIME", length=19)

    public Timestamp getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    
    @Column(name="CREATE_TIME", length=19)

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="MODIFY_TIME", length=19)

    public Timestamp getModifyTime() {
        return this.modifyTime;
    }
    
    public void setModifyTime(Timestamp modifyTime) {
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
