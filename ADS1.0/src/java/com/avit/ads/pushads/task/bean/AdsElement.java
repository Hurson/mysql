package com.avit.ads.pushads.task.bean;

public class AdsElement {
	//key=adsTypeCode:areaCode:channelCode
	private String adsTypeCode;
	private String areaCode;
	private String paramCode;
	private String filepath;
	private Integer priority;
	private Integer playListId;
	public AdsElement(String adsTypeCode, String areaCode, String paramCode,
			String filepath) {
		super();
		this.adsTypeCode = adsTypeCode;
		this.areaCode = areaCode;
		this.paramCode = paramCode;
		this.filepath = filepath;
	}
	public AdsElement(Integer playListId, String adsTypeCode, String areaCode, String paramCode,
			String filepath) {
		super();
		this.playListId = playListId;
		this.adsTypeCode = adsTypeCode;
		this.areaCode = areaCode;
		this.paramCode = paramCode;
		this.filepath = filepath;
	}
	public AdsElement(String adsTypeCode, String areaCode, String paramCode,
			String filepath, Integer priority) {
		super();
		this.adsTypeCode = adsTypeCode;
		this.areaCode = areaCode;
		this.paramCode = paramCode;
		this.filepath = filepath;
		this.priority = priority;
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
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Integer getPriority() {
		if(null == priority){
			return 0;
		}
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getPlayListId() {
		return playListId;
	}
	public void setPlayListId(Integer playListId) {
		this.playListId = playListId;
	}


	
}
