package com.dvnchina.advertDelivery.position.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 广告位包信息
 * @author mengjt
 *
 */
public class PositionPackage implements Serializable{

	private static final long serialVersionUID = 8437375280898613682L;
	/** 主键 */
	private Integer id;
	/** 广告位包编码 */
	private String positionPackageCode;
	/** 广告位包名称 */
	private String positionPackageName;
	/** 广告位类型  0：双向实时广告 1：双向实时请求广告 2：单向实时广告 3：单向非实时广告 */
	private Integer positionPackageType;
	/** 高标清标识  0：只支持标清  1：只支持高清  2：高清标清都支持 */
	private Integer videoType;
	/** 是否轮询  1：是 0：否 */
	private Integer isLoop;
	/** 是否叠加   1：是 0：否 */
	private Integer isAdd;
	/** 子广告位个数 */
	private Integer positionCount;
	/** 投放策略描述 */
	private String ployDescription;
	/** 广告位包描述 */
	private String description;
	/** 投放方式  0 投放式  1 请求式 */
	private Integer deliveryMode;
	/** 是否可以投放视频   1：是 0：否 */
	private Integer isVideo;
	/** 是否可以投放图片   1：是 0：否 */
	private Integer isImage;
	/** 是否可以投放文字   1：是 0：否 */
	private Integer isText;
	/** 是否可以投放问卷   1：是 0：否 */
	private Integer isQuestion;
	/** 子广告位列表 */
	private List<AdvertPosition> adPositionList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPositionPackageCode() {
		return positionPackageCode;
	}
	public void setPositionPackageCode(String positionPackageCode) {
		this.positionPackageCode = positionPackageCode;
	}
	public String getPositionPackageName() {
		return positionPackageName;
	}
	public void setPositionPackageName(String positionPackageName) {
		this.positionPackageName = positionPackageName;
	}
	public Integer getPositionPackageType() {
		return positionPackageType;
	}
	public void setPositionPackageType(Integer positionPackageType) {
		this.positionPackageType = positionPackageType;
	}
	public Integer getVideoType() {
		return videoType;
	}
	public void setVideoType(Integer videoType) {
		this.videoType = videoType;
	}
	public Integer getIsLoop() {
		return isLoop;
	}
	public void setIsLoop(Integer isLoop) {
		this.isLoop = isLoop;
	}
	public Integer getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(Integer isAdd) {
		this.isAdd = isAdd;
	}
	public Integer getPositionCount() {
		return positionCount;
	}
	public void setPositionCount(Integer positionCount) {
		this.positionCount = positionCount;
	}
	public String getPloyDescription() {
		return ployDescription;
	}
	public void setPloyDescription(String ployDescription) {
		this.ployDescription = ployDescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(Integer deliveryMode) {
		this.deliveryMode = deliveryMode;
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
	public Integer getIsText() {
		return isText;
	}
	public void setIsText(Integer isText) {
		this.isText = isText;
	}
	public Integer getIsQuestion() {
		return isQuestion;
	}
	public void setIsQuestion(Integer isQuestion) {
		this.isQuestion = isQuestion;
	}
	public List<AdvertPosition> getAdPositionList() {
		return adPositionList;
	}
	public void setAdPositionList(List<AdvertPosition> adPositionList) {
		this.adPositionList = adPositionList;
	}
}
