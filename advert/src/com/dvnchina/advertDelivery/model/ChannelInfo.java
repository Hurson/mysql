package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class ChannelInfo implements Serializable{

	private static final long serialVersionUID = 2474970398602385552L;

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
	 * 频道描述
	 */
	private String  channelDesc;
	/**
	 * 创建时间
	 */
	private Timestamp CreateTime;
	/**
	 * 修改时间
	 */
	private Timestamp modifyTime;
	/**
	 * 状态
	 */
	private Character state;
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
	
	//创建时间 前
	private Date  createTimeA;
	//创建时间 后
	private Date  createTimeB;
	
	private String infoState;
	
	public ChannelInfo()
	{
		
	}
	public ChannelInfo(Integer channelId, String channelCode,
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
		CreateTime = createTime;
		this.modifyTime = modifyTime;
		this.state = state;
		this.serviceId = serviceId;
		this.tsId = tsId;
		this.networkId = networkId;
		this.createTimeA = createTimeA;
		this.createTimeB = createTimeB;
		this.infoState = infoState;
	}
	public ChannelInfo(Integer channelId,
			String channelName, String channelCode, Integer serviceId) {
		super();
		this.channelId = channelId;
		this.channelCode = channelCode;
		this.channelName = channelName;
		this.serviceId = serviceId;
	}
	public ChannelInfo(String channelName, String channelCode, Integer serviceId) {
		super();
		this.channelCode = channelCode;
		this.channelName = channelName;
		this.serviceId = serviceId;
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
	public String getChannelDesc() {
		return channelDesc;
	}
	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}
	public Timestamp getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Timestamp createTime) {
		CreateTime = createTime;
	}
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Character getState() {
		return state;
	}
	public void setState(Character state) {
		this.state = state;
	}
	public Date getCreateTimeA() {
		return createTimeA;
	}
	public void setCreateTimeA(Date createTimeA) {
		this.createTimeA = createTimeA;
	}
	public Date getCreateTimeB() {
		return createTimeB;
	}
	public void setCreateTimeB(Date createTimeB) {
		this.createTimeB = createTimeB;
	}
	public String getInfoState() {
		return infoState;
	}
	public void setInfoState(String infoState) {
		this.infoState = infoState;
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
	
	
}
