package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="subtitle")
public class Subtitle {
	@XmlElement(name="subtitleInfo")
	private ChannelSubtitleInfo subtitleInfo;
	@XmlElement(name="subtitleSet")
	private SubtitleContent subtileContent;
	public ChannelSubtitleInfo getSubtitleInfo() {
		return subtitleInfo;
	}
	public void setSubtitleInfo(ChannelSubtitleInfo subtitleInfo) {
		this.subtitleInfo = subtitleInfo;
	}
	public SubtitleContent getSubtileContent() {
		return subtileContent;
	}
	public void setSubtileContent(SubtitleContent subtileContent) {
		this.subtileContent = subtileContent;
	}
	

}
