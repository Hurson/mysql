package com.dvnchina.advertDelivery.order.bean;

public class AreaResource {
	
	private Integer id;
	private String orderCode;
	private String startTime;
	private String endTime;
	private Integer areaCode;
	private String areaName;
	private Integer channelGroupId;
	private String channelGroupName;
	private Integer resourceId;
	private String resourceName;
	private String contain;
	private Integer pollIndex;
	public final Integer getId() {
		return id;
	}
	public final void setId(Integer id) {
		this.id = id;
	}
	public final String getOrderCode() {
		return orderCode;
	}
	public final void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public final String getStartTime() {
		return startTime;
	}
	public final void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public final String getEndTime() {
		return endTime;
	}
	public final void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public final Integer getAreaCode() {
		return areaCode;
	}
	public final void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}
	public final String getAreaName() {
		return areaName;
	}
	public final void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public final Integer getChannelGroupId() {
		return channelGroupId;
	}
	public final void setChannelGroupId(Integer channelGroupId) {
		this.channelGroupId = channelGroupId;
	}
	public final String getChannelGroupName() {
		return channelGroupName;
	}
	public final void setChannelGroupName(String channelGroupName) {
		this.channelGroupName = channelGroupName;
	}
	
	public final Integer getResourceId() {
		return resourceId;
	}
	public final void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public final String getResourceName() {
		return resourceName;
	}
	public final void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public final String getContain() {
		return contain;
	}
	public final void setContain(String contain) {
		this.contain = contain;
	}
	public Integer getPollIndex() {
		return pollIndex;
	}
	public void setPollIndex(Integer pollIndex) {
		this.pollIndex = pollIndex;
	}
	
}
