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
	
	@XmlElement(name="subtitle", required = true)
	private Subtitle subtitle;

	public TvnTarget getTvnTarget() {
		return tvnTarget;
	}

	public void setTvnTarget(TvnTarget tvnTarget) {
		this.tvnTarget = tvnTarget;
	}

	public Subtitle getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(Subtitle subtitle) {
		this.subtitle = subtitle;
	}

	
	
	
	
}
