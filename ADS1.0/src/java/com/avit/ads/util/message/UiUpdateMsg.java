package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="sendUIMessage")
public class UiUpdateMsg {
	@XmlAttribute(name="updateType", required = true)
	private String updateType;
	@XmlAttribute(name="networkID", required = true)
	private String networkID;
	@XmlAttribute(name="tsID", required = true)
	private String tsID;
	@XmlAttribute(name="servicesID", required = true)
	private String servicesID;
	

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getNetworkID() {
		return networkID;
	}

	public void setNetworkID(String networkID) {
		this.networkID = networkID;
	}

	public String getTsID() {
		return tsID;
	}

	public void setTsID(String tsID) {
		this.tsID = tsID;
	}

	public String getServicesID() {
		return servicesID;
	}

	public void setServicesID(String servicesID) {
		this.servicesID = servicesID;
	}
	
	
	
}
