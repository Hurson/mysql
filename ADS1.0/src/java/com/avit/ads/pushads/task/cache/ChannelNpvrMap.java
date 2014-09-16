package com.avit.ads.pushads.task.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.avit.ads.pushads.task.bean.SendAds;
import com.avit.ads.pushads.task.bean.TChannelinfo;
import com.avit.ads.pushads.task.bean.TChannelinfoNpvr;

public class ChannelNpvrMap {
	private static List<TChannelinfoNpvr> channelMap = new ArrayList<TChannelinfoNpvr>();
	/** 
	 * 读写锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();
	public static List<TChannelinfoNpvr> getChannelMap() {
		return channelMap;
	}
	public static void setChannelMap(List<TChannelinfoNpvr> channelMap) {
		lock.lock();
		ChannelNpvrMap.channelMap = channelMap;
		lock.unlock();
	}
}
