package com.avit.ads.requestads.bean.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "AdStatusReportReq")
public class AdStatusReportReqXmlBean {

	/** 用户标识，每个请求只包含一个*/
	@XmlElement(name="tvnId")
	private String tvnId; 
	
	/** 广告发布接口中的会话ID*/
	@XmlElement(name="token")
	private String token; 
	
	/** 广告投放情况报告列表*/
	@XmlElement(name="AdReportList")
	private AdReportList adReportList;
	@XmlElement(name="subTitleStaus")
	private int subTitleStaus;
	@XmlElement(name="rightTopPicStatus")
	private int rightTopPicStatus;
	@XmlElement(name="pausePic")
	private int pausePic;
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

	public AdReportList getAdReportList() {
		return adReportList;
	}

	public void setAdReportList(AdReportList adReportList) {
		this.adReportList = adReportList;
	}

	public int getSubTitleStaus() {
		return subTitleStaus;
	}

	public void setSubTitleStaus(int subTitleStaus) {
		this.subTitleStaus = subTitleStaus;
	}

	public int getRightTopPicStatus() {
		return rightTopPicStatus;
	}

	public void setRightTopPicStatus(int rightTopPicStatus) {
		this.rightTopPicStatus = rightTopPicStatus;
	}

	public int getPausePic() {
		return pausePic;
	}

	public void setPausePic(int pausePic) {
		this.pausePic = pausePic;
	}

}
