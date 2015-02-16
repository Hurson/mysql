package com.avit.ads.util.message;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="channelRecomend")
public class ChannelRecomend {
	@XmlElement(name="channelRecomendElement")
	private List<ChannelRecomendElement> channelRecomendElemList;

	public List<ChannelRecomendElement> getChannelRecomendElemList() {
		return channelRecomendElemList;
	}

	public void setChannelRecomendElemList(
			List<ChannelRecomendElement> channelRecomendElemList) {
		this.channelRecomendElemList = channelRecomendElemList;
	}
	
}
