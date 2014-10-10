package com.dvnchina.advertDelivery.order.bean;

import java.util.Date;

/**
 * 投放式播出单信息
 * @author mengjt
 *
 */
public class PutInPlayListBean {
	private Integer orderId;
	private Integer ployId;
	private Integer positionId;
	private Integer contractId;
	private Date startTime;
	private Date endTime;
	// 1开机素材 2 多图素材 3 字幕素材 4 链接素材。如果是单个素材为空';
	private String contentType;
	private String contentId;
	//特征值的格式:
	//	高标清(SD/HD),插播位置(0  1/3   2/3)
	//	如 HD,1/3
	private String identification;

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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

}
