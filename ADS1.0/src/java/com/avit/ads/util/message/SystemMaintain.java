package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="systemMaintain")
public class SystemMaintain {

	@XmlAttribute(name="activeHour", required = true)
	private String activeHour;
	
	@XmlAttribute(name="actionCode", required = true)
	private String actionCode;

	public String getActiveHour() {
		return activeHour;
	}

	public void setActiveHour(String activeHour) {
		this.activeHour = activeHour;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	
	
	
}
