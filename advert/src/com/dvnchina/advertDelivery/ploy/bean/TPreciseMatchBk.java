package com.dvnchina.advertDelivery.ploy.bean;

import java.math.BigDecimal;

import com.dvnchina.advertDelivery.utils.StringUtil;

/**
 * TPreciseMatch entity. @author MyEclipse Persistence Tools
 */

public class TPreciseMatchBk implements java.io.Serializable {

	// Fields

	private Long id;
	private String matchName;
	private Long precisetype;
	private String productId;
	private String assetName;
	private String assetKey;
	private String assetSortId;
	private String dtvChannelId;
	private String playbackChannelId;
	private String lookbackCategoryId;
	private String userArea;
	private String userArea2;
	private String userArea3;
	private String userindustrys;
	private String userlevels;
	private String tvnExpression;
	private String tvnNumber;
	private Short priority;
	private Long ployId;
	private Integer delflag;
	private String groupId;
	// Constructors

	/** default constructor */
	public TPreciseMatchBk() {
	}

	/** minimal constructor */
	public TPreciseMatchBk(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TPreciseMatchBk(Long id, String matchName, Long precisetype,
			String productId, String assetName, String assetKey,
			String assetSortId, String dtvChannelId, String playbackChannelId,
			String lookbackCategoryId, String userArea, String userindustrys,
			String userlevels, String tvnNumber, Short priority, Long ployId) {
		this.id = id;
		this.matchName = matchName;
		this.precisetype = precisetype;
		this.productId = productId;
		this.assetName = assetName;
		this.assetKey = assetKey;
		this.assetSortId = assetSortId;
		this.dtvChannelId = dtvChannelId;
		this.playbackChannelId = playbackChannelId;
		this.lookbackCategoryId = lookbackCategoryId;
		this.userArea = userArea;
		this.userindustrys = userindustrys;
		this.userlevels = userlevels;
		this.tvnNumber = tvnNumber;
		this.priority = priority;
		this.ployId = ployId;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatchName() {
		return this.matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public Long getPrecisetype() {
		return this.precisetype;
	}

	public void setPrecisetype(Long precisetype) {
		this.precisetype = precisetype;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetKey() {
		return this.assetKey;
	}

	public void setAssetKey(String assetKey) {
		this.assetKey = assetKey;
	}

	public String getAssetSortId() {
		return this.assetSortId;
	}

	public void setAssetSortId(String assetSortId) {
		this.assetSortId = assetSortId;
	}

	public String getDtvChannelId() {
		return this.dtvChannelId;
	}

	public void setDtvChannelId(String dtvChannelId) {
		this.dtvChannelId = dtvChannelId;
	}

	public String getPlaybackChannelId() {
		return this.playbackChannelId;
	}

	public void setPlaybackChannelId(String playbackChannelId) {
		this.playbackChannelId = playbackChannelId;
	}

	public String getLookbackCategoryId() {
		return this.lookbackCategoryId;
	}

	public void setLookbackCategoryId(String lookbackCategoryId) {
		this.lookbackCategoryId = lookbackCategoryId;
	}

	public String getUserArea() {
		return this.userArea;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	public String getUserindustrys() {
		return this.userindustrys;
	}

	public void setUserindustrys(String userindustrys) {
		this.userindustrys = userindustrys;
	}

	public String getUserlevels() {
		return this.userlevels;
	}

	public void setUserlevels(String userlevels) {
		this.userlevels = userlevels;
	}

	public String getTvnNumber() {
		return this.tvnNumber;
	}

	public void setTvnNumber(String tvnNumber) {
		this.tvnNumber = tvnNumber;
	}

	public Short getPriority() {
		return this.priority;
	}

	public void setPriority(Short priority) {
		this.priority = priority;
	}

	public Long getPloyId() {
		return this.ployId;
	}

	public void setPloyId(Long ployId) {
		this.ployId = ployId;
	}

	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}
	/**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(id)) ? "ID:" + id + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(matchName)) ? "精准名称:" + matchName + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(ployId)) ? "策略ID:" + ployId + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(precisetype)) ? "精准类型:" + precisetype + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(priority)) ? "优先级:" + priority+ "," : "");
         if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserArea2() {
		return userArea2;
	}

	public void setUserArea2(String userArea2) {
		this.userArea2 = userArea2;
	}

	public String getUserArea3() {
		return userArea3;
	}

	public void setUserArea3(String userArea3) {
		this.userArea3 = userArea3;
	}

	public String getTvnExpression() {
		return tvnExpression;
	}

	public void setTvnExpression(String tvnExpression) {
		this.tvnExpression = tvnExpression;
	}
	
}