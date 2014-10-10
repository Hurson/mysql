package com.dvnchina.advertDelivery.ploy.bean;

import java.util.Date;
import java.util.List;

public class AdvertPositionAndType {
	private Integer id;
	/**
	 * 广告位类型
	 */
	private Integer positionTypeId;
	/**
	 * 广告位类型编码 拼接特征值时使用
	 */
	private String positionTypeCode;
	/**
	 * 广告位类型名称 此属性只作为查询条件，不足为入库必选参数
	 */
	private String positionTypeName;
	/**
	 * 是否处理特征值CPS与外部系统使用 1 是 0 否
	 */
	private Integer isCharacteristic;
	/**
	 * 特征标识
	 */
	private String characteristicIdentification;
	/**
	 * 广告位名称
	 */
	private String positionName;
	
	/**
	 * 是否高清（0-否 1-有 2-两个都有）
	 */
	private Integer isHd;
	/**
	 * 是否叠加
	 */
	private Integer isAdd;
	/**
	 * 是否轮询
	 */
	private Integer isLoop;
	/**
	 * 投放方式 以供订单使用Delivery_MODE
	 */
	private Integer deliveryMode;
	
	/**
	 * 状态 0 可用(默认) 1 不可用
	 */
	private String state;
	
	/**
	 * 是否全时段
	 */
	private Integer isAllTime;
	/**
	 * 是否频道
	 */
	private Integer isChannel;
	private Integer isArea;
	/**
	 * 是否字幕  1：是 0：否
	 */
	private Integer isText;
	public AdvertPositionAndType() {
		// TODO Auto-generated constructor stub
	}
	public AdvertPositionAndType(Integer id, Integer positionTypeId,
			String positionTypeCode, String positionTypeName,
			Integer isCharacteristic, String positionName, Integer isHd,
			Integer isAdd, Integer isLoop, Integer deliveryMode,
			Integer isAllTime, Integer isChannel,Integer isArea, Integer isText) {
		this.id = id;
		this.positionTypeId = positionTypeId;
		this.positionTypeCode = positionTypeCode;
		this.positionTypeName = positionTypeName;
		this.isCharacteristic = isCharacteristic;
		this.positionName = positionName;
		this.isHd = isHd;
		this.isAdd = isAdd;
		this.isLoop = isLoop;
		this.deliveryMode = deliveryMode;
		this.isAllTime = isAllTime;
		this.isChannel = isChannel;
		this.isArea=isArea;
		this.isText=isText;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPositionTypeId() {
		return positionTypeId;
	}
	public void setPositionTypeId(Integer positionTypeId) {
		this.positionTypeId = positionTypeId;
	}
	public String getPositionTypeCode() {
		return positionTypeCode;
	}
	public void setPositionTypeCode(String positionTypeCode) {
		this.positionTypeCode = positionTypeCode;
	}
	public String getPositionTypeName() {
		return positionTypeName;
	}
	public void setPositionTypeName(String positionTypeName) {
		this.positionTypeName = positionTypeName;
	}
	public Integer getIsCharacteristic() {
		return isCharacteristic;
	}
	public void setIsCharacteristic(Integer isCharacteristic) {
		this.isCharacteristic = isCharacteristic;
	}
	public String getCharacteristicIdentification() {
		return characteristicIdentification;
	}
	public void setCharacteristicIdentification(String characteristicIdentification) {
		this.characteristicIdentification = characteristicIdentification;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public Integer getIsHd() {
		return isHd;
	}
	public void setIsHd(Integer isHd) {
		this.isHd = isHd;
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
	public Integer getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(Integer deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getIsAllTime() {
		return isAllTime;
	}
	public void setIsAllTime(Integer isAllTime) {
		this.isAllTime = isAllTime;
	}
	public Integer getIsChannel() {
		return isChannel;
	}
	public void setIsChannel(Integer isChannel) {
		this.isChannel = isChannel;
	}
	public Integer getIsArea() {
		return isArea;
	}
	public void setIsArea(Integer isArea) {
		this.isArea = isArea;
	}
	public Integer getIsText() {
		return isText;
	}
	public void setIsText(Integer isText) {
		this.isText = isText;
	}
	
}
