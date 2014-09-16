package com.avit.ads.pushads.ui.bean.ui;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UiNitNetworkid entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ui_nit_networkid", catalog = "ui")
public class UiNitNetworkid implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer networkId;

	// Constructors

	/** default constructor */
	public UiNitNetworkid() {
	}

	/** full constructor */
	public UiNitNetworkid(Integer networkId) {
		this.networkId = networkId;
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

}