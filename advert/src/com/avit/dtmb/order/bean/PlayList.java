package com.avit.dtmb.order.bean;
// default package

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PlayList entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "d_play_list", catalog = "ads_x")
public class PlayList implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4012728974405188658L;
	
	private Integer id;
	private String orderCode;
	private String positionCode;
	private String areaCode;
	private Date startTime;
	private Date endTime;
	private String ployType;
	private String typeValue;
	private String resourceIds;
	private String resourcePaths;
	private String status;

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
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

	@Column(name = "POSITION_CODE", length = 20)
	public String getPositionCode() {
		return this.positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	@Column(name = "AREA_CODE", length = 20)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "START_TIME", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "PLOY_TYPE", length = 2)
	public String getPloyType() {
		return this.ployType;
	}

	public void setPloyType(String ployType) {
		this.ployType = ployType;
	}

	@Column(name = "TYPE_VALUE")
	public String getTypeValue() {
		return this.typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	@Column(name = "RESOURCE_IDS")
	public String getResourceIds() {
		return this.resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	@Column(name = "RESOURCE_PATHS")
	public String getResourcePaths() {
		return this.resourcePaths;
	}

	public void setResourcePaths(String resourcePaths) {
		this.resourcePaths = resourcePaths;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
