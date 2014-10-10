package com.dvnchina.advertDelivery.contract.bean;

import java.util.Date;


public class PositionAD {

	private static final long serialVersionUID = -4879127837142232709L;

	private Integer positionId;
	private String positionName;
	private Integer deliveryMode;
	private Integer videoType;
	private Integer packageType;
	private Date positionStartDate;
	private Date positionEndDate;
	
	public PositionAD(){}
	
	public PositionAD(
	        Integer positionId,
	        Integer packageType,
	        Integer videoType,
	        Integer deliveryMode,         
            String positionName,
            Date positionStartDate,
            Date positionEndDate
            ) {
        this.positionId = positionId;
        this.packageType = packageType;
        this.videoType = videoType;
        this.deliveryMode = deliveryMode;
        this.positionName = positionName;
        this.positionStartDate = positionStartDate;
        this.positionEndDate = positionEndDate;
       
    }
	
   
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    public Date getPositionStartDate() {
        return positionStartDate;
    }
    public void setPositionStartDate(Date positionStartDate) {
        this.positionStartDate = positionStartDate;
    }
    public Date getPositionEndDate() {
        return positionEndDate;
    }
    public void setPositionEndDate(Date positionEndDate) {
        this.positionEndDate = positionEndDate;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    public Integer getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer packageType) {
        this.packageType = packageType;
    }
	
	
	
	
}
