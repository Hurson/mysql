package com.avit.ads.pushads.task.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.avit.ads.pushads.task.bean.SendAds;
import com.avit.ads.pushads.task.bean.TChannelinfo;
import com.avit.ads.pushads.task.bean.TLoopbackCategory;

public class LookbackCategoryMap {
	private static List<TLoopbackCategory> categoryMap = new ArrayList<TLoopbackCategory>();
	/** 
	 * 读写锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();
	public static List<TLoopbackCategory> getCategoryMap() {
		return categoryMap;
	}
	public static void setCategoryMap(List<TLoopbackCategory> channelMap) {
		lock.lock();
		LookbackCategoryMap.categoryMap = channelMap;
		lock.unlock();
	}
	
	
	
}
