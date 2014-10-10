/**
 * Copyright (c) AVIT LTD (2012). All Rights Reserved.
 * Welcome to <a href="www.avit.com.cn">www.avit.com.cn</a>
 */
package com.avit.ads.xml.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Description:
 * @author lizhiwei
 * @Date: 2012-7-30
 * @Version: 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="TransferStatus")
public class TransferStatus {
	@XmlAttribute(name="providerID")
	private String providerID;
	@XmlAttribute(name="assetID")
	private String assetID;
	@XmlAttribute(name="volumeName")
	private String volumeName;
	@XmlAttribute(name="destination")
	private String destination;
	@XmlAttribute(name="state")
	private String state;
	@XmlAttribute(name="percentComplete")
	private String percentComplete;
	@XmlAttribute(name="contentSize")
	private String contentSize;
	@XmlAttribute(name="md5Checksum")
	private String md5Checksum;
	@XmlAttribute(name="md5DateTime")
	private String md5DateTime;
	@XmlAttribute(name="bitrate")
	private String bitrate;
	@XmlAttribute(name="destination_type")
	private String destinationType;
	public String getProviderID() {
		return providerID;
	}
	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}
	public String getAssetID() {
		return assetID;
	}
	public void setAssetID(String assetID) {
		this.assetID = assetID;
	}
	public String getVolumeName() {
		return volumeName;
	}
	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPercentComplete() {
		return percentComplete;
	}
	public void setPercentComplete(String percentComplete) {
		this.percentComplete = percentComplete;
	}
	public String getContentSize() {
		return contentSize;
	}
	public void setContentSize(String contentSize) {
		this.contentSize = contentSize;
	}
	public String getMd5Checksum() {
		return md5Checksum;
	}
	public void setMd5Checksum(String md5Checksum) {
		this.md5Checksum = md5Checksum;
	}
	public String getMd5DateTime() {
		return md5DateTime;
	}
	public void setMd5DateTime(String md5DateTime) {
		this.md5DateTime = md5DateTime;
	}
	public String getBitrate() {
		return bitrate;
	}
	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}
	public String getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}
	
}
