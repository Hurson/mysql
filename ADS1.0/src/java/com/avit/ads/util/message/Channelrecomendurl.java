package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="channelrecomendurl")
public class Channelrecomendurl {

	@XmlAttribute(name="ServiceID", required = true)
	private String serviceID;
	@XmlAttribute(name="TVNType", required = true)
	private String tVNType;
	@XmlAttribute(name="TVN", required = true)
	private String tVN;
	@XmlAttribute(name="CAIndustryType", required = true)
	private String cAIndustryType;
	@XmlAttribute(name="CAUserLevel", required = true)
	private String cAUserLevel;
	@XmlAttribute(name="Type", required = true)
	private String type;
	@XmlAttribute(name="URL", required = true)
	private String uRL;
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String gettVNType() {
		return tVNType;
	}
	public void settVNType(String tVNType) {
		this.tVNType = tVNType;
	}
	public String gettVN() {
		return tVN;
	}
	public void settVN(String tVN) {
		this.tVN = tVN;
	}
	public String getcAIndustryType() {
		return cAIndustryType;
	}
	public void setcAIndustryType(String cAIndustryType) {
		this.cAIndustryType = cAIndustryType;
	}
	public String getcAUserLevel() {
		return cAUserLevel;
	}
	public void setcAUserLevel(String cAUserLevel) {
		this.cAUserLevel = cAUserLevel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getuRL() {
		return uRL;
	}
	public void setuRL(String uRL) {
		this.uRL = uRL;
	}
	
	
	
}
