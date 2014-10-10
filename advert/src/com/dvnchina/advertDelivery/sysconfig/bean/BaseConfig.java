package com.dvnchina.advertDelivery.sysconfig.bean;

import java.io.Serializable;

public class BaseConfig implements Serializable {

	private static final long serialVersionUID = -3207135679920030191L;
	
	
	private String remindKey;
	
	
	private String remindValue;
	
	private Integer id;
	
	private String remindDesc;
	
	private String remindName;

	public String getRemindDesc() {
		return remindDesc;
	}

	public void setRemindDesc(String remindDesc) {
		this.remindDesc = remindDesc;
	}

	public String getRemindName() {
		return remindName;
	}

	public void setRemindName(String remindName) {
		this.remindName = remindName;
	}

	public String getRemindKey() {
		return remindKey;
	}

	public void setRemindKey(String remindKey) {
		this.remindKey = remindKey;
	}

	public String getRemindValue() {
		return remindValue;
	}

	public void setRemindValue(String remindValue) {
		this.remindValue = remindValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	
	
}
