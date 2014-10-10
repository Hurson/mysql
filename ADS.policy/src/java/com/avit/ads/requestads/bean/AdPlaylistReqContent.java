package com.avit.ads.requestads.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * AdPlaylistReqContent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_PLAYLIST_REQ_CONTENT")
public class AdPlaylistReqContent implements java.io.Serializable {

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
	@Column(name = "CONTENT_TYPE")
	private String contentType;
	@Column(name = "CONTENT_PATH")
	private String contentPath;
	@Column(name = "CONTENT_ID")
	private String contentId;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PLAYLIST_ID",  updatable = false, insertable = false)
	private AdPlaylistReq adPlaylistReq;

	public AdPlaylistReq getAdPlaylistReq() {
		return adPlaylistReq;
	}

	public void setAdPlaylistReq(AdPlaylistReq adPlaylistReq) {
		this.adPlaylistReq = adPlaylistReq;
	}

	/** default constructor */
	public AdPlaylistReqContent() {
	}

	/** minimal constructor */
	public AdPlaylistReqContent(long id) {
		this.id = id;
	}

	/** full constructor */
	public AdPlaylistReqContent(long id, long playlistId,
			String contentType, String contentPath, String contentId) {
		this.id = id;
		this.playlistId = playlistId;
		this.contentType = contentType;
		this.contentPath = contentPath;
		this.contentId = contentId;
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

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentPath() {
		return this.contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	public String getContentId() {
		return this.contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

}