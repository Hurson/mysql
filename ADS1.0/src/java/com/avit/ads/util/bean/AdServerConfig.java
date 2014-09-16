package com.avit.ads.util.bean;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AdServerConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_SERVER_CONFIG", schema = "ADS")
public class AdServerConfig implements java.io.Serializable {

	// Fields

	private BigDecimal id;
	private BigDecimal interfaceId;
	private String areaCode;
	private String serverIp;
	private BigDecimal serverPort;
	private String serverPath;
	private String user;
	private String password;

	// Constructors

	/** default constructor */
	public AdServerConfig() {
	}

	/** minimal constructor */
	public AdServerConfig(BigDecimal id) {
		this.id = id;
	}

	/** full constructor */
	public AdServerConfig(BigDecimal id, BigDecimal interfaceId,
			String areaCode, String serverIp, BigDecimal serverPort,
			String serverPath, String user, String password) {
		this.id = id;
		this.interfaceId = interfaceId;
		this.areaCode = areaCode;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.serverPath = serverPath;
		this.user = user;
		this.password = password;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "INTERFACE_ID", precision = 22, scale = 0)
	public BigDecimal getInterfaceId() {
		return this.interfaceId;
	}

	public void setInterfaceId(BigDecimal interfaceId) {
		this.interfaceId = interfaceId;
	}

	@Column(name = "AREA_CODE", length = 50)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "SERVER_IP", length = 30)
	public String getServerIp() {
		return this.serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	@Column(name = "SERVER_PORT", precision = 22, scale = 0)
	public BigDecimal getServerPort() {
		return this.serverPort;
	}

	public void setServerPort(BigDecimal serverPort) {
		this.serverPort = serverPort;
	}

	@Column(name = "SERVER_PATH", length = 100)
	public String getServerPath() {
		return this.serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	@Column(name = "USER", length = 100)
	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Column(name = "PASSWORD", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}