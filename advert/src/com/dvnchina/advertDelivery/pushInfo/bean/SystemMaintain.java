package com.dvnchina.advertDelivery.pushInfo.bean;

import java.util.Date;

public class SystemMaintain {
	private Integer id;
	private Integer activeHour;
	private Integer actionCode;
	private Date sendTime;
	private Integer duration;
	private String areaCodes;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActiveHour() {
		return activeHour;
	}
	public void setActiveHour(Integer activeHour) {
		this.activeHour = activeHour;
	}
	public Integer getActionCode() {
		return actionCode;
	}
	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}
	
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public String getAreaCodes() {
		return areaCodes;
	}
	public void setAreaCodes(String areaCodes) {
		this.areaCodes = areaCodes;
	}
	@Override
	public String toString() {
		return "待机 [id=" + id + ", 开机小时数=" + activeHour
				+ ", 动作码=" + actionCode + ", 发送时间=" + sendTime
				+ ", 持续时间=" + duration + ", 区域=" + areaCodes + "]";
	}
	

}
