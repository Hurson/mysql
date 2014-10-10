package com.dvnchina.advertDelivery.model;

import java.util.Date;
import java.util.Map;

/**
 * 封装资源的相关信息
 * */
public class AssetInfo {
	
	private Integer id;
	private String assetId;
	private String assetName;
	private String shortName;
	private String title;
	private String year;
	private String keyword;
	private String rating;
	private String runtime;
	private char isPackage;
	private Date assetCreatetime;
	private String displayRuntime;
	private String assetDesc;
	private String posterUrl;
	private String previewAssetId;
	private String previewAssetName;
	private String previewRuntime;
	private String director;
	private String actor;
	private Date createTime;
	private Date modifyTime;
	private char state;
	
	/**
	 * 类型
	 */
	private String category;
	
	/**
	 * 得分
	 */
	private Double score;
	
	/**
	 * 视频编码格式，包括MPEG2，H.264D等
	 */
	private String videoCode;
	
	/**
	 * 视频分辨率
	 */
	private String videoResolution;

	/**
	 * 资源包与资源的关系映射
	 * */
	private Map<Integer,PackageAsset> assetMap;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public char getIsPackage() {
		return isPackage;
	}

	public void setIsPackage(char isPackage) {
		this.isPackage = isPackage;
	}

	public Date getAssetCreatetime() {
		return assetCreatetime;
	}

	public void setAssetCreatetime(Date assetCreatetime) {
		this.assetCreatetime = assetCreatetime;
	}

	public String getDisplayRuntime() {
		return displayRuntime;
	}

	public void setDisplayRuntime(String displayRuntime) {
		this.displayRuntime = displayRuntime;
	}

	public String getAssetDesc() {
		return assetDesc;
	}

	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getPreviewAssetId() {
		return previewAssetId;
	}

	public void setPreviewAssetId(String previewAssetId) {
		this.previewAssetId = previewAssetId;
	}

	public String getPreviewAssetName() {
		return previewAssetName;
	}

	public void setPreviewAssetName(String previewAssetName) {
		this.previewAssetName = previewAssetName;
	}

	public String getPreviewRuntime() {
		return previewRuntime;
	}

	public void setPreviewRuntime(String previewRuntime) {
		this.previewRuntime = previewRuntime;
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

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getVideoCode() {
		return videoCode;
	}

	public void setVideoCode(String videoCode) {
		this.videoCode = videoCode;
	}

	public String getVideoResolution() {
		return videoResolution;
	}

	public void setVideoResolution(String videoResolution) {
		this.videoResolution = videoResolution;
	}
	
	public Map<Integer, PackageAsset> getAssetMap() {
		return assetMap;
	}

	public void setAssetMap(Map<Integer, PackageAsset> assetMap) {
		this.assetMap = assetMap;
	}

	@Override
	public int hashCode() {	
		String code = this.assetId;
		if(this.modifyTime!=null){
			code += this.modifyTime.getTime();
		}
		return code.hashCode();
	}
}
