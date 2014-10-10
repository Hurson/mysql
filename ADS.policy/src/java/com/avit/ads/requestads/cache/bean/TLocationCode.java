package com.avit.ads.requestads.cache.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TLocationCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_location_code")
public class TLocationCode implements java.io.Serializable {

	// Fields

	private Long locationcode;
	private String locationname;
	private Long parentlocation;
	private String locationtype;

	// Constructors

	/** default constructor */
	public TLocationCode() {
	}

	/** minimal constructor */
	public TLocationCode(Long locationcode) {
		this.locationcode = locationcode;
	}

	/** full constructor */
	public TLocationCode(Long locationcode, String locationname,
			Long parentlocation, String locationtype) {
		this.locationcode = locationcode;
		this.locationname = locationname;
		this.parentlocation = parentlocation;
		this.locationtype = locationtype;
	}

	// Property accessors
	@Id
	@Column(name = "LOCATIONCODE", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getLocationcode() {
		return this.locationcode;
	}

	public void setLocationcode(Long locationcode) {
		this.locationcode = locationcode;
	}

	@Column(name = "LOCATIONNAME", length = 200)
	public String getLocationname() {
		return this.locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	@Column(name = "PARENTLOCATION", precision = 12, scale = 0)
	public Long getParentlocation() {
		return this.parentlocation;
	}

	public void setParentlocation(Long parentlocation) {
		this.parentlocation = parentlocation;
	}

	@Column(name = "LOCATIONTYPE", length = 2)
	public String getLocationtype() {
		return this.locationtype;
	}

	public void setLocationtype(String locationtype) {
		this.locationtype = locationtype;
	}

}