package com.avit.ads.pushads.task.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_MULTICAST_INFO")
public class TMulticastInfo implements java.io.Serializable {

	private static final long serialVersionUID = -7898702313939696203L;
	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 38, scale = 0)
	private Long Id;
	@Column(name = "AREA_CODE")
	private String areaCode;
	@Column(name = "TS_ID")
	private String tsId;
	@Column(name = "MULTICAST_IP")
	private String multicastIp;
	@Column(name = "MULTICAST_PORT")
	private String multicastPort;
	@Column(name = "MULTICAST_BITRATE")
	private String multicastBitrate;
	@Column(name = "STREAM_ID")
	private String streamId;
	@Column(name= "FLAG")
	private String flag;
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getTsId() {
		return tsId;
	}
	public void setTsId(String tsId) {
		this.tsId = tsId;
	}
	public String getMulticastIp() {
		return multicastIp;
	}
	public void setMulticastIp(String multicastIp) {
		this.multicastIp = multicastIp;
	}
	public String getMulticastPort() {
		return multicastPort;
	}
	public void setMulticastPort(String multicastPort) {
		this.multicastPort = multicastPort;
	}
	public String getMulticastBitrate() {
		return multicastBitrate;
	}
	public void setMulticastBitrate(String multicastBitrate) {
		this.multicastBitrate = multicastBitrate;
	}
	public String getStreamId() {
		return streamId;
	}
	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
		
}