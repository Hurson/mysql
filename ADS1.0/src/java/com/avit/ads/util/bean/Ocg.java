package com.avit.ads.util.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Ocg")
public class Ocg {	
	
	@XmlAttribute(name="streamId", required = true)
	private String streamId;
	@XmlAttribute(name="bitrate", required = true)
	private String bitrate;
	@XmlAttribute(name="sendAddress", required = true)
	private String sendAddress;
	@XmlAttribute(name="sendPort", required = true)
	private String sendPort;
	
	public String getStreamId() {
		return streamId;
	}
	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}
	public String getBitrate() {
		return bitrate;
	}
	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}
	public String getSendPort() {
		return sendPort;
	}
	public void setSendPort(String sendPort) {
		this.sendPort = sendPort;
	}
	
	
	
}
