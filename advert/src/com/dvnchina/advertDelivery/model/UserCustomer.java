package com.dvnchina.advertDelivery.model;

/**
 * 
 * 用户---客户的关系表
 * @author Administrator
 *
 */
public class UserCustomer extends CommonObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户的ID userId-->USER_ID
	 */
	private Integer userId;
	
	/**
	 * 客户的ID customerId-->  CUTOMER_ID
	 */
	private Integer customerId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}
