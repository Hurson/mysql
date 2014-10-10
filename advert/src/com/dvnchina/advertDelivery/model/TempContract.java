package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.bean.marketingRule.MarketingRuleBean;

/**
 * 封装合同相关字段，编辑合同时使用
 * 
 * @author lester
 */
public class TempContract implements Serializable {

	private static final long serialVersionUID = -1895942834887240903L;
	/**
	 * 关系表T_CONTRACT_AD_BACKUP记录主键id
	 */
	private Integer tabId;
	/**
	 * 合同id
	 */
	private Integer contractId;
	/**
	 * 合同编号
	 */
	private String contractNumber;
	/**
	 * 合同代码
	 */
	private String contractCode;
	/**
	 * 合同名称
	 */
	private String contractName;
	/**
	 * 送审单位
	 */
	private String submitUnits;
	/**
	 * 合同金额
	 */
	private String financialInformation;
	/**
	 * 审批文号
	 */
	private String approvalCode;
	/**
	 * 素材路径
	 */
	private String metarialPath;
	/**
	 * 合同有效期的开始时间
	 */
	private Date effectiveStartDate;
	/**
	 * 合同有效期的结束时间
	 */
	private Date effectiveEndDate;
	/**
	 * 审批文号有效期的开始日期
	 */
	private Date approvalStartDate;
	/**
	 * 审批文号有效期的截止日期
	 */
	private Date approvalEndDate;
	/**
	 * 其他内容
	 */
	private String otherContent;
	/**
	 * 审批意见
	 */
	private String examinationOpinions;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 备注描述
	 */
	private String contractDesc;
	/**
	 * 广告位id
	 */
	private Integer positionId;
	/**
	 * 广告位名称
	 */
	private String positionName;
	/**
	 * 客户id 从t_contract_ad_backup表中直接获取
	 */
	private Integer advertisersId;
	/**
	 * 客户名称 从t_contract_ad_backup表中直接获取表中直接获取
	 */
	private String advertisersName;
	/**
	 * 广告位类型id 从 t_contract_ad_backup表中直接获取
	 */
	private String adType;
	/**
	 * 广告位类型名称 从 t_contract_ad_backup表中直接获取
	 */
	private String adTypeName;
	/**
	 * 合同绑定广告位时使用 开始时间 从t_contract_ad_backup表中直接获取
	 */
	private Date validStart;
	/**
	 * 合同绑定广告位时使用 结束时间 从t_contract_ad_backup表中直接获取
	 */
	private Date validEnd;
	/**
	 * 规则主键
	 */
	private Integer mrId;
	/** 规则名称 */
	private String mrRuleName;
	/** 开始日期 */
	private String mrStartTime;
	/** 结束日期 */
	private String mrEndTime;
	/** 广告位 */
	private String mrPositionName;
	/** 地区 */
	private String mrAreaName;
	/** 频道 */
	private String mrChannelName;
	/** 创建时间 */
	private Date mrCreateTime;
	/** 状态 */
	private Integer mrState;
	/**
	 *  marketRuleId 
	 */
	private Integer marketingRuleId;
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
	private List<MarketingRuleBean> alreadyChooseMarketRules;
	/**
	 * 合同绑定广告位时使用 开始时间
	 */
	private Date validStartDate;
	/**
	 * 合同绑定广告位时使用 结束时间
	 */
	private Date validEndDate;
	/**
	 * 中间表为空时，直接冲合同表中直接获取
	 */
	private Integer customerId;
	
	public Integer getTabId() {
		return tabId;
	}
	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getSubmitUnits() {
		return submitUnits;
	}
	public void setSubmitUnits(String submitUnits) {
		this.submitUnits = submitUnits;
	}
	public String getFinancialInformation() {
		return financialInformation;
	}
	public void setFinancialInformation(String financialInformation) {
		this.financialInformation = financialInformation;
	}
	public String getApprovalCode() {
		return approvalCode;
	}
	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}
	public String getMetarialPath() {
		return metarialPath;
	}
	public void setMetarialPath(String metarialPath) {
		this.metarialPath = metarialPath;
	}
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public Date getApprovalStartDate() {
		return approvalStartDate;
	}
	public void setApprovalStartDate(Date approvalStartDate) {
		this.approvalStartDate = approvalStartDate;
	}
	public Date getApprovalEndDate() {
		return approvalEndDate;
	}
	public void setApprovalEndDate(Date approvalEndDate) {
		this.approvalEndDate = approvalEndDate;
	}
	public String getOtherContent() {
		return otherContent;
	}
	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContractDesc() {
		return contractDesc;
	}
	public void setContractDesc(String contractDesc) {
		this.contractDesc = contractDesc;
	}
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public Integer getAdvertisersId() {
		return advertisersId;
	}
	public void setAdvertisersId(Integer advertisersId) {
		this.advertisersId = advertisersId;
	}
	public String getAdvertisersName() {
		return advertisersName;
	}
	public void setAdvertisersName(String advertisersName) {
		this.advertisersName = advertisersName;
	}
	public String getAdType() {
		return adType;
	}
	public void setAdType(String adType) {
		this.adType = adType;
	}
	public String getAdTypeName() {
		return adTypeName;
	}
	public void setAdTypeName(String adTypeName) {
		this.adTypeName = adTypeName;
	}
	public Date getValidStart() {
		return validStart;
	}
	public void setValidStart(Date validStart) {
		this.validStart = validStart;
	}
	public Date getValidEnd() {
		return validEnd;
	}
	public void setValidEnd(Date validEnd) {
		this.validEnd = validEnd;
	}
	public Integer getMrId() {
		return mrId;
	}
	public void setMrId(Integer mrId) {
		this.mrId = mrId;
	}
	public String getMrRuleName() {
		return mrRuleName;
	}
	public void setMrRuleName(String mrRuleName) {
		this.mrRuleName = mrRuleName;
	}
	
	public String getMrStartTime() {
		return mrStartTime;
	}
	public void setMrStartTime(String mrStartTime) {
		this.mrStartTime = mrStartTime;
	}
	public String getMrEndTime() {
		return mrEndTime;
	}
	public void setMrEndTime(String mrEndTime) {
		this.mrEndTime = mrEndTime;
	}
	public String getMrPositionName() {
		return mrPositionName;
	}
	public void setMrPositionName(String mrPositionName) {
		this.mrPositionName = mrPositionName;
	}
	public String getMrAreaName() {
		return mrAreaName;
	}
	public void setMrAreaName(String mrAreaName) {
		this.mrAreaName = mrAreaName;
	}
	public String getMrChannelName() {
		return mrChannelName;
	}
	public void setMrChannelName(String mrChannelName) {
		this.mrChannelName = mrChannelName;
	}
	public Date getMrCreateTime() {
		return mrCreateTime;
	}
	public void setMrCreateTime(Date mrCreateTime) {
		this.mrCreateTime = mrCreateTime;
	}
	public Integer getMrState() {
		return mrState;
	}
	public void setMrState(Integer mrState) {
		this.mrState = mrState;
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
	public void setWidthHeight(String widthHeight) {
		this.widthHeight = widthHeight;
	}
	public List<MarketingRuleBean> getAlreadyChooseMarketRules() {
		return alreadyChooseMarketRules;
	}
	public void setAlreadyChooseMarketRules(
			List<MarketingRuleBean> alreadyChooseMarketRules) {
		this.alreadyChooseMarketRules = alreadyChooseMarketRules;
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
	public String getExaminationOpinions() {
		return examinationOpinions;
	}
	public void setExaminationOpinions(String examinationOpinions) {
		this.examinationOpinions = examinationOpinions;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getMarketingRuleId() {
		return marketingRuleId;
	}
	public void setMarketingRuleId(Integer marketingRuleId) {
		this.marketingRuleId = marketingRuleId;
	}
	
	

}
