package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="sendUNTMessage")
public class UNTMessage {

	@XmlAttribute(name="sendType", required = true)
	private String sendType;
	
	@XmlAttribute(name="systemMaintain")
	private SystemMaintain systemMaintain;
	@XmlAttribute(name="weatherforecast")
	private Weatherforecast weatherforecast;
	@XmlAttribute(name="channelrecomendurl")
	private Channelrecomendurl channelrecomendurl;
	@XmlAttribute(name="msubtitleInfo")
	private MsubtitleInfo msubtitleInfo;
	@XmlAttribute(name="adsConfig")
	private AdsConfig adsConfig;
	@XmlAttribute(name="adsImage")
	private AdsImage adsImage;
	
	
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public SystemMaintain getSystemMaintain() {
		return systemMaintain;
	}
	public void setSystemMaintain(SystemMaintain systemMaintain) {
		this.systemMaintain = systemMaintain;
	}
	public Weatherforecast getWeatherforecast() {
		return weatherforecast;
	}
	public void setWeatherforecast(Weatherforecast weatherforecast) {
		this.weatherforecast = weatherforecast;
	}
	public Channelrecomendurl getChannelrecomendurl() {
		return channelrecomendurl;
	}
	public void setChannelrecomendurl(Channelrecomendurl channelrecomendurl) {
		this.channelrecomendurl = channelrecomendurl;
	}
	public MsubtitleInfo getMsubtitleInfo() {
		return msubtitleInfo;
	}
	public void setMsubtitleInfo(MsubtitleInfo msubtitleInfo) {
		this.msubtitleInfo = msubtitleInfo;
	}
	public AdsConfig getAdsConfig() {
		return adsConfig;
	}
	public void setAdsConfig(AdsConfig adsConfig) {
		this.adsConfig = adsConfig;
	}
	public AdsImage getAdsImage() {
		return adsImage;
	}
	public void setAdsImage(AdsImage adsImage) {
		this.adsImage = adsImage;
	}
	
	
	
	
	
}
