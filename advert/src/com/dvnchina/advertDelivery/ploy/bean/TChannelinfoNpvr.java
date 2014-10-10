package com.dvnchina.advertDelivery.ploy.bean;

import java.util.Date;

/**
 * TChannelinfoNpvr entity. @author MyEclipse Persistence Tools
 */

public class TChannelinfoNpvr implements java.io.Serializable {

	// Fields

	private Long channelId;
	private String channelCode;
	private String channelType;
	private String channelName;
	private String serviceId;
	private String channelLanguage;
	private String channelLogo;
	private String keyword;
	private String locationCode;
	private String locationName;
	private Date createTime;
	private Date modifyTime;
	private String state;
	private Long tsId;
	private Long networkId;
	private String channelDesc;
	private Boolean isPlayback;
	private String summaryshort;

	// Constructors

	/** default constructor */
	public TChannelinfoNpvr() {
	}


	/** full constructor */
	public TChannelinfoNpvr(Long channelId, String channelCode,
			String channelType, String channelName, String serviceId,
			String channelLanguage, String channelLogo, String keyword,
			String locationCode, String locationName, Date createTime,
			Date modifyTime, String state, Long tsId, Long networkId,
			String channelDesc, Boolean isPlayback, String summaryshort) {
		this.channelId = channelId;
		this.channelCode = channelCode;
		this.channelType = channelType;
		this.channelName = channelName;
		this.serviceId = serviceId;
		this.channelLanguage = channelLanguage;
		this.channelLogo = channelLogo;
		this.keyword = keyword;
		this.locationCode = locationCode;
		this.locationName = locationName;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.state = state;
		this.tsId = tsId;
		this.networkId = networkId;
		this.channelDesc = channelDesc;
		this.isPlayback = isPlayback;
		this.summaryshort = summaryshort;
	}
	public TChannelinfoNpvr(Long channelId,
			String channelName, String channelCode, String serviceId) {
		super();
		this.channelId = channelId;
		this.channelCode = channelCode;
		this.channelName = channelName;
		this.serviceId = serviceId;
	}
	public TChannelinfoNpvr(String channelName, String channelCode, String serviceId) {
		super();
		this.channelCode = channelCode;
		this.channelName = channelName;
		this.serviceId = serviceId;
	}
	// Property accessors

	public Long getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelCode() {
		return this.channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelType() {
		return this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChannelName() {
		return this.channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getChannelLanguage() {
		return this.channelLanguage;
	}

	public void setChannelLanguage(String channelLanguage) {
		this.channelLanguage = channelLanguage;
	}

	public String getChannelLogo() {
		return this.channelLogo;
	}

	public void setChannelLogo(String channelLogo) {
		this.channelLogo = channelLogo;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getTsId() {
		return this.tsId;
	}

	public void setTsId(Long tsId) {
		this.tsId = tsId;
	}

	public Long getNetworkId() {
		return this.networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}

	public String getChannelDesc() {
		return this.channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	public Boolean getIsPlayback() {
		return this.isPlayback;
	}

	public void setIsPlayback(Boolean isPlayback) {
		this.isPlayback = isPlayback;
	}

	public String getSummaryshort() {
		return this.summaryshort;
	}

	public void setSummaryshort(String summaryshort) {
		this.summaryshort = summaryshort;
	}

}