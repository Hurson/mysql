package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
import java.util.List;
/**
 * 频道信息
 * @author lester
 *
 */
public class Channel implements Serializable {

	private static final long serialVersionUID = -3148599124814026870L;
	/**
	 * 频道主键id
	 */
	private Integer id;
	/**
	 * 频道名称
	 */
	private String channelName;
	/**
	 * 频道区域关系
	 */
	private List<ChannelLocationRelation> channelLocationRelationList;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public List<ChannelLocationRelation> getChannelLocationRelationList() {
		return channelLocationRelationList;
	}

	public void setChannelLocationRelationList(
			List<ChannelLocationRelation> channelLocationRelationList) {
		this.channelLocationRelationList = channelLocationRelationList;
	}
	
}
