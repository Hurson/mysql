package com.dvnchina.advertDelivery.bean.marketingRule;

import java.util.List;

/**
 * 添加营销规则地区数据的封装
 * */
public class AreaBean {
	
	private String id;
	/** 频道 */
	private List<ChannelBean> channel;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ChannelBean> getChannel() {
		return channel;
	}
	public void setChannel(List<ChannelBean> channel) {
		this.channel = channel;
	}
	
}
