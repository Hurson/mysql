package com.dvnchina.advertDelivery.model;

public class ImageReal extends CommonObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 素材名称
	 */
	private String name;
	/**
	 * 素材大小
	 */
	private String fileSize;
	
	/**
	 * 素材宽度
	 */
	private String fileWidth;
	
	/**
	 * 素材高度
	 */
	private String fileHeigth;
	
	/**
	 * 文件格式
	 */
	private String fileFormat;
	
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 图片Url
	 */
	private String imageUrl;
	
	/**
	 * 正式库存储空间
	 */
	
	private String formalFilePath;
	
	public ImageReal(){}

	public ImageReal(String name, String fileSize,String fileWidth, 
			String fileHeigth, String fileFormat,
			String description, String formalFilePath,String imageUrl) {
		super();
		this.name = name;
		this.fileSize = fileSize;
		this.fileWidth = fileWidth;
		this.fileHeigth = fileHeigth;
		this.fileFormat = fileFormat;
		this.description = description;
		this.formalFilePath = formalFilePath;
		this.imageUrl=imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileWidth() {
		return fileWidth;
	}

	public void setFileWidth(String fileWidth) {
		this.fileWidth = fileWidth;
	}

	public String getFileHeigth() {
		return fileHeigth;
	}

	public void setFileHeigth(String fileHeigth) {
		this.fileHeigth = fileHeigth;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormalFilePath() {
		return formalFilePath;
	}

	public void setFormalFilePath(String formalFilePath) {
		this.formalFilePath = formalFilePath;
	}
	
}
