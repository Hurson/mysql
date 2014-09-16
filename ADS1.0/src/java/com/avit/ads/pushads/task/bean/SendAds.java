package com.avit.ads.pushads.task.bean;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * 投放播出单发送bean，记录当前正在投放的播出单.
 */
public class SendAds {
	
	/** 播出单ID. */
	private String adsId;
	
	/** 广告位编码. */
	private String adsTypeCode;
	
	/** 播出单特征值. */
	private String adsIdentification;
	
	/**
	 * 广告资源文件名 ,如多个文件","分隔:abc.jpg,epg.jpg 
    */
	private String adsFile;
	
	/** 播出单结束时间. */
	private Date   adsEndtime;

	/** 频道ID. */
	private String   channleId;
	
	/** 区域编码. */
	private String   areaCode;
	
	
	public String getAdsId() {
		return adsId;
	}

	public void setAdsId(String adsId) {
		this.adsId = adsId;
	}

	public String getAdsTypeCode() {
		return adsTypeCode;
	}

	public void setAdsTypeCode(String adsTypeCode) {
		this.adsTypeCode = adsTypeCode;
	}

	public String getAdsIdentification() {
		return adsIdentification;
	}

	public void setAdsIdentification(String adsIdentification) {
		this.adsIdentification = adsIdentification;
	}

	public String getAdsFile() {
		return adsFile;
	}

	public void setAdsFile(String adsFile) {
		this.adsFile = adsFile;
	}

	public Date getAdsEndtime() {
		return adsEndtime;
	}

	public void setAdsEndtime(Date adsEndtime) {
		this.adsEndtime = adsEndtime;
	}
	
}
