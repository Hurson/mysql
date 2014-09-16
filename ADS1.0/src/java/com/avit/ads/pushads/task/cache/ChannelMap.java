package com.avit.ads.pushads.task.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.avit.ads.pushads.task.bean.SendAds;
import com.avit.ads.pushads.task.bean.TChannelinfo;

public class ChannelMap {
	private static List<TChannelinfo> channelMap = new ArrayList<TChannelinfo>();
	/** 
	 * 读写锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();
	public static List<TChannelinfo> getChannelMap() {
		return channelMap;
	}
	public static void setChannelMap(List<TChannelinfo> channelMap) {
		lock.lock();
		ChannelMap.channelMap = channelMap;
		lock.unlock();
	}
}
