package com.dvnchina.advertDelivery.model;

import java.util.Date;
import java.util.Map;

/**
 * 封装产品的相关信息
 * */
public class ProductInfo {

	private Integer id;
	private String productId;
	private String productName;
	private String price;
	private String productDesc;
	private char isPackage;
	private String posterUrl;
	private String type;
	private String bizId;
	private String bizDesc;
	private String billingModelName;
	private String billingModelId;
	private String billingModelType;
	private String spId;
	private Date createTime;
	private Date modifyTime;
	private char state;
	
	private Date startTime;
	private Date endTime;

	/**
	 * 产品与资源的关系映射
	 * */
	private Map<Integer, AssetProduct> assetMap;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public char getIsPackage() {
		return isPackage;
	}

	public void setIsPackage(char isPackage) {
		this.isPackage = isPackage;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getBizDesc() {
		return bizDesc;
	}

	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}

	public String getBillingModelName() {
		return billingModelName;
	}

	public void setBillingModelName(String billingModelName) {
		this.billingModelName = billingModelName;
	}

	public String getBillingModelId() {
		return billingModelId;
	}

	public void setBillingModelId(String billingModelId) {
		this.billingModelId = billingModelId;
	}

	public String getBillingModelType() {
		return billingModelType;
	}

	public void setBillingModelType(String billingModelType) {
		this.billingModelType = billingModelType;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
	}

	public Map<Integer, AssetProduct> getAssetMap() {
		return assetMap;
	}

	public void setAssetMap(Map<Integer, AssetProduct> assetMap) {
		this.assetMap = assetMap;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		String code = this.productId;
		if(this.modifyTime!=null){
			code+= this.modifyTime.getTime();
		}
		return code.hashCode();
	}
}
