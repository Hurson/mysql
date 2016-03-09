package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="subtitle")
public class Subtitle {
	
	@XmlAttribute(name="encryption")
	private String encryption = "0";
	
	@XmlAttribute(name="key")
	private String key = "";
	
	@XmlElement(name="subtitleInfo")
	private ChannelSubtitleInfo subtitleInfo;
	
	@XmlElement(name="subtitleSet")
	private SubtitleContent subtileContent;
	
	public String getEncryption() {
		return encryption;
	}
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
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
