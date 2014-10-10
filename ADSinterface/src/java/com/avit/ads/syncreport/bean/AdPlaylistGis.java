package com.avit.ads.syncreport.bean;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AdPlaylistGis entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_PLAYLIST_GIS")
public class AdPlaylistGis implements java.io.Serializable {

	// Fields

	private BigDecimal id;
	private Date startTime;
	private Date endTime;
	private String contentPath;
	private String contentType;
	private String adSiteCode;
	private String characteristicIdentification;
	private String channelId;
	private String areas;
	private String userindustrys;
	private String userlevels;
	private Long state;
	private BigDecimal contractId;
	private BigDecimal orderId;
	private String contentId;
	private String categoryId;
	private String assetId;
	// Constructors

	/** default constructor */
	public AdPlaylistGis() {
	}

	/** minimal constructor */
	public AdPlaylistGis(BigDecimal id) {
		this.id = id;
	}

	/** full constructor */
	public AdPlaylistGis(BigDecimal id, Date startTime, Date endTime,
			String contentPath, String contentType, String adSiteCode,
			String characteristicIdentification, String channelId,
			String areas, String userindustrys, String userlevels,
			Long state, BigDecimal contractId, BigDecimal orderId,
			String contentId) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.contentPath = contentPath;
		this.contentType = contentType;
		this.adSiteCode = adSiteCode;
		this.characteristicIdentification = characteristicIdentification;
		this.channelId = channelId;
		this.areas = areas;
		this.userindustrys = userindustrys;
		this.userlevels = userlevels;
		this.state = state;
		this.contractId = contractId;
		this.orderId = orderId;
		this.contentId = contentId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 38, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	//@Temporal(TemporalType.DATE)
	@Column(name = "START_TIME")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

//@Temporal(TemporalType.DATE)
	@Column(name = "END_TIME", length = 7)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "CONTENT_PATH")
	public String getContentPath() {
		return this.contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	@Column(name = "CONTENT_TYPE")
	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Column(name = "AD_SITE_CODE", length = 20)
	public String getAdSiteCode() {
		return adSiteCode;
	}

	public void setAdSiteCode(String adSiteCode) {
		this.adSiteCode = adSiteCode;
	}

	@Column(name = "CHARACTERISTIC_IDENTIFICATION")
	public String getCharacteristicIdentification() {
		return this.characteristicIdentification;
	}

	

	public void setCharacteristicIdentification(
			String characteristicIdentification) {
		this.characteristicIdentification = characteristicIdentification;
	}

	@Column(name = "service_ID")
	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Column(name = "AREAS",columnDefinition = "text") 
	public String getAreas() {
		return this.areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	@Column(name = "USERINDUSTRYS")
	public String getUserindustrys() {
		return this.userindustrys;
	}

	public void setUserindustrys(String userindustrys) {
		this.userindustrys = userindustrys;
	}

	@Column(name = "USERLEVELS")
	public String getUserlevels() {
		return this.userlevels;
	}

	public void setUserlevels(String userlevels) {
		this.userlevels = userlevels;
	}

	@Column(name = "STATE",  precision = 1, scale = 0)
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	@Column(name = "CONTRACT_ID", precision = 22, scale = 0)
	public BigDecimal getContractId() {
		return this.contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	@Column(name = "ORDER_ID", precision = 36, scale = 0)
	public BigDecimal getOrderId() {
		return this.orderId;
	}

	public void setOrderId(BigDecimal orderId) {
		this.orderId = orderId;
	}

	@Column(name = "CONTENT_ID")
	public String getContentId() {
		return this.contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	@Column(name = "CATEGORY_ID")
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "ASSET_ID")
	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}


}