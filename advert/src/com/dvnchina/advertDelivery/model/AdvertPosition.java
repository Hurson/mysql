package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.bean.marketingRule.MarketingRuleBean;

public class AdvertPosition implements Serializable {

	private static final long serialVersionUID = 3485262462374872516L;

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
	private Date createTime;
	/**
	 * 广告位背景图BACKGROUND_PATH
	 */
	private String backgroundPath;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
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
	/**
	 * 广告位已绑策略【维护合同相关信息时使用】
	 */
	private List<MarketingRuleBean> marketRules;
	/**
	 * 合同绑定广告位时使用 开始时间
	 */
	private Date validStartDate;
	/**
	 * 合同绑定广告位时使用 结束时间
	 */
	private Date validEndDate;
	/**
	 * 不入库，只为前台使用
	 */
	private String chooseMarketRulesElement;
	/**
	 * 不入库，只为前台使用
	 */
	private String currentIndex;
	/**
	 * 不入库，只为前台使用
	 */
	private String chooseState;
	
	/**
	 * 合同绑定广告位时使用 开始时间
	 */
	private String validStartDateShow;
	/**
	 * 合同绑定广告位时使用 结束时间
	 */
	private String validEndDateShow;
	/**
	 * 已选规则
	 */
	private String chooseMarketRules;
	/**
	 * 是否修改标记  0 默认 1 新增 2 删除 3 修改
	 */
	private String modify;
	/**
	 * 关系表中id
	 */
	private List<Integer> tabIdList;
	/**
	 * 前台元素的唯一标识
	 */
	private String positionIndexFlag;
	/**
	 * 是否全时段
	 */
	private Integer isAllTime;
	/**
	 * 是否频道
	 */
	private Integer isChannel;
	
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

	/**
	 * 重新hashCode方法
	 * @return
	 */
	public int compareHashCode() {
		String characteristicIdentification = this.characteristicIdentification;
		if(this.modifyTime != null){
			characteristicIdentification += modifyTime;
		}
		return characteristicIdentification.hashCode();
	}
	
	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public Integer getId() {
		return id;
	}

	public String getChooseMarketRules() {
		return chooseMarketRules;
	}

	public void setChooseMarketRules(String chooseMarketRules) {
		this.chooseMarketRules = chooseMarketRules;
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

	public void setCharacteristicIdentification(
			String characteristicIdentification) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBackgroundPath() {
		return backgroundPath;
	}

	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
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

	public List<MarketingRuleBean> getMarketRules() {
		return marketRules;
	}

	public void setMarketRules(List<MarketingRuleBean> marketRules) {
		this.marketRules = marketRules;
	}

	public void setWidthHeight(String widthHeight) {
		this.widthHeight = widthHeight;
	}

	public Date getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	public String getChooseMarketRulesElement() {
		return chooseMarketRulesElement;
	}

	public void setChooseMarketRulesElement(String chooseMarketRulesElement) {
		this.chooseMarketRulesElement = chooseMarketRulesElement;
	}

	public String getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(String currentIndex) {
		this.currentIndex = currentIndex;
	}

	public String getChooseState() {
		return chooseState;
	}

	public void setChooseState(String chooseState) {
		this.chooseState = chooseState;
	}

	public String getValidStartDateShow() {
		return validStartDateShow;
	}

	public void setValidStartDateShow(String validStartDateShow) {
		this.validStartDateShow = validStartDateShow;
	}

	public String getValidEndDateShow() {
		return validEndDateShow;
	}

	public void setValidEndDateShow(String validEndDateShow) {
		this.validEndDateShow = validEndDateShow;
	}

	public List<Integer> getTabIdList() {
		return tabIdList;
	}

	public void setTabIdList(List<Integer> tabIdList) {
		this.tabIdList = tabIdList;
	}

	public String getPositionIndexFlag() {
		return positionIndexFlag;
	}

	public void setPositionIndexFlag(String positionIndexFlag) {
		this.positionIndexFlag = positionIndexFlag;
	}

	public String getPositionTypeCode() {
		return positionTypeCode;
	}

	public void setPositionTypeCode(String positionTypeCode) {
		this.positionTypeCode = positionTypeCode;
	}

	public Integer getIsCharacteristic() {
		return isCharacteristic;
	}

	public void setIsCharacteristic(Integer isCharacteristic) {
		this.isCharacteristic = isCharacteristic;
	}
	

}
