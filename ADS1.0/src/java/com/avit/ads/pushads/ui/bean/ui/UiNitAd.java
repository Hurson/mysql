package com.avit.ads.pushads.ui.bean.ui;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UiNitAd entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ui_nit_ad", catalog = "ui")
public class UiNitAd implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer networkId;
	private Integer tsid;
	private Integer serviceId;

	// Constructors

	/** default constructor */
	public UiNitAd() {
	}

	/** full constructor */
	public UiNitAd(Integer networkId, Integer tsid, Integer serviceId) {
		this.networkId = networkId;
		this.tsid = tsid;
		this.serviceId = serviceId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NetworkID", nullable = false)
	public Integer getNetworkId() {
		return this.networkId;
	}

	public void setNetworkId(Integer networkId) {
		this.networkId = networkId;
	}

	@Column(name = "TSID", nullable = false)
	public Integer getTsid() {
		return this.tsid;
	}

	public void setTsid(Integer tsid) {
		this.tsid = tsid;
	}

	@Column(name = "ServiceID", nullable = false)
	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

}