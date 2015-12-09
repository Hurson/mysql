package com.avit.dtmb.order.bean;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DOrderMaterialRef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "d_order_mate_rel")
public class DOrderMateRel implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6902616209233440440L;
	private Integer id;
	private String orderCode;
	private Integer resourceId;
	private String startTime;
	private String endTime;
	private String areaCode;
	private Integer ployDetailId;
	private Integer indexNum;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ORDER_CODE")
	public String getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Column(name = "RESOURCE_ID")
	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "START_TIME", length = 8)
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", length = 8)
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "AREA_CODE", length = 20)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "INDEX_NUM")
	public Integer getIndexNum() {
		return this.indexNum;
	}
	
	@Column(name = "PLOY_DETAIL_ID")
	public Integer getPloyDetailId() {
		return ployDetailId;
	}

	public void setPloyDetailId(Integer ployDetailId) {
		this.ployDetailId = ployDetailId;
	}

	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}

}
