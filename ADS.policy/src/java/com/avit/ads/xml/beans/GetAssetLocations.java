/**
 * Copyright (c) AVIT LTD (2012). All Rights Reserved.
 * Welcome to <a href="www.avit.com.cn">www.avit.com.cn</a>
 */
package com.avit.ads.xml.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description:资产定位查询请求消息
 * @author lizhiwei
 * @Date: 2012-5-25
 * @Version: 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="GetAssetLocations")
public class GetAssetLocations {
	@XmlAttribute(name="assetId", required = true)
	private String assetId;
	@XmlAttribute(name="providerID", required = true)
	private String providerID;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getProviderID() {
		return providerID;
	}

	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}
	
}
