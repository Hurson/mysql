package com.dvnchina.advertDelivery.ploy.bean;

/**
 * TProductinfo entity. @author MyEclipse Persistence Tools
 */

public class TProductinfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String productId;
	private String productName;
	private String productDesc;
	private String price;
	private String billingModelName;
	private String billingModelId;
	private String billingModelType;
	private String spId;
	private String isPackage;
	private String posterUrl;
	private String type;
	private String bizId;
	private String bizDesc;
	private String createTime;
	private String modifyTime;
	private String state;

	// Constructors

	/** default constructor */
	public TProductinfo() {
	}

	/** minimal constructor */
	public TProductinfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TProductinfo(Long id, String productId, String productName,
			String productDesc, String price, String billingModelName,
			String billingModelId, String billingModelType, String spId,
			String isPackage, String posterUrl, String type, String bizId,
			String bizDesc, String createTime, String modifyTime, String state) {
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.productDesc = productDesc;
		this.price = price;
		this.billingModelName = billingModelName;
		this.billingModelId = billingModelId;
		this.billingModelType = billingModelType;
		this.spId = spId;
		this.isPackage = isPackage;
		this.posterUrl = posterUrl;
		this.type = type;
		this.bizId = bizId;
		this.bizDesc = bizDesc;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.state = state;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return this.productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBillingModelName() {
		return this.billingModelName;
	}

	public void setBillingModelName(String billingModelName) {
		this.billingModelName = billingModelName;
	}

	public String getBillingModelId() {
		return this.billingModelId;
	}

	public void setBillingModelId(String billingModelId) {
		this.billingModelId = billingModelId;
	}

	public String getBillingModelType() {
		return this.billingModelType;
	}

	public void setBillingModelType(String billingModelType) {
		this.billingModelType = billingModelType;
	}

	public String getSpId() {
		return this.spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getIsPackage() {
		return this.isPackage;
	}

	public void setIsPackage(String isPackage) {
		this.isPackage = isPackage;
	}

	public String getPosterUrl() {
		return this.posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBizId() {
		return this.bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getBizDesc() {
		return this.bizDesc;
	}

	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}