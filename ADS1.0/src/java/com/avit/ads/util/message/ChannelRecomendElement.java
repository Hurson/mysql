package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="channelRecomendElement")
public class ChannelRecomendElement {
	@XmlElement(name="tvn_target", required = true)
	private TvnTarget tvnTarget;
	
	@XmlElement(name="ssu_location", required = true)
	private SsuLocation ssuLocation;

	public TvnTarget getTvnTarget() {
		return tvnTarget;
	}

	public void setTvnTarget(TvnTarget tvnTarget) {
		this.tvnTarget = tvnTarget;
	}

	public SsuLocation getSsuLocation() {
		return ssuLocation;
	}

	public void setSsuLocation(SsuLocation ssuLocation) {
		this.ssuLocation = ssuLocation;
	}
	
}
