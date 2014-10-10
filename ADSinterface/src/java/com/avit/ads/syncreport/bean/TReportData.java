package com.avit.ads.syncreport.bean;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TReportData entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_report_data")
public class TReportData implements java.io.Serializable {

	// Fields

	private Integer id;
	private String positionCode;
	private String cmaFlag;
	private Integer resourceId;
	private Date pushDate;
	private Time pushTime;
	private Integer receiveCount;
	private Integer orderId;

	private Long effCount;
	private Long reacheCount;
	// Constructors

	/** default constructor */
	public TReportData() {
	}

	/** full constructor */
	public TReportData(String positionCode, String cmaFlag, Integer resourceId,
			Timestamp pushDate, Time pushTime, Integer receiveCount,
			Integer orderId) {
		this.positionCode = positionCode;
		this.cmaFlag = cmaFlag;
		this.resourceId = resourceId;
		this.pushDate = pushDate;
		this.pushTime = pushTime;
		this.receiveCount = receiveCount;
		this.orderId = orderId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "position_code", length = 20)
	public String getPositionCode() {
		return this.positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	@Column(name = "cma_flag", length = 200)
	public String getCmaFlag() {
		return this.cmaFlag;
	}

	public void setCmaFlag(String cmaFlag) {
		this.cmaFlag = cmaFlag;
	}

	@Column(name = "resource_id")
	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "push_date", length = 19)
	public Date getPushDate() {
		return this.pushDate;
	}

	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}

	@Column(name = "push_time", length = 8)
	public Time getPushTime() {
		return this.pushTime;
	}

	public void setPushTime(Time pushTime) {
		this.pushTime = pushTime;
	}

	@Column(name = "receive_count")
	public Integer getReceiveCount() {
		return this.receiveCount;
	}

	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}

	@Column(name = "order_id")
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "eff_count")
	public Long getEffCount() {
		return effCount;
	}

	public void setEffCount(Long effCount) {
		this.effCount = effCount;
	}

	@Column(name = "reach_count")
	public Long getReacheCount() {
		return reacheCount;
	}

	public void setReacheCount(Long reacheCount) {
		this.reacheCount = reacheCount;
	}

}