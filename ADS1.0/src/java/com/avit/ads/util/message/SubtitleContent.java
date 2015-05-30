package com.avit.ads.util.message;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="subtitleSet")
public class SubtitleContent {

	@XmlElement(name="subtitle")
	private List<SubtitlePart> subtitleList;

	public List<SubtitlePart> getSubtitleList() {
		return subtitleList;
	}

	public void setSubtitleList(List<SubtitlePart> subtitleList) {
		this.subtitleList = subtitleList;
	}
	
}
