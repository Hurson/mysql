package com.avit.ads.pushads.task.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="system_maintain")
public class UntSystemMaintain {
	private Integer id;
	private Integer activeHour;
	private Integer actionCode;
	
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
	

}
