package com.avit.ads.pushads.task.bean;

import java.io.Serializable;
import java.sql.Time;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_system_maintain")
@WebService
@SOAPBinding(style=Style.RPC)
public class SystemMaintainBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer activeHour;
	private Integer actionCode;
	private Time sendTime;
	private Integer duration;
	private String cycle;
	private String AreaCodes;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="ActiveHour")
	public Integer getActiveHour() {
		return activeHour;
	}
	public void setActiveHour(Integer activeHour) {
		this.activeHour = activeHour;
	}
	@Column(name="ActionCode")
	public Integer getActionCode() {
		return actionCode;
	}
	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}
	@Column(name="SendTime")
	public Time getSendTime() {
		return sendTime;
	}
	public void setSendTime(Time sendTime) {
		this.sendTime = sendTime;
	}
	@Column(name="Duration")
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	@Column(name="Cycle")
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	@Column(name="AreaCodes")
	public String getAreaCodes() {
		return AreaCodes;
	}
	public void setAreaCodes(String areaCodes) {
		AreaCodes = areaCodes;
	}
	

}
