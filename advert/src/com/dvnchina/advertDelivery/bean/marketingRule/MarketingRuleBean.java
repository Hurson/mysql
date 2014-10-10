package com.dvnchina.advertDelivery.bean.marketingRule;

import java.util.Date;

/**
 * 营销规则列表显示数据的封装
 * */
public class MarketingRuleBean {
	
	private int id;
	/** 规则ID */
	private String ruleId;
	/** 规则名称 */
	private String ruleName;
	/** 开始日期 */
	private String startTime;
	/** 结束日期 */
	private String endTime;
	/** 广告位 */
	private String positionName;
	/** 地区 */
	private String areaName;
	/** 频道 */
	private String channelName;
	/** 广告位 */
	private String positionId;
	/** 地区 */
	private String areaId;
	/** 频道 */
	private String channelId;
	/** 创建时间 */
	private Date createTime;
	/** 状态 */
	private int state;
	/**
	 * 修改标记 0 默认 1 新增 2 删除 3 修改
	 */
	private String modify;
	/**
	 * 关系表 主键
	 */
	private Integer tabId;
	
	public String getModify() {
		return modify;
	}
	public void setModify(String modify) {
		this.modify = modify;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
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
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public Integer getTabId() {
		return tabId;
	}
	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}
	
}
