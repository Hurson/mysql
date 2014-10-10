package com.dvnchina.advertDelivery.ploy.bean;

import java.util.ArrayList;
import java.util.List;

public class PloyTimeCGroup {

	private String starttimes;
	private String endtimes;
	private String channelgroups;
	private String areas;
	private String priority;
	private List<PloyBackup>   timeList = new ArrayList();
	private List<PloyBackup>   channelgroupList = new ArrayList();
	
	public PloyTimeCGroup() {
		areas="0";
	}
	public String getStarttimes() {
		return starttimes;
	}
	public void setStarttimes(String starttimes) {
		this.starttimes = starttimes;
	}
	public String getEndtimes() {
		return endtimes;
	}
	public void setEndtimes(String endtimes) {
		this.endtimes = endtimes;
	}
	public String getChannelgroups() {
		return channelgroups;
	}
	public void setChannelgroups(String channelgroups) {
		this.channelgroups = channelgroups;
	}
	public String getAreas() {
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public List<PloyBackup> getTimeList() {
		return timeList;
	}
	public void setTimeList(List<PloyBackup> timeList) {
		this.timeList = timeList;
	}
	public List<PloyBackup> getChannelgroupList() {
		return channelgroupList;
	}
	public void setChannelgroupList(List<PloyBackup> channelgroupList) {
		this.channelgroupList = channelgroupList;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	
}
