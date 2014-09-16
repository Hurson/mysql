package com.avit.ads.pushads.task.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 回看栏目 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_PLAYLIST_GIS")
public class TLoopbackCategory implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 38, scale = 0)
	private Long id;
	@Column(name = "CATEGORY_ID", length = 7)
	private String categoryId;
	@Column(name = "CATEGORY_NAME", length = 7)
	private String categoryName;
	@Column(name = "category_Type", length = 7)
	private String categoryType;
	@Column(name = "template_Id", length = 7)
	private String templateId;
	@Column(name = "template_Name", length = 7)
	private String templateName;
	@Column(name = "create_Time", length = 7)
	private Date createTime;
	@Column(name = "modify_Time", length = 7)
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