package com.avit.ads.requestads.bean.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "InsertedAdList")
public class InsertedAdList {
	
	/** 待插入的广告信息，包含1到多个*/
	@XmlElement(name="insertedAd")
	private List<InsertedAd> lstInsertedAd;

	public List<InsertedAd> getLstInsertedAd() {
		return lstInsertedAd;
	}

	public void setLstInsertedAd(List<InsertedAd> lstInsertedAd) {
		this.lstInsertedAd = lstInsertedAd;
	}
	
}
