package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="sendFile")
public class OcgPlayMsg {
	@XmlAttribute(name="sendPath", required = true)
	private String sendPath;
	@XmlAttribute(name="sendType", required = true)
	private String sendType;
	@XmlAttribute(name="adsType", required = true)
	private String adsType;
	
	public String getSendPath() {
		return sendPath;
	}
	public void setSendPath(String sendPath) {
		this.sendPath = sendPath;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getAdsType() {
		return adsType;
	}
	public void setAdsType(String adsType) {
		this.adsType = adsType;
	}
	
	
}
