package com.avit.ads.pushads.task.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.avit.ads.pushads.task.bean.NvodMenuType;

public class NvodMenuTypeMap {
	private static List<NvodMenuType> menuTypeMap = new ArrayList<NvodMenuType>();
	/** 
	 * 读写锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();
	public static List<NvodMenuType> getMenuTypeMap() {
		return menuTypeMap;
	}
	public static void setMenuTypeMap(List<NvodMenuType> menuTypeMap) {
		lock.lock();
		NvodMenuTypeMap.menuTypeMap = menuTypeMap;
		lock.unlock();
	}
}
