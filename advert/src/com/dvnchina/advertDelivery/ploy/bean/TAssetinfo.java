package com.dvnchina.advertDelivery.ploy.bean;

/**
 * TAssetinfo entity. @author MyEclipse Persistence Tools
 */

public class TAssetinfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String assetId;
	private String assetName;
	private String shortName;
	private String title;
	private String year;
	private String keyword;
	private String rating;
	private String runtime;
	private String isPackage;
	private String assetCreateTime;
	private String displayRuntime;
	private String assetDesc;
	private String posterUrl;
	private String previewAssetId;
	private String previewAssetName;
	private String previewRuntime;
	private String videoCode;
	private String videoResolution;
	private String director;
	private String actor;
	private String productId;
	private String category;
	private Double score;
	private String createTime;
	private String modifyTime;
	private String state;

	// Constructors

	/** default constructor */
	public TAssetinfo() {
	}

	/** minimal constructor */
	public TAssetinfo(Long id, String assetId) {
		this.id = id;
		this.assetId = assetId;
	}

	/** full constructor */
	public TAssetinfo(Long id, String assetId, String assetName,
			String shortName, String title, String year, String keyword,
			String rating, String runtime, String isPackage,
			String assetCreateTime, String displayRuntime, String assetDesc,
			String posterUrl, String previewAssetId, String previewAssetName,
			String previewRuntime, String videoCode, String videoResolution,
			String director, String actor, String productId, String category,
			Double score, String createTime, String modifyTime, String state) {
		this.id = id;
		this.assetId = assetId;
		this.assetName = assetName;
		this.shortName = shortName;
		this.title = title;
		this.year = year;
		this.keyword = keyword;
		this.rating = rating;
		this.runtime = runtime;
		this.isPackage = isPackage;
		this.assetCreateTime = assetCreateTime;
		this.displayRuntime = displayRuntime;
		this.assetDesc = assetDesc;
		this.posterUrl = posterUrl;
		this.previewAssetId = previewAssetId;
		this.previewAssetName = previewAssetName;
		this.previewRuntime = previewRuntime;
		this.videoCode = videoCode;
		this.videoResolution = videoResolution;
		this.director = director;
		this.actor = actor;
		this.productId = productId;
		this.category = category;
		this.score = score;
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

	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetName() {
		return this.assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRuntime() {
		return this.runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getIsPackage() {
		return this.isPackage;
	}

	public void setIsPackage(String isPackage) {
		this.isPackage = isPackage;
	}

	public String getAssetCreateTime() {
		return this.assetCreateTime;
	}

	public void setAssetCreateTime(String assetCreateTime) {
		this.assetCreateTime = assetCreateTime;
	}

	public String getDisplayRuntime() {
		return this.displayRuntime;
	}

	public void setDisplayRuntime(String displayRuntime) {
		this.displayRuntime = displayRuntime;
	}

	public String getAssetDesc() {
		return this.assetDesc;
	}

	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}

	public String getPosterUrl() {
		return this.posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getPreviewAssetId() {
		return this.previewAssetId;
	}

	public void setPreviewAssetId(String previewAssetId) {
		this.previewAssetId = previewAssetId;
	}

	public String getPreviewAssetName() {
		return this.previewAssetName;
	}

	public void setPreviewAssetName(String previewAssetName) {
		this.previewAssetName = previewAssetName;
	}

	public String getPreviewRuntime() {
		return this.previewRuntime;
	}

	public void setPreviewRuntime(String previewRuntime) {
		this.previewRuntime = previewRuntime;
	}

	public String getVideoCode() {
		return this.videoCode;
	}

	public void setVideoCode(String videoCode) {
		this.videoCode = videoCode;
	}

	public String getVideoResolution() {
		return this.videoResolution;
	}

	public void setVideoResolution(String videoResolution) {
		this.videoResolution = videoResolution;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return this.actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
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