package com.dvnchina.advertDelivery.order.bean.playlist;

import java.util.List;

/**
 * 投放式精准相关的播出单信息
 * @author mengjt
 *
 */
public class PrecisePlayListGisRel {

	private Integer mateId;//素材ID
	private String path;//素材路径
	private String playLocation;//播放位置
	private Integer pollIndex;//轮询序号
	private Integer resourceType;//素材类型
	private Integer preciseId;//精准ID
	private Integer preciseType;//精准类型
	private Integer ployId;//策略ID
	private String dtvChannelId;//直播频道IDS
	private String playbackChannelId;//回放频道IDS
	private List<String> lookbackCategoryIdList;//回看频道IDS
	private Integer channelGroupId;//频道组ID
	private List<String> serviceIdList;//频道组ID对应的频道serviceId列表
	private String userArea;//区域信息
	private List<String> industryList;//用户行业列表
	private List<String> levelList;//用户级别列表
	private List<String> assetIdList;//节目ID列表
	private TextMate text;//文字信息
	private String startTime;//开始时段
	private String endTime;//结束时段
	private Integer priority; //优先级
	private String areaCode;//投放区域
	private String menuTypeCode;//投放类型
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
	public Integer getPreciseType() {
		return preciseType;
	}
	public void setPreciseType(Integer preciseType) {
		this.preciseType = preciseType;
	}
	public Integer getPloyId() {
		return ployId;
	}
	public void setPloyId(Integer ployId) {
		this.ployId = ployId;
	}
	public String getDtvChannelId() {
		return dtvChannelId;
	}
	public void setDtvChannelId(String dtvChannelId) {
		this.dtvChannelId = dtvChannelId;
	}
	public String getPlaybackChannelId() {
		return playbackChannelId;
	}
	public void setPlaybackChannelId(String playbackChannelId) {
		this.playbackChannelId = playbackChannelId;
	}
	public List<String> getLookbackCategoryIdList() {
		return lookbackCategoryIdList;
	}
	public void setLookbackCategoryIdList(List<String> lookbackCategoryIdList) {
		this.lookbackCategoryIdList = lookbackCategoryIdList;
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
	public String getUserArea() {
		return userArea;
	}
	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}
	public List<String> getIndustryList() {
		return industryList;
	}
	public void setIndustryList(List<String> industryList) {
		this.industryList = industryList;
	}
	public List<String> getLevelList() {
		return levelList;
	}
	public void setLevelList(List<String> levelList) {
		this.levelList = levelList;
	}
	public List<String> getAssetIdList() {
		return assetIdList;
	}
	public void setAssetIdList(List<String> assetIdList) {
		this.assetIdList = assetIdList;
	}
	public TextMate getText() {
		return text;
	}
	public void setText(TextMate text) {
		this.text = text;
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
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getMenuTypeCode() {
		return menuTypeCode;
	}
	public void setMenuTypeCode(String menuTypeCode) {
		this.menuTypeCode = menuTypeCode;
	}
	
	
}
