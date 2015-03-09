package com.dvnchina.advertDelivery.sysconfig.bean;

import java.io.Serializable;

public class AreaOCG implements Serializable{

	private static final long serialVersionUID = 8612288750485033517L;
	
	private Integer id;
	
	private String ip;
	
	private String port;
	
	private String user;
	
	private String pwd;
	
	private String areaCode;
	
	private String areaName;
	
	private String version;
	
	public AreaOCG(){
		
	}
	public AreaOCG(AreaOCG ocg, String areaName){
		this.id = ocg.id;
		this.ip = ocg.ip;
		this.port = ocg.port;
		this.user = ocg.user;
		this.pwd = ocg.pwd;
		this.areaCode = ocg.areaCode;
		this.version = ocg.version;
		this.areaName = areaName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	

}
