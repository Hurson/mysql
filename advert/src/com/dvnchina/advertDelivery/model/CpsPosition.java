package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
import java.util.Date;

public class CpsPosition implements Serializable {

	private static final long serialVersionUID = -9090024144887948184L;
	
	private Integer id;

	private String cname;

	private Integer width;

	private Integer height;

	private Integer top;

	private Integer left;
	
//---------------------------------------------------------
	
	/**
	 * 描述
	 */
	private String describe;
	/**
	 * 图片规格ID
	 */
	private Integer pictureMaterialSpeciId;
	/**
	 * 视频规格ID
	 */
	private Integer videoMaterialSpeciId;
	/**
	 * 文字规格ID
	 */
	private Integer contentMaterialSpeciId;
	/**
	 * 问卷规格ID
	 */
	private Integer questionMaterialSpeciId;
	/**
	 * 是否叠加
	 */
	private Integer isOverlying;

	/**
	 * 广告位编码
	 */
	private String cCode;
	
	/**
	 * 广告位标识
	 */
	
	private String eigenValue;
	
	/**
	 * 修改时间
	 */
//	private Date modifyTime;
	
	private String modifyTime;
	/**
	 * 广告位类型编码
	 */
	private String positionTypeCode;

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Integer getPictureMaterialSpeciId() {
		return pictureMaterialSpeciId;
	}

	public void setPictureMaterialSpeciId(Integer pictureMaterialSpeciId) {
		this.pictureMaterialSpeciId = pictureMaterialSpeciId;
	}

	public Integer getVideoMaterialSpeciId() {
		return videoMaterialSpeciId;
	}

	public void setVideoMaterialSpeciId(Integer videoMaterialSpeciId) {
		this.videoMaterialSpeciId = videoMaterialSpeciId;
	}

	public Integer getContentMaterialSpeciId() {
		return contentMaterialSpeciId;
	}

	public void setContentMaterialSpeciId(Integer contentMaterialSpeciId) {
		this.contentMaterialSpeciId = contentMaterialSpeciId;
	}

	public Integer getQuestionMaterialSpeciId() {
		return questionMaterialSpeciId;
	}

	public void setQuestionMaterialSpeciId(Integer questionMaterialSpeciId) {
		this.questionMaterialSpeciId = questionMaterialSpeciId;
	}

	public Integer getIsOverlying() {
		return isOverlying;
	}

	public void setIsOverlying(Integer isOverlying) {
		this.isOverlying = isOverlying;
	}

	public String getCCode() {
		return cCode;
	}

	public void setCCode(String code) {
		cCode = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public String getEigenValue() {
		return eigenValue;
	}

	public void setEigenValue(String eigenValue) {
		this.eigenValue = eigenValue;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getPositionTypeCode() {
		return positionTypeCode;
	}

	public void setPositionTypeCode(String positionTypeCode) {
		this.positionTypeCode = positionTypeCode;
	}
	
}
