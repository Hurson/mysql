package com.avit.ads.pushads.task.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_release_area")
public class TReleaseArea implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 38, scale = 0)
	private Long id;
	@Column(name = "AREA_CODE")
	private String areaCode;
	@Column(name = "AREA_NAME")
	private String areaName;
	@Column(name = "PARENT_CODE")
	private String parentCode;
	@Column(name = "LOCATIONTYPE")
	private String locationType;
	@Column(name = "LOCATION_CODE")
	private String locationCode;
//	@Column(name = "TS_ID")
//	private String tsId;
	@Column(name = "OCS_ID")
	private String ocsId;
	
	public TReleaseArea(){};
	
	public TReleaseArea(Long id, String areaCode, String areaName,
			String parentCode, String locationType, String locationCode) {
		super();
		this.id = id;
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.parentCode = parentCode;
		this.locationType = locationType;
		this.locationCode = locationCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

//	public String getTsId() {
//		return tsId;
//	}
//
//	public void setTsId(String tsId) {
//		this.tsId = tsId;
//	}

	public String getOcsId() {
		return ocsId;
	}

	public void setOcsId(String ocsId) {
		this.ocsId = ocsId;
	}
	
	
}
