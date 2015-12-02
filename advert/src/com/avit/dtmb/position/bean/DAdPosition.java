package com.avit.dtmb.position.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "d_advertposition")
public class DAdPosition implements Serializable {

	private static final long serialVersionUID = 2284044631669410533L;
	
	private Integer Id;
	/**
	 * 广告位名称
	 */
	private String positionName;
	/**
	 *广告位code 
	 */
	private String positionCode;
    /**
     * 广告位类型1、单向实时；2、单向非实时；3、字幕
     */
	private String positionType;
	/**
	 * 高标清标示位
	 */
	private String isHd;
	/**
	 * 素材类型1、图片；2、视频；3、文字
	 */
	private String resourceType;
	/**
	 * 素材个数
	 */
	private int resourceCount;
	/**
	 * 素材规格ID
	 */
	private Integer specificationId;
	/**
	 * 策略列表，多个用“|”分割
	 */
	private String ployTypes;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 预览背景图片路径
	 */
	private String bgImagePath;
	/**
	 * 预览起始坐标
	 */
	private String coordinate;
	/**
	 * 预览区域
	 */
	private String domain;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@Column(name="POSITION_NAME")
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	@Column(name="POSITION_CODE")
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	@Column(name="POSITION_TYPE")
	public String getPositionType() {
		return positionType;
	}
	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}
	@Column(name="IS_HD")
	public String getIsHd() {
		return isHd;
	}
	public void setIsHd(String isHd) {
		this.isHd = isHd;
	}
	@Column(name="RESOURCE_TYPE")
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	@Column(name="RESOURCE_COUNT")
	public int getResourceCount() {
		return resourceCount;
	}
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}
	@Column(name="SPECIFICATION_ID")
	public Integer getSpecificationId() {
		return specificationId;
	}
	public void setSpecificationId(Integer specificationId) {
		this.specificationId = specificationId;
	}
	@Column(name="PLOY_TYPES")
	public String getPloyTypes() {
		return ployTypes;
	}
	public void setPloyTypes(String ployTypes) {
		this.ployTypes = ployTypes;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="BG_IMAGE_PATH")
	public String getBgImagePath() {
		return bgImagePath;
	}
	public void setBgImagePath(String bgImagePath) {
		this.bgImagePath = bgImagePath;
	}
	@Column(name="COORDINATE")
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	@Column(name="DOMAIN")
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	

}
