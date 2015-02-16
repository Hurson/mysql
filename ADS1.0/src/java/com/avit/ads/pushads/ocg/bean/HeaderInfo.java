package com.avit.ads.pushads.ocg.bean;

import java.io.Serializable;

public class HeaderInfo implements Serializable{

	private static final long serialVersionUID = 5824771771460460433L;
	
	/**
	 * 消息类型：1表示流化，其他暂时保留
	 */
	private String messageType;
	/**
	 * 流ID，用于表示每个流，不同的流采用不同的ID，由外部规范约束
	 */
	private String streamId;
	/**
	 * 控制指令，1表示播发，0表示停止
	 */
	private String controlCommand;
	/**
	 * 播发码率
	 */
	private String bitrate;
	/**
	 * 组播地址
	 */
	private String sendAddress;
	/**
	 * 组播端口
	 */
	private String sendPort;
	/**
	 * 是否预约，0表示无预约，1表示预约，后面的effect_time字段有效
	 */
	private String reserveTime;
	/**
	 * 预约生效时间
	 */
	private String effectTime;
	/**
	 * 预约失效时间
	 */
	private String outdateTime;
	
	public HeaderInfo(){
		
	}
	
	public HeaderInfo(String streamId, String bitrate, String sendAddress, String sendPort){
		this.messageType = "1";
		this.streamId = streamId;
		this.controlCommand = "1";
		this.bitrate = bitrate;
		this.sendAddress = sendAddress;
		this.sendPort = sendPort;
		this.reserveTime = "0";
		this.effectTime = "0";
		this.outdateTime = "0";
	}
	
	
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getStreamId() {
		return streamId;
	}
	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}
	public String getControlCommand() {
		return controlCommand;
	}
	public void setControlCommand(String controlCommand) {
		this.controlCommand = controlCommand;
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
	public String getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	public String getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(String effectTime) {
		this.effectTime = effectTime;
	}
	public String getOutdateTime() {
		return outdateTime;
	}
	public void setOutdateTime(String outdateTime) {
		this.outdateTime = outdateTime;
	}

}
