package com.dvnchina.advertDelivery.model;

public class UserRole extends CommonObject{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 用户的ID
	 */
	private Integer userID;
	/**
	 * 角色的ID
	 */
	private Integer roleID;
	
	
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getRoleID() {
		return roleID;
	}
	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}
}
