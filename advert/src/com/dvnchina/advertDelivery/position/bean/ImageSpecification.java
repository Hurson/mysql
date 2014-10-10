package com.dvnchina.advertDelivery.position.bean;

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
	 * 高
	 */
	private String imageHeight;
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
	
	public ImageSpecification(){}
	
	public ImageSpecification(
	        Integer id,
            String imageWidth,      
            String imageHeight,
            String type,
            String fileSize        
	){
	    this.setId(id);
	    this.imageWidth=imageWidth;
	    this.imageHeight=imageHeight;
	    this.type=type;
	    this.fileSize=fileSize;
	}
	

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

	public String getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
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
