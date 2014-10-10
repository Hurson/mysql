package com.avit.ads.requestads.bean;

public class Content {

	/** 内容ID*/
	private String contentId;
	
	/** 内容类型 1开机素材 2 多图素材 3 字幕素材。如果是单个素材为空*/
	private String contentType;
	
	/** 内容资源 */
	private String content;
	
	/** 内容路径 如：存放内容资源路径的TXT文件路径*/
	private String contentPath;
	
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

}
