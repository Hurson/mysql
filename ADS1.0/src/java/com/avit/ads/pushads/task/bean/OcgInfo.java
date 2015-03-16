package com.avit.ads.pushads.task.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "T_OCGINFO")
public class OcgInfo implements java.io.Serializable {

	private static final long serialVersionUID = 8641894356904132813L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Integer id;
	@Column(name = "IP")
	private String ip;
	@Column(name = "PORT")
	private String port;
	@Column(name = "USER")
	private String user;
	@Column(name = "PWD")
	private String pwd;
	@Column(name = "AREACODE")
	private String areaCode;
	
	@Column(name = "MULTICAST_IP")
	private String multicastIp;
	@Column(name = "MULTICAST_PORT")
	private String multicastPort;
	@Column(name = "MULTICAST_BITRATE")
	private String multicastBitrate;
	
	
	
	
	
	public OcgInfo() {
	}
	
	public OcgInfo(Integer id, String ip, String port, String user, String pwd,
			String areaCode) {
		super();
		this.id = id;
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
		this.areaCode = areaCode;
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

	public String getMulticastIp() {
		return multicastIp;
	}

	public void setMulticastIp(String multicastIp) {
		this.multicastIp = multicastIp;
	}

	public String getMulticastPort() {
		return multicastPort;
	}

	public void setMulticastPort(String multicastPort) {
		this.multicastPort = multicastPort;
	}

	public String getMulticastBitrate() {
		return multicastBitrate;
	}

	public void setMulticastBitrate(String multicastBitrate) {
		this.multicastBitrate = multicastBitrate;
	}
	
	
	
	
	
}
