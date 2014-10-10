package com.avit.ads.requestads.cache.bean;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCategoryinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_categoryinfo")
public class TCategoryinfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String categoryId;
	private String categoryName;
	private String posterUrl;
	private String hasCategory;
	private String type;
	private Timestamp createTime;
	private Timestamp modifyTime;
	private String state;
	private Long parentId;

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
			Timestamp createTime, Timestamp modifyTime, String state,
			Long parentId) {
		this.id = id;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.posterUrl = posterUrl;
		this.hasCategory = hasCategory;
		this.type = type;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.state = state;
		this.parentId = parentId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CATEGORY_ID", length = 254)
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "CATEGORY_NAME", length = 254)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "POSTER_URL", length = 254)
	public String getPosterUrl() {
		return this.posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	@Column(name = "HAS_CATEGORY", length = 254)
	public String getHasCategory() {
		return this.hasCategory;
	}

	public void setHasCategory(String hasCategory) {
		this.hasCategory = hasCategory;
	}

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "CREATE_TIME", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "MODIFY_TIME", length = 19)
	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "PARENT_ID", precision = 16, scale = 0)
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}