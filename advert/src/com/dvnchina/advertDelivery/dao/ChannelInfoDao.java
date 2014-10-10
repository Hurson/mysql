package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.model.ReleaseArea;

public interface ChannelInfoDao {
	
	/**
	 * 通过id 获得对应结果集
	 */
	public ChannelInfo getChannelInfoById(Integer id);
	
	/**
	 * 删除投放信息管理表单记录
	 * 
	 */
	public int deleteChannelInfo(ChannelInfo channelInfo);
	
	

	/**
	 * 查询记录总数
	 */
	
	public int getChannelInfoCount(ChannelInfo channelInfo,String state);
	
	/**
	 * 查询总的结果集
	 */
	
	public List<ChannelInfo> listChannelInfoMgr(ChannelInfo channelInfo,int x,int y,String state);

}
