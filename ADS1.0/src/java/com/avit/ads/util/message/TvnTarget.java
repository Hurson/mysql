package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="tvn_target")
public class TvnTarget {
	@XmlAttribute(name="serviceID", required = true)
	private String serviceID;
	@XmlAttribute(name="tvnType", required = true)
	private String tvnType;
	@XmlAttribute(name="tvn", required = true)
	private String tvn;
	@XmlAttribute(name="caIndustryType", required = true)
	private String caIndustryType;
	@XmlAttribute(name="caUserLevel", required = true)
	private String caUserLevel;
	
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String getTvnType() {
		return tvnType;
	}
	public void setTvnType(String tvnType) {
		this.tvnType = tvnType;
	}
	public String getTvn() {
		return tvn;
	}
	public void setTvn(String tvn) {
		this.tvn = tvn;
	}
	public String getCaIndustryType() {
		return caIndustryType;
	}
	public void setCaIndustryType(String caIndustryType) {
		this.caIndustryType = caIndustryType;
	}
	public String getCaUserLevel() {
		return caUserLevel;
	}
	public void setCaUserLevel(String caUserLevel) {
		this.caUserLevel = caUserLevel;
	}
	
	
}
