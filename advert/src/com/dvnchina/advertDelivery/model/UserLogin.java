package com.dvnchina.advertDelivery.model;

import java.util.List;

public class UserLogin {
	
	private Integer userId;
	/** 角色类型 （1-广告商，2-运营商） */
	private Integer roleType;
	/** 绑定的广告商IDS */
	private Integer customerId;
	/** 指定广告位包id*/
	private List<Integer> positionIds;
	/** 用户名称  */
	private String userName;
	/**指定用户区域*/
	private String areaCodes;
	/**登陆用户具有访问权限的用户id*/
	private List<Integer> accessUserIds;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public List<Integer> getPositionIds() {
		return positionIds;
	}
	public void setPositionIds(List<Integer> positionIds) {
		this.positionIds = positionIds;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAreaCodes() {
		return areaCodes;
	}
	public void setAreaCodes(String areaCodes) {
		this.areaCodes = areaCodes;
	}
	public List<Integer> getAccessUserIds() {
		return accessUserIds;
	}
	public void setAccessUserIds(List<Integer> accessUserIds) {
		this.accessUserIds = accessUserIds;
	}
	
}
