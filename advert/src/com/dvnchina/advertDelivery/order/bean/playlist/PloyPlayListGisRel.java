package com.dvnchina.advertDelivery.order.bean.playlist;

import java.io.Serializable;
import java.util.List;

/**
 * 投放式策略相关的播出单信息
 * @author mengjt
 *
 */
public class PloyPlayListGisRel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer mateId;//素材ID
	private String path;//素材路径
	private String playLocation;//播放位置
	private Integer pollIndex;//轮询序号
	private Integer resourceType;//素材类型
	private Integer preciseId;//策略主键ID
	private Integer ployId;//策略ID
	private String startTime;//策略开始时间
	private String endTime;//策略结束时间
	private Integer channelGroupId;//频道组ID
	private List<String> serviceIdList;//频道组ID对应的频道serviceId列表
	private List<String> lookbackCategoryIdList;//回看频道IDS
	private String areaCode;//策略区域信息
	private String userIndustrys;//用户行业信息
	private String userLevels;//用户级别信息
	private String tvnNumber;//TVN号段
	private TextMate text;//文字信息
	private Integer PRECISETYPE; 

	public Integer getMateId() {
		return mateId;
	}
	public void setMateId(Integer mateId) {
		this.mateId = mateId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPlayLocation() {
		return playLocation;
	}
	public void setPlayLocation(String playLocation) {
		this.playLocation = playLocation;
	}
	public Integer getPollIndex() {
		return pollIndex;
	}
	public void setPollIndex(Integer pollIndex) {
		this.pollIndex = pollIndex;
	}
	public Integer getResourceType() {
		return resourceType;
	}
	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}
	public Integer getPreciseId() {
		return preciseId;
	}
	public void setPreciseId(Integer preciseId) {
		this.preciseId = preciseId;
	}
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
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
	public String getTvnNumber() {
		return tvnNumber;
	}
	public void setTvnNumber(String tvnNumber) {
		this.tvnNumber = tvnNumber;
	}
	public TextMate getText() {
		return text;
	}
	public void setText(TextMate text) {
		this.text = text;
	}
	public List<String> getLookbackCategoryIdList() {
		return lookbackCategoryIdList;
	}
	public void setLookbackCategoryIdList(List<String> lookbackCategoryIdList) {
		this.lookbackCategoryIdList = lookbackCategoryIdList;
	}
	public Integer getPRECISETYPE() {
		return PRECISETYPE;
	}
	public void setPRECISETYPE(Integer pRECISETYPE) {
		PRECISETYPE = pRECISETYPE;
	}
	
}
