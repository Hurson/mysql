package com.avit.ads.util.message;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="channelSubtitle")
public class ChannelSubtitle {

	@XmlElement(name="channelSubtitleElement")
	private List<ChannelSubtitleElement> channelSubtitleElemList;
	
	
	public List<ChannelSubtitleElement> getChannelSubtitleElemList() {
		return channelSubtitleElemList;
	}
	public void setChannelSubtitleElemList(List<ChannelSubtitleElement> channelSubtitleElemList) {
		this.channelSubtitleElemList = channelSubtitleElemList;
	}

	
	
	
}
