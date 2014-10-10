package com.dvnchina.advertDelivery.bean.contract;

import java.io.Serializable;
import java.util.List;

public class PositionModifyFlag implements Serializable{
	
	private static final long serialVersionUID = -785559314842617266L;
	/**
	 * position 主键
	 */
	private Integer id;
	/**
	 * 开始时间
	 */
	private long validStartDate;
	/**
	 * 结束时间
	 */
	private long validEndDate;
	/**
	 *  0 代表此数据在数据库中已存在 
	 */
	private Integer dbFlag;
	/**
	 * 0 默认 1 新增  2 删除  3 修改
	 */
	private Integer flag;
	
	private String positionIndexFlag;
	/**
	 * 记录关联表的主键
	 */
	private List<Integer> tabIdList;	
	/**
	 * 保存规则列表
	 */
	private List<MarketRuleModifyFlag> marketRules;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public long getValidStartDate() {
		return validStartDate;
	}
	public void setValidStartDate(long validStartDate) {
		this.validStartDate = validStartDate;
	}
	public long getValidEndDate() {
		return validEndDate;
	}
	public void setValidEndDate(long validEndDate) {
		this.validEndDate = validEndDate;
	}
	public Integer getDbFlag() {
		return dbFlag;
	}
	public void setDbFlag(Integer dbFlag) {
		this.dbFlag = dbFlag;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getPositionIndexFlag() {
		return positionIndexFlag;
	}
	public void setPositionIndexFlag(String positionIndexFlag) {
		this.positionIndexFlag = positionIndexFlag;
	}
	public List<Integer> getTabIdList() {
		return tabIdList;
	}
	public void setTabIdList(List<Integer> tabIdList) {
		this.tabIdList = tabIdList;
	}
	public List<MarketRuleModifyFlag> getMarketRules() {
		return marketRules;
	}
	public void setMarketRules(List<MarketRuleModifyFlag> marketRules) {
		this.marketRules = marketRules;
	}
}
