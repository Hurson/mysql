package com.avit.ads.pushads.task.bean;

public class AdsConfigElement {
	//key=adsTypeCode:areaCode:channelCode
	private String adsTypeCode;
	private String areaCode;
	private String channelCode;
	private String hdPath;
	private String sdPath;
	public AdsConfigElement(String adsTypeCode,String areaCode,String channelCode,String hdPath,String sdPath)
	{
		this.adsTypeCode=adsTypeCode;
		this.areaCode=areaCode;
		this.channelCode=channelCode;
		this.hdPath=hdPath;
		this.sdPath=sdPath;
	}
	public String getAdsTypeCode() {
		return adsTypeCode;
	}
	public void setAdsTypeCode(String adsTypeCode) {
		this.adsTypeCode = adsTypeCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getHdPath() {
		return hdPath;
	}
	public void setHdPath(String hdPath) {
		this.hdPath = hdPath;
	}
	public String getSdPath() {
		return sdPath;
	}
	public void setSdPath(String sdPath) {
		this.sdPath = sdPath;
	}
	
}
