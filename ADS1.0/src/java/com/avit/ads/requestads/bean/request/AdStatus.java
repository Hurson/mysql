package com.avit.ads.requestads.bean.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "AdStatus")
public class AdStatus {

	/** 请求会话中投放的广告序号*/
	@XmlElement(name="seq")
	private long seq; 
	
	/** 广告投放状态(小于10的整数)：0：成功播放；1：未播放；2：播放部分中断；3..9保留*/
	@XmlElement(name="status")
	private int status; 
	
	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
}
