package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="sendUNTMessage")
public class UNTMessage {

	@XmlAttribute(name="sendType", required = true)
	private String sendType;
	
	@XmlElement(name="weatherForecast")
	private Weatherforecast weatherforecast;
	
	@XmlElement(name="subtitle")
	private Subtitle subtitle;
	
	@XmlElement(name="adsConfig")
	private AdsConfigJs adsConfig;
	
	@XmlElement(name="adsImage")
	private AdsImage adsImage;
	
	@XmlElement(name="systemMaintain")
	private SystemMaintain systemMaintain;
	
	@XmlElement(name="channelSubtitle")
	private ChannelSubtitle channelSubtitle;
	
	@XmlElement(name="channelRecomend")
	private ChannelRecomend channelRecomend;
	
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


	public Subtitle getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(Subtitle subtitle) {
		this.subtitle = subtitle;
	}
	public AdsConfigJs getAdsConfig() {
		return adsConfig;
	}

	public void setAdsConfig(AdsConfigJs adsConfig) {
		this.adsConfig = adsConfig;
	}
	public AdsImage getAdsImage() {
		return adsImage;
	}
	public void setAdsImage(AdsImage adsImage) {
		this.adsImage = adsImage;
	}
	public ChannelSubtitle getChannelSubtitle() {
		return channelSubtitle;
	}
	public void setChannelSubtitle(ChannelSubtitle channelSubtitle) {
		this.channelSubtitle = channelSubtitle;
	}
	public ChannelRecomend getChannelRecomend() {
		return channelRecomend;
	}
	public void setChannelRecomend(ChannelRecomend channelRecomend) {
		this.channelRecomend = channelRecomend;
	}
	
	
	
	
	
}
