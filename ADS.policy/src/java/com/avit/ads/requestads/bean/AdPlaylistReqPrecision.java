package com.avit.ads.requestads.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * AdPlaylistReqPrecision entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_PLAYLIST_REQ_PRECISION")
public class AdPlaylistReqPrecision implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;
	@Column(name = "PLAYLIST_ID")
	private long playlistId;
	@Column(name = "TYPE")
	private int type;
	@Column(name = "PRODUCT_ID")
	private String productCode;
	@Column(name = "DTV_SERVICE_ID")
	private String dtvServiceId;
	@Column(name = "ASSET_SORT_ID")
	private String assetSortId;
	@Column(name = "ASSET_KEY")
	private String key;
	@Column(name = "USER_AREA")
	private String userArea;
	@Column(name = "USERINDUSTRYS")
	private String userindustrys;
	@Column(name = "USERLEVELS")
	private String userlevels;
	@Column(name = "TVN_NUMBER")
	private String tvnNumber;
	@Column(name = "TVN_EXPRESSION")
	private String tvnExpression;
	@Column(name = "USE_LEVEL")
	private short useLevel;
	@Column(name = "PRECISION_ID")
	private long precisionId;
	@Column(name = "ASSET_ID")
	private String assetId;
	@Column(name = "PLAYBACK_SERVICE_ID")
	private String playbackServiceId;
	@Column(name = "LOOKBACK_CATEGORY_ID")
	private String lookbackCategoryId;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PLAYLIST_ID", insertable = false, updatable = false)
	private AdPlaylistReq adPlaylistReq;

	public AdPlaylistReq getAdPlaylistReq() {
		return adPlaylistReq;
	}

	public void setAdPlaylistReq(AdPlaylistReq adPlaylistReq) {
		this.adPlaylistReq = adPlaylistReq;
	}

	@OneToOne(mappedBy = "adPlaylistReqPrecision", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private AdPlaylistReqPContent preContent;

	public AdPlaylistReqPContent getPreContent() {
		return preContent;
	}

	public void setPreContent(AdPlaylistReqPContent preContent) {
		this.preContent = preContent;
	}

	// Constructors
	/** default constructor */
	public AdPlaylistReqPrecision() {
	}

	/** minimal constructor */
	public AdPlaylistReqPrecision(long id) {
		this.id = id;
	}

	/** full constructor */
	public AdPlaylistReqPrecision(long id, long playlistId, int type,
			String productCode, String dtvServiceId, String assetSortId,
			String key, String userArea, String userindustrys,
			String userlevels, String tvnNumber, String tvnExpression,
			short useLevel) {
		this.id = id;
		this.playlistId = playlistId;
		this.type = type;
		this.productCode = productCode;
		this.dtvServiceId = dtvServiceId;
		this.assetSortId = assetSortId;
		this.key = key;
		this.userArea = userArea;
		this.userindustrys = userindustrys;
		this.userlevels = userlevels;
		this.tvnNumber = tvnNumber;
		this.tvnExpression = tvnExpression;
		this.useLevel = useLevel;
	}

	public String getTvnExpression() {
		return tvnExpression;
	}

	public void setTvnExpression(String tvnExpression) {
		this.tvnExpression = tvnExpression;
	}

	public long getPrecisionId() {
		return precisionId;
	}

	public void setPrecisionId(long precisionId) {
		this.precisionId = precisionId;
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

	// Property accessors
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPlaylistId() {
		return this.playlistId;
	}

	public void setPlaylistId(long playlistId) {
		this.playlistId = playlistId;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDtvServiceId() {
		return dtvServiceId;
	}

	public void setDtvServiceId(String dtvServiceId) {
		this.dtvServiceId = dtvServiceId;
	}

	public String getAssetSortId() {
		return this.assetSortId;
	}

	public void setAssetSortId(String assetSortId) {
		this.assetSortId = assetSortId;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public short getUseLevel() {
		return this.useLevel;
	}

	public void setUseLevel(short useLevel) {
		this.useLevel = useLevel;
	}

}