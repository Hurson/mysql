/*
 * @(#)Material.java
 *
 * 2012-3-6
 *
 * Copyright 2008 dvnchina, Inc. All Rights Reserved. 
 */
package com.dvnchina.advertDelivery.model;

/**
 * 素材实体
 * 
 * @author zf
 * 
 */
public class Material extends CommonObject {

	private static final long serialVersionUID = 1L;

	/**
	 * 素材名称
	 */
	private String name;

	/**
	 * 类型 ：0-视频， 1-图片，2-文字，3-压缩包
	 */
	private int type;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 素材大小
	 */
	private String fileSize;
	
	/**
	 * 素材路径
	 */
	private String path;
	
	/**
	 * 状态：0-未绑定，1-已绑定
	 */
	private int state = 0;

	/**
	 * 广告位的ID
	 */
	private int positionId;
	
	/**
	 * 广告位
	 */
	private AdvertPosition position;

	
	/**
	 * 类别id
	 */
	private int typeId; 
	
	/**
	 * 是否是高清类型，0-标清，1-高清
	 */
	private int isHDType;
	
	/**
	 * 软件版本
	 */
	private String STBVersion;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public AdvertPosition getPosition() {
		return position;
	}

	public void setPosition(AdvertPosition position) {
		this.position = position;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getIsHDType() {
		return isHDType;
	}

	public void setIsHDType(int isHDType) {
		this.isHDType = isHDType;
	}

	public String getSTBVersion() {
		return STBVersion;
	}

	public void setSTBVersion(String sTBVersion) {
		STBVersion = sTBVersion;
	}

	@Override
	public String toString() {
		return "Material [content=" + content + ", fileSize=" + fileSize + ", isHDType=" + isHDType + ", name=" 
				+ name + ", path=" + path + ", position=" + position + ", positionId=" + positionId
				+ ", state=" + state + ", type=" + type + ", typeId=" + typeId + "]";
	}

		
}
