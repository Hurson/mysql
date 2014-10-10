package com.dvnchina.advertDelivery.bean.contract;

import java.io.Serializable;

public class MarketRuleModifyFlag implements Serializable{
	
	private static final long serialVersionUID = -4126703751227136373L;
	
	private Integer id;
	/**
	 * 0 代表记录在数据库中已存在
	 */
	private Integer dbFlag;
	/**
	 * 0 默认 1 新增 2 删除 3修改
	 */
	private Integer flag;
	
	private Integer tabId;
	/**
	 * 中间表关联主键
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getTabId() {
		return tabId;
	}

	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}
}
