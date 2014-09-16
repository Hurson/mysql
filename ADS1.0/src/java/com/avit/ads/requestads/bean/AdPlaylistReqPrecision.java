package com.avit.ads.requestads.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * AdPlaylistReqPrecision entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_PLAYLIST_REQ_PRECISION", schema = "ADS")
public class AdPlaylistReqPrecision implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;
	@Column(name = "PLAYLIST_ID")
	private long playlistId;
	@Column(name = "TYPE")
	private int type;
	@Column(name = "PRODUCT_CODE")
	private String productCode;
	@Column(name = "BTV_CHANNEL_ID")
	private String btvChannelId;
	@Column(name = "ASSET_SORT_ID")
	private String assetSortId;
	@Column(name = "KEY")
	private String key;
	@Column(name = "USER_AREA")
	private String userArea;
	@Column(name = "USERINDUSTRYS")
	private String userindustrys;
	@Column(name = "USERLEVELS")
	private String userlevels;
	@Column(name = "TVN_NUMBER")
	private String tvnNumber;
	@Column(name = "USE_LEVEL")
	private short useLevel;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PLAYLIST_ID", insertable= false, updatable = false)
	private AdPlaylistReq adPlaylistReq;

	public AdPlaylistReq getAdPlaylistReq() {
		return adPlaylistReq;
	}

	public void setAdPlaylistReq(AdPlaylistReq adPlaylistReq) {
		this.adPlaylistReq = adPlaylistReq;
	}
	@OneToMany( mappedBy = "adPlaylistReqPrecision", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<AdPlaylistReqPContent> colPreContents;
	
	public Set<AdPlaylistReqPContent> getColPreContents() {
		return colPreContents;
	}

	public void setColPreContents(Set<AdPlaylistReqPContent> colPreContents) {
		this.colPreContents = colPreContents;
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
	public AdPlaylistReqPrecision(long id, long playlistId,
			int type, String productCode, String btvChannelId,
			String assetSortId, String key, String userArea,
			String userindustrys, String userlevels, String tvnNumber,
			short useLevel) {
		this.id = id;
		this.playlistId = playlistId;
		this.type = type;
		this.productCode = productCode;
		this.btvChannelId = btvChannelId;
		this.assetSortId = assetSortId;
		this.key = key;
		this.userArea = userArea;
		this.userindustrys = userindustrys;
		this.userlevels = userlevels;
		this.tvnNumber = tvnNumber;
		this.useLevel = useLevel;
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

	public String getBtvChannelId() {
		return this.btvChannelId;
	}

	public void setBtvChannelId(String btvChannelId) {
		this.btvChannelId = btvChannelId;
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