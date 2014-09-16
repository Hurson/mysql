package com.avit.ads.util.bean;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AdSiteInterface entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_SITE_INTERFACE", schema = "ADS")
public class AdSiteInterface implements java.io.Serializable {

	// Fields

	private String adSiteCode;
	private BigDecimal interfaceId;

	// Constructors

	/** default constructor */
	public AdSiteInterface() {
	}

	/** minimal constructor */
	public AdSiteInterface(String adSiteCode) {
		this.adSiteCode = adSiteCode;
	}

	/** full constructor */
	public AdSiteInterface(String adSiteCode, BigDecimal interfaceId) {
		this.adSiteCode = adSiteCode;
		this.interfaceId = interfaceId;
	}

	// Property accessors
	@Id
	@Column(name = "AD_SITE_CODE", unique = true, nullable = false, length = 100)
	public String getAdSiteCode() {
		return this.adSiteCode;
	}

	public void setAdSiteCode(String adSiteCode) {
		this.adSiteCode = adSiteCode;
	}

	@Column(name = "INTERFACE_ID", precision = 22, scale = 0)
	public BigDecimal getInterfaceId() {
		return this.interfaceId;
	}

	public void setInterfaceId(BigDecimal interfaceId) {
		this.interfaceId = interfaceId;
	}

}