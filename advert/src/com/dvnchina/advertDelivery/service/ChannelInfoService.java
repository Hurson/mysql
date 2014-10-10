package com.dvnchina.advertDelivery.service;

import java.util.List;

import com.dvnchina.advertDelivery.model.ChannelInfo;

public interface ChannelInfoService {
	
	/**
	 * 删除记录
	 */
	public int deleteChannelInfoById(Integer id);
	
	/**
	 * 查询记录总数
	 */
	
	public int getChannelInfoCount(ChannelInfo channelInfo,String state);
	
	/**
	 * 查询总的结果集
	 */
	
	public List<ChannelInfo> listChannelInfoMgr(ChannelInfo channelInfo,int x,int y,String state);

}
