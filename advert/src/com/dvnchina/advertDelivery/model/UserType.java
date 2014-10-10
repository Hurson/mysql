package com.dvnchina.advertDelivery.model;

public class UserType {
	
	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 用户类型编码
	 */
	private String userTypeCode;
	
	/**
	 * 用户类型值
	 */
	private String userTypeValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public String getUserTypeValue() {
		return userTypeValue;
	}

	public void setUserTypeValue(String userTypeValue) {
		this.userTypeValue = userTypeValue;
	}

}
