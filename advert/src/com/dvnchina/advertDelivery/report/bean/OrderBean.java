package com.dvnchina.advertDelivery.report.bean;

import java.io.Serializable;

public class OrderBean implements Serializable {

	private static final long serialVersionUID = 4183681017794734236L;
	
	/** 主键 */
	private Integer id;
	/** 订单编码 */
	private String orderCode;

	/** 订单结束时间 yyyy-MM-dd */
	private String endTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
