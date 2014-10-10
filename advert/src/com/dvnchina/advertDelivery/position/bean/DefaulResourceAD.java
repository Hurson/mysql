package com.dvnchina.advertDelivery.position.bean;

import java.io.Serializable;

public class DefaulResourceAD implements Serializable{
	
	private static final long serialVersionUID = -1710825651005627933L;
	/** 主键 */
	private Integer id;
	/** 广告位包ID */
	private Integer positionPackageId;
	/** 广告位包名称 */
	private String positionPackageName;
	/** 广告位包类型 */
	private Integer positionPackageType;
	/** 子广告位ID */
	private Integer adId;
	/** 子广告位名称 */
	private String adName;
	/** 素材Id */
	private Integer resourceId;
	/** 素材名称 */
	private String resourceName;
	/** 素材类型 */
	private Integer resourceType;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getPositionPackageType() {
		return positionPackageType;
	}
	public void setPositionPackageType(Integer positionPackageType) {
		this.positionPackageType = positionPackageType;
	}
	public Integer getAdId() {
		return adId;
	}
	public void setAdId(Integer adId) {
		this.adId = adId;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getResourceType() {
		return resourceType;
	}
	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}
}
