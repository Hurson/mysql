package com.dvnchina.advertDelivery.model;

import java.util.Date;

/**
 * 营销规则实体
 * */
public class MarketingRule {
	/** 主键 */
	private Integer id;
	/** 开始日期 */
	private String startTime;
	/** 结束日期 */
	private String endTime;
	/** 创建时间 */
	private Date createTime;
	/** 状态 */
	private Integer state;
	/** 营销规则ID */
	private Integer marketingRuleId;
	/** 营销规则名称 */
	private String marketingRuleName;
	/** 广告位ID */
	private Integer positionId;
	/** 投放区域ID */
	private Integer locationId;
	/** 投放频道ID */
	private Integer channelId;
	/** 操作员 */
	private Integer operationId;
	
	private String locationName;
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	private String channelName;
	public MarketingRule(){
		
	}
	public MarketingRule(Integer locationId, String locationName)
	{
		this.locationId = locationId;
		this.locationName = locationName;
	}
	public MarketingRule(String startTime,String endTime,Date createTime,Integer state,Integer marketingRuleId,String marketingRuleName,Integer positionId)
	{
		this.startTime=startTime;
		this.endTime=endTime;
		this.createTime=createTime;
		this.state=state;
		this.marketingRuleId=marketingRuleId;
		this.marketingRuleName=marketingRuleName;
		this.positionId=positionId;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getMarketingRuleId() {
		return marketingRuleId;
	}

	public void setMarketingRuleId(Integer marketingRuleId) {
		this.marketingRuleId = marketingRuleId;
	}

	public String getMarketingRuleName() {
		return marketingRuleName;
	}

	public void setMarketingRuleName(String marketingRuleName) {
		this.marketingRuleName = marketingRuleName;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

}
