package com.avit.ads.requestads.bean.cache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_ASSETINFO")
public class AssetBean {
	
	public AssetBean(){
		
	}
	
	public AssetBean(String assetId, String keyword, String director, String actor, String assetName){
		this.assetId = assetId;
		this.keyword = keyword;
		this.director = director;
		this.actor = actor;
		this.assetName = assetName;
	}
	@Id
	@Column(name = "ID")
	private Integer id;	
	@Column(name = "ASSET_ID")
	private String assetId;
	
	@Column(name = "KEYWORD")
	private String keyword;
	
	@Column(name = "DIRECTOR")
	private String director;
	
	@Column(name = "ACTOR")
	private String actor;
	
	@Column(name="ASSET_NAME")
	private String assetName;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
