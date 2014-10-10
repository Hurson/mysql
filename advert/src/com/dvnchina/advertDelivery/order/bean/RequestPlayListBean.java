package com.dvnchina.advertDelivery.order.bean;

import java.util.Date;
import java.util.List;

/**
 * 请求是播出单信息
 * @author mengjt
 *
 */
public class RequestPlayListBean {
	private Integer id;
	private Integer orderId;
	private Integer ployId;
	private Integer positionId;
	private Integer contractId;
	private Date startDate;
	private Date endDate;
	private String startTime;
	private String endTime;
	//特征值的格式:
	//	高标清(SD/HD),插播位置(0  1/3   2/3)
	//	如 HD,1/3
	private String identification;
	private List<PlayListResource> resources;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getPloyId() {
		return ployId;
	}

	public void setPloyId(Integer ployId) {
		this.ployId = ployId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public List<PlayListResource> getResources() {
		return resources;
	}

	public void setResources(List<PlayListResource> resources) {
		this.resources = resources;
	}

}
