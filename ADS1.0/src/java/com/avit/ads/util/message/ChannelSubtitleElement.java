package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="channelSubtitleElement")
public class ChannelSubtitleElement {
	
	@XmlElement(name="tvn_target", required = true)
	private TvnTarget tvnTarget;
	
	@XmlElement(name="subtitleInfo", required = true)
	private ChannelSubtitleInfo subtitleInfo;
	
	@XmlElement(name="subtitleSet", required = true)
	private SubtitleContent subtitleContent;

	public TvnTarget getTvnTarget() {
		return tvnTarget;
	}

	public void setTvnTarget(TvnTarget tvnTarget) {
		this.tvnTarget = tvnTarget;
	}

	public ChannelSubtitleInfo getSubtitleInfo() {
		return subtitleInfo;
	}

	public void setSubtitleInfo(ChannelSubtitleInfo subtitleInfo) {
		this.subtitleInfo = subtitleInfo;
	}

	public SubtitleContent getSubtitleContent() {
		return subtitleContent;
	}

	public void setSubtitleContent(SubtitleContent subtitleContent) {
		this.subtitleContent = subtitleContent;
	}

}
