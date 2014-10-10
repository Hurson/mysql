package com.dvnchina.advertDelivery.position.bean;

import java.io.Serializable;

public class PositionOccupy implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 广告商名称 */
	private String advertisersName;
	/** 合同名称 */
	private String contractName;
	/** 合同编码 */
	private String contractCode;
	/** 合同开始时间 */
	private String startTime;
	/** 合同结束时间 */
	private String endTime;
	public String getAdvertisersName() {
		return advertisersName;
	}
	public void setAdvertisersName(String advertisersName) {
		this.advertisersName = advertisersName;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
