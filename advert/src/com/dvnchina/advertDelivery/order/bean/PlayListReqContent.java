package com.dvnchina.advertDelivery.order.bean;

/**
 * 请求式内容实体
 * */
public class PlayListReqContent {

	/** 序号 */
	private Integer id;
	
	/** 播出单号 */
	private Integer playListId;
	
	/** 内容类型 */
	private String contentType;
	
	/** 内容路径 */
	private String contentPath;
	
	/** 内容id */
	private String contentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlayListId() {
		return playListId;
	}

	public void setPlayListId(Integer playListId) {
		this.playListId = playListId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentPath() {
		return contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

}
