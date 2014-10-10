package com.dvnchina.advertDelivery.sysconfig.service;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;

public interface ChannelService {
	
	/**
	 * 分页查询频道信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryChannelList(ChannelInfo channel,int pageNo, int pageSize);
	
	/**
	 * 根据频道ID获取频道信息
	 * @param channelId
	 * @return
	 */
	public ChannelInfo getChannelById(Integer channelId);
	
	/**
	 * 修改频道信息
	 * @param channel
	 */
	public void updateChannel(ChannelInfo channel);

}
