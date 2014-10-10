package com.dvnchina.advertDelivery.model;

import java.io.Serializable;

public class ImageSpecification implements Serializable {

	private static final long serialVersionUID = 7395099125052893681L;

	private Integer id;
	/**
	 * 图片规格描述
	 */
	private String imageDesc;
	/**
	 * 宽
	 */
	private String imageWidth;
	/**
	 * 长
	 */
	private String imageLength;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 是否有链接
	 */
	private Integer isLink;
	/**
	 * 文件大小
	 */
	private String fileSize;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageDesc() {
		return imageDesc;
	}

	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}

	public String getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getImageLength() {
		return imageLength;
	}

	public void setImageLength(String imageLength) {
		this.imageLength = imageLength;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIsLink() {
		return isLink;
	}

	public void setIsLink(Integer isLink) {
		this.isLink = isLink;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

}
