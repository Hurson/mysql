package com.dvnchina.advertDelivery.order.bean.playlist;

import java.util.List;

public class PloyBean {
	private Integer ployId;
	private String startTime;
	private String endTime;
	private String userIndustrys;
	private String userLevels;
	private Integer ployNumber;
	private String tvnNumber;
	private Integer areaId;
	private Integer channelId;
//	private String serviceId;
	private Integer channelGroupId;
	private List<String> serviceIdList;

	public Integer getPloyId() {
		return ployId;
	}

	public void setPloyId(Integer ployId) {
		this.ployId = ployId;
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

	public String getUserIndustrys() {
		return userIndustrys;
	}

	public void setUserIndustrys(String userIndustrys) {
		this.userIndustrys = userIndustrys;
	}

	public String getUserLevels() {
		return userLevels;
	}

	public void setUserLevels(String userLevels) {
		this.userLevels = userLevels;
	}

	public Integer getPloyNumber() {
		return ployNumber;
	}

	public void setPloyNumber(Integer ployNumber) {
		this.ployNumber = ployNumber;
	}

	public String getTvnNumber() {
		return tvnNumber;
	}

	public void setTvnNumber(String tvnNumber) {
		this.tvnNumber = tvnNumber;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

//	public String getServiceId() {
//		return serviceId;
//	}
//
//	public void setServiceId(String serviceId) {
//		this.serviceId = serviceId;
//	}

	public Integer getChannelGroupId() {
		return channelGroupId;
	}

	public void setChannelGroupId(Integer channelGroupId) {
		this.channelGroupId = channelGroupId;
	}

	public List<String> getServiceIdList() {
		return serviceIdList;
	}

	public void setServiceIdList(List<String> serviceIdList) {
		this.serviceIdList = serviceIdList;
	}
}
