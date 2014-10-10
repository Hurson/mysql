package com.avit.ads.requestads.cache.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TBsmpUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_asset_view")
public class TAssetV implements java.io.Serializable {

	// Fields
	private String Id;
	private String assetId;
	private String assetName;
	private String assetKey;
	private String categoryId;	

	// Constructors

	/** default constructor */
	
	/** minimal constructor */

	public TAssetV(String assetId, String assetName, String assetKey,
			String categoryId) {
		super();
		this.assetId = assetId;
		this.assetName = assetName;
		this.assetKey = assetKey;
		this.categoryId = categoryId;
	}
	public TAssetV() {
		super();
	}
	@Id
	//@Column(name = "USERID", unique = true, nullable = false)
	@Column(name = "Id", length = 50)public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	/** full constructor */
	//@Id
	//@Column(name = "USERID", unique = true, nullable = false)
	@Column(name = "assetId", length = 20)
	public String getAssetId() {
		return assetId;
	}



	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	@Column(name = "assetName", length = 20)
	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	@Column(name = "assetKey", length = 20)
	public String getAssetKey() {
		return assetKey;
	}

	public void setAssetKey(String assetKey) {
		this.assetKey = assetKey;
	}

	@Column(name = "categoryId", length = 20)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	

}