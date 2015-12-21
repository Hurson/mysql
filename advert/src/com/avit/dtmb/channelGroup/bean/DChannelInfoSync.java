package com.avit.dtmb.channelGroup.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "d_channelinfo_sync")
public class DChannelInfoSync {
	/** 主键 */
	private Long channelId;
	
	/** 频道名称 */
	private String channelName;
	
	/** serviceID */
	private String serviceId;
	
	/** 传输流ID */
	private Long tsId;
	
	/** 频道类型 */
	private String channelType;
	
	/**网络Id*/
	private String networkId;
	
	/**
	 * 频道组ID
	 */
	private Long channelGroupId;
	
	private Integer isPlayBack;
	private String channelCode;
	
	
	public DChannelInfoSync(Long channelId,String channelName,String channelType,String serviceId,Long tsId,String networkId,Integer isPlayBack){
		this.channelId = channelId;
		this.channelName = channelName;
		this.channelType = channelType;
		this.serviceId = serviceId;
		this.tsId = tsId;
		this.networkId = networkId;
		this.isPlayBack = isPlayBack;
	}
	/*public DChannelInfoSync(Long channelId,String channelName,String channelType,String serviceId,Long tsId,Integer isPlayBack){
		this.channelId = channelId;
		this.channelName = channelName;
		this.channelType = channelType;
		this.serviceId = serviceId;
		this.tsId = tsId;
		this.isPlayBack = isPlayBack;
	}*/
	public DChannelInfoSync() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CHANNEL_ID", unique = true, nullable = false)
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	
	@Column(name = "CHANNEL_NAME")
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Column(name = "SERVICE_ID")
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	@Column(name = "TS_ID")
	public Long getTsId() {
		return tsId;
	}
	public void setTsId(Long tsId) {
		this.tsId = tsId;
	}	
	@Column(name="NETWORK_ID")
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	@Column(name = "CHANNEL_TYPE")
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	
	@Column(name = "IS_PLAYBACK")
	public Integer getIsPlayBack() {
		return isPlayBack;
	}
	public void setIsPlayBack(Integer isPlayBack) {
		this.isPlayBack = isPlayBack;
	}
	public Long getChannelGroupId() {
		return channelGroupId;
	}
	public void setChannelGroupId(Long channelGroupId) {
		this.channelGroupId = channelGroupId;
	}
	@Column(name = "CHANNEL_CODE")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}	
	
	
}
