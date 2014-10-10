package com.avit.ads.requestads.cache.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TNoAdPloyView entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_no_ad_ploy_view")
public class TNoAdPloyView implements java.io.Serializable {

	// Fields

	private Long id;
	private String ployname;
	private Long positionid;
	private String positionCode;
	
	private String tvn;
	private Date startDate;
	private Date endDate;

	// Constructors

	/** default constructor */
	public TNoAdPloyView() {
	}

	/** full constructor */
	public TNoAdPloyView(String ployname, Long positionid, String tvn,
			Date startDate, Date endDate) {
		this.ployname = ployname;
		this.positionid = positionid;
		this.tvn = tvn;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PLOYNAME")
	public String getPloyname() {
		return this.ployname;
	}

	public void setPloyname(String ployname) {
		this.ployname = ployname;
	}

	@Column(name = "POSITIONID", precision = 10, scale = 0)
	public Long getPositionid() {
		return this.positionid;
	}
	public void setPositionid(Long positionid) {
		this.positionid = positionid;
	}

	@Column(name = "POSITION_CODE", precision = 10, scale = 0)
	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	@Column(name = "TVN")
	public String getTvn() {
		return this.tvn;
	}

	public void setTvn(String tvn) {
		this.tvn = tvn;
	}

	@Column(name = "START_DATE", length = 19)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE", length = 19)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}