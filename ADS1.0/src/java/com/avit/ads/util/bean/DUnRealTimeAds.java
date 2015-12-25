package com.avit.ads.util.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="DtmbUnRealTimeAds")
public class DUnRealTimeAds {
	
	@XmlAttribute(name="adsConfigFile")
	private String adsConfigFile;	
	@XmlAttribute(name="adsTempPath")
	private String adsTempPath;	
	
	@XmlAttribute(name="adsIframeHDTempPath")
	private String adsIframeHDTempPath;
	@XmlAttribute(name="adsIframeSDTempPath")
	private String adsIframeSDTempPath;
	
	@XmlAttribute(name="adsaudioHDTempPath")
	private String adsaudioHDTempPath;
	@XmlAttribute(name="adsaudioSDTempPath")
	private String adsaudioSDTempPath;
	@XmlAttribute(name="adsTargetPath")
	private String adsTargetPath;
	
	public String getAdsConfigFile() {
		return adsConfigFile;
	}
	public void setAdsConfigFile(String adsConfigFile) {
		this.adsConfigFile = adsConfigFile;
	}
	public String getAdsTempPath() {
		return adsTempPath;
	}
	public void setAdsTempPath(String adsTempPath) {
		this.adsTempPath = adsTempPath;
	}
	public String getAdsTargetPath() {
		return adsTargetPath;
	}
	public void setAdsTargetPath(String adsTargetPath) {
		this.adsTargetPath = adsTargetPath;
	}
	
	public String getAdsaudioHDTempPath() {
		return adsaudioHDTempPath;
	}
	public void setAdsaudioHDTempPath(String adsaudioHDTempPath) {
		this.adsaudioHDTempPath = adsaudioHDTempPath;
	}
	public String getAdsaudioSDTempPath() {
		return adsaudioSDTempPath;
	}
	public void setAdsaudioSDTempPath(String adsaudioSDTempPath) {
		this.adsaudioSDTempPath = adsaudioSDTempPath;
	}
	public String getAdsIframeHDTempPath() {
		return adsIframeHDTempPath;
	}
	public void setAdsIframeHDTempPath(String adsIframeHDTempPath) {
		this.adsIframeHDTempPath = adsIframeHDTempPath;
	}
	public String getAdsIframeSDTempPath() {
		return adsIframeSDTempPath;
	}
	public void setAdsIframeSDTempPath(String adsIframeSDTempPath) {
		this.adsIframeSDTempPath = adsIframeSDTempPath;
	}
}
