package com.avit.ads.requestads.bean.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "AdInsertRespContent")
public class AdInsertRespContent {
	
	/** 用户标识*/
	@XmlElement(name="tvnId")
	private String tvnId; 
	
	/** 会话标识*/
	@XmlElement(name="token")
	private String token; 
	
	/** 待插入的广告列表*/
	@XmlElement(name="InsertedAdList")
	private InsertedAdList insertedAdList;

	public String getTvnId() {
		return tvnId;
	}

	public void setTvnId(String tvnId) {
		this.tvnId = tvnId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public InsertedAdList getInsertedAdList() {
		return insertedAdList;
	}

	public void setInsertedAdList(InsertedAdList insertedAdList) {
		this.insertedAdList = insertedAdList;
	} 

}
