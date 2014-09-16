package com.avit.ads.requestads.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "AD_PLAYLIST_REQ_HISTORY", schema = "ADS")
@SequenceGenerator(name = "history_seq", sequenceName= "AD_PLAYLIST_REQ_HISTORY_SEQ", allocationSize = 25)
public class TempObjectStorgePlaylistContent implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** 内容ID*/
	@Column(name = "CONTENT_ID")
	private String contentId;
	
	/** 内容类型 1开机素材 2 多图素材 3 字幕素材。如果是单个素材为空*/
	@Column(name = "CONTENT_TYPE")
	private String contentType;
	
	/** 内容资源 */
	@Transient
	private String content;
	
	/** 内容路径*/
	@Column(name = "CONTENT_PATH")
	private String contentPath;
	
	/** 播出单ID*/
	@Column(name = "PLAYLIST_ID")
	private long id;
	
	/** 特征值，锚定位置和时间*/
	@Transient
	private String anthoring;
	
	/** 合同号*/
	@Column(name = "CONTRACT_ID")
	private long contractId;
	
	/** 广告位编码*/
	@Column(name = "AD_SITE_CODE")
	private String adSiteCode;
	
	/** 用户编码 tvnId*/
	@Column(name = "USERCODE")
	private String userCode;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATETIME")
	private Date dateTime;
	
	/** 广告内容编码系列号*/
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "history_seq")
	@Column(name = "ID")
	private long adContentSeq;
	
	@Column(name = "STATE")
	private int state;
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getAdContentSeq() {
		return adContentSeq;
	}

	public void setAdContentSeq(long adContentSeq) {
		this.adContentSeq = adContentSeq;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAnthoring() {
		return anthoring;
	}

	public void setAnthoring(String anthoring) {
		this.anthoring = anthoring;
	}

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public String getAdSiteCode() {
		return adSiteCode;
	}

	public void setAdSiteCode(String adSiteCode) {
		this.adSiteCode = adSiteCode;
	}
	
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getContentPath() {
		return contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

}
