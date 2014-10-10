package com.dvnchina.advertDelivery.contract.bean;

/**
 * 
 * @ClassName: AdvertPositionPackage 
 * @Description: 广告位包
 * @author wangfei@avit.com.cn
 * @date 2013-4-28 上午10:14:37 
 * @version: v1.0.0
 */
public class AdvertPositionPackage {
	private Integer id;

	private Integer packageType; //广告位类型  0：双向实时广告 1：双向实时请求广告 2：单向实时广告 3：单向非实时广告
	
	private Integer videoType; //高标清标识  0：只支持标清  1：只支持高清  2：高清标清都支持

	private String code;

	private String name;

	private Integer isLoop; //是否轮训  1：是 0：否

	private Integer isAdd; //是否叠加   1：是 0：否

	private Integer positionCount; //子广告位个数
	
	private String ployDesc; //投放策略描述
	
	private String description; //广告位包描述

	private Integer deliveryMode; //投放方式  0 投放式  1 请求式

	private Integer isVideo; //是否可以投放视频   1：是 0：否
	
	private Integer isImage; //是否可以投放图片   1：是 0：否

	private Integer isText; //是否可以投放文字   1：是 0：否
	
	private Integer isQuestion; //是否可以投放问卷   1：是 0：否
	
	public AdvertPositionPackage() {

	}
	public AdvertPositionPackage(
			Integer id,
			Integer packageType,
			Integer videoType,
			String code,
			String name,
			Integer isLoop,
			Integer isAdd,
			Integer positionCount,
			String ployDesc,
			String description,
			Integer deliveryMode,
			Integer isVideo,
			Integer isImage, 
            Integer isText,
            Integer isQuestion
			) {
		this.id = id;
		this.packageType = packageType;
		this.videoType = videoType;
		this.code = code;
		this.name = name;
		this.isLoop = isLoop;
		this.isAdd = isAdd;
		this.positionCount = positionCount;
		this.ployDesc = ployDesc;
		this.description = description;
		this.deliveryMode = deliveryMode;
		this.isVideo = isVideo;
		this.isImage=isImage;
		this.isText=isText;
		this.isQuestion=isQuestion;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	public Integer getIsText() {
		return isText;
	}
	public void setIsText(Integer isText) {
		this.isText = isText;
	}
	public Integer getPackageType() {
		return packageType;
	}
	public void setPackageType(Integer packageType) {
		this.packageType = packageType;
	}
	public Integer getVideoType() {
		return videoType;
	}
	public void setVideoType(Integer videoType) {
		this.videoType = videoType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPositionCount() {
		return positionCount;
	}
	public void setPositionCount(Integer positionCount) {
		this.positionCount = positionCount;
	}
	public String getPloyDesc() {
		return ployDesc;
	}
	public void setPloyDesc(String ployDesc) {
		this.ployDesc = ployDesc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getIsVideo() {
		return isVideo;
	}
	public void setIsVideo(Integer isVideo) {
		this.isVideo = isVideo;
	}
	public Integer getIsImage() {
		return isImage;
	}
	public void setIsImage(Integer isImage) {
		this.isImage = isImage;
	}
	public Integer getIsQuestion() {
		return isQuestion;
	}
	public void setIsQuestion(Integer isQuestion) {
		this.isQuestion = isQuestion;
	}
	
}
