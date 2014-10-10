package com.dvnchina.advertDelivery.ploy.bean;

public class LocationCode {

	
	public LocationCode() {
		super();
	}

	private Long locationcode;
	
	private String locationname;
	
	private Long parentlocation;
	
	private String locationtype;

	public Long getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(Long locationcode) {
		this.locationcode = locationcode;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public Long getParentlocation() {
		return parentlocation;
	}

	public void setParentlocation(Long parentlocation) {
		this.parentlocation = parentlocation;
	}

	public String getLocationtype() {
		return locationtype;
	}

	public void setLocationtype(String locationtype) {
		this.locationtype = locationtype;
	}

	
	
}
