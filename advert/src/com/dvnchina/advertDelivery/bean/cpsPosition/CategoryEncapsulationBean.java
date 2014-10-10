package com.dvnchina.advertDelivery.bean.cpsPosition;

import java.io.Serializable;

public class CategoryEncapsulationBean implements Serializable{
	
	private static final long serialVersionUID = -5727225482906198490L;
	
	private Integer id;

	private String categoryId;
	
	private String categoryName;
	
	private Integer treeId;
	
	private String treeName;

	private Integer templateId;
	
	private Integer networkId;
	
	private Integer userLevel;
	
	private Integer industryType;
	
	private String modifyTime;

	/**
	 * 0 标清 1 高清
	 */
	private Integer type;

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

	@Override
	public boolean equals(Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CategoryEncapsulationBean)) return false;
		else {
			CategoryEncapsulationBean categoryEncapsulationBean  = (CategoryEncapsulationBean) obj;
			if (null == this.getId() || null == categoryEncapsulationBean.getId()) return false;
			else return (this.getId().equals(categoryEncapsulationBean.getId()));
		}
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
