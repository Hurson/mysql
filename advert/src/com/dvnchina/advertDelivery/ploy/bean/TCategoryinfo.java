package com.dvnchina.advertDelivery.ploy.bean;

/**
 * TCategoryinfo entity. @author MyEclipse Persistence Tools
 */

public class TCategoryinfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String categoryId;
	private String categoryName;
	private String posterUrl;
	private String hasCategory;
	private String type;
	private String createTime;
	private String modifyTime;
	private String state;

	// Constructors

	/** default constructor */
	public TCategoryinfo() {
	}

	/** minimal constructor */
	public TCategoryinfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TCategoryinfo(Long id, String categoryId, String categoryName,
			String posterUrl, String hasCategory, String type,
			String createTime, String modifyTime, String state) {
		this.id = id;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.posterUrl = posterUrl;
		this.hasCategory = hasCategory;
		this.type = type;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.state = state;
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

	public String getPosterUrl() {
		return this.posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getHasCategory() {
		return this.hasCategory;
	}

	public void setHasCategory(String hasCategory) {
		this.hasCategory = hasCategory;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}