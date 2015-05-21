package com.avit.ads.util.message;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="subtitle")
public class Subtitle {
	@XmlElement(name="subtitleInfo")
	private List<MsubtitleInfo> subtitleInfoList;

	public List<MsubtitleInfo> getSubtitleInfoList() {
		return subtitleInfoList;
	}

	public void setSubtitleInfoList(List<MsubtitleInfo> subtitleInfoList) {
		this.subtitleInfoList = subtitleInfoList;
	}
	
}
