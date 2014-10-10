package com.dvnchina.advertDelivery.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.dao.ChannelInfoDao;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.service.ChannelInfoService;

public class ChannelInfoServiceImpl implements ChannelInfoService {

	private static Logger logger = Logger.getLogger(ChannelInfoServiceImpl.class);
	
	private ChannelInfoDao channelInfoDao;
	
	
	@Override
	public int deleteChannelInfoById(Integer id) {
		int count = 0;
		if(id != null){
			ChannelInfo channelInfo = channelInfoDao.getChannelInfoById(id);
			channelInfoDao.deleteChannelInfo(channelInfo);
			count = 1;
		}
		return count;
	}
	
	
	@Override
	public int getChannelInfoCount(ChannelInfo channelInfo, String state) {
		return channelInfoDao.getChannelInfoCount(channelInfo, state);
	}

	@Override
	public List<ChannelInfo> listChannelInfoMgr(ChannelInfo channelInfo, int x,int y, String state) {
		return channelInfoDao.listChannelInfoMgr(channelInfo, x, y, state);
	}

	public ChannelInfoDao getChannelInfoDao() {
		return channelInfoDao;
	}

	public void setChannelInfoDao(ChannelInfoDao channelInfoDao) {
		this.channelInfoDao = channelInfoDao;
	}

}
