package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="channelInfo")
public class Channelrecomendurl {

	@XmlAttribute(name="serviceID", required = true)
	private String serviceID;
	@XmlAttribute(name="tvnType", required = true)
	private String tVNType;
	@XmlAttribute(name="tvn", required = true)
	private String tVN;
	@XmlAttribute(name="caIndustryType", required = true)
	private String cAIndustryType;
	@XmlAttribute(name="caUserLevel", required = true)
	private String cAUserLevel;

	@XmlAttribute(name="url", required = true)
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

	public String getuRL() {
		return uRL;
	}
	public void setuRL(String uRL) {
		this.uRL = uRL;
	}
	
	
	
}
