package com.avit.dtmb.order.bean;
// default package


import java.util.Date;

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
@Table(name = "d_play_list")
public class PlayList implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4012728974405188658L;
	
	private Integer id;
	private String orderCode;
	private String positionCode;
	private String areaCode;
	private Date startDate;
	private Date endDate;
	private String startTime;
	private String endTime;
	private String ployType;
	private String typeValue;
	private Integer resourceId;
	private String resourcePath;
	private String userIndustries;
	private String userLevels;
	private String tvn;
	private Integer indexNum;
	private String isDefault;
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

	@Column(name = "START_TIME")
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME")
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
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

	@Column(name = "RESOURCE_ID")
	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "RESOURCE_PATH")
	public String getResourcePath() {
		return this.resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "START_DATE")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Column(name = "END_DATE")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column(name = "USER_INDUSTRIES")
	public String getUserIndustries() {
		return userIndustries;
	}

	public void setUserIndustries(String userIndustries) {
		this.userIndustries = userIndustries;
	}
	@Column(name = "USER_LEVELS")
	public String getUserLevels() {
		return userLevels;
	}

	public void setUserLevels(String userLevels) {
		this.userLevels = userLevels;
	}
	@Column(name = "TVN")
	public String getTvn() {
		return tvn;
	}

	public void setTvn(String tvn) {
		this.tvn = tvn;
	}
	@Column(name = "INDEX_NUM")
	public Integer getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}
	@Column(name = "IS_DEFAULT")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

}
