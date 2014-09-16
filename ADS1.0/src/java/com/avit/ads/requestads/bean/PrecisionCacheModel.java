package com.avit.ads.requestads.bean;

import java.util.List;

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
	private short useLevel;
	
	private List<Content> lstPreContent;
	
	public List<Content> getLstPreContent() {
		return lstPreContent;
	}
	public void setLstPreContent(List<Content> lstPreContent) {
		this.lstPreContent = lstPreContent;
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
	
}
