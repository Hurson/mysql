package com.avit.ads.requestads.bean.request;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "AssetInfo")
public class AssetInfo {
	
	/** 待播放的媒体名称 */
	@XmlElement(name="assetName")
	private String assetName; 
	
	/** 带播放的媒体长度(单位，秒)*/
	@XmlElement(name="assetDuration")
	private long assetDuration; 
	
	/** 媒体描述或媒体关键字列表*/
	@XmlElement(name="AssetDescription")
	private List<String> assetDescription; 
	
	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public long getAssetDuration() {
		return assetDuration;
	}

	public void setAssetDuration(long assetDuration) {
		this.assetDuration = assetDuration;
	}

	public List<String> getAssetDescription() {
		return assetDescription;
	}

	public void setAssetDescription(List<String> assetDescription) {
		this.assetDescription = assetDescription;
	}

}
