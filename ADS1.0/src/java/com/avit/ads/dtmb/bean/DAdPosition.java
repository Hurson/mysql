package com.avit.ads.dtmb.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name = "d_advertposition")
public class DAdPosition implements Serializable {

	private static final long serialVersionUID = 2284044631669410533L;
	
	private Integer id;
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
	 * 主策略类型（用来求笛卡尔积,时段和区域除外）
	 */
	private String mainPloy;
	/**
	 * 描述
	 */
	private String description;
	
	private String position;
	
	private String filePath="";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	@Column(name="MAIN_PLOY")
	public String getMainPloy() {
		return mainPloy;
	}
	public void setMainPloy(String mainPloy) {
		this.mainPloy = mainPloy;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="POSITION")
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Transient
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
