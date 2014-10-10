package com.dvnchina.advertDelivery.order.bean;

import java.util.List;

/**
 * 请求式精准实体
 * */
public class PlayListReqPrecise {
	
	private Integer id;

	/** 精准编号 */
	private Integer precisionId;

	/** 播出单号 */
	private Integer playListId;

	/** 精准类型 */
	private Integer type;

	/** 产品编码 */
	private String productId;

	/** 回看频道 */
	private String dtvServiceId;

	/** 影片分类 */
	private String assetSortId;

	/** 关键字 */
	private String assetKey;

	/** 用户区域 */
	private String userArea;

	/** tvn表达式 0：不等于TVN  1:等于TVN */
	private String tvnExpression;
	
	/** tvn号段 */
	private String tvnNumber;

	/** 用户行业 */
	private String userIndustrys;

	/** 用户级别 */
	private String userLevels;

	/** 优先级 */
	private Integer useLevel;
	
	/** 影片IDS"," 分隔 */
	private String assetId;
	
	/** 回放频道 IDS","分隔 */
	private String playbackServiceId;
	
	/** 回看栏目 IDS","分隔 */
	private String lookbackCategoryId;

	private List<PlayListReqPContent> pContents;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrecisionId() {
		return precisionId;
	}

	public void setPrecisionId(Integer precisionId) {
		this.precisionId = precisionId;
	}

	public Integer getPlayListId() {
		return playListId;
	}

	public void setPlayListId(Integer playListId) {
		this.playListId = playListId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDtvServiceId() {
		return dtvServiceId;
	}

	public void setDtvServiceId(String dtvServiceId) {
		this.dtvServiceId = dtvServiceId;
	}

	public String getAssetSortId() {
		return assetSortId;
	}

	public void setAssetSortId(String assetSortId) {
		this.assetSortId = assetSortId;
	}

	public String getAssetKey() {
		return assetKey;
	}

	public void setAssetKey(String assetKey) {
		this.assetKey = assetKey;
	}

	public String getUserArea() {
		return userArea;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	public String getTvnExpression() {
		return tvnExpression;
	}

	public void setTvnExpression(String tvnExpression) {
		this.tvnExpression = tvnExpression;
	}

	public String getTvnNumber() {
		return tvnNumber;
	}

	public void setTvnNumber(String tvnNumber) {
		this.tvnNumber = tvnNumber;
	}

	public String getUserIndustrys() {
		return userIndustrys;
	}

	public void setUserIndustrys(String userIndustrys) {
		this.userIndustrys = userIndustrys;
	}

	public String getUserLevels() {
		return userLevels;
	}

	public void setUserLevels(String userLevels) {
		this.userLevels = userLevels;
	}

	public Integer getUseLevel() {
		return useLevel;
	}

	public void setUseLevel(Integer useLevel) {
		this.useLevel = useLevel;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

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

	public List<PlayListReqPContent> getPContents() {
		return pContents;
	}

	public void setPContents(List<PlayListReqPContent> contents) {
		pContents = contents;
	}


}
