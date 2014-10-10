package com.dvnchina.advertDelivery.model;

/**
 * 
 * 用户---客户的关系表
 * @author Administrator
 *
 */
public class UserLocation extends CommonObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户的ID 
	 */
	private Integer userId;
	
	/**
	 * 客户的ID areaCode -->RELEASE_AREACODE
	 */
	private String areaCode;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Override
	public String toString() {
		return "UserLocation [areaCode=" + areaCode + ", userId=" + userId + "]";
	}
	
}
