package com.dvnchina.advertDelivery.model;

/**
 * 地区实体类  T_LOCATION_CODE
 * 
 * @author Administrator
 */
public class Location extends CommonObject{

	private static final long serialVersionUID = -1524476560328730482L;
	
	/**
	 * 地区的名字 < - > AREA_NAME
	 */
	private String  areaName;
	
	/**
	 * 地区码< - >  AREA_CODE
	 */
	private String areaCode;
	
	/**
	 * 父级区域编码< - >PARENT_CODE
	 */
	private String parentCode;
	
	/**
	 * 区域类型< - >LOCATIONTYPE
	 */
	private String locationType;
	/**
	 * 区域类型< - >LOCATIONTYPE
	 */
	private String locationCode;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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

	@Override
	public String toString() {
		return "Location [areaCode=" + areaCode + ", areaName=" + areaName + ", locationType=" + locationType + ", parentCode="
				+ parentCode + "]";
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	
}
