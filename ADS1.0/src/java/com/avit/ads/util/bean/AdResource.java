package com.avit.ads.util.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="AdResource")
public class AdResource {
	@XmlAttribute(name="ip", required = true)
	private String ip;
	@XmlAttribute(name="port", required = true)
	private String port;
	@XmlAttribute(name="user", required = true)
	private String user;
	@XmlAttribute(name="pwd", required = true)
	private String pwd;
	@XmlAttribute(name="adsResourcePath", required = true)
	private String adsResourcePath;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getAdsResourcePath() {
		return adsResourcePath;
	}
	public void setAdsResourcePath(String adsResourcePath) {
		this.adsResourcePath = adsResourcePath;
	}	
	
}
