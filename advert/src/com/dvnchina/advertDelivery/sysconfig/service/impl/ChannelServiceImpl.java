package com.dvnchina.advertDelivery.sysconfig.service.impl;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;
import com.dvnchina.advertDelivery.sysconfig.dao.ChannelDao;
import com.dvnchina.advertDelivery.sysconfig.service.ChannelService;

public class ChannelServiceImpl implements ChannelService{
	
	private ChannelDao  channelDao;
	
	/**
	 * 分页查询频道信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryChannelList(ChannelInfo channel,int pageNo, int pageSize){
		return channelDao.queryChannelList(channel,pageNo, pageSize);
	}
	
	/**
	 * 根据频道ID获取频道信息
	 * @param channelId
	 * @return
	 */
	public ChannelInfo getChannelById(Integer channelId){
		return channelDao.getChannelById(channelId);
	}
	
	/**
	 * 修改频道信息
	 * @param channel
	 */
	public void updateChannel(ChannelInfo channel){
		channelDao.updateChannel(channel);
	}
	
	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}
	
	

}
