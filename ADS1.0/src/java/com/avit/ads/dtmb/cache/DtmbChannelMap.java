package com.avit.ads.dtmb.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.avit.ads.dtmb.bean.DChannelinfo;

public class DtmbChannelMap {
	private static List<DChannelinfo> dtmbChannelMap = new ArrayList<DChannelinfo>();
	/** 
	 * 读写锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();
	
	public static List<DChannelinfo> getDtmbChannelMap() {
		return dtmbChannelMap;
	}
	public static void setDtmbChannelMap(List<DChannelinfo> dtmbChannelMap) {
		lock.lock();
		DtmbChannelMap.dtmbChannelMap = dtmbChannelMap;
		lock.unlock();
	}
	
}
