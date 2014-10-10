package com.dvnchina.advertDelivery.channelGroup.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class ChannelGroupRef implements Serializable{

	private static final long serialVersionUID = 2474970398602385552L;

	/**
	 * id
	 */
	private long id;
	/**
	 * 频道id
	 */
	private Integer channelId;
	/**
	 * 频道组id
	 */
	private Integer  channelGroupId;
	
	


	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getChannelGroupId() {
		return channelGroupId;
	}
	public void setChannelGroupId(Integer channelGroupId) {
		this.channelGroupId = channelGroupId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
