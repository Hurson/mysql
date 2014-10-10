package com.dvnchina.advertDelivery.ploy.bean;

import java.io.Serializable;
import java.util.List;

import com.dvnchina.advertDelivery.model.ResourceReal;

/**
 * 子广告位信息
 * @author mengjt
 *
 */
public class ContractAdvertPosition implements Serializable{

	private static final long serialVersionUID = 3933082652318260190L;
	/** 主键 */
	private Integer id;
	/** 广告位编码 */
	private String positionCode;
	/** 广告位名称 */
	private String positionName;
	/** 广告位描述 */
	private String description;
	/** 图片规格ID */
	private Integer imageRuleId;
	/** 视频规格ID */
	private Integer videoRuleId;
	/** 文本规格ID */
	private Integer textRuleId;
	/** 问卷规格ID */
	private Integer questionRuleId;
	/** 是否高清  0：标清 1：高清 */
	private Integer isHD;
	/** 是否叠加  1：是 0：否 */
	private Integer isAdd;
	/** 是否轮询  1：是 0：否 */
	private Integer isLoop;
	/** 轮询个数 */
	private Integer loopCount;
	/** 投放方式  0 投放式  1 请求式 */
	private Integer deliveryMode;
	/** 价格 */
	private String price;
	/** 折扣 */
	private String discount;
	/** 广告位包ID */
	private Integer positionPackageId;
	/** 广告位包名称 */
	private String positionPackageName;
	/** 背景图路径 */
	private String backgroundPath;
	/** 坐标   x*y */
	private String coordinate;
	/** 宽,高 如 800*600 */
	private String widthHeight;
	/** 是否全时段  1：是 0：否 */
	private Integer isAllTime;
	/** 是否字幕  1：是 0：否 */
	private Integer isText;
	/** 是否图片  1：是 0：否 */
	private Integer isImage;
	/** 是否视频  1：是 0：否 */
	private Integer isVideo;
	/** 是否问卷  1：是 0：否 */
	private Integer isQuestion;
	/** 是否区域 1：是 0：否 */
	private Integer isArea;
	/** 是否频道  1：是 0：否 */
	private Integer isChannel;
	/** 是否频率  1：是 0：否 */
	private Integer isFreq;
	/** 是否回看频道 1：是 0：否 */
	private Integer isLookback;
	/** 是否回放频道 1：是 0：否    回放菜单，插播，暂停 */   
	private Integer isPlayback;
	/** 是否处理特征值CPS与外部系统使用 (1：是CPS的广告位 0：否，不是CPS的广告位) */
	private Integer isCharacteristic;
	/** 是否回看栏目 1：是 0：否  回看菜单*/	
	private Integer isColumn;
	/** 是否回看产品 1：是 0：否  回看插播，暂停 */
	private Integer isLookbackProduct;
	
	/** 合同主键ID */
	private Integer contractId;
	/** 规则编码 */
	private Integer marketingRuleId;
	private Integer isAsset;
	/** 是否随片 1：是 0：否  */
	private Integer isFollowAsset;
	
	/** 广告位对应的素材名称 */
	private String resourceNames;
	
	public ContractAdvertPosition(Integer id, String positionName,
			Integer isAllTime, Integer isArea, Integer isChannel,
			Integer isFreq, Integer isPlayback, Integer isCharacteristic,
			Integer isColumn, Integer isLookbackProduct, Integer contractId) {
		//super();
		this.id = id;
		this.positionName = positionName;
		this.isAllTime = isAllTime;
		this.isArea = isArea;
		this.isChannel = isChannel;
		this.isFreq = isFreq;
		this.isPlayback = isPlayback;
		this.isCharacteristic = isCharacteristic;
		this.isColumn = isColumn;
		this.isLookbackProduct = isLookbackProduct;
		this.contractId = contractId;
		//this.ruleId=ruleId;
	}
	
	public ContractAdvertPosition(Integer id, String positionName, String positionCode,
			Integer isAllTime, Integer isArea, Integer isChannel,
			Integer isFreq, Integer isPlayback, Integer isCharacteristic,
			Integer isColumn, Integer isLookbackProduct, Integer contractId,Integer marketingRuleId,Integer isAsset,Integer isFollowAsset) {
		//super();
		this.id = id;
		this.positionName = positionName;
		this.positionCode = positionCode;
		this.isAllTime = isAllTime;
		this.isArea = isArea;
		this.isChannel = isChannel;
		this.isFreq = isFreq;
		this.isPlayback = isPlayback;
		this.isCharacteristic = isCharacteristic;
		this.isColumn = isColumn;
		this.isLookbackProduct = isLookbackProduct;
		this.contractId = contractId;
		this.marketingRuleId=marketingRuleId;
		this.isAsset=isAsset;
		this.isFollowAsset=isFollowAsset;
	}

	public ContractAdvertPosition(Integer id, String positionName, String positionCode,
			Integer isAllTime, Integer isArea, Integer isChannel,
			Integer isFreq, Integer isPlayback, Integer isCharacteristic,
			Integer isColumn, Integer isLookbackProduct, Integer isAsset,Integer isFollowAsset) {
		//super();
		this.id = id;
		this.positionName = positionName;
		this.positionCode = positionCode;
		this.isAllTime = isAllTime;
		this.isArea = isArea;
		this.isChannel = isChannel;
		this.isFreq = isFreq;
		this.isPlayback = isPlayback;
		this.isCharacteristic = isCharacteristic;
		this.isColumn = isColumn;
		this.isLookbackProduct = isLookbackProduct;
			this.isAsset=isAsset;
		this.isFollowAsset=isFollowAsset;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
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
	public Integer getVideoRuleId() {
		return videoRuleId;
	}
	public void setVideoRuleId(Integer videoRuleId) {
		this.videoRuleId = videoRuleId;
	}
	public Integer getTextRuleId() {
		return textRuleId;
	}
	public void setTextRuleId(Integer textRuleId) {
		this.textRuleId = textRuleId;
	}
	public Integer getQuestionRuleId() {
		return questionRuleId;
	}
	public void setQuestionRuleId(Integer questionRuleId) {
		this.questionRuleId = questionRuleId;
	}
	public Integer getIsHD() {
		return isHD;
	}
	public void setIsHD(Integer isHD) {
		this.isHD = isHD;
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
	public Integer getLoopCount() {
		return loopCount;
	}
	public void setLoopCount(Integer loopCount) {
		this.loopCount = loopCount;
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
	public Integer getPositionPackageId() {
		return positionPackageId;
	}
	public void setPositionPackageId(Integer positionPackageId) {
		this.positionPackageId = positionPackageId;
	}
	public String getPositionPackageName() {
		return positionPackageName;
	}
	public void setPositionPackageName(String positionPackageName) {
		this.positionPackageName = positionPackageName;
	}
	public String getBackgroundPath() {
		return backgroundPath;
	}
	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public String getWidthHeight() {
		return widthHeight;
	}
	public void setWidthHeight(String widthHeight) {
		this.widthHeight = widthHeight;
	}
	public Integer getIsAllTime() {
		return isAllTime;
	}
	public void setIsAllTime(Integer isAllTime) {
		this.isAllTime = isAllTime;
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
	public Integer getIsLookback() {
		return isLookback;
	}
	public void setIsLookback(Integer isLookback) {
		this.isLookback = isLookback;
	}
	public Integer getIsPlayback() {
		return isPlayback;
	}
	public void setIsPlayback(Integer isPlayback) {
		this.isPlayback = isPlayback;
	}
	public Integer getIsCharacteristic() {
		return isCharacteristic;
	}
	public void setIsCharacteristic(Integer isCharacteristic) {
		this.isCharacteristic = isCharacteristic;
	}
	public String getResourceNames() {
		return resourceNames;
	}
	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}
	public Integer getIsColumn() {
		return isColumn;
	}
	public void setIsColumn(Integer isColumn) {
		this.isColumn = isColumn;
	}
	public Integer getIsLookbackProduct() {
		return isLookbackProduct;
	}
	public void setIsLookbackProduct(Integer isLookbackProduct) {
		this.isLookbackProduct = isLookbackProduct;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getMarketingRuleId() {
		return marketingRuleId;
	}

	public void setMarketingRuleId(Integer marketingRuleId) {
		this.marketingRuleId = marketingRuleId;
	}

	public Integer getIsAsset() {
		return isAsset;
	}

	public void setIsAsset(Integer isAsset) {
		this.isAsset = isAsset;
	}

	public Integer getIsFollowAsset() {
		return isFollowAsset;
	}

	public void setIsFollowAsset(Integer isFollowAsset) {
		this.isFollowAsset = isFollowAsset;
	}

}
