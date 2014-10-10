package com.avit.ads.requestads.bean.cache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CHANNELINFO_NPVR")
public class ChannelInfoCache {

	public ChannelInfoCache(){
	}
	
	public ChannelInfoCache(String serviceId, String summaryShort){
		this.serviceId = serviceId;
		this.summaryShort = summaryShort;
	}
	
	@Column(name = "SUMMARYSHORT")
	private String summaryShort;
	@Id	
	@Column(name = "CHANNEL_ID")
	private Integer channelId;
	@Column(name = "SERVICE_ID")
	private String serviceId;

	public String getSummaryShort() {
		return summaryShort;
	}

	public void setSummaryShort(String summaryShort) {
		this.summaryShort = summaryShort;
	}

	public String getServiceId() {
		return serviceId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	
}
