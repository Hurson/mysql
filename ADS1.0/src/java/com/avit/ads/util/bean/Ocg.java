package com.avit.ads.util.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Ocg")
public class Ocg {
	@XmlAttribute(name="ip", required = true)
	private String ip;
	@XmlAttribute(name="port", required = true)
	private String port;
	@XmlAttribute(name="user", required = true)
	private String user;
	@XmlAttribute(name="pwd", required = true)
	private String pwd;
	
	@XmlAttribute(name="areaCode", required = true)
	private String areaCode;
	@XmlAttribute(name="nid", required = true)
	private String nid;
	@XmlAttribute(name="untUrl", required = true)
	private String untUrl;

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
	

	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getUntUrl() {
		return untUrl;
	}
	public void setUntUrl(String untUrl) {
		this.untUrl = untUrl;
	}
	
	
}
