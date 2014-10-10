package com.avit.ads.requestads.bean.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "AdInsertRequestXmlBean")
public class AdInsertRequestXmlBean {

	public AdInsertRequestXmlBean() {
		this.definition = "HD";
	}

	/** 用户标识，每个请求只包含一个 */
	@XmlElement(name = "tvnId")
	private String tvnId;

	/** 会话标识，每个请求只包含一个 */
	@XmlElement(name = "token")
	private String token;

	/** 待播放的媒体asset标识，每个请求只包含一个 */
	@XmlElement(name = "assetId")
	private String assetId;

	// /** 用户所属的区域代码*/
	// @XmlElement(name="locationId")
	// private String locationId;

	// /** 用于标识用户*/
	// @XmlElement(name="userType")
	// private int userType;
	//
	/** 待播放的媒体属性 */
	@XmlElement(name = "AssetInfo")
	private AssetInfo assetInfo;

	/** 产品ID */
	@XmlElement(name = "productId")
	private String productId;

	// /** 用户行业类别 */
	// @XmlElement(name="userType2")
	// private String userType2;

	/** 视频的清晰度 */
	@XmlElement(name = "deviceType")
	private String definition;

	// /** 广告位编码*/
	// @XmlElement(name="adCode")
	// private String adCode;

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getTvnId() {
		return tvnId;
	}

	public void setTvnId(String tvnId) {
		this.tvnId = tvnId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public AssetInfo getAssetInfo() {
		return assetInfo;
	}

	public void setAssetInfo(AssetInfo assetInfo) {
		this.assetInfo = assetInfo;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
