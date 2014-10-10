package com.avit.ads.requestads.bean.cache;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_NO_AD_PLOY")
public class BanAdvertising {
	
	public BanAdvertising() {
	}
	
	public BanAdvertising(Integer id, Integer positionid, String tvn){
		this.id = id;
		this.positionid = positionid;
		this.tvn = tvn;
	}
	@Id
	@Column(name = "ID")
	private Integer id;	
	@Column(name = "PLOYNAME")
	private String ployname;
	
	@Column(name = "POSITIONID")
	private Integer positionid;
	
	@Column(name = "TVN")
	private String tvn;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE")
	private Date endDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPloyname() {
		return ployname;
	}

	public void setPloyname(String ployname) {
		this.ployname = ployname;
	}

	public Integer getPositionid() {
		return positionid;
	}

	public void setPositionid(Integer positionid) {
		this.positionid = positionid;
	}

	public String getTvn() {
		return tvn;
	}

	public void setTvn(String tvn) {
		this.tvn = tvn;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
}
