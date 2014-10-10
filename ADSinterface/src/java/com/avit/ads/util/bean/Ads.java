package com.avit.ads.util.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Ads")
public class Ads {
	@XmlAttribute(name="adsCode", required = true)
	private String adsCode;	
	@XmlAttribute(name="position")
	private String position;	
	@XmlAttribute(name="defaultRes")
	private String defaultRes;	
	public String getAdsCode() {
		return adsCode;
	}
	public void setAdsCode(String adsCode) {
		this.adsCode = adsCode;
	}
	
	public String getDefaultRes() {
		return defaultRes;
	}
	public void setDefaultRes(String defaultRes) {
		this.defaultRes = defaultRes;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	
}
