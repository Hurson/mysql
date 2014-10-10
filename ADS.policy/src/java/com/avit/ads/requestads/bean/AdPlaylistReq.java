package com.avit.ads.requestads.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OrderBy;

/**
 * AdPlaylistReq entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_PLAYLIST_REQ")
public class AdPlaylistReq implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "ORDER_ID")
	private int orderId;
	
	@Column(name = "BEGIN")
	private String begin;
	
	@Column(name = "END")
	private String end;
	
	@Column(name = "PLAY_TIME")
	private int playTime;
	
	@Column(name = "AD_SITE_CODE")
	private String adSiteCode;
	
	@Column(name = "CHARACTERISTIC_IDENTIFICATION")
	private String characteristicIdentification;
	
	@Column(name = "AREAS")
	private String areas;
	
	@Column(name = "USERINDUSTRYS")
	private String userindustrys;
	
	@Column(name = "USERLEVELS")
	private String userlevels;
	
	@Column(name = "STATE")
	private Integer state;
	
	@Column(name = "CONTRACT_ID")
	private Long contractId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "BEGIN_DATE")
	private Date beginDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "CHANNELS")
	private String channels;
	@Column(name = "PLOY_ID")
	private Integer ployId;
	@Column(name = "TVN")
	private String tvn;
	

	public List<AdPlaylistReqPrecision> getColPrecisions() {
		return colPrecisions;
	}

	public void setColPrecisions(List<AdPlaylistReqPrecision> colPrecisions) {
		this.colPrecisions = colPrecisions;
	}

	@OneToOne( mappedBy = "adPlaylistReq", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private AdPlaylistReqContent contents;
	
	@OneToMany( mappedBy = "adPlaylistReq", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@OrderBy(clause="USE_LEVEL desc")
	private List<AdPlaylistReqPrecision> colPrecisions;
	
	// Constructors
	/** default constructor */
	public AdPlaylistReq() {
	}

	/** minimal constructor */
	public AdPlaylistReq(int id) {
		this.id = id;
	}

	/** full constructor */
	public AdPlaylistReq(int id, String begin, String end,
			int playTime, String adSiteCode,
			String characteristicIdentification, String areas,
			String userindustrys, String userlevels, int state,
			long contractId, Date beginDate, Date endDate, String channels) {
		this.id = id;
		this.begin = begin;
		this.end = end;
		this.playTime = playTime;
		this.adSiteCode = adSiteCode;
		this.characteristicIdentification = characteristicIdentification;
		this.areas = areas;
		this.userindustrys = userindustrys;
		this.userlevels = userlevels;
		this.state = state;
		this.contractId = contractId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.channels = channels;
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBegin() {
		return this.begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return this.end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getPlayTime() {
		return this.playTime;
	}

	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}

	public String getAdSiteCode() {
		return this.adSiteCode;
	}

	public void setAdSiteCode(String adSiteCode) {
		this.adSiteCode = adSiteCode;
	}

	public String getCharacteristicIdentification() {
		return this.characteristicIdentification;
	}

	public void setCharacteristicIdentification(
			String characteristicIdentification) {
		this.characteristicIdentification = characteristicIdentification;
	}

	public String getAreas() {
		return this.areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
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

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getContractId() {
		return this.contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public String getChannels() {
		return this.channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Integer getPloyId() {
		return ployId;
	}

	public void setPloyId(Integer ployId) {
		this.ployId = ployId;
	}

	public String getTvn() {
		return tvn;
	}

	public void setTvn(String tvn) {
		this.tvn = tvn;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public AdPlaylistReqContent getContents() {
		return contents;
	}

	public void setContents(AdPlaylistReqContent contents) {
		this.contents = contents;
	}
	


}