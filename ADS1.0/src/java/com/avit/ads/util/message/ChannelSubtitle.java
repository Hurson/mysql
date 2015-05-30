package com.avit.ads.util.message;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="channelSubtitle")
public class ChannelSubtitle {

	@XmlAttribute(name="ts_id")
	private String tsId;
	
	@XmlElement(name="channelSubtitleElement")
	private List<ChannelSubtitleElement> channelSubtitleElemList;
	
	public String getTsId() {
		return tsId;
	}
	public void setTsId(String tsId) {
		this.tsId = tsId;
	}
	public List<ChannelSubtitleElement> getChannelSubtitleElemList() {
		return channelSubtitleElemList;
	}
	public void setChannelSubtitleElemList(List<ChannelSubtitleElement> channelSubtitleElemList) {
		this.channelSubtitleElemList = channelSubtitleElemList;
	}

}
