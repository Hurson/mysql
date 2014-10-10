package com.dvnchina.advertDelivery.bean.position;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 保存数据时使用
 * @author lester
 *
 */
public class PositionBean implements Serializable{
	
	private static final long serialVersionUID = -6878465724985073634L;
	
	private Integer id;
	/**
	 * 广告位类型
	 */
	private Integer positionTypeId;
	/**
	 * 广告位类型名称 此属性只作为查询条件，不足为入库必选参数
	 */
	private String positionTypeName;
	/**
	 * 特征标识
	 */
	private String characteristicIdentification;
	/**
	 * 广告位名称
	 */
	private String positionName;
	/**
	 * 描述DESCRIPTION
	 */
	private String description;
	/**
	 * 图片规格ID
	 */
	private Integer imageRuleId;
	/**
	 * 图片规格ID List 不入库，只在页面中使用
	 */
	private String imageRuleIdList;
	/**
	 * 视频规格ID
	 */
	private Integer videoRuleId;
	/**
	 * 视频规格ID List 不入库，只在页面中使用
	 */
	private String videoRuleIdList;
	/**
	 * 文字规格ID
	 */
	private Integer textRuleId;
	/**
	 * 文字规格ID List 不入库，只在页面中使用
	 */
	private String textRuleIdList;
	/**
	 * 问卷规格ID
	 */
	private Integer questionRuleId;
	/**
	 * 问卷规格ID List 不入库，只在页面中使用
	 */
	private String questionRuleIdList;
	/**
	 * 素材选择概述
	 */
	private String describeChooseSpeci;
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
	 * 轮询素材个数
	 */
	private Integer materialNumber;
	/**
	 * 投放方式 以供订单使用Delivery_MODE
	 */
	private Integer deliveryMode;
	/**
	 * 价格
	 */
	private String price;
	/**
	 * 折扣
	 */
	private String discount;
	/**
	 * 操作人员
	 */
	private String operationId;
	/**
	 * 状态 0 可用(默认) 1 不可用
	 */
	private String state;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 广告位背景图BACKGROUND_PATH
	 */
	private String backgroundPath;
	/**
	 * 修改时间
	 */
	private Timestamp modifyTime;
	/**
	 * 广告位图片坐标
	 */
	private String coordinate;
	/**
	 * 投放平台 生成播出计划单使用
	 */
	private String deliveryPlatform;
	/**
	 * 投放节点ID
	 */
	private String categoryId;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 宽-高
	 */
	private String widthHeight;
	
	public int compareHashCode() {
		String characteristicIdentification = this.characteristicIdentification;
		if(this.modifyTime != null){
			characteristicIdentification += modifyTime;
		}
		return characteristicIdentification.hashCode();
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

	public String getPositionTypeName() {
		return positionTypeName;
	}

	public void setPositionTypeName(String positionTypeName) {
		this.positionTypeName = positionTypeName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getImageRuleId() {
		return imageRuleId;
	}

	public void setImageRuleId(Integer imageRuleId) {
		this.imageRuleId = imageRuleId;
	}

	public String getImageRuleIdList() {
		return imageRuleIdList;
	}

	public void setImageRuleIdList(String imageRuleIdList) {
		this.imageRuleIdList = imageRuleIdList;
	}

	public Integer getVideoRuleId() {
		return videoRuleId;
	}

	public void setVideoRuleId(Integer videoRuleId) {
		this.videoRuleId = videoRuleId;
	}

	public String getVideoRuleIdList() {
		return videoRuleIdList;
	}

	public void setVideoRuleIdList(String videoRuleIdList) {
		this.videoRuleIdList = videoRuleIdList;
	}

	public Integer getTextRuleId() {
		return textRuleId;
	}

	public void setTextRuleId(Integer textRuleId) {
		this.textRuleId = textRuleId;
	}

	public String getTextRuleIdList() {
		return textRuleIdList;
	}

	public void setTextRuleIdList(String textRuleIdList) {
		this.textRuleIdList = textRuleIdList;
	}

	public Integer getQuestionRuleId() {
		return questionRuleId;
	}

	public void setQuestionRuleId(Integer questionRuleId) {
		this.questionRuleId = questionRuleId;
	}

	public String getQuestionRuleIdList() {
		return questionRuleIdList;
	}

	public void setQuestionRuleIdList(String questionRuleIdList) {
		this.questionRuleIdList = questionRuleIdList;
	}

	public String getDescribeChooseSpeci() {
		return describeChooseSpeci;
	}

	public void setDescribeChooseSpeci(String describeChooseSpeci) {
		this.describeChooseSpeci = describeChooseSpeci;
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

	public Integer getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(Integer materialNumber) {
		this.materialNumber = materialNumber;
	}

	public Integer getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(Integer deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getBackgroundPath() {
		return backgroundPath;
	}

	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getDeliveryPlatform() {
		return deliveryPlatform;
	}

	public void setDeliveryPlatform(String deliveryPlatform) {
		this.deliveryPlatform = deliveryPlatform;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getWidthHeight() {
		return widthHeight;
	}

	public void setWidthHeight(String widthHeight) {
		this.widthHeight = widthHeight;
	}
}
