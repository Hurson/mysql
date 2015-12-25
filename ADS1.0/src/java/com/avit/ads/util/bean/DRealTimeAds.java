package com.avit.ads.util.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="DtmbRealTimeAds")
public class DRealTimeAds {
	
		
	@XmlAttribute(name="adsConfigFile")
	private String adsConfigFile;	
	@XmlAttribute(name="adsTempPath")
	private String adsTempPath;	
	@XmlAttribute(name="adsTargetPath")
	private String adsTargetPath;	
	
	@XmlAttribute(name="adsTempConfigPath")
	private String adsTempConfigPath;	
	@XmlAttribute(name="adsTargetConfigPath")
	private String adsTargetConfigPath;	
	@XmlAttribute(name="untUrl")
	private String untUrl;
	
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
	
	public String getUntUrl() {
		return untUrl;
	}
	public void setUntUrl(String untUrl) {
		this.untUrl = untUrl;
	}
	public String getAdsTempConfigPath() {
		return adsTempConfigPath;
	}
	public void setAdsTempConfigPath(String adsTempConfigPath) {
		this.adsTempConfigPath = adsTempConfigPath;
	}
	public String getAdsTargetConfigPath() {
		return adsTargetConfigPath;
	}
	public void setAdsTargetConfigPath(String adsTargetConfigPath) {
		this.adsTargetConfigPath = adsTargetConfigPath;
	}
	
}
