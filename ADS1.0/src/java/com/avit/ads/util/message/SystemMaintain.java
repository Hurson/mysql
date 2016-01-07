package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="systemMaintain")
public class SystemMaintain {
	

	@XmlAttribute(name="activeHour", required = true)
	private Integer activeHour;
	
	@XmlAttribute(name="actionCode", required = true)
	private Integer actionCode;

	public Integer getActiveHour() {
		return activeHour;
	}

	public void setActiveHour(Integer activeHour) {
		this.activeHour = activeHour;
	}

	public Integer getActionCode() {
		return actionCode;
	}

	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}

	
	
	
}
