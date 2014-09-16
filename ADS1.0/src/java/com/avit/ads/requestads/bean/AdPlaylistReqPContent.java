package com.avit.ads.requestads.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AdPlaylistReqPContent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_PLAYLIST_REQ_P_CONTENT", schema = "ADS")
public class AdPlaylistReqPContent implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;
	@Column(name = "PRECISION_ID")
	private long precisionId;
	@Column(name = "CONTENT_PATH")
	private String contentPath;
	@Column(name = "CONTENT_TYPE")
	private String contentType;
	@Column(name = "CONTENT_ID")
	private String contentId;

	@ManyToOne
	@JoinColumn(name = "PRECISION_ID", insertable= false, updatable = false)
	private AdPlaylistReqPrecision adPlaylistReqPrecision;
	// Constructors

	public AdPlaylistReqPrecision getAdPlaylistReqPrecision() {
		return adPlaylistReqPrecision;
	}

	public void setAdPlaylistReqPrecision(
			AdPlaylistReqPrecision adPlaylistReqPrecision) {
		this.adPlaylistReqPrecision = adPlaylistReqPrecision;
	}

	/** default constructor */
	public AdPlaylistReqPContent() {
	}

	/** minimal constructor */
	public AdPlaylistReqPContent(long id) {
		this.id = id;
	}

	/** full constructor */
	public AdPlaylistReqPContent(long id, long precisionId,
			String contentPath, String contentType, String contentId) {
		this.id = id;
		this.precisionId = precisionId;
		this.contentPath = contentPath;
		this.contentType = contentType;
		this.contentId = contentId;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPrecisionId() {
		return this.precisionId;
	}

	public void setPrecisionId(long precisionId) {
		this.precisionId = precisionId;
	}

	public String getContentPath() {
		return this.contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentId() {
		return this.contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

}