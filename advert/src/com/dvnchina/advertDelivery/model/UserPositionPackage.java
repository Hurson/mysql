package com.dvnchina.advertDelivery.model;

import java.io.Serializable;

public class UserPositionPackage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;
	private Integer advertPositionPackageId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAdvertPositionPackageId() {
		return advertPositionPackageId;
	}
	public void setAdvertPositionPackageId(Integer advertPositionPackageId) {
		this.advertPositionPackageId = advertPositionPackageId;
	}

}
