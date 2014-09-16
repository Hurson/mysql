package com.avit.ads.requestads.bean.request;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "AdReportList")
public class AdReportList {

	/** 广告投放情况报告列表*/
	@XmlElement(name="AdStatus")
	private List<AdStatus> adStatus;

	public List<AdStatus> getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(List<AdStatus> adStatus) {
		this.adStatus = adStatus;
	} 
}
