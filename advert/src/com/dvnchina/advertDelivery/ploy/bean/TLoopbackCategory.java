package com.dvnchina.advertDelivery.ploy.bean;

import java.util.Date;

/**
 * TLoopbackCategory entity. @author MyEclipse Persistence Tools
 */

public class TLoopbackCategory implements java.io.Serializable {

	// Fields

	private Long id;
	private String categoryId;
	private String categoryName;
	private String categoryType;
	private String templateId;
	private String templateName;
	private Date createTime;
	private Date modifyTime;

	// Constructors

	/** default constructor */
	public TLoopbackCategory() {
	}

	/** minimal constructor */
	public TLoopbackCategory(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TLoopbackCategory(Long id, String categoryId, String categoryName,
			String categoryType, String templateId, String templateName,
			Date createTime, Date modifyTime) {
		this.id = id;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryType = categoryType;
		this.templateId = templateId;
		this.templateName = templateName;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryType() {
		return this.categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}