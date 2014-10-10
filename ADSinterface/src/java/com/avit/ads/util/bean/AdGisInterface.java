package com.avit.ads.util.bean;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AdGisInterface entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_GIS_INTERFACE", schema = "ADS")
public class AdGisInterface implements java.io.Serializable {

	// Fields

	private BigDecimal id;
	private String name;
	private BigDecimal seconds;

	// Constructors

	/** default constructor */
	public AdGisInterface() {
	}

	/** minimal constructor */
	public AdGisInterface(BigDecimal id) {
		this.id = id;
	}

	/** full constructor */
	public AdGisInterface(BigDecimal id, String name, BigDecimal seconds) {
		this.id = id;
		this.name = name;
		this.seconds = seconds;
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

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SECONDS", precision = 22, scale = 0)
	public BigDecimal getSeconds() {
		return this.seconds;
	}

	public void setSeconds(BigDecimal seconds) {
		this.seconds = seconds;
	}

}