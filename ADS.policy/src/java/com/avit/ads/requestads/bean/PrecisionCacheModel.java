package com.avit.ads.requestads.bean;


public class PrecisionCacheModel {
	private long id;
	private long playlistId;
	private int type;
	private String productCode;
	private String btvChannelId;
	private String assetSortId;
	private String key;
	private String userArea;
	private String userindustrys;
	private String userlevels;
	private String tvnNumber;
	private String tvnExpression;
	private String assetId;
	private short useLevel;
	private String playbackServiceId;
	private String lookbackCategoryId;
	private AdPlaylistReqPContent preContent;
	
	public String getPlaybackServiceId() {
		return playbackServiceId;
	}
	public void setPlaybackServiceId(String playbackServiceId) {
		this.playbackServiceId = playbackServiceId;
	}
	public String getLookbackCategoryId() {
		return lookbackCategoryId;
	}
	public void setLookbackCategoryId(String lookbackCategoryId) {
		this.lookbackCategoryId = lookbackCategoryId;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public AdPlaylistReqPContent getPreContent() {
		return preContent;
	}
	public void setPreContent(AdPlaylistReqPContent preContent) {
		this.preContent = preContent;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPlaylistId() {
		return playlistId;
	}
	public void setPlaylistId(long playlistId) {
		this.playlistId = playlistId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getBtvChannelId() {
		return btvChannelId;
	}
	public void setBtvChannelId(String btvChannelId) {
		this.btvChannelId = btvChannelId;
	}
	public String getAssetSortId() {
		return assetSortId;
	}
	public void setAssetSortId(String assetSortId) {
		this.assetSortId = assetSortId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUserArea() {
		return userArea;
	}
	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}
	public String getUserindustrys() {
		return userindustrys;
	}
	public void setUserindustrys(String userindustrys) {
		this.userindustrys = userindustrys;
	}
	public String getUserlevels() {
		return userlevels;
	}
	public void setUserlevels(String userlevels) {
		this.userlevels = userlevels;
	}
	public String getTvnNumber() {
		return tvnNumber;
	}
	public void setTvnNumber(String tvnNumber) {
		this.tvnNumber = tvnNumber;
	}
	public short getUseLevel() {
		return useLevel;
	}
	public void setUseLevel(short useLevel) {
		this.useLevel = useLevel;
	}
	public String getTvnExpression() {
		return tvnExpression;
	}
	public void setTvnExpression(String tvnExpression) {
		this.tvnExpression = tvnExpression;
	}
	
}
