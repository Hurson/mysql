package com.avit.ads.requestads.bean.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "InsertedAd")
public class InsertedAd {
	/** 待插入的广告序号*/
	@XmlElement(name="adSeq")
	private String adSeq; 
	
	/** 广告类型  1：视频 2：暂停图片 3：挂角 4：字幕*/
	@XmlElement(name="adType")
	private String adType; 
	
	/** 字幕 格式参照UNT字幕 */
	@XmlElement(name="adText")
	private String adText; 
	
	/** 待插入的广告在IP推流服务器的Url */
	@XmlElement(name="adUrl")
	private String adUrl; 
	
	/** 每个广告需要插入的时间点，该值在0和1之间，即整个影片的百分比数值 */
	@XmlElement(name="insertedTime")
	private String insertedTime; 
	
	/** 广告位编码*/
	@XmlElement(name="adCode")
	private String adCode;
	
	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	public String getAdSeq() {
		return adSeq;
	}

	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getAdText() {
		return adText;
	}

	public void setAdText(String adText) {
		this.adText = adText;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public String getInsertedTime() {
		return insertedTime;
	}

	public void setInsertedTime(String insertedTime) {
		this.insertedTime = insertedTime;
	}

}
