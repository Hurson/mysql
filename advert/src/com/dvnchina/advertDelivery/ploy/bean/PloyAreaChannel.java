package com.dvnchina.advertDelivery.ploy.bean;

import java.util.List;


/**
 * 投放区域管理
 * @author Weicl
 *
 */
public class PloyAreaChannel {

	private static final long serialVersionUID = -4879127837142232709L;
	
	/**
	 * 投放区域CODE
	 */
	
	private String areaCode;
	private int areaId;
	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	/**
	 * 投放区域名称
	 */
	private String areaName;
	
	/**
	 * 父级区域编码
	 */
	private String patentCode;
	private List<PloyChannel> channels;
	// 字符串格式保存的channel，以逗号隔开CCTV,CCTV2
	private String strChannels;
	private String strChannelIds;
	public String getStrChannelIds() {
		return strChannelIds;
	}

	public void setStrChannelIds(String strChannelIds) {
		this.strChannelIds = strChannelIds;
	}

	public String getStrChannels() {
		return strChannels;
	}

	public void setStrChannels(String strChannels) {
		this.strChannels = strChannels;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPatentCode() {
		return patentCode;
	}

	public void setPatentCode(String patentCode) {
		this.patentCode = patentCode;
	}

	public List<PloyChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<PloyChannel> channels) {
		this.channels = channels;
	} 
	
}
