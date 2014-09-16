package com.avit.ads.pushads.task.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 子广告位信息
 * @author mengjt
 *
 */
@Entity
@Table(name = "T_ADVERTPOSITION")
public class AdvertPosition implements Serializable{

	private static final long serialVersionUID = 3933082652318260190L;
	/** 主键 */
	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 38, scale = 0)
	private Integer id;
	/** 广告位编码 */
	@Column(name = "position_Code")
	private String positionCode;
	/** 广告位名称 */
	@Column(name = "POSITION_NAME")
	private String positionName;
	/** 广告位描述 */
	@Column(name = "DESCRIPTION")
	private String description;
	/** 图片规格ID */
	@Column(name = "IMAGE_RULE_ID")
	private Integer imageRuleId;
	/** 视频规格ID */
	@Column(name = "VIDEO_RULE_ID")
	private Integer videoRuleId;
	/** 文本规格ID */
	@Column(name = "TEXT_RULE_ID")
	private Integer textRuleId;
	/** 问卷规格ID */
	@Column(name = "QUESTION_RULE_ID")
	private Integer questionRuleId;
	/** 是否高清  0：标清 1：高清 */
	@Column(name = "IS_HD")
	private Integer isHD;
	/** 是否叠加  1：是 0：否 */
	@Column(name = "is_Add")
	private Integer isAdd;
	/** 是否轮询  1：是 0：否 */
	@Column(name = "is_Loop")
	private Integer isLoop;
	/** 轮询个数 */
	@Column(name = "loop_Count")
	private Integer loopCount;
	/** 投放方式  0 投放式  1 请求式 */
	@Column(name = "delivery_Mode")
	private Integer deliveryMode;
	/** 价格 */
	@Column(name = "price")
	private String price;
	/** 折扣 */
	@Column(name = "discount")
	private String discount;
	/** 广告位包ID */
	@Column(name = "POSITION_PACKAGE_ID")
	private Integer positionPackageId;
	/** 是否全时段  1：是 0：否 */
	@Column(name = "IS_ALLTIME")
	private Integer isAllTime;
	/** 是否字幕  1：是 0：否 */
	@Column(name = "is_Text")
	private Integer isText;
	/** 是否图片  1：是 0：否 */
	@Column(name = "is_Image")
	private Integer isImage;
	/** 是否视频  1：是 0：否 */
	@Column(name = "is_Video")
	private Integer isVideo;
	/** 是否问卷  1：是 0：否 */
	@Column(name = "is_Question")
	private Integer isQuestion;
	/** 是否区域 1：是 0：否 */
	@Column(name = "is_Area")
	private Integer isArea;
	/** 是否频道  1：是 0：否 */
	@Column(name = "is_Channel")
	private Integer isChannel;
	/** 是否频率  1：是 0：否 */
	@Column(name = "is_Freq")
	private Integer isFreq;
	/** 是否回看频道 1：是 0：否 */
	@Column(name = "is_Lookback")
	private Integer isLookback;
	/** 是否回放频道 1：是 0：否    回放菜单，插播，暂停 */   
	@Column(name = "is_Playback")
	private Integer isPlayback;
	/** 是否处理特征值CPS与外部系统使用 (1：是CPS的广告位 0：否，不是CPS的广告位) */
	@Column(name = "is_Characteristic")
	private Integer isCharacteristic;
	/** 是否回看栏目 1：是 0：否  回看菜单*/	
	@Column(name = "is_Column")
	private Integer isColumn;
	/** 是否回看产品 1：是 0：否  回看插播，暂停 */
	@Column(name = "IS_LOOKBACK_PRODUCT")
	private Integer isLookbackProduct;
	/** 是否影片 1：是 0：否  */
	@Column(name = "is_Asset")
	private Integer isAsset;
	/** 是否随片 1：是 0：否  */
	@Column(name = "is_Follow_Asset")
	private Integer isFollowAsset;


	
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
