package com.dvnchina.advertDelivery.npvrChannelGroup.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class NpvrChannelInfo implements Serializable{

	private static final long serialVersionUID = 2474970398605285552L;

	/**
	 * 频道id
	 */
	private Integer channelId;
	/**
	 * 频道编码
	 */
	private String  channelCode;
	/**
	 * 频道类型
	 */
	private String  channelType;
	/**
	 * 频道名称
	 */
	private String  channelName;
	/**
	 * 频道语种
	 */
	private String  channelLanguage;
	/**
	 * 图标
	 */
	private String  channelLogo;
	/**
	 * 关键词
	 */
	private String  keyWord;
	/**
	 * 区域码
	 */
	private String  locationCode;
	/**
	 * 区域名
	 */
	private String  locationName;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * SERVICE_ID
	 */
	private Integer serviceId;
	/**
	 * TS_ID
	 */
	private Integer tsId;
	/**
	 * NETWORK_ID
	 */
	private Integer networkId;
	
	/**
	 * 频道描述
	 */
	private String  channelDesc;
	
	/**
	 * 是否回放频道 1：是 0：否
	 */
	private Integer  isPlayBack;
	/**
	 * 简称
	 */
	private String  summaryShort;
	/**
	 * 频道组ID
	 */
	private Integer channelGroupId;
	
	public NpvrChannelInfo()
	{
		
	}
	public NpvrChannelInfo(Integer channelId, String channelCode,
			String channelType, String channelName, String channelLanguage,
			String channelLogo, String keyWord, String locationCode,
			String locationName, String channelDesc, Timestamp createTime,
			Timestamp modifyTime, Character state, Integer serviceId,
			Integer tsId, Integer networkId, Date createTimeA,
			Date createTimeB, String infoState) {
		super();
		this.channelId = channelId;
		this.channelCode = channelCode;
		this.channelType = channelType;
		this.channelName = channelName;
		this.channelLanguage = channelLanguage;
		this.channelLogo = channelLogo;
		this.keyWord = keyWord;
		this.locationCode = locationCode;
		this.locationName = locationName;
		this.channelDesc = channelDesc;
		this.modifyTime = modifyTime;
		this.serviceId = serviceId;
		this.tsId = tsId;
		this.networkId = networkId;
	}
	public NpvrChannelInfo(Integer channelId,
			String channelName, String channelCode, Integer serviceId) {
		super();
		this.channelId = channelId;
		this.channelCode = channelCode;
		this.channelName = channelName;
		this.serviceId = serviceId;
	}
	public NpvrChannelInfo(String channelName, String channelCode, Integer serviceId) {
		super();
		this.channelCode = channelCode;
		this.channelName = channelName;
		this.serviceId = serviceId;
	}
	/**
	 * 
	 * 频道组使用
	 * 
	 */
	public NpvrChannelInfo(
			           Integer channelId,
			           String channelName, 
			           String channelCode, 
			           String  channelType,
			           Integer serviceId,
			           Integer tsId, 
			           Integer networkId,
			           Integer  isPlayBack
			           ) {
		super();
		this.channelId=channelId;
		this.channelCode = channelCode;
		this.channelName = channelName;
		this.channelType= channelType;
		this.serviceId = serviceId;
		this.tsId= tsId;
		this.networkId= networkId;
		this.isPlayBack= isPlayBack;
	}

	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelLanguage() {
		return channelLanguage;
	}
	public void setChannelLanguage(String channelLanguage) {
		this.channelLanguage = channelLanguage;
	}
	public String getChannelLogo() {
		return channelLogo;
	}
	public void setChannelLogo(String channelLogo) {
		this.channelLogo = channelLogo;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getTsId() {
		return tsId;
	}
	public void setTsId(Integer tsId) {
		this.tsId = tsId;
	}
	public Integer getNetworkId() {
		return networkId;
	}
	public void setNetworkId(Integer networkId) {
		this.networkId = networkId;
	}
	public String getChannelDesc() {
		return channelDesc;
	}
	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}
	public Integer getIsPlayBack() {
		return isPlayBack;
	}
	public void setIsPlayBack(Integer isPlayBack) {
		this.isPlayBack = isPlayBack;
	}
	public String getSummaryShort() {
		return summaryShort;
	}
	public void setSummaryShort(String summaryShort) {
		this.summaryShort = summaryShort;
	}
	public Integer getChannelGroupId() {
		return channelGroupId;
	}
	public void setChannelGroupId(Integer channelGroupId) {
		this.channelGroupId = channelGroupId;
	}
	
	
}
