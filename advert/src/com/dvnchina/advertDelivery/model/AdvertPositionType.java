package com.dvnchina.advertDelivery.model;

import java.io.Serializable;

/**
 * 广告位类型
 * 
 * @author lester
 * 
 */
public class AdvertPositionType implements Serializable{

	private static final long serialVersionUID = 8084306811021791488L;
	
	private Integer id;
	/**
	 * 广告位类型编码
	 */
	private String positionTypeCode;
	/**
	 * 广告位类型名称
	 */
	private String positionTypeName;
	/**
	 * 是否叠加   1：是 0：否
	 */
	private Integer isAdd;
	/**
	 * 是否轮训  1：是 0：否
	 */
	private Integer isLoop;
	/**
	 * 是否全时段  1：是 0：否
	 */
	private Integer isAlltime;
	/**
	 * 是否字幕  1：是 0：否
	 */
	private Integer isText;
	/**
	 * 是否图片  1：是 0：否
	 */
	private Integer isImage;
	/**
	 * 是否视频  1：是 0：否
	 */
	private Integer isVideo;
	/**
	 * 是否问卷  1：是 0：否
	 */
	private Integer isQuestion;
	/**
	 * 是否区域
	 */
	private Integer isArea;
	/**
	 * 是否频道  1：是 0：否
	 */
	private Integer isChannel;
	/**
	 * 是否频率  1：是 0：否
	 */
	private Integer isFreq;
	/**
	 * 投放方式 0 投放式  1 请求式
	 */
	private Integer deliveryType;
	/**
	 * 是否处理特征值CPS与外部系统使用 
	 */
	private Integer isCharacteristic;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPositionTypeName() {
		return positionTypeName;
	}

	public void setPositionTypeName(String positionTypeName) {
		this.positionTypeName = positionTypeName;
	}

	public String getPositionTypeCode() {
		return positionTypeCode;
	}

	public void setPositionTypeCode(String positionTypeCode) {
		this.positionTypeCode = positionTypeCode;
	}

	public Integer getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(Integer isAdd) {
		this.isAdd = isAdd;
	}

	public Integer getIsLoop() {
		return isLoop;
	}

	public void setIsLoop(Integer isLoop) {
		this.isLoop = isLoop;
	}

	public Integer getIsAlltime() {
		return isAlltime;
	}

	public void setIsAlltime(Integer isAlltime) {
		this.isAlltime = isAlltime;
	}

	public Integer getIsText() {
		return isText;
	}

	public void setIsText(Integer isText) {
		this.isText = isText;
	}

	public Integer getIsImage() {
		return isImage;
	}

	public void setIsImage(Integer isImage) {
		this.isImage = isImage;
	}

	public Integer getIsVideo() {
		return isVideo;
	}

	public void setIsVideo(Integer isVideo) {
		this.isVideo = isVideo;
	}

	public Integer getIsQuestion() {
		return isQuestion;
	}

	public void setIsQuestion(Integer isQuestion) {
		this.isQuestion = isQuestion;
	}

	public Integer getIsArea() {
		return isArea;
	}

	public void setIsArea(Integer isArea) {
		this.isArea = isArea;
	}

	public Integer getIsChannel() {
		return isChannel;
	}

	public void setIsChannel(Integer isChannel) {
		this.isChannel = isChannel;
	}

	public Integer getIsFreq() {
		return isFreq;
	}

	public void setIsFreq(Integer isFreq) {
		this.isFreq = isFreq;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Integer getIsCharacteristic() {
		return isCharacteristic;
	}

	public void setIsCharacteristic(Integer isCharacteristic) {
		this.isCharacteristic = isCharacteristic;
	}
}
