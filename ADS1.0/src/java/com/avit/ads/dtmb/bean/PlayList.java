package com.avit.ads.dtmb.bean;
// default package

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	private String resourceId;
	private String resourcePath;
	private String status;
	private String isHd;
	private String position;
	private String userindustries;
	private String userlevels;
	private String tvn;
	private String isDefault;
	private Integer indexNum;
	
	public PlayList(){}
	public PlayList(PlayList pl, String isHd, String position){
		this.id = pl.getId();
		this.orderCode = pl.orderCode;
		this.positionCode = pl.positionCode;
		this.areaCode = pl.areaCode;
		this.startDate = pl.startDate;
		this.endDate = pl.endDate;
		this.startTime = pl.startTime;
		this.endTime = pl.endTime;
		this.ployType = pl.ployType;
		this.typeValue = pl.typeValue;
		this.resourceId = pl.resourceId;
		this.resourcePath = pl.resourcePath;
		this.status = pl.status;
		this.userindustries = pl.userindustries;
		this.userlevels = pl.userlevels;
		this.tvn = pl.tvn;
		this.isDefault = pl.isDefault;
		this.indexNum = pl.indexNum;
		this.isHd = isHd;
		this.position = position;
	}

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
	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "RESOURCE_PATH")
	public String getResourcePath() {
		return this.resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Transient
	public String getIsHd() {
		return isHd;
	}

	public void setIsHd(String isHd) {
		this.isHd = isHd;
	}
	@Transient
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	@Column(name ="USER_INDUSTRIES")
	public String getUserindustries() {
		return userindustries;
	}

	public void setUserindustries(String userindustries) {
		this.userindustries = userindustries;
	}
	@Column(name ="USER_LEVELS")
	public String getUserlevels() {
		return userlevels;
	}

	public void setUserlevels(String userlevels) {
		this.userlevels = userlevels;
	}
	@Column(name ="TVN")
	public String getTvn() {
		return tvn;
	}

	public void setTvn(String tvn) {
		this.tvn = tvn;
	}
	@Column(name ="IS_DEFAULT")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	@Column(name ="INDEX_NUM")
	public Integer getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}

}
