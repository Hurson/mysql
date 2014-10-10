package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CpsCategory implements Serializable {

	private static final long serialVersionUID = 4058667027201940114L;
	
	/**
	 * id为category表主键
	 */
	private Integer id;

	/**
	 * categoryId收视分析系统使用
	 */
	private String categoryId;

	private String categoryName;

	private Integer templateId;

	private Integer networkId;

	private Integer userLevel;

	private Integer industryType;
	
	/**
	 * type为树上展示各种类型【 type:类型 8:分类 7：业务 6：应用 5：频道 4： 3：产品 2：资源包 1：资源 】
	 */
	private Integer type;
	
	private Integer treeId;
	
	private String treeName;
	
	//private Timestamp modifyTime;

	private String modifyTime;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Integer networkId) {
		this.networkId = networkId;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public Integer getIndustryType() {
		return industryType;
	}

	public void setIndustryType(Integer industryType) {
		this.industryType = industryType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTreeId() {
		return treeId;
	}

	public void setTreeId(Integer treeId) {
		this.treeId = treeId;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
