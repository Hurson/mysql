package com.dvnchina.advertDelivery.bean.marketingRule;

import java.util.Date;
import java.util.List;

/**
 * 添加营销规则数据的封装
 * */
public class AddRuleBean {
	
	private String id;
	/** 规则ID */
	private String ruleId;
	/** 规则名称 */
	private String ruleName;
	/** 开始日期 */
	private String startTime;
	/** 结束日期 */
	private String endTime;
	/** 广告位 */
	private String positionId;
	/** 地区 */
	private List<AreaBean> bindingArea;
	/** 创建时间 */
	private Date createTime;
	/** 状态 */
	private int operationId;
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
	
	/** 广告位 */
	private List<String> bindingPosition;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public List<AreaBean> getBindingArea() {
		return bindingArea;
	}

	public void setBindingArea(List<AreaBean> bindingArea) {
		this.bindingArea = bindingArea;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public Integer getTabId() {
		return tabId;
	}

	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}

	public List<String> getBindingPosition() {
		return bindingPosition;
	}

	public void setBindingPosition(List<String> bindingPosition) {
		this.bindingPosition = bindingPosition;
	}
	
}
